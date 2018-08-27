package dataStructures;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeThat;

@RunWith(JUnitQuickcheck.class)
public class LinkedQueueProperties {

    @Property
    public void removingDecreasesSizeByOne(@From(QueueGenerator.class) LinkedQueue queue) {
        assumeThat(queue.isEmpty(), not(true));
        final int originalSize = queue.size();
        queue.remove();
        final int newSize = queue.size();
        assertEquals(originalSize - 1, newSize);
    }

    @Property
    public void addingIncreasesSizeByOne(@From(QueueGenerator.class) LinkedQueue queue) {
        final int newValue = 5896320;
        final int originalSize = queue.size();
        queue.put(newValue);
        final int newSize = queue.size();
        assertEquals(originalSize + 1, newSize);
    }

    @Property
    public void allElementsAreInts(@From(QueueGenerator.class) LinkedQueue queue) {
        while (!queue.isEmpty()) {
            Object value = queue.remove();
            assertEquals(value.getClass(), Integer.class);
        }
    }

    @Property
    public void canRemoveNumElementsEqualToSize(@From(QueueGenerator.class) LinkedQueue queue) {
        final int size = queue.size();
        for (int i=0; i<size; ++i) {
            assertNotNull(queue.remove());
        }
        assertNull(queue.remove());
    }
}
