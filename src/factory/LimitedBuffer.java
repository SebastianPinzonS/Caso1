package factory;

public class LimitedBuffer {
	
	private static BufferQueue bufferQueue;//Se usa un queue para mantener el orden de entrada
	
	public LimitedBuffer (int oLimit) {
		bufferQueue = new BufferQueue(oLimit);
	}
	
	public synchronized void addToBuffer(Message element, boolean isOrange) {
		if(isOrange) {
			while (!bufferQueue.queuePut(element)) {
			}
		} else {
			while (!bufferQueue.queuePut(element)){ //Revisa si hay suficiente espacio en el buffer para agregar nuevos elementos
				try {
					wait(); //Si no hay espacio el thread va a la bolsa
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		notifyAll(); //Se usa notiyAll debido a que en la bolsa pueden haber productores o consumidores y si llega a entrar 
	}

	
	public synchronized Message exctractFromBuffer(boolean isOrange){
		if(isOrange) {
			Message element = bufferQueue.queueGet();
			while (element == null) {
				element = bufferQueue.queueGet();
			}
			notifyAll();
			return element;

		} else {
			Message element = bufferQueue.queueGet();
			while (element == null){ //Revisa si hay elementos en el buffer para extraer 
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				element = bufferQueue.queueGet();
			}
			notifyAll();
			return element;
		}
	}
}
