import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
   private Node<Item> head;
   private Node<Item> tail;
   private int size;
   
   public Deque() {
	   
   }
   
   public boolean isEmpty() {
	   return size == 0;
   }
   
   public int size() {
	   return size;
   }
   
   public void addFirst(Item item) {
	   if (item == null) {
		   throw new IllegalArgumentException();
	   }
	   Node<Item> node = new Node<>(item);
	   node.setNext(head);
	   if (size == 0) {
		   tail = node;
	   }
	   head = node;
	   size++;
   }
   
   public void addLast(Item item) {
	   if (size == 0) {
		   addFirst(item);
	   }else {
		   if(item == null) {
			   throw new IllegalArgumentException();
		   }
	   }
	   Node<Item> node = new Node<>(item);
	   tail.setNext(node);
	   tail = node;
	   if (size == 0) {
		   head = tail;
	   }
	   size++;
   }
   
   public Item removeFirst() {
	   if (isEmpty()) {
		   throw new NoSuchElementException();
	   }
	   Item item = head.getItem();
	   head = head.getNext();
	   size--;
	   if (size == 0) {
		   tail = head;
	   }
	   return item;
   }
   
   public Item removeLast() {
	   if (isEmpty()) {
		   throw new NoSuchElementException();
	   }
	   if (size == 1) {
		   removeFirst();
		   }
	   Node<Item> cur = head;
	   while (cur.getNext() != tail) {
		   cur = cur.getNext();
	   }
	   Item item = tail.getItem();
	   tail = cur;
	   tail.setNext(null);
	   size--;
	   return item;
   }
   
   @Override
   public Iterator<Item> iterator(){
	   return new Iterator<Item>() {
           private Node<Item> cur = head;

           @Override
           public boolean hasNext() {
               return cur != null;
           }

           @Override
           public Item next() {
               if (cur == null) {
                   throw new NoSuchElementException();
               }
               Item item = cur.getItem();
               cur = cur.getNext();
               return item;
           }
       };
   }
   
   public static void main(String[] args) {
	   Deque<Integer> dq = new Deque<>();
	   dq.addFirst(0);
       System.out.println(dq.removeFirst());
       dq.addFirst(1);
       System.out.println(dq.removeLast());
       dq.addLast(2);
       System.out.println(dq.removeFirst());
       dq.addLast(3);
       System.out.println(dq.removeLast());
       System.out.println(dq.isEmpty());
       dq.addFirst(200);
       dq.addFirst(100);
       dq.addLast(300);
       dq.addLast(400);
       System.out.println(dq.size());
       Iterator<Integer> it = dq.iterator();
       while (it.hasNext()) {
           System.out.println(it.next());

       }
   }
}

class Node<Item> {
	private final Item item;
	private Node<Item> next;
	
	public Node(Item item) {
		this.item = item;
		next = null;
	}
	
	public void setNext(Node<Item> next) {
		this.next = next;
	}
	
	public Item getItem() {
		return item;
	}
	
	public Node<Item> getNext(){
		return next;
	}
}

