public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String field) {
        super("Invalid data field: " + field);
    }
}
