package com.project.bank.view;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.bank.chat.AssistantKind;
import com.project.bank.controller.AccountController;
import com.project.bank.controller.ChatController;
import com.project.bank.controller.MemberController;
import com.project.bank.controller.TransactionController;
import com.project.bank.model.dto.TransactionAccountNumDto;
import com.project.bank.model.dto.TransactionDto;
import com.project.bank.model.vo.Account;
import com.project.bank.model.vo.Member;

public class View {
    private MemberController mem;
    private AccountController act;
    private TransactionController trn;
    private ChatController chat;
    private Scanner sc;
    
	public View() {
		
		this.mem = new MemberController();
		this.act = new AccountController();
		this.trn = new TransactionController();
		this.chat=new ChatController();
		this.sc=new Scanner(System.in);
	}
    
	public void mainMenu() {
		while(true) {
			System.out.println("========메인메뉴=======");
			System.out.println("1.회원등록 2.본인정보조회 3.회원탈퇴 4.계좌개설 5.계좌조회 6.전계좌조회 7.계좌삭제 8.거래메뉴 9.분석 0종료");
			int a=sc.nextInt();
			sc.nextLine();
			switch(a) {
			case 1:
				this.reigisterMember();
				break;
			case 2:
				this.searchMember();
				break;
			case 3:
				this.deleteMember();
				break;
			case 4:
				this.makeAccount();
				break;
			case 5:
				this.searchAccount();
				break;
			case 6:
				this.searchAllAcount();
				break;
			case 7:
				this.deleteAccount();
				break;	
			case 8:
				this.TransactionMenu();
				break;
			case 9:
				this.AccountAnalysis();
				break;
			case 0:
//				mem.dbDisConnect();
//				act.disconnect();
//				trn.dbDisConnect();
				sc.close();
				System.exit(1);
				return;
			default:
				System.out.println("다시입력하세요");
			
			}
	  }		
		
		
		
	}
	
	
   

	//=======메세지=====================''
	   public void SuccessMessage(String message) {
		 System.out.println(message+"성공");
	   }
	   public void Message(String message) {
		   System.out.println(message);
	   }
	
	   public void FailMessage(String message) {
			 System.out.println(message+"실패");
		}
		
	
	
	private void deleteMember() {
		System.out.println("===회원탈퇴===");
		System.out.print("이름을 입력하세요:");
		String name=sc.nextLine();
		System.out.print("주민 번호를 입력하세요:");
		String memberNo=sc.nextLine();
	    mem.deleteMember(name, memberNo);
	}

	private void searchMember() {
	    System.out.println("===본인 정보 조회===");
	    System.out.print("이름을 입력하세요:");
		String name=sc.nextLine();
		System.out.print("주민 번호를 입력하세요");
		String memberNo=sc.nextLine();
		
		mem.selectMember(name, memberNo);
		
		
	}

	private void reigisterMember() {
		
	   System.out.println("=======회원등록=======");
	   System.out.print("이름을 입력하세요:");
	   String name=sc.nextLine();
	   System.out.print("주민번호를 입력하세요:");
	   String memberNo=sc.nextLine();
	   System.out.print("성별을입력하세요 M또는F");
	   String gender=sc.next().charAt(0)+"".toUpperCase();
	   sc.nextLine();
	   System.out.print("주소를 입력하세요: ");
	   String address=sc.nextLine();
	   System.out.print("전화번호를 입력하세요:");
	   String phoneNumber=sc.nextLine();
	   System.out.print("이메일을 입력하세요:");
	   String email=sc.nextLine();
	   
	   Date birthDate=null;
	   String birth=null;
	  
	  while(true) {
		  try {
			  System.out.print("생일을 입력하세요: 형식: yyyy-mm-dd");
			  birth=sc.nextLine();
			  birthDate=Date.valueOf(birth);
			  break;
		  }catch(IllegalArgumentException e) {
			  System.out.println("다시입력하세요 형식 :yyyy-mm-dd:");
		  }
	  }
	
	  
	  
	  Member member=new Member(
			  name,
			  memberNo,
			  gender,
			  address,
			  phoneNumber,
			  email,
			  birthDate
	   );      
	    mem.MemberCreate(member);
	}
	


	private void deleteAccount() {
		System.out.println("삭제할 계좌 정보입력");
		System.out.println("계좌번호를 입력하세요");
		String accountNum=sc.nextLine();
		System.out.println("계좌 비밀번호를 입력하세요");
		String accountPassword=sc.nextLine();
		act.deleteAccount(accountNum, accountPassword);
		
		
	}
	
	
	private void searchAllAcount() {
		System.out.println("====모든계좌정보 조회===");
		System.out.print("이름을 입력하세요:");
		String name=sc.nextLine();
		System.out.print("주민번호를 입력하세요: ");
		String MemberNum=sc.nextLine();
	    act.ShowAccounts(name, MemberNum);
		
	}
	

	private void searchAccount() {
		System.out.println("========계좌정보조회=====");
		System.out.print("이름을 입력하세요:");
		String name=sc.nextLine();
		System.out.print("계좌번호를 입력하세요: ");
		String accountNum=sc.nextLine();
		System.out.println("계좌비밀번호를 입력하세요");
		String accountPassword =sc.nextLine();
		
		act.ShowAccount(name, accountNum, accountPassword);
		
		
	}

