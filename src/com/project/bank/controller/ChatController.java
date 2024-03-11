package com.project.bank.controller;


import java.net.HttpURLConnection;

import com.project.bank.chat.AssistantKind;
import com.project.bank.chat.ChatBotHandler;


public class ChatController {
	
	private  ChatBotHandler chatbotHandler;
	
	

	public ChatController() {
		this.chatbotHandler=new ChatBotHandler();
	}
	


	
	public StringBuffer ChatWithChatBot(String userMessage) {//채팅 내용 반환
		
		
		 if(userMessage.contains("\"")) {
			 
			 userMessage=userMessage.replace("\""," ");		
		 }
	
		
		// openai에 요청해더 전송
	     HttpURLConnection connection=this.chatbotHandler.requestHeader();
	     String requestBody = this.chatbotHandler.requestBody(userMessage);
	     //System.out.println(requestBody);
	     
	     /////////////////////////////
	     
        //요청헤더를 openai에게 보낸다.
	     this.chatbotHandler.sendToChatBot(connection,requestBody);
	       
			
        //openai로부터 응답을 받아온다. json형태로 응답이 들어온다.
	     
	     StringBuffer response=  new StringBuffer(this.chatbotHandler.recevieFromChatBot(connection));
	     
	     
	     ///////////////
	    	
	     //json형태의 메세지에서 챗봇 응답부분만 추출
	  
	     StringBuffer chatbotResponse=  new StringBuffer(this.chatbotHandler.convertMessageToString(response));
	       
	
	     
	        
		 return chatbotResponse;
		
		
		
	}
	
	public StringBuffer  setAssistant(AssistantKind p) {
		
		// openai에 요청해더 전송
	     HttpURLConnection connection=this.chatbotHandler.requestHeader();
	     String requestBody = this.chatbotHandler.requestBody(p);
	     //System.out.println(requestBody);
	     
	     /////////////////////////////
	     
	     
	     
       //요청헤더를 openai에게 보낸다.
	     this.chatbotHandler.sendToChatBot(connection,requestBody);
	       
			
       //openai로부터 응답을 받아온다. json형태로 응답이 들어온다.
	     
	     StringBuffer response=  new StringBuffer(this.chatbotHandler.recevieFromChatBot(connection));
	     
	     
	     ///////////////
	    	
	     //json형태의 메세지에서 챗봇 응답부분만 추출
	  
	     StringBuffer chatbotResponse= new StringBuffer(this.chatbotHandler.convertMessageToString(response));
	       
	     
	  
	     
	        
		 return chatbotResponse;
		
	}
	
	public void deleteMessage() {
		
		this.chatbotHandler.deleteMessage();
	}
	

	
	

	

}
