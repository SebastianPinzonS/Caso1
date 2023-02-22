package factory;

import java.util.HashMap;

public class UnlimitedBuffer {

	public static HashMap<Integer, Message> bufferMap= new HashMap<Integer, Message>();
	public static int currentId = 0;



	public synchronized void addToBuffer(Message element) {
		bufferMap.put(element.getId(), element);
		notify();
	}
	
	public synchronized Message exctractFromBuffer(){
		boolean exit = false;
		while (!exit){
			exit = bufferMap.containsKey(currentId);
		}
		Message value = bufferMap.get(currentId);
		currentId++;
		return value;
		
	}
	public boolean isEmpty() {
		return bufferMap.isEmpty();
	}
}
