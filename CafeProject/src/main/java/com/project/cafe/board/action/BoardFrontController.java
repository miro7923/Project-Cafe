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
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
		else if (command.equals("/BoardList.bo"))
		{
			System.out.println("C : /BoardList.bo 호출");
			
			action = new BoardListAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/BoardContent.bo"))
		{
			System.out.println("C : /BoardContent.bo 호출");
			
			action = new BoardContentAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/BoardModify.bo"))
		{
			System.out.println("C : /BoardModify.bo 호출");
			
			action = new BoardModifyAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/BoardModifyPro.bo"))
		{
			System.out.println("C : /BoardModifyPro.bo 호출");
			
			action = new BoardModifyProAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/BoardDelete.bo"))
		{
			System.out.println("C : /BoardDelete.bo 호출");
			
			action = new BoardDeleteAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/BoardReWriteAction.bo"))
		{
			System.out.println("C : /BoardReWriteAction.bo 호출");
			
			action = new BoardReWriteAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/GetFeed.bo"))
		{
			System.out.println("C : /GetFeed.bo 호출");
			
			action = new GetFeed();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/SendMailAction.bo"))
		{
			System.out.println("C : /SendMailAction.bo 호출");
			
			action = new SendMailAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/BoardFileDownloadAction.bo"))
		{
			System.out.println("C : /BoardFileDownloadAction.bo 호출");
			
			action = new BoardFileDownloadAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/BoardImgAction.bo"))
		{
			System.out.println("C : /BoardImgAction.bo 호출");
			
			action = new BoardImgAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/CommentWriteAction.bo"))
		{
			System.out.println("C : /CommentWriteAction.bo 호출");
			
			action = new CommentWriteAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/CommentModifyAction.bo"))
		{
			System.out.println("C : /CommentModifyAction.bo 호출");
			
			action = new CommentModifyAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/CommentDeleteAction.bo"))
		{
			System.out.println("C : /CommentDeleteAction.bo 호출");
			
			action = new CommentDeleteAction();
			
			try {
				forward = action.execute(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/AdminDeleteAction.bo"))
		{
			System.out.println("C : /AdminDeleteAction.bo 호출");
			
			action = new AdminDeleteAction();
			
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

			if (command.equals("/BoardWrite.bo"))
			{
				System.out.println("C : /BoardWrite.bo 호출");
				
				forward.setPath("./contents/boardWrite.jsp");
			}
			else if (command.equals("/BoardReWrite.bo"))
			{
				System.out.println("C : /BoardReWrite.bo 호출");
				
				forward.setPath("./contents/boardReWrite.jsp");
			}
			else if (command.equals("/Contact.bo"))
			{
				System.out.println("C : ./Contact.bo 호출");
				
				forward.setPath("./contents/contactUs.jsp");
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
