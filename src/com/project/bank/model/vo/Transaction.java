package com.project.bank.model.vo;

import java.time.LocalDate;

public class Transaction {

	
	private int id;
	private int accountID; //pk키
	private String transactionType;
	private int amount;
	private String counterpartyAccountNum;
	
	
	public  Transaction() {
		
	}
	
	
	public Transaction(int accountID, String transactionType, int amount, String counterpartyAccountNum) {
		super();
		this.accountID = accountID;
		this.transactionType = transactionType;
		this.amount = amount;
		this.counterpartyAccountNum = counterpartyAccountNum;
	}


	public int getAccountID() {
		return accountID;
	}




	public String getTransactionType() {
		return transactionType;
	}



	public int getAmount() {
		return amount;
	}



	public String getCounterpartyAccountNum() {
		return counterpartyAccountNum;
	}


	@Override
	public String toString() {
		return "transactionType=" + transactionType + ", amount=" + amount + ", counterpartyAccountNum="
				+ counterpartyAccountNum;
	}


	

	
	
	
	
}
