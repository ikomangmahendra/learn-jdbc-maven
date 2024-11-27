import java.math.BigDecimal;

public class ProductValidator {

    public static void validator(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidDataException("Product name");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidDataException("Product price");
        }

        if (product.getStock() == null || product.getStock() < 0) {
            throw new InvalidDataException("Stock");
        }
    }
}
