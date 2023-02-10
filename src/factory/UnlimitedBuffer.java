package factory;

import java.util.LinkedList;
import java.util.Queue;

public class UnlimitedBuffer {
	
	private static int buffer = 0;
	private static Queue<Product> bufferQueue = new LinkedList<Product>();
	private static int currentId = 0;



	public synchronized void addToBuffer(Product element) {
		bufferQueue.offer(element);
		buffer++;
		notify();
	}
	
	public synchronized Product exctractFromBuffer(){
		if (buffer < 1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Product element = bufferQueue.poll();
		if (element.getId() != currentId) {
			bufferQueue.offer(element);
		} else {
			currentId++;
			buffer--;
			return element;
		}
		
		return null;
	}
}
