package com.project.bank.model.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Account {//계좌

	
	
	private int memberId; //pk키
	private String accountNumber;
	private String accountPassword;
	private String accountType;
	private Date openingDate;
	private int balance;
	
	
	public Account() {
		
	}

   public Account(int memberId, String accountNumber, String accountPassword, String accountType) {
		super();
		this.memberId = memberId;
		this.accountNumber = accountNumber;
		this.accountPassword = accountPassword;
		this.accountType = accountType;
	}


	public int getMemberId() {
		return memberId;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public String getAccountPassword() {
		return accountPassword;
	}


	public String getAccountType() {
		return accountType;
	}


	public Date getOpeningDate() {
		return openingDate;
	}

	public void setBalance(int balance) {
		this.balance=balance;
	}

	public int getBalance() {
		return balance;
	}

    
	
    
	
	
	
	
	
	
	
	

}
