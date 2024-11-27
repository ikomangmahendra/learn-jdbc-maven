import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManagement {

    private static final String URL = "jdbc:postgresql://localhost:5432/retail";
    private static final String USER = "ais";
    private static final String PASSWORD = "mysecretpassword";

    public Optional<Product> insert(Product product) throws SQLException {
        // validate data
        ProductValidator.validator(product);

        String sql = "insert into product (name, price, category, stock, status) values (?, ?, ?, ?, ?)";
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            mappingObjToStatement(product, statement);

            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getLong(1));
                        return Optional.of(product);
                    } else {
                        throw new SQLException("Failed to retrieve product ID");
                    }
                }
            } else {
                throw new SQLException("Failed to insert product");
            }
        }
    }

    public Product update(Product product) throws SQLException {
        ProductValidator.validator(product);

        String sql = "update product set name=?, price=?, category=?, stock=?, status=? where id=?";
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            mappingObjToStatement(product, statement);

            statement.setLong(6, product.getId());

            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                return product;
            } else {
                throw new SQLException("Failed to update product");
            }
        }
    }

    public void deleteProductById(long id) throws SQLException {
        String sql = "delete from product where id=?";
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<Product> findById(long id) throws SQLException {
        String sql = "select * from product where id=?";
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = mappingResultSetToObj(resultSet);
                return Optional.of(product);
            }
        }

        return Optional.empty();
    }

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD);) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from product");
            while (resultSet.next()) {
                Product product = mappingResultSetToObj(resultSet);
                products.add(product);
            }
        }

        return products;
    }

    private Product mappingResultSetToObj(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setCategory(resultSet.getString("category"));
        product.setStock(resultSet.getInt("stock"));

        ProductStatus status = ProductStatus.valueOf(resultSet.getString("status"));
        product.setStatus(status);

        return product;
    }

    private void mappingObjToStatement(Product product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getName());
        statement.setBigDecimal(2, product.getPrice());
        statement.setString(3, product.getCategory());
        statement.setInt(4, product.getStock());
        statement.setString(5, product.getStatus().name());
    }
}
