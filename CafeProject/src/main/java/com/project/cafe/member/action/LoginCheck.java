package com.project.cafe.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.member.db.MemberDAO;
import com.project.cafe.member.db.MemberDTO;

public class LoginCheck implements Action
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : LoginCheck - execute() 호출");
		
		PrintWriter out = response.getWriter();
		HttpSession session = null;
		MemberDAO dao = new MemberDAO();
		
		MemberDTO dto = new MemberDTO();
		dto.setId(request.getParameter("id"));
		dto.setPass(request.getParameter("pass"));
		
		int result = dao.loginCheck(dto);
		if (1 == result)
		{
			session = request.getSession();
			session.setAttribute("id", dto.getId());
			session.setMaxInactiveInterval(600);
		}
		
		out.print(result);
		
		out.close();

		return null;
	}
}
