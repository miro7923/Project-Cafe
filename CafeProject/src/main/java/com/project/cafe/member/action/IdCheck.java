package com.project.cafe.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.member.db.MemberDAO;

public class IdCheck implements Action
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : IdCheck - execute() 호출");
		
		MemberDAO dao = new MemberDAO();
		PrintWriter out = response.getWriter();
		boolean result = dao.isExist(request.getParameter("id"));
		if (result)
			out.print("false");
		else 
			out.print("true");
		
		out.close();
		
		return null;
	}
}
