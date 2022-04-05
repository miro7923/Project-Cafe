package com.project.cafe.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.member.db.MemberDAO;
import com.project.cafe.member.db.MemberDTO;

public class MemberContentAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : MemberContentAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("utf-8");
		
		// 세션에 있는 아이디 정보 저장
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		// DB 접속해서 해당 회원정보 가져오기
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
		
		// 세션에 저장
		session.setAttribute("dto", dto);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./myPage.me");
		forward.setRedirect(true);
		
		return forward;
	}
}
