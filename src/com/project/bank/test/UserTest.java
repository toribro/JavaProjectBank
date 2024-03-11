package com.project.bank.test;

import java.sql.Date;

import com.project.bank.model.dao.MemberDao;
import com.project.bank.model.vo.Member;
import com.project.bank.service.MemberService;

public class UserTest {

	public static void main(String[] args) {
	    회원가입();
		
		//회원탈퇴();
       회원조회();
	}
	
	public static void 회원가입() {
		MemberService mo =new MemberService();
		;
		
		Member member=new Member(
				"신토리",
				"980416-",
				"M",
				"서울",
				"01051533761",
				"tori@naver.com",
				Date.valueOf("1998-04-16")
		);
		mo.MemberCreate(member);
		
	}
	
	public static void 회원조회() {
		MemberService mo =new MemberService();
		
		Member m=mo.selectMember("신토리","980416-");
		System.out.println(m);
		
		
	}
	
	public static void 회원탈퇴() {
		MemberService mo =new MemberService();
		
		int m=mo.deleteMember("신토리","980416-");
		System.out.println(m);
		
	}

}
