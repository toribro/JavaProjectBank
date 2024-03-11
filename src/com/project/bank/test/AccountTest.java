package com.project.bank.test;





import java.sql.Date;
import java.util.ArrayList;

import com.project.bank.model.dao.AccountDao;
import com.project.bank.model.dao.MemberDao;
import com.project.bank.model.dto.AccountDto;
import com.project.bank.model.vo.Account;
import com.project.bank.service.AccountService;
import com.project.bank.service.MemberService;

public class AccountTest {
   
	public static void main(String []args) {
		
		//계좌입력();
		//계좌조회();
		//전체계좌조회();
		//계좌삭제();
		//전체계좌조회();
	}
	
	public static void 계좌입력() {
		
		
		
		MemberService mo =new MemberService();
		int id=mo.findMemberId("신토리","980416-");
		
		
		AccountService ad=new AccountService ();
		
		
		Account a= new Account(
				id,
				"12345678",
				"6666",
				"일반"
		);
		ad.insertAccount(a);
		
		
	}
	
	public static void 계좌조회() {
		
		AccountService ad=new AccountService ();
	
	
		AccountDto result=ad.selectAccount("신토리","1234567","5555");
		System.out.println(result);
		
		
	}
	
   public static void 전체계좌조회() {
		
	    AccountService ad=new AccountService ();
		
		ArrayList<AccountDto> result=ad.selectAccounts("신토리","980416-");
		for(AccountDto dto:result) {
			System.out.println(dto);
		}
		
		
		
	}
	
	
	
	public static void 계좌삭제() {
		
		
		AccountService ad=new AccountService ();
		
	    ad.deleteAccount("12345678", "6666");
	       
		
	}
}
