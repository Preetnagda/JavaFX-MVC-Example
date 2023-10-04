package src.CustomExceptions;

/**
 * DuplicatePost Exception class is thrown when inserting a Post with existing ID.
 */

public class DuplicatePost extends Exception {
    public DuplicatePost(String errorMessage) {
        super(errorMessage);
    }
}
