package com.project.bank.controller;


import com.project.bank.model.vo.Member;
import com.project.bank.service.MemberService;
import com.project.bank.view.View;

public class MemberController {
	
	
	private MemberService service;
	
	public MemberController() {
	    service=new MemberService();
		
	}

	
	
	public void MemberCreate(Member u) {
		
		int result=this.service.MemberCreate(u);
		String re="등록";
		if(result>0) {
			new View().SuccessMessage(re);
		}
		else {
			new View().FailMessage(re);
		}
		
	}
	
	public void selectMember(String name,String memberNo) {
		
		
		Member member=this.service.selectMember(name, memberNo);
		if(member==null) {
			new View().Message("정보가없습니다.");
		}
		else {
			new View().Message(member.toString());
		}
		
	}
	
	//>0일때 회원을 찾는다.
	public int findMemberId(String name ,String memberNo) {
		
	
		return this.service.findMemberId(name, memberNo);
		
	}
	
	public void deleteMember(String name,String memberNo) {
		
		
		int result=this.service.deleteMember(name, memberNo);
		
		if(result>0) {
	        new View().SuccessMessage("삭제");
		}else {
			new View().FailMessage("삭제");
		}
		
	}
	
	
	
	
	
}
