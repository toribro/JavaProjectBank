package com.project.bank.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.project.bank.connection.DBconnection;
import com.project.bank.model.dao.TransactionDao;
import com.project.bank.model.dto.TransactionAccountNumDto;
import com.project.bank.model.dto.TransactionDto;
import com.project.bank.view.View;

public class TransactionService {
private TransactionDao tDao;
	
	public TransactionService () {
		tDao=new TransactionDao();
		
	}
	
	
	//입금
	public int InputMoney(int amount,String accountNum) {
		Connection con = DBconnection.getConnection();
		int result=this.tDao.insertTransaction(con,amount, accountNum);
		if(result>0) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		DBconnection.close(con);
		
		return result;
	}
	
	//출금
	
	public int OutputMoney(int amount,String accountNum,
			String accountPassword) {
		
		Connection con = DBconnection.getConnection();
		int result=this.tDao.insertTransaction(con,amount, accountNum,accountPassword);
		if(result>0) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		DBconnection.close(con);
		
		return result;
	
		
	}

	//이체
	
	public int TransmitMoney(int amount,String counterAccount,String accountNum
			,String accountPassword) {
		
		Connection con = DBconnection.getConnection();
		
		
	    int result=this.tDao.insertTransaction(con,amount, counterAccount,accountNum,accountPassword);
		if(result>0) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		DBconnection.close(con);
		
		return result;
		
	}
	
	public int checkTransmitAccount(String accountNum,String accountPassword, String accountAccount ) {
		Connection con = DBconnection.getConnection();
		
		int id=this.tDao.findByAccountId(con,accountNum,accountPassword);
 		int countId=this.tDao.findByAccountId(con,accountAccount);
 		if (id==-1||countId==-1)return -1;
 		else if (id==countId) return -2;
 		else return 1;
 		
 		
		
	}
	
	
	//거래목록
	
	public ArrayList<TransactionDto> TransactionList(String name,String accountNum,String accountPassword) {
		
		Connection con = DBconnection.getConnection();
		ArrayList<TransactionDto> dtos=tDao.selectTransaction(con,name, accountNum, accountPassword);
		DBconnection.close(con);
		
	   return dtos;
	}
	
	
	public ArrayList<TransactionAccountNumDto> TransactionAllList(String name,String memberNo) {
		Connection con = DBconnection.getConnection();
		ArrayList<TransactionAccountNumDto> dtos=tDao.selectALLTransaction(con,name,memberNo);
		DBconnection.close(con);
		
	    return dtos;
	}
	
	
	
}
