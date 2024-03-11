package com.project.bank.chat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatBotHandler {
	
	private String apiKey;
	private String endpoint;
	private URL url;
	private ArrayList<Messages> messages;

	
	
	public ChatBotHandler() {

		
		this.apiKey=readApiKey();
		this.endpoint="https://api.openai.com/v1/chat/completions";
		try {
			this.url=new URL(endpoint);
		} catch (MalformedURLException e) {
			System.err.println("url오류");
			e.printStackTrace();
		}
		messages=new ArrayList<Messages>();
	 
		
		
	}
	private static String readApiKey() {
		 
		 
		 try(BufferedReader read =new BufferedReader(new FileReader("resources/api-key.txt"));) {
			
			 String value=null;
			 String message=null;
			 while((value=read.readLine())!=null){
				  message=value;
		     }
			 
			 return message;
		
			 
			 
			 
		} catch (FileNotFoundException e) {
			
			return "api-key오류";
		
		} catch (IOException e1) {
			
			return "api-key오류";
		}
		 
	
	 }
	
	private String getAssiatant(AssistantKind p) {
		
		 
		 String value=null;
		 
		 switch(p) {
		 case 분석:
			 value=Assistant.get계좌분석assistant();
			 break;
		 default :
		 	value=null;
		 }
		
		 return value;
	}
	
	
	
    public void deleteMessage() {
    	messages.clear();
    }
	
    
	public ArrayList<Messages> getMessages() {
		return messages;
	}
	public  HttpURLConnection  requestHeader() {
		 //conntection은 무조건 지역변수로 ; 
		 //openConnection ->getOutputStream->write ->getInputStream->read
		 //멤버변수로 커넥션을 설정해버리면 새로운 커넥션이 들어가지 않고 원래 커넥션이 유지 되어 버린다.
	     //URL url=null;
	     HttpURLConnection connection=null;;
		
		 try{
			 
			 connection = (HttpURLConnection) url.openConnection();
			 connection .setRequestMethod("POST");
			 connection .setRequestProperty("Content-Type", "application/json");
			 connection .setRequestProperty("Authorization", "Bearer "+ apiKey);
			 connection .setDoOutput(true);
				   
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	                
		 return connection;
		
	}
	
	 public  String  requestBody(AssistantKind p) {//어시스턴트
		   
		 //ArrayList<String> arr =new ArrayList<String>();
		 
		 String value=getAssiatant(p);
		 if(value==null) messages.add(new Messages("assistant","안녕하세요 무엇을 도와드릴까요?"));
	     
		 messages.add(new Messages("assistant",value));
		
		    
		    
		 //gpt-4
		//gpt-3.5-turbo
		return String.format(
		        	   "{" +
		        	    "  \"model\": \"gpt-3.5-turbo\"," + // 모델명 지정
		        	    "  \"messages\":"+ messages+"" +    // 사용자 메시지
		        	    "}");   //제이슨 형식 (요청 형식은 제이슨이다.)
		
	}
	
	
	
   public  String  requestBody(String message) {//자유채팅 응답
	   
	   
	     messages.add(new Messages("user",message));
	    
	     
		//gpt-4
		//gpt-3.5-turbo
		return String.format(
		        	   "{" +
		        	    "  \"model\": \"gpt-3.5-turbo\"," + // 모델명 지정
		        	    "  \"messages\":"+messages+"" +    // 사용자 메시지
		        	    "}");   //제이슨 형식 (요청 형식은 제이슨이다.)
		
	}
	
	
	
	public void sendToChatBot(HttpURLConnection connection,String requestBody) {
		    
		 
		   System.out.println("분석중입니다.....");
		
		    byte[] input=null;
	        int responseCode=0;
			try(OutputStream outputStream=connection.getOutputStream();) {
				
				input = requestBody.getBytes("utf-8");
				outputStream.write(input,0,input.length);
				outputStream.flush();
				
				//응답상태 반환
				responseCode = connection.getResponseCode();
	          
	           
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}

	public StringBuffer recevieFromChatBot(HttpURLConnection connection) {
		
		StringBuffer response= new StringBuffer();
    	//StringBuilder response =new StringBuilder();
    	String value=null;
		try(BufferedReader reader= new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));) {
			
	    	while((value=reader.readLine())!=null) {
	    		response.append(value.trim());
	    	}
	    	//System.out.println(response);
	    	 
	    	 return new StringBuffer(response);
	    	
	    } catch (UnsupportedEncodingException e) {
		    System.err.println("인코딩에러");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return new StringBuffer("error");
		
	}
	
	public StringBuffer convertMessageToString(StringBuffer response) {
    	
		
    	  //json 변환후 응답 추출
	     ObjectMapper mapper = new ObjectMapper();
	     JsonNode node=null;
	     JsonNode choices=null;
	     JsonNode message=null;
	     StringBuffer content=null;
	         
			try {
				node = mapper.readTree(response.toString());
			    choices=node.get("choices");
			    
			    
		         if(choices.isArray()) {
		        	
		        	  for(JsonNode contents : choices) {
		        		 message=contents.get("message");
		        		 content=new StringBuffer(message.get("content").asText());
		        	  }
		        	 
		        	
		         }

		         
		         String re=content.toString().replaceAll("\\n","\\*");//개행 처리후 전송
		         
		    	 if(re.contains("\"")) {
					 
		    		 re=re.replace("\""," ");		
				 }
				
		         messages.add(new Messages("assistant",re));
		         
			     //System.out.println(re);
		         return new StringBuffer(re);     
				
			} catch (JsonMappingException e) {
				e.printStackTrace();
				return new StringBuffer("제이슨 매핑오류");
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return  new StringBuffer("제이슨 오류");
			}
    	
    }
	
}
