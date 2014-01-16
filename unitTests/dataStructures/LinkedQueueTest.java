package dataStructures;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedQueueTest {

    @Test
    public void testIsEmpty() {
        LinkedQueue queue = new LinkedQueue();
        assertTrue(queue.isEmpty());
        queue.put("An item");
        assertFalse(queue.isEmpty());
        queue.remove();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testQueueOperations() {
        LinkedQueue queue = new LinkedQueue();
        assertNull(queue.getFrontElement());
        assertNull(queue.getRearElement());
        final String firstItem = "First item";
        final String secondItem = "Second item";
        queue.put(firstItem);
        assertEquals(firstItem, queue.getFrontElement());
        assertEquals(firstItem, queue.getRearElement());
        queue.put(secondItem);
        assertEquals(firstItem, queue.getFrontElement());
        assertEquals(secondItem, queue.getRearElement());
        assertEquals(firstItem, queue.remove());
        assertEquals(secondItem, queue.getFrontElement());
        assertEquals(secondItem, queue.getRearElement());
        assertEquals(secondItem, queue.remove());
        assertNull(queue.getFrontElement());
        assertNull(queue.getRearElement());
        assertNull(queue.remove());
    }

}
