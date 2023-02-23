package factory;


public class Message {
	
	private int id;
	private boolean orange;
	public String stage = "";

	
	public Message(boolean oOrange) {
		
		this.orange = oOrange;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int oId) {
		this.id = oId;
	}
	
	public boolean isOrange() {
		return this.orange;
	}
	
	public void advanceStage(int stageNum, int stageDelay) {
		if(this.orange) {
			stage = stage + "|La fase " + stageNum + " ha terminado, mi delay fue: " + stageDelay + "ms y soy naranja| ";
		} else {
		stage = stage + "|La fase " + stageNum + " ha terminado y mi delay fue: "+ stageDelay+ "ms| ";
		}
	}
	
	public String getStage() {
		return stage;
	}
}
