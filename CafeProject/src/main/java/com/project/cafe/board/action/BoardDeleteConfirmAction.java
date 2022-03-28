package com.project.cafe.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;

public class BoardDeleteConfirmAction implements Action
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardDeleteConfirmAction - execute() 호출");
		
		// 파라메터 저장
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		// 한글처리
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print("<script>");
		out.print("var flag = confirm('게시글을 삭제하시겠습니까?');");
		out.print("if (flag)");
		out.print("location.href='./BoardDelete.bo?num="+num+"&pageNum="+pageNum+"';");
		out.print("else");
		out.print("history.back();");
		out.print("</script>");
		
		out.close();
		
		return null;
	}
}
