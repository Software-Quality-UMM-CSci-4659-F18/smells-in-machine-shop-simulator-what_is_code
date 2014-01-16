package dataStructures;

import org.junit.Test;
import static org.junit.Assert.*;

public class LinkedQueueAcceptanceTest {
    
    /**
     * A simple acceptance test based on the one little test that
     * Sahni provided with the code.
     */
    @Test
    public void sahniTest() {
        LinkedQueue q = new LinkedQueue(3);
        // add a few elements
        q.put(new Integer(1));
        q.put(new Integer(2));
        q.put(new Integer(3));
        q.put(new Integer(4));

        assertEquals(4, q.getRearElement());
        assertEquals(1, q.getFrontElement());
        assertEquals(1, q.remove());
        assertEquals(4, q.getRearElement());
        assertEquals(2, q.getFrontElement());
        assertEquals(2, q.remove());
        assertEquals(4, q.getRearElement());
        assertEquals(3, q.getFrontElement());
        assertEquals(3, q.remove());
        assertEquals(4, q.getRearElement());
        assertEquals(4, q.getFrontElement());
        assertEquals(4, q.remove());
        
        assertTrue(q.isEmpty());
    }
    
    @Test
    public void emptyQueueTests() {
        LinkedQueue q = new LinkedQueue();
        assertTrue(q.isEmpty());
        assertNull(q.getFrontElement());
        assertNull(q.getRearElement());
        assertNull(q.remove());
    }
}
