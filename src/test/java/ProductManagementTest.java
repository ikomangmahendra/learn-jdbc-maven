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

    @Test
    void test_update() throws SQLException {
        // prepare new data
        Product product = new Product();
        product.setName("iPhone 16");
        product.setCategory("Smartphone");
        product.setPrice(BigDecimal.valueOf(10.5));
        product.setStock(10);

        ProductManagement productManagement = new ProductManagement();
        product = productManagement.insert(product).orElseThrow();

        // update inserted data
        product.setName("IPhone 16");
        product.setCategory("Smartphone");
        product.setPrice(BigDecimal.valueOf(15));
        product.setStock(5);

        // execute
        Product updatedProduct = productManagement.update(product);

        // verification
        Assertions.assertEquals(product.getId(), updatedProduct.getId());
        Assertions.assertEquals(product.getName(), updatedProduct.getName());
        Assertions.assertEquals(product.getPrice(), updatedProduct.getPrice());
        Assertions.assertEquals(product.getCategory(), updatedProduct.getCategory());
        Assertions.assertEquals(product.getStock(), updatedProduct.getStock());
    }

    @Test
    void test_delete() throws SQLException {
        // prepare new data
        Product product = new Product();
        product.setName("iPhone 16");
        product.setCategory("Smartphone");
        product.setPrice(BigDecimal.valueOf(10.5));
        product.setStock(10);

        ProductManagement productManagement = new ProductManagement();
        product = productManagement.insert(product).orElseThrow();

        // execute
        productManagement.deleteProductById(product.getId());

        Optional<Product> optProduct = productManagement.findById(product.getId());
        Assertions.assertFalse(optProduct.isEmpty());
    }

    @Test
    void test_findById() throws SQLException {
        // prepare new data
        Product product = new Product();
        product.setName("iPhone 16");
        product.setCategory("Smartphone");
        product.setPrice(BigDecimal.valueOf(10.5));
        product.setStock(10);

        ProductManagement productManagement = new ProductManagement();
        product = productManagement.insert(product).orElseThrow();

        Product productFind = productManagement.findById(product.getId()).orElseThrow();

        Assertions.assertEquals(product.getId(), productFind.getId());
        Assertions.assertEquals(product.getName(), productFind.getName());
        Assertions.assertEquals(product.getPrice(), productFind.getPrice());
        Assertions.assertEquals(product.getCategory(), productFind.getCategory());
        Assertions.assertEquals(product.getStock(), productFind.getStock());
    }
}