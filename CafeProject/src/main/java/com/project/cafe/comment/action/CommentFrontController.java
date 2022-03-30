package com.project.cafe.comment.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.action.CommentListAction;

public class CommentFrontController extends HttpServlet 
{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("C : controller 호출");
		
		// 1. 가상주소 계산
		String requestURI = request.getRequestURI();
		System.out.println("requestURI : " + requestURI);
		
		String ctxPath = request.getContextPath();
		System.out.println("ctxPath : " + ctxPath);
		
		String command = requestURI.substring(ctxPath.length());
		System.out.println("command : " + command);
		
		System.out.println("1. 가상주소 계산 완료");
		
		// 2. 가상주소 매핑
		Action action = null;
		ActionForward forward = null;
		
		if (command.equals("/CommentList.co"))
		{
			System.out.println("C : /CommentList.co 호출");
			
			action = new CommentListAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("2. 가상주소 매핑 완료");
		
		// 3. 페이지 이동
		if (forward != null)
		{
			if (forward.isRedirect())
			{
				// 주소 바꾸면서 이동
				System.out.println("C : sendRedirect 방식으로 이동 - " + forward.getPath());
				response.sendRedirect(forward.getPath());
			}
			else 
			{
				// 주소 변경 없이 포워딩
				System.out.println("C : forward 방식으로 이동 - " + forward.getPath());
				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
			}
		}
		
		System.out.println("3. 페이지 이동 완료");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doProcess(request, response);
	}
}
