package factory;

public class IdAssigner {
	
	private static int id = 0;
	
	public synchronized int getId() {
		id++;
		return id;
	}

}
