package com.project.cafe.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.api.sms.SmsService;

public class MemberFrontController extends HttpServlet
{
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// 1. 전달되는 가상주소 계산
		// 매핑된(.me로 끝나는) 주소를 받아옴
		String requestURI = req.getRequestURI();
		System.out.println("requestURI : " + requestURI);
		
		// 매핑된 해당 프로젝트 주소를 구함
		String ctxPath = req.getContextPath();
		System.out.println("ctxPath : " + ctxPath);
		
		// 매핑된 주소 - 프로젝트 주소 = 계속 바뀔 뒷자리 주소 구함
		String command = requestURI.substring(ctxPath.length());
		System.out.println("command : " + command);
		
		System.out.println("C : 가상주소 계산 완료\n");
		// 1. 전달되는 가상주소 계산
		
		
		// 2. 가상주소 매핑
		Action action = null;
		ActionForward forward = null;

		if (command.equals("/MemberJoinAction.me"))
		{
			System.out.println("C : /MemberJoinAction.me 호출");
			System.out.println("C : 이전 페이지 정보를 가져와서 DB 테이블에 저장 후 페이지 이동");
			
			action = new MemberJoinAction(); // 인터페이스를 통해 객체를 생성함으로써 약한결합이 되도록 한다.
			
			try 
			{
				forward = action.execute(req, resp);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else if (command.equals("/memberUpdateAction.me"))
		{
			System.out.println("C : /memberUpdateAction.me 호출");
			
			action = new MemberUpdateAction();
			
			try {
				forward = action.execute(req, resp);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			forward = new ActionForward();

			if (command.equals("/main.me"))
			{
				System.out.println("C : 메인페이지 호출");
				forward.setPath("./main/main.jsp");				
			}
			else if (command.equals("/join.me"))
			{
				System.out.println("C : 회원가입 페이지 호출");
				forward.setPath("./member/join.jsp");				
			}
			else if (command.equals("/login.me"))
			{
				System.out.println("C : 로그인 페이지 호출");
				forward.setPath("./member/login.jsp");				
			}
			else if (command.equals("/logout.me"))
			{
				System.out.println("C : 로그아웃 페이지 호출");
				forward.setPath("/logout.me");
			}
			else if (command.equals("/myPage.me"))
			{
				System.out.println("C : 마이페이지 호출");
				forward.setPath("./member/myPage.jsp");
			}
			
			forward.setRedirect(false);
		}
		
		System.out.println("C : 가상주소 매핑 완료\n");
		// 2. 가상주소 매핑
		
		
		// 3. 페이지 이동
		if (null != forward) // 페이지 이동정보가 있을 때
		{
			if (forward.isRedirect())
			{
				System.out.println("C : sendRedirect 방식 - " + forward.getPath() + " 이동");
				resp.sendRedirect(forward.getPath());
			}
			else 
			{
				System.out.println("C : forward 방식 - " + forward.getPath() + " 이동");
				RequestDispatcher dis = req.getRequestDispatcher(forward.getPath());
				dis.forward(req, resp);
			}
			
			System.out.println("C : 페이지 이동 완료");
		}
		// 3. 페이지 이동
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
