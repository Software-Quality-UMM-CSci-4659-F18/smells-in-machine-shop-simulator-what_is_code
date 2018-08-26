package dataStructures;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChainNodeTest {

    @Test
    public final void testChainNode() {
        ChainNode node = new ChainNode();
        assertNull(node.element);
        assertNull(node.next);
    }

    @Test
    public final void testChainNodeObject() {
        String string = "A test object";
        ChainNode node = new ChainNode(string);
        assertEquals(string, node.element);
        assertNull(node.next);
    }

    @Test
    public final void testChainNodeObjectNext() {
        String firstNodeString = "First node";
        String secondNodeString = "Second node";
        ChainNode firstNode = new ChainNode(firstNodeString);
        ChainNode secondNode = new ChainNode(secondNodeString, firstNode);
        assertEquals(secondNodeString, secondNode.element);
        assertEquals(firstNode, secondNode.next);
    }
}
