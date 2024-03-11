package com.project.bank.model.vo;

import java.sql.Date;

public class Member {

	private int id;
	private String name;
	private String memberNo;
	private String gender;
	private String address;
	private String phoneNum;
	private String email;
	private Date birth;
	
	public Member() {
		
	}
	
	public Member(String name, String memberNo, String gender, String address, String phoneNum, String email,
			Date birth) {
		super();
		this.name = name;
		this.memberNo = memberNo;
		this.gender = gender;
		this.address = address;
		this.phoneNum = phoneNum;
		this.email = email;
		this.birth = birth;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "이름: " + name + ", 주민번호: "+memberNo +", 성별: " + gender + ", 주소: " + address
				+ ", 전화번호: " + phoneNum + ", 이메일: " + email + ", 생일: " + birth;
	}
	
	
	
	
}
