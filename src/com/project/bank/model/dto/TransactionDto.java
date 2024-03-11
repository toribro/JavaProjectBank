package com.project.bank.model.dto;

public class TransactionDto {


	private String accountType;
	private String amount; //거래 금액
	private String accountCounter; //이체계좌
	private int balance;// 거래후 계좌 총액
	
	
	public TransactionDto() {
		
		
	}
	

	public TransactionDto( String accountType, String amount,int balance) {
		super();
		this.accountType = accountType;
		this.amount = amount;
		this.accountCounter=null;
		this.balance = balance;
	}


	public TransactionDto( String accountType, String amount, String accountCounter,int balance) {
		super();
		
		this.accountType = accountType;
		this.amount = amount;
		this.accountCounter=accountCounter;
		this.balance = balance;
	}


	@Override
	public String toString() {
		
		
		return  " 거래유형: " + accountType+ ", 거래금액: " + amount + ", 계좌금액: " + balance+" 이체계좌: "+this.accountCounter+"\n";
	}
	
	
	
}
