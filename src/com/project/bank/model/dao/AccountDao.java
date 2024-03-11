package com.project.bank.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.project.bank.connection.DBconnection;
import com.project.bank.model.dto.AccountDto;
import com.project.bank.model.vo.Account;
import com.project.bank.model.vo.Member;

public class AccountDao {
	 

	
	
	private Properties account =new Properties();

	
	
	public AccountDao() {
		
		try {
		
			account.loadFromXML(new FileInputStream("./resources/accountData.xml"));
			
			
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
    public int insertAccount(Connection con,Account c) {//계좌 생성
		
		String sql=account.getProperty("insertAccount");
		Connection conn=con;
		PreparedStatement pstmt=null;
		int result=0;
		
		
		try {
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, c.getMemberId());
			pstmt.setString(2,c.getAccountNumber());
			pstmt.setString(3,c.getAccountPassword());
			pstmt.setString(4,c.getAccountType());
		    result=pstmt.executeUpdate();
		
			
			
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		finally {
			DBconnection.close(pstmt);
		 }
		return result;
		
	}
	
	public AccountDto selectAccount(Connection con,String name,
			String accountNum,String accountPassword) {
		
		String sql=account.getProperty("selectAccount");
		
		Connection conn=con;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		
	    AccountDto dto=null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, accountNum);
			pstmt.setString(3, accountPassword);
		
			
			result=pstmt.executeQuery();
			
			if(result.next()) {
				 dto=new AccountDto(
						result.getString("NAME"),
						result.getString("ACCOUNT_NUMBER"),
						result.getString("ACCOUNT_TYPE"),
						result.getDate("OPENING_DATE"),
						result.getInt("BALANCE")
				);
				
				
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			
			DBconnection.close(result);
			DBconnection.close(pstmt);
		}
		
		return dto;
		
	}
	
	
	public ArrayList<AccountDto> selectAccounts(Connection con,String name,
			String memberNo) {
		
		String sql=account.getProperty("selectAccounts");
		
		Connection conn=con;
		PreparedStatement pstmt=null;
		ResultSet result=null;
		ArrayList<AccountDto> dtos=new ArrayList<>();
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, memberNo);
			
			result=pstmt.executeQuery();
			
			while(result.next()) {
				 AccountDto dto=new AccountDto(
						result.getString("NAME"),
						result.getString("ACCOUNT_NUMBER"),
						result.getString("ACCOUNT_TYPE"),
						result.getDate("OPENING_DATE"),
						result.getInt("BALANCE")
				);
				dtos.add(dto); 
				
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			
			DBconnection.close(result);
			DBconnection.close(pstmt);
			
			 
		}
	
		
		return dtos;
		
	}
	
	
	public int deleteAccount(Connection con,String accountNum,String accountPassword) {
		String sql=account.getProperty("deleteAccount");
		Connection conn=con;
		PreparedStatement pstmt=null;
		
		int result=0;
		
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,accountNum);
			pstmt.setString(2,accountPassword);
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		finally {
			
			DBconnection.close(pstmt);
		}
		
		return result;
	
		
	}
	


}
