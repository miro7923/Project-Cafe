package com.project.cafe.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;

public class BoardWriteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardWriteAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 파라메터를 DTO에 저장
		
		// DB에 DTO 보내서 저장
		
		// 완료되면 글 목록 페이지로 이동
		
		return null;
	}
}
