package com.project.bank.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.bank.connection.DBconnection;
import com.project.bank.model.dao.AccountDao;
import com.project.bank.model.dto.AccountDto;
import com.project.bank.model.vo.Account;

public class AccountService {
    
	
	private AccountDao account;
	
	public AccountService() {
	
		this.account = new AccountDao();
	}

	public int insertAccount(Account c) {//계좌 생성
		Connection con=DBconnection.getConnection();
		int result=this.account.insertAccount(con,c);
		if(result>0) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		
		DBconnection.close(con);
		
		return result;
		
	}
	
	public AccountDto selectAccount(String name,
			String accountNum,String accountPassword) {
		
		Connection con=DBconnection.getConnection();
		AccountDto result=this.account.selectAccount(con,name,accountNum,accountPassword);
		
		DBconnection.close(con);
		
		return result;
    }
	
	
	public ArrayList<AccountDto> selectAccounts(String name,
			String memberNo) {
	
	
		Connection con=DBconnection.getConnection();
		ArrayList<AccountDto> results=this.account.selectAccounts(con,name,memberNo);
		DBconnection.close(con);
		
		return results;
	
		
	}
	
	
	public int deleteAccount(String accountNum,String accountPassword) {
		
		
		Connection con=DBconnection.getConnection();
		int result=this.account.deleteAccount(con,accountNum,accountPassword);
		if(result>0) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		
		DBconnection.close(con);
		
		return result;
	
		
	}

	
}



