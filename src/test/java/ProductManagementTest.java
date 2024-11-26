import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagementTest {

    @Test
    void test_getAll() throws SQLException {
        ProductManagement productManagement = new ProductManagement();
        List<Product> results = productManagement.getAll();
        Assertions.assertNotNull(results);
    }

    @Test
    void test_insert() throws SQLException {
        // prepare data
        Product product = new Product();
        product.setName("iPhone 16");
        product.setCategory("Smartphone");
        product.setPrice(BigDecimal.valueOf(10.5));
        product.setStock(10);

        // execute
        ProductManagement productManagement = new ProductManagement();
        Optional<Product> optProduct = productManagement.insert(product);

        // verification
        Assertions.assertTrue(optProduct.isPresent());
        Product createdProduct = optProduct.get();
        Assertions.assertEquals(product.getName(), createdProduct.getName());
        Assertions.assertEquals(product.getPrice(), createdProduct.getPrice());
        Assertions.assertEquals(product.getCategory(), createdProduct.getCategory());
        Assertions.assertEquals(product.getStock(), createdProduct.getStock());
    }
}