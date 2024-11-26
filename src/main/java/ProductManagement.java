import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductManagement {

    private static final String URL = "jdbc:postgresql://localhost:5432/retail";
    private static final String USER = "ais";
    private static final String PASSWORD = "mysecretpassword";

    /*public Product insert(Product product) throws SQLException {
        String sql = "insert into product (name, price, category, stock) values (?, ?, ?, ?)";
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD);) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.get);
            statement.setInt(2, price);
            statement.setString(3, category);
            statement.setInt(4, stock);

            var result = statement.executeUpdate();
            System.out.println(result);
        }
    }
*/
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
