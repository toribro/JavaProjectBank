package com.project.bank.test;

import java.util.ArrayList;

import com.project.bank.controller.AccountController;
import com.project.bank.model.dao.MemberDao;
import com.project.bank.model.dao.TransactionDao;
import com.project.bank.model.dto.TransactionAccountNumDto;
import com.project.bank.model.dto.TransactionDto;
import com.project.bank.model.vo.Account;
import com.project.bank.model.vo.Member;
import com.project.bank.service.TransactionService;

public class Test {
	
	 // static AccountController ar=new AccountController();
	  
	 public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		 전계좌조회();
		 
	
//          TestDao userdb= new TestDao();
//		 userdb.dbConnection();
//		
//		 System.out.println(userdb.testSelect(2));
//		 
//		 userdb.dbDisconnect();
		
//		 조회();//통과
//		 System.out.println();
//		 
//		 입금();
//		 System.out.println();
//		 조회();
//		 System.out.println();
//		
//		 
//		 출금();
//		 System.out.println();
//		 조회();
//		 System.out.println();
//		 
//		 이체();
//		 System.out.println();
//		 조회();
//		 System.out.println();
		 
		 
	//	 조회2();
		 
	}
	 
	 public static void 조회() {
		 TransactionService tdao=new TransactionService();
		
		 
		 ArrayList<TransactionDto> result= tdao. TransactionList("0admin","0001000", "admin1234");
		 for(TransactionDto r:result) {
			 System.out.println(r);
			 
		 }
		
		
		 
	 }
	 
	 public static void 전계좌조회() {
		 TransactionService tdao=new TransactionService();
		 ;
		 
		 ArrayList<TransactionAccountNumDto> result= tdao.TransactionAllList("0admin", "admin001");
		 for(TransactionAccountNumDto r:result) {
			 System.out.println(r);
			 
		 }
		
		
		 
	 }
	 
	 public static void 조회2() {
		 TransactionService tdao=new TransactionService();
		
		 
		 ArrayList<TransactionDto> result2= tdao.TransactionList("tori","1111111", "tori1234");
		 for(TransactionDto r:result2) {
			 System.out.println(r);
			 
		 }
		
		 
	 }
	 
	 
	 public static void 입금() {
		 
		 TransactionService tdao=new TransactionService();
		
		 
		 
		 tdao.InputMoney(7700, "0001000");
		 
		 
		 
		 
	 }
	 
	 
	 
	 public static void 출금() {
		 
		 TransactionService tdao=new TransactionService();
		
		 
		 tdao.OutputMoney(3000, "0001000","admin1234");
		 
		 
		 
		 
	 }
	 

     public static void 이체() {
		 
    	 TransactionService tdao=new TransactionService();
		
		 
		 
		 tdao.TransmitMoney(3000,"1111111","0001000","admin1234");
		 
		 
		 
	 }


}
