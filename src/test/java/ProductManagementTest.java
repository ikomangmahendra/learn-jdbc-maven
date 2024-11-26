import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagementTest {

    @Test
    void getAll() throws SQLException {
        ProductManagement productManagement = new ProductManagement();
        List<Product> results = productManagement.getAll();
        Assertions.assertNotNull(results);
    }
}