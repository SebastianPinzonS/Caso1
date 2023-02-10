package factory;

import java.util.LinkedList;
import java.util.Queue;

public class LimitedBuffer {
	private static int bufferAvailability = 0;
	private static int bufferLimit;
	private static Queue<Product> bufferQueue = new LinkedList<Product>();//Se usa un queue para mantener el orden de entrada
	
	public LimitedBuffer (int oLimit) {
		bufferLimit = oLimit;
	}
	
	public synchronized void addToBuffer(Product element) {
		while (bufferAvailability > bufferLimit){ //Revisa si hay suficiente espacio en el buffer para agregar nuevos elementos
			try {
				wait(); //Si no hay espacio el thread va a la bolsa
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		bufferQueue.offer(element);
		bufferAvailability++;
		notifyAll(); //Se usa notiyAll debido a que en la bolsa pueden haber productores o consumidores y si llega a entrar 
	}

	
	public synchronized Product exctractFromBuffer(){
		while (bufferAvailability < 1){ //Revisa si hay elementos en el buffer para extraer 
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Product element = bufferQueue.poll();
		bufferAvailability--;
		notifyAll();
		return element;
	}
}
