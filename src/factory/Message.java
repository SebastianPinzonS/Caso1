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
		return orange;
	}
	
	public void advanceStage(int stageNum) {
		if(this.orange) {
			stage = stage + "La fase " + stageNum + " ha terminado y soy naranja, ";
		} else {
		stage = stage + "La fase " + stageNum + " ha terminado, ";
		}
	}
	
	public String getStage() {
		return stage;
	}
}
