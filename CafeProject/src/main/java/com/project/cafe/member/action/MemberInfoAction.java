package com.project.cafe.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.member.db.MemberDAO;
import com.project.cafe.member.db.MemberDTO;

public class MemberInfoAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : MemberInfoAction - execute() 호출");
		
		// 파라메터 저장
		String memberId = request.getParameter("id");
		
		// DB 접속해서 회원 정보 가져오기
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(memberId);
		
		// request 영역에 회원정보 저장
		request.setAttribute("dto", dto);

		// 회원 상세 정보 페이지로 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./admin/memberInfo.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
}
