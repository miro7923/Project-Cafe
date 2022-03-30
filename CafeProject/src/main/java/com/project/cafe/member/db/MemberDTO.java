package com.project.cafe.member.db;

import java.sql.Date;
import java.sql.Timestamp;

public class MemberDTO 
{
	private int member_num;
	private String id;
	private String pass;
	private String name;
	private Date birth;
	private int age;
	private String gender;
	private int postalcode;
	private String address;
	private String phone;
	private String email;
	private Timestamp regdate;
	
	public int getMemberNum() {return member_num;}
	public void setMemberNum(int num) {this.member_num = num;}
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	public String getPass() {return pass;}
	public void setPass(String pass) {this.pass = pass;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Date getBirth() {return birth;}
	public void setBirth(Date birth) {this.birth = birth;}
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	public String getGender() {return gender;}
	public void setGender(String gender) {this.gender = gender;}
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}
	public int getPostalcode() {return postalcode;}
	public void setPostalcode(int postalcode) {this.postalcode = postalcode;}
	public String getPhone() {return phone;}
	public void setPhone(String phone) {this.phone = phone;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public Timestamp getRegdate() {return regdate;}
	public void setRegdate(Timestamp regdate) {this.regdate = regdate;}
	
	@Override
	public String toString() {
		return "MemberDTO [member_num=" + member_num + ", id=" + id + ", pass=" + pass + ", name=" + name + ", birth=" + birth
				+ ", age=" + age + ", gender=" + gender + ", address=" + address + ", phone=" + phone + ", email="
				+ email + ", regdate=" + regdate + "]";
	}
}
