package com.project.cafe.member.action;

public class ActionForward 
{
	// 페이지를 이동할 때 필요한 정보를 저장하는 클래스
	
	private String path; // 이동경로
	private boolean isRedirect; // 이동방식
	
	// isRedirect = true  => 주소가 바뀌고 화면도 바뀜
	// isRedirect = false  => 주소는 바뀌지 않고 화면만 바뀜
	
	public String getPath() {return path;}
	public void setPath(String path) {this.path = path;}
	public boolean isRedirect() {return isRedirect;}
	public void setRedirect(boolean isRedirect) {this.isRedirect = isRedirect;}
}
