package factory;

public class LimitedBuffer {
	
	private  BufferQueue bufferQueue;//Se usa un queue para mantener el orden de entrada
	
	public LimitedBuffer (int oLimit) {
		bufferQueue = new BufferQueue(oLimit);
	}
	
	public boolean addToBuffer(Message element, boolean isOrange) {
		if(isOrange) {
			return addToBufferOrange(element);
		} else {
			return addToBufferNOrange(element);
		}
	}
	
	public synchronized boolean addToBufferOrange(Message element) {
		boolean attempt = bufferQueue.queuePut(element);
		if (attempt) {
			notifyAll();
		}
		return attempt;
	}
	
	public synchronized boolean addToBufferNOrange(Message element) {
		while (!bufferQueue.queuePut(element)){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		notifyAll();
		return true;
	}


	
	public Message extractFromBuffer(boolean isOrange) {
		if(isOrange) {
			return extractFromBufferOrange();
		} else {
			return extractFromBufferNoOrange();

		}
	}
	
	public synchronized Message extractFromBufferOrange(){
		Message element = bufferQueue.peek();
		if (element == null || !element.isOrange()) {
			return null;
		}
		element = bufferQueue.queueGet();
		notifyAll();
		return element;

	}
	public synchronized Message extractFromBufferNoOrange(){
		Message element = bufferQueue.peek();
		while (element == null || element.isOrange()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			element = bufferQueue.peek();
		}
		notifyAll();
		return bufferQueue.queueGet();
	}
	
}
