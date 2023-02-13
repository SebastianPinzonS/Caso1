package factory;

public class Message {
	
	private IdAssigner idAssigner = new IdAssigner();
	private int id;
	private boolean orange;
	private int stage = 0;
	
	public Message(boolean oOrange) {
		
		orange = oOrange;
		this.id = idAssigner.getId();	
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean isOrange() {
		return orange;
	}
	
	public void advanceStage() {
		stage++;
	}
	
	public int getStage() {
		return stage;
	}
}
