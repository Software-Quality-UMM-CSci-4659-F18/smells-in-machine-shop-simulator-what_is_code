package exceptions;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyInputExceptionTest {

    @Test
    public final void testMyInputException() {
        MyInputException exception = new MyInputException();
        assertNull(exception.getMessage());
    }

    @Test
    public final void testMyInputExceptionString() {
        String message = "A test message";
        MyInputException exception = new MyInputException(message);
        assertEquals(message, exception.getMessage());
    }

}
