import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Node<Item> head;
	private int size;
	
	public RandomizedQueue() {
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		Node<Item> node = new Node<>(item);
		node.setNext(head);
		head = node;
		size++;
	}
	
	private Item random(boolean delete) {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		int n = StdRandom.uniform(0,size);
		if (n == 0) {
			Item item = head.getItem();
			if (delete) {
				head = head.getNext();
				size--;
			}
			return item;
			
		}
		Node<Item> pre = head;
		Node<Item> cur = head.getNext();
		for (int i =1; i < n; i++) {
			pre = cur;
			cur = cur.getNext();
		}
		Item item = cur.getItem();
		if (delete) {
			pre.setNext(cur.getNext());
			size--;
		}
		return item;
	}
	
	public Item dequeue() {
		return random(true);
	}
	
	public Item sample() {
		return random(false);
	}
	
	@Override
	public Iterator<Item> iterator(){
		return new Iterator<Item>() {
			int left = size;
			boolean[] done = new boolean[left];
			
			public boolean hasNext() {
				return left > 0;
			}
			
			public Item next() {
				if (left == 0) {
					throw new IllegalArgumentException();
				}
				int n = StdRandom.uniform(size);
				while (done[n]) {
					n = StdRandom.uniform(size);
				}
				done[n] = true;
				left--;
				Node<Item> cur = head;
				for (int i = 0; i < n; i++) {
					cur = cur.getNext();
				}
				return cur.getItem();
			}
		};
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<>();
		rq.enqueue(1);
		rq.enqueue(2);
		rq.enqueue(3);
		System.out.println(rq.isEmpty());
		System.out.println(rq.size());
		System.out.println(rq.sample());
		System.out.println(rq.dequeue());
		Iterator<Integer> it = rq.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

}


