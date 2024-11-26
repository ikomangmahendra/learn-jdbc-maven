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
        String sql = "insert into product (name, price, category, stock) values (?, ?, ?, ?)";
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setString(3, product.getCategory());
            statement.setInt(4, product.getStock());

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

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD);) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from product");
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setCategory(resultSet.getString("category"));
                product.setStock(resultSet.getInt("stock"));

                products.add(product);
            }
        }

        return products;
    }
}
