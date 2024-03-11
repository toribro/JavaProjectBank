package com.project.bank.controller;

import java.util.ArrayList;

import com.project.bank.model.dao.AccountDao;
import com.project.bank.model.dto.AccountDto;
import com.project.bank.model.vo.Account;
import com.project.bank.service.AccountService;
import com.project.bank.view.View;

public class AccountController {

	private AccountService service;
	
	public AccountController() {
		
		this.service=new AccountService();
	}

	
	public void CreateAccount(Account c) {
		
		
		int result=this.service.insertAccount(c);
		
		if(result>0) {
			new View().SuccessMessage("계좌생성");
		}
		else {
			new View().FailMessage("계좌생성");
			new View().Message("이미 있는 계좌번호입니다.");
		}
		
	}
	
	//계좌 조회
	public void ShowAccount(String name,String accountNum,String accountPassword) {
		
		
		
		AccountDto result=this.service.selectAccount(name, accountNum, accountPassword);
		if(result!=null) {
			new View().Message(result.toString());
		}
		else {
			new View().FailMessage("계좌조회");
			new View().Message("계좌정보가없거나 잘못입력하셨습니다.");
		}
	}
	//전계좌 조회
	public void ShowAccounts(String name,String memberNo) {
		
		ArrayList<AccountDto> results=this.service.selectAccounts(name, memberNo);
		if(!results.isEmpty()) {
			new View().Message(results.toString());
		}
		else {
           new View().Message("계좌가 없습니다.");
		}
	}
	
	//계좌 삭제
	public void deleteAccount(String accountNum,String accountPassword) {
		
		int result=this.service.deleteAccount(accountNum, accountPassword);
		
		if(result>0) {
			new View().SuccessMessage("삭제");
		}else {
			new View().SuccessMessage("삭제실패");
		}
		
		
	}
	
	
}
