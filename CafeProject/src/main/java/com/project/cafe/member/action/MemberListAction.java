package com.project.cafe.member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.member.db.MemberDAO;
import com.project.cafe.member.db.MemberDTO;

public class MemberListAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : MemberListAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("utf-8");
		
		// DB에서 전체 회원 목록 불러오기
		MemberDAO dao = new MemberDAO();
		ArrayList<MemberDTO> memberList = dao.getMemberList();
		
		// request에 저장
		request.setAttribute("memberList", memberList);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./admin/memberManagement.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
}
