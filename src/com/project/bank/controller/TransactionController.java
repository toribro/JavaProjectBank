package com.project.bank.controller;

import java.util.ArrayList;

import com.project.bank.model.dao.TransactionDao;
import com.project.bank.model.dto.TransactionAccountNumDto;
import com.project.bank.model.dto.TransactionDto;
import com.project.bank.service.TransactionService;
import com.project.bank.view.View;

public class TransactionController {
	private TransactionService service;
	
	public TransactionController () {
		service=new TransactionService();
	}
	
	
	//입금
	public void InputMoney(int amount,String accountNum) {
		int result=this.service.InputMoney(amount, accountNum);
		
		if(result>0) {
			new View().SuccessMessage("입금");
		}else {
			new View().FailMessage("입금");
		}
		
		
	}
	
	//출금
	
	public void OutputMoney(int amount,String accountNum,
			String accountPassword) {
        int result=this.service.OutputMoney(amount, accountNum,accountPassword);
		
		if(result>0) {
			new View().SuccessMessage("성공");
		}else {
			new View().FailMessage("실패");
			new View().Message("정보가잘못되었거나 잔액이 부족합니다."); 
		}
		
	}

	//이체
	
	public void TransmitMoney(int amount,String counterAccount,String accountNum
			,String accountPassword) {
		
		   int result=this.service.TransmitMoney(amount,counterAccount, accountNum,accountPassword);
			
			if(result>0) {
				new View().SuccessMessage("이체");
			}else if(result==-2) {
				new View().Message("자기 계좌에는 이체하실수 없습니다.");
			}
			
			else {
				new View().FailMessage("이체");
			}
		
	}
	
	//거래목록
	
	public ArrayList<TransactionDto> TransactionList(String name,String accountNum,String accountPassword) {
		
		ArrayList<TransactionDto> dtos=this.service.TransactionList(name, accountNum, accountPassword);
		
		if(!dtos.isEmpty()) {
			new View().Message(dtos.toString());
		}
		else {
			new View().Message("거래목록이 없습니다.");
		}
		return dtos;
	}
	
	
	public ArrayList<TransactionAccountNumDto>  TransactionAllList(String name,String memberNo) {
		
		ArrayList<TransactionAccountNumDto> dtos=this.service.TransactionAllList(name,memberNo);
		
		if(!dtos.isEmpty()) {
			new View().Message(dtos.toString());
		}
		else {
			new View().Message("거래목록이없습니다.");
		}
		return dtos;
	}
}
