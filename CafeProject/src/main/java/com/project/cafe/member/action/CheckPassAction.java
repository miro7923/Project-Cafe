package com.project.cafe.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.member.db.MemberDAO;
import com.project.cafe.member.db.MemberDTO;

public class CheckPassAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : CheckPassAction - execute() 호출");
		
		// 파라메터 저장
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		// 회원정보 저장
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPass(pass);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		ActionForward forward = new ActionForward();
		
		// DB 연동해서 아이디와 비밀번호가 일치하는 지 확인
		MemberDAO dao = new MemberDAO();
		int result = dao.loginCheck(dto);
		if (1 == result)
		{
			// 비번 일치
			forward.setPath("./myPage.me");
		}
		else 
		{
			// 비번 불일치
			out.println("<script>alert('비밀번호가 일치하지 않습니다!');</script>");
			forward.setPath("./checkPass.me");
		}
		
		forward.setRedirect(true);
		out.close();
		
		return forward;
	}
}
