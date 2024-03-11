package com.project.bank.service;

import java.sql.Connection;

import com.project.bank.connection.DBconnection;
import com.project.bank.model.dao.MemberDao;
import com.project.bank.model.vo.Member;
import com.project.bank.view.View;

public class MemberService {
	private MemberDao mDo;
	
	public MemberService() {
		mDo=new MemberDao();
	
	}

	
	
	public int MemberCreate(Member u) {
		Connection con = DBconnection.getConnection();
		int result=this.mDo.insertUser(con,u);
		if(result>0) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		DBconnection.close(con);
		return result;
	}
	
	public Member selectMember(String name,String memberNo) {
		Connection con = DBconnection.getConnection();
		Member mem=this.mDo.selectMember(con,name,memberNo);
		if(mem==null) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		DBconnection.close(con);
	
		return mem;
		
	}
	
	//>0일때 회원을 찾는다.
	public int findMemberId(String name ,String memberNo) {
	   
		Connection con = DBconnection.getConnection();
		int result=this.mDo.findMemberId(con,name,memberNo);
		if(result>0) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		DBconnection.close(con);
	    return result;
	}
	
	public int deleteMember(String name,String memberNo) {
		
		Connection con = DBconnection.getConnection();
		int result=this.mDo.deleteMember(con,name,memberNo);
		if(result>0) {
			DBconnection.commit(con);
		}else {
			DBconnection.rollback(con);
		}
		DBconnection.close(con);
		return result;
	}
}
