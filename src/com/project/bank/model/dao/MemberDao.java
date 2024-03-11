package com.project.bank.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.project.bank.connection.DBconnection;
import com.project.bank.model.vo.Account;
import com.project.bank.model.vo.Member;


public class MemberDao{

	private Properties member =new Properties();
	
	
	public MemberDao() {
		
		try {
			
			member.loadFromXML(new FileInputStream("resources/memberData.xml"));
			
			
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	public int insertUser(Connection con,Member u) {
		
		String sql=member.getProperty("insertUser");
		Connection conn=con;
		PreparedStatement pstmt=null;
		int result=0;
		
		
		try {
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, u.getName());
			pstmt.setString(2,u.getMemberNo());
			pstmt.setString(3,u.getGender());
			pstmt.setString(4,u.getAddress());
			pstmt.setString(5,u.getPhoneNum());
			pstmt.setString(6,u.getEmail());
			pstmt.setDate(7,u.getBirth());
			
			result=pstmt.executeUpdate();
		
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			 DBconnection.close(pstmt);
	    }
		return result;
		
	}
	
	public int deleteMember(Connection con,String name,String memberNo) {
		String sql=member.getProperty("deleteMember");
		Connection conn=con;
		PreparedStatement pstmt=null;
		
		int result=0;
		
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,name);
			pstmt.setString(2,memberNo);
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
		      
			System.out.println("계좌를 먼저 삭제해 주세요");
		}
		
		finally {
			 DBconnection.close(pstmt);
		}
		
		return result;
	
		
	}
	
	public Member selectMember(Connection con,String name,String memberNo) {//본인 개인정보조회
		
		String sql=member.getProperty("selectMember");
		Connection conn=con;
		PreparedStatement pstmt =null;
		ResultSet reSet=null;
		Member member=null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, memberNo);
			
			reSet=pstmt.executeQuery();
			
			if(reSet.next()) {
				member =new Member(
						reSet.getString("NAME"),
						reSet.getString("MEMBERNO"),
						reSet.getString("GENDER"),
						reSet.getString("ADDRESS"),
						reSet.getString("PHONENUM"),
						reSet.getString("EMAIL"),
						reSet.getDate("BIRTH")
			   );
		   }
				
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			 DBconnection.close(reSet);
			 DBconnection.close(pstmt);
		}
		
		return member;
		
		
		
	}

	public int findMemberId(Connection con,String name,String memberNo) {
		
		String sql=member.getProperty("findMemberId");
		Connection conn=con;
		PreparedStatement pstmt =null;
		ResultSet reSet=null;
	    int id=-1;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, memberNo);
			
			
			reSet=pstmt.executeQuery();
			
			if(reSet.next()) {
				id=reSet.getInt("ID");
			}
			
	   } catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			 DBconnection.close(pstmt);
			
		}
		return id;
		
		
		
		
	}
	
	
	
	
}
