package src.CustomExceptions;

/**
 * InvalidDataType exception is thrown when the data from input does not
 * meet the expected data type or format.
 */

public class InvalidInputDataType extends Exception {
    public InvalidInputDataType(String errorMessage) {
        super(errorMessage);
    }
}
