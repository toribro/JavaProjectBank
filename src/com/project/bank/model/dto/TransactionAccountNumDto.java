package com.project.bank.model.dto;

public class TransactionAccountNumDto {
	
	private String accountNum;
	private String accountType;
	private String amount; //거래 금액
	private String accountCounter; //이체계좌
	private int balance;// 거래후 계좌 총액
	
	
	public TransactionAccountNumDto () {
		
	}
	
	



	public TransactionAccountNumDto(String accountNum, String accountType, String amount, String accountCounter,
			int balance) {
		super();
		this.accountNum = accountNum;
		this.accountType = accountType;
		this.amount = amount;
		this.accountCounter = accountCounter;
		this.balance = balance;
	}





	@Override
	public String toString() {
		return  "계좌번호:"+ this.accountNum+", 거래유형: " + accountType+ ", 거래금액: " + amount + ", 계좌금액: " + balance+" 이체계좌: "+this.accountCounter+"\n";
	}
}
