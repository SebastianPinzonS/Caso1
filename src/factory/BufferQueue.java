package factory;

public class BufferQueue {

	private int head = 0;
	private int tail = 0;
	private Message[] queue;
	private int size;
	private int fill = 0;
	
	public BufferQueue(int oSize) {
		queue = new Message[oSize];
		size = oSize;
	};
	
	public synchronized boolean queuePut(Message value) {
		if (fill == size) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} else {
			queue[tail] = value;
			if (tail == size - 1) {
				tail = 0;
			} else {	
				tail++;
			}
			fill++;
			notifyAll();
			return true;

		}
	}
	
	public synchronized Message queueGet(){
		if (fill == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			Message value = queue[head];
			if (head == size - 1) {
				head = 0;
			} else {	
				head++;
			}
			fill--;
			notifyAll();
			return value;
		}
	}
	
	
}
