package com.project.bank.chat;



public class Messages {
	private String message;
	//private JsonNode json;
    private String role;
    
	public Messages(String role,String message) {
		super();
		
		this.message = message;
		this.role=role;
		//this.json=null;
		
		
	}
	
//	public Messages(String role,JsonNode json) {
//		this.json=json;
//	}
	
	
	
	
	public String getMessage() {
		return message;
	}
	

	public String getRole() {
		return role;
	}



	@Override
	public String toString() {
		return String.format("{ \"role\":\"%s\" ,\"content\":\"%s\"}",this.role,this.message);
	}

	
	
	
}
