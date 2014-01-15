

/** a linked queue class */

package dataStructures;


public class LinkedQueue implements Queue
{
   // data members
   protected ChainNode front;
   protected ChainNode rear;

   // constructors
   /** create an empty queue */
   public LinkedQueue(int initialCapacity)
   {
       // the default initial value of front is null
   }

   public LinkedQueue()
      {this(0);}

   // methods
   /** @return true iff queue is empty */
   public boolean isEmpty()
       {return front == null;}


   /** @return the element at the front of the queue
     * @return null if the queue is empty */
   public Object getFrontElement()
   {
      if (isEmpty())
         return null;
      else
         return front.element;
   }

   /** @return the element at the rear of the queue
     * @return null if the queue is empty */
   public Object getRearElement()
   {
      if (isEmpty())
         return null;
      else
         return rear.element;
   }

   /** insert theElement at the rear of the queue */
   public void put(Object theElement)
   {
      // create a node for theElement
      ChainNode p = new ChainNode(theElement, null);

      // append p to the chain
      if (front == null)
         front = p;                 // empty queue
      else 
         rear.next = p;             // nonempty queue
      rear = p;
   }

   /** remove an element from the front of the queue
     * @return removed element
     * @return null if the queue is empty */
   public Object remove()
   {
      if (isEmpty())
         return null;
      Object frontElement = front.element;
      front = front.next;
      if (isEmpty())
         rear = null;  // enable garbage collection
      return frontElement;
   }
   
   /** test program */
   public static void main(String [] args)
   {  
      int x;
      LinkedQueue q = new LinkedQueue(3);
      // add a few elements
      q.put(new Integer(1));
      q.put(new Integer(2));
      q.put(new Integer(3));
      q.put(new Integer(4));


      // delete all elements
      while (!q.isEmpty())
      {
         System.out.println("Rear element is " + q.getRearElement());
         System.out.println("Front element is " + q.getFrontElement());
         System.out.println("Removed the element " + q.remove());
      }
   }  
}
