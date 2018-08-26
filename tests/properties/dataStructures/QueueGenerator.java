package dataStructures;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class QueueGenerator extends Generator<LinkedQueue> {
    public QueueGenerator() {
        super(LinkedQueue.class);
    }

    @Override public LinkedQueue generate(SourceOfRandomness r, GenerationStatus status) {
        int queueSize = r.nextInt(10);
        LinkedQueue result = new LinkedQueue(queueSize);
        for (int i=0; i<queueSize; ++i) {
            result.put(i);
        }

        return result;
    }

    /**
     * We'll make a smaller queue by dropping the first item from the larger queue,
     * and then copying all the remaining elements into the new, smaller queue.
     * @param r source of randomness
     * @param largeQueue the queue we're trying to shrink
     * @return the same queue with the first item removed
     */
    @Override public List<LinkedQueue> doShrink(SourceOfRandomness r, LinkedQueue largeQueue) {
        if (largeQueue.isEmpty()) {
            return Collections.emptyList();
        }

        LinkedQueue copyQueue = new LinkedQueue();
        while (!largeQueue.isEmpty()) {
            copyQueue.put(largeQueue.remove());
        }

        largeQueue.put(copyQueue.remove());

        LinkedQueue smallerQueue = new LinkedQueue();
        while (!copyQueue.isEmpty()) {
            Object element = copyQueue.remove();
            largeQueue.put(element);
            smallerQueue.put(element);
        }

        return Collections.singletonList(smallerQueue);
    }

    @Override public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(((LinkedQueue) value).size());
    }
}
