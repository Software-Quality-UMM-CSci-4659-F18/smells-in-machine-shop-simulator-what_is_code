/** This exception is thrown when an input
 * exception occurs.  It is used by MyInputStream
 * to convert checked exceptions such as
 * FileNotFoundException and EOFException
 * into unchecked runtime exceptions. */

package exceptions;

public class MyInputException extends RuntimeException {
    private static final long serialVersionUID = -7921978290082262832L;

    public MyInputException() {
        super();
    }

    public MyInputException(String message) {
        super(message);
    }
}
