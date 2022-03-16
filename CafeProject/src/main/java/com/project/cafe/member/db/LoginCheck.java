package com.project.cafe.member.db;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginCheck.me")
public class LoginCheck extends HttpServlet
{
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		System.out.println("M : LoginCheck - doProcess() 호출");
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=utf-8");
		
		PrintWriter out = resp.getWriter();
		HttpSession session = null;
		MemberDAO dao = new MemberDAO();
		
		MemberDTO dto = new MemberDTO();
		dto.setId(req.getParameter("id"));
		dto.setPass(req.getParameter("pass"));
		
		int result = dao.loginCheck(dto);
		if (1 == result)
		{
			session = req.getSession();
			session.setAttribute("id", dto.getId());
			session.setMaxInactiveInterval(600);
		}
		
		out.print(result);
		
		out.close();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		doProcess(req, resp);
	}
}