	public void makeAccount() {
		System.out.println("만들 계좌정보 입력");
		System.out.println("이름을 입력하세요");
		String name=sc.nextLine();
		System.out.println("주민번호를 입력하세요");
		String memberNo=sc.nextLine();
		int id=mem.findMemberId(name,memberNo);
		if(id<=0) {
			System.out.println("회원이없거나 잘못입력하셨습니다.");
			return;
		}
		
		System.out.println("계좌번호를 입력하세요");
		String accountNumber=sc.nextLine();
		System.out.println("계좌비밀번호를 입력하세요");
		String accountPassword=sc.nextLine();
		System.out.println("계설할 계좌 유형을 입력하세요 (일반,저축,투자)");
		String accountType=sc.nextLine();
		
		
		act.CreateAccount(new Account(id,accountNumber,accountPassword,accountType));
		
	}
	
	
	   
	
	public void TransactionMenu() {
		
		while(true) {
		
			System.out.println("====거래 메뉴====");
			System.out.println("1.입금 2.출금 ,3.이체 4.거래정보 5.전계좌거래정보 9.돌아가기");
			int a=sc.nextInt();
			sc.nextLine();
			switch(a) {
			case 1:
				this.inputMoney();
				break;
			case 2:
				this.outputMoney();
				break;
			case 3:
				this.transmit();
				break;
			case 4:
				this.searchTransaction();
				break;
			case 5:
				this.searchAllTransaction();
				break;
			case 9:
				return;
			default:
				System.out.println("다시 입력하세요");
			}
		}	
	}
	
	
	private void searchAllTransaction() {
		System.out.println("===전체계좌목록조회====");
		System.out.print("이름을 입력하세요: ");
		String name=sc.nextLine();
		System.out.print("주민번호를 입력하세요:");
		String memberNo=sc.nextLine();
		trn.TransactionAllList(name, memberNo);
	}

	private void searchTransaction() {
		System.out.println("===거래목록조회====");
		System.out.print("이름을 입력하세요: ");
		String name=sc.nextLine();
		System.out.print("계좌번호를 입력하세요: ");
		String accountNum=sc.nextLine();
		System.out.print("비밀번호를 입력하세요: ");
		String accountPassword=sc.nextLine();
		
		trn.TransactionList(name, accountNum, accountPassword);
		
	}

	private void transmit() {
		System.out.println("====이체====");
		System.out.print("이체 계좌번호를 입력하세요: ");
		String countAccountNum=sc.nextLine();
		
		
		int balance=0;
		while(true) {
			try {
				System.out.print("이체 금액을 입력하세요: ");
				balance=Integer.parseInt(sc.nextLine());
				if(balance<=0) {
					System.out.println("금액은 양수이어야합니다.");
				}
				else {
					break;
				}
				
			}catch(NumberFormatException e) {
				System.out.println("숫자로입력하세요");
			}
		}
		
		
		System.out.print("계좌번호를 입력하세요: ");
		String AccountNum=sc.nextLine();
		System.out.print("비밀번호를 입력하세요: ");
		String AccountPassword=sc.nextLine();
		
		trn.TransmitMoney(balance, countAccountNum, AccountNum, AccountPassword);
		
		
	}
	
	private void outputMoney() {
		System.out.println("====출금====");
		int balance=0;
		while(true) {
			try {
				System.out.print("출금 금액을 입력하세요: ");
				balance=Integer.parseInt(sc.nextLine());
				if(balance<=0) {
					System.out.println("금액은 양수이어야합니다.");
				}
				else {
					break;
				}
				
			}catch(NumberFormatException e) {
				System.out.println("숫자로입력하세요");
			}
		}
		
		System.out.print("계좌번호를 입력하세요: ");
		String AccountNum=sc.nextLine();
		System.out.print("비밀번호를 입력하세요: ");
		String AccountPassword=sc.nextLine();
		
		trn.OutputMoney(balance, AccountNum, AccountPassword);
		
		
		
	}

	private void inputMoney() {
		System.out.println("====입금====");
		int balance=0;
		while(true) {
			try {
				System.out.print("입금 금액을 입력하세요: ");
				balance=Integer.parseInt(sc.nextLine());
				if(balance<=0) {
					System.out.println("금액은 양수이어야합니다.");
				}
				else {
					break;
				}
				
			}catch(NumberFormatException e) {
				System.out.println("숫자로입력하세요");
			}
		}
		
		System.out.print("계좌번호를 입력하세요: ");
		String AccountNum=sc.nextLine();
		trn.InputMoney(balance, AccountNum);
		
	}
	
	private void AccountAnalysis() {
		System.out.println("===회원 계좌 분석====");
		System.out.print("이름을 입력하세요: ");
		String name=sc.nextLine();
		System.out.print("주민번호를 입력하세요: ");
		String userNum=sc.nextLine();
	
		
		ArrayList<TransactionAccountNumDto> dtos=trn.TransactionAllList(name, userNum);
		
		if(dtos.isEmpty()) {
			return;
		}
		chat.setAssistant(AssistantKind.분석);
		
		String result=dtos.toString();
		result=result.replace("\n", "\\n");
		
		int i=1;
		while(i<=2) {
			StringBuffer message=chat.ChatWithChatBot(result);
			System.out.println(message.toString());
			i++;
		}
		
	}
	
}
