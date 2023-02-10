package factory;

public class IdAssigner {
	
	private static int id = -1;
	
	public synchronized int getId() {
		id++;
		return id;
	}

}
