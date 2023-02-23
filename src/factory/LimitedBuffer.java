package factory;

public class LimitedBuffer {
	
	private  BufferQueue bufferQueue;//Se usa un queue para mantener el orden de entrada
	
	public LimitedBuffer (int oLimit) {
		bufferQueue = new BufferQueue(oLimit);
	}
	
	public  void addToBuffer(Message element, boolean isOrange) {
		if(isOrange) {
			addToBufferOrange(element);
		} else {
			addToBufferNOrange(element);
		}
	}
	
	public void addToBufferOrange(Message element) {
		while(!bufferQueue.queuePut(element)) {
			Thread.yield();
		}
	}
	
	public void addToBufferNOrange(Message element) {
		while (!bufferQueue.queuePut(element)){ //Revisa si hay suficiente espacio en el buffer para agregar nuevos elementos
		}
	}

	
	public Message extractFromBufferNoOrange(){
		Message element = bufferQueue.queueGet();
		while (element == null || element.isOrange()){				//Revisa si hay elementos en el buffer para extraer 
			if(element != null) {
				addToBufferNOrange(element);
			}
			element = bufferQueue.queueGet();
		}
		return element;
	}

	
	public Message extractFromBufferOrange(){
		Message element = bufferQueue.queueGet();
		while (element == null || !element.isOrange()) {
			if(element != null) {
				addToBufferOrange(element);
			}
			Thread.yield();
			element = bufferQueue.queueGet();
		}
		return element;

	}
	public Message extractFromBuffer(boolean isOrange) {
		if(isOrange) {
			return extractFromBufferOrange();
		} else {
			return extractFromBufferNoOrange();

		}
	}
}
