package factory;

import java.util.concurrent.ConcurrentHashMap;

public class UnlimitedBuffer {

	private static ConcurrentHashMap<Integer, Message> bufferMap= new ConcurrentHashMap<Integer, Message>();
	private static int currentId = 0;



	public void addToBuffer(Message element) {
		bufferMap.put(element.getId(), element);
		notify();
	}
	
	public synchronized Message exctractFromBuffer(){
		boolean exit = false;
		while (!exit){
			bufferMap.containsKey(currentId);
		}
		Message value = bufferMap.get(currentId);
		currentId++;
		return value;
		
	}
}
