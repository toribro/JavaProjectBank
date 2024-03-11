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
import com.project.bank.model.dto.TransactionAccountNumDto;
import com.project.bank.model.dto.TransactionDto;

public class TransactionDao {
	
	
	private Properties transaction =new Properties();
	
	
   
	
	public TransactionDao() {
		
		try {
		
			transaction.loadFromXML(new FileInputStream("resources/transactionData.xml"));
			
			
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	
	//계좌 아이디 조회(private)
	public int findByAccountId(Connection con,String accountNum) {
		
		int id=-1;
		String sql=transaction.getProperty("findByAccountId1");
		Connection conn=con;
		PreparedStatement pstmt=null;
		ResultSet re=null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,accountNum);
		    
			re=pstmt.executeQuery();
			
			if(re.next()) {
				System.out.println("계좌조회 성공");
				id=re.getInt("ID");
				conn.commit();
			}
			else {
				System.out.println("계좌가 없거나 잘못입력하셨습니다..");
				conn.rollback();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBconnection.close(re);
			DBconnection.close(pstmt);
		}
	
		return id;
		
	}
	
	
	//계좌 아이디 조회(private)
		public int findByAccountId(Connection con,String accountNum,String accountPassword) {
			
			int id=-1;
			String sql=transaction.getProperty("findByAccountId2");
			Connection conn=con;
			PreparedStatement pstmt=null;
			ResultSet re=null;
			
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1,accountNum);
				pstmt.setString(2,accountPassword);
			    
				re=pstmt.executeQuery();
				
				if(re.next()) {
					id=re.getInt("ID");
					conn.commit();
				}
				else {
					System.out.println("계좌가 없거나 잘못입력하셨습니다..");
					conn.rollback();
				}
				
				
		    } catch (SQLException e) {
				
			}
			finally {
				DBconnection.close(re);
				DBconnection.close(pstmt);
			}
		
			return id;
			
		}
	
       private int findBalance(Connection con,int id) {
			
			int balance=-1;
			String sql=transaction.getProperty("findBalance");
			Connection conn=con;
			PreparedStatement pstmt=null;
			ResultSet re=null;
			
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1,id);
			    
				re=pstmt.executeQuery();
				
				if(re.next()) {
				
					balance=re.getInt("BALANCE");
					conn.commit();
				}
				else {
					
					conn.rollback();
				}
				
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			finally {
				DBconnection.close(re);
				DBconnection.close(pstmt);
			}
		
			return balance;
			
		}
	
	//입금
       
       
       
       
	public int insertTransaction(Connection con,int amount,String accountNum) {
		
		
		int id=findByAccountId(con,accountNum);
		if (id==-1)return -1;
		
		int balance =findBalance(con,id);
		
		
		
		String sql=transaction.getProperty("insertTransaction_Input");
		Connection conn=con;
		PreparedStatement pstmt=null;
		int result=0;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, amount);
			pstmt.setInt(2, id);
			pstmt.setString(3,accountNum);
			pstmt.setInt(4, balance);
			
			
			result=pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBconnection.close(pstmt);
		}
	
		return result;
		
	}
	
	
	//출금
     public int insertTransaction(Connection conn,int amount,String accountNum,String accountPassword) {
		
    		int id=findByAccountId(conn,accountNum,accountPassword);
    		if (id==-1)return -1;
    		
    		int balance =findBalance(conn,id);
    		
    		

    		String sql=transaction.getProperty("insertTransaction_Output");
    		Connection con=conn;
    		PreparedStatement pstmt=null;
    		int result=0;
    		
    		try {
    			pstmt=conn.prepareStatement(sql);
    			pstmt.setInt(1, amount);
    			pstmt.setInt(2, id);
    			pstmt.setString(3,accountNum);
    			pstmt.setInt(4, balance);
    			
    			result=pstmt.executeUpdate();
    			
    			
    		} catch (SQLException e) {
    			
    			e.printStackTrace();
    		}
    		finally {
    			DBconnection.close(pstmt);
    		}
    	
    		return result;
		
	}
	
	
	//이체
	
     public int insertTransaction(Connection con,int amount,String counterAccount,String accountNum,String accountPassword) {
 		
 		int id=findByAccountId(con,accountNum,accountPassword);
 		int countId=findByAccountId( con,counterAccount);
 		if (id==-1||countId==-1)return -1;
 		else if (id==countId) return -2;
 		
 		int mybalance =findBalance(con,id);
 		int counterbalance =findBalance(con,countId);
 
 		

 		String sql=transaction.getProperty("insertTransaction_transmit");
 		Connection conn=con;
 		PreparedStatement pstmt=null;
 		int result=0;
 		
 		try {
 			pstmt=conn.prepareStatement(sql);
 			pstmt.setInt(1, amount);
 			pstmt.setInt(2, id);
 			pstmt.setString(3, accountNum);
 			pstmt.setInt(4,countId);
 			pstmt.setString(5,counterAccount);
 			pstmt.setInt(6,mybalance);
 			pstmt.setInt(7, counterbalance);
 			
 			result=pstmt.executeUpdate();
 		
 			
 			
 		} catch (SQLException e) {
 			System.out.println("잔액이부족합니다.");
 			
 		}
 		finally {
 			DBconnection.close(pstmt);
 		}
 	
 		return result;
		
	}
     
     //거래목록
	public ArrayList<TransactionDto> selectTransaction(Connection con,String name,String accountNum,String accountPassword) {
		
		ArrayList<TransactionDto> dtos=new ArrayList<TransactionDto>();
		
		//int id=findByAccountId(con,accountNum,accountPassword);
 		//if (id==-1)return dtos;
		
 		
 		String sql=transaction.getProperty("selectTransaction");
 		
		Connection conn=con;
		PreparedStatement pstmt=null;
		ResultSet re=null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,accountNum);
			pstmt.setString(2, accountPassword);
			pstmt.setString(3,name);
			
			re=pstmt.executeQuery();
			
			
			while(re.next()) {
				TransactionDto dto=new TransactionDto(
						re.getString("TRANSACTION_TYPE"),
						re.getString("AMOUNT"),
						re.getString("이체계좌"),
						re.getInt("거래후 계좌 잔액")
				);
				dtos.add(dto);
				
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			DBconnection.close(re);
			DBconnection.close(pstmt);
		}
		
		return dtos;
 		
	}
	
   public ArrayList<TransactionAccountNumDto> selectALLTransaction(Connection con,String name,String memberNo) {
		
		
		
 		
 		ArrayList<TransactionAccountNumDto> dtos=new ArrayList<TransactionAccountNumDto>();
 		 
		String sql=transaction.getProperty("selectALLTransaction");
 		
		Connection conn= con;
		PreparedStatement pstmt=null;
		ResultSet re=null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, memberNo);
			pstmt.setString(2,name);
			
			
			re=pstmt.executeQuery();
			
			
			while(re.next()) {
				TransactionAccountNumDto dto=new TransactionAccountNumDto(
						re.getString("ACCOUNT_NUMBER"),
						re.getString("TRANSACTION_TYPE"),
						re.getString("AMOUNT"),
						re.getString("이체계좌"),
						re.getInt("거래후 계좌 잔액")
				);
				dtos.add(dto);
				
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			
			DBconnection.close(re);
			DBconnection.close(pstmt);
			
		}
		
		return dtos;
     }	

   
   
  
 	
}
