package com.project.bank.model.dto;

import java.sql.Date;

public class AccountDto {
	
	private String name;
	private String accountNum;
	private String accountType;
	private Date openingDate;
	private int balance;
	
	public AccountDto() {
		
		
	}

	public AccountDto(String name, String accountNum, String accountType, Date openingDate, int balance) {
		super();
		this.name = name;
		this.accountNum = accountNum;
		this.accountType = accountType;
		this.openingDate = openingDate;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "name=" + name + ", accountNum=" + accountNum + ", accountType=" + accountType
				+ ", openingDate=" + openingDate + ", balance=" + balance+"\n";
	}
	
	
}
