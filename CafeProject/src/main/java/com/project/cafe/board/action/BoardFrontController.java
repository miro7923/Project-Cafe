package com.project.cafe.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;

public class BoardFrontController extends HttpServlet
{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// 1. 전달되는 가상주소 계산
		// 매핑된(.bo로 끝나는) 주소를 받아옴
		String requestURI = request.getRequestURI();
		System.out.println("requestURI : " + requestURI);
		
		// 매핑된 해당 프로젝트 주소 구함
		String ctxPath = request.getContextPath();
		System.out.println("ctxPath : " + ctxPath);
		
		// 매핑된 주소(requestURI)에서 프로젝트 주소(ctxPath)를 빼서 계속 바뀌는 맨 뒤 주소를 구함
		String command = requestURI.substring(ctxPath.length());
		System.out.println("command : " + command);
		
		System.out.println("1. 가상주소 계산 완료\n");
		
		
		// 2. 가상주소 매핑
		Action action = null;
		ActionForward forward = null;
		
		if (command.equals("/BoardWriteAction.bo"))
		{
			System.out.println("C : /BoardWriteAction.bo 호출");
			
			action = new BoardWriteAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/boardList.bo"))
		{
			System.out.println("C : /boardList.bo 호출");
			
			action = new BoardListAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else 
		{
			forward = new ActionForward();

			if (command.equals("/boardWrite.bo"))
			{
				System.out.println("C : /boardWrite.bo 호출");
				
				forward.setPath("./contents/boardWrite.jsp");
			}			

			forward.setRedirect(false);
		}
		
		System.out.println("2. 가상주소 매핑 완료");
		
		
		// 3. 페이지 이동
		if (null != forward)
		{
			// 페이지 정보가 있을 때
			if (forward.isRedirect())
			{
				System.out.println("C : sendRedirect 방식으로 페이지 이동 : " + forward.getPath());
				response.sendRedirect(forward.getPath());
			}
			else 
			{
				System.out.println("C : forward 방식으로 페이지 이동 : " + forward.getPath());
				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
			}
			
			System.out.println("3. 페이지 이동 완료");
		}
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
