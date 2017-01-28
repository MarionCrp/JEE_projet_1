package aideProjet;

import java.util.ArrayList;
import java.util.List;

public class Flash {
	private String type;
	private List<String> messages = new ArrayList<String>();
	
	public Flash(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
	
	public List<String> getAllMessages(){
		return this.messages;
	}
	
	public String getFirstMessage(){
		return this.messages.get(0);
	}
	
	public void addMessage(String message){
		this.messages.add(message);
	}
	
	public boolean hasAnyMessages(){
		return !this.getAllMessages().isEmpty();
	}
	
	public boolean hasNoMessage(){
		return this.getAllMessages().isEmpty();
	}
	
	public void destroyAllMessages(){
		this.messages.clear();
	}
}