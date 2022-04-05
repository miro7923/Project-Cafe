package com.project.cafe.board.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;

public class AdminDeleteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : AdminDeleteAction - execute() 호출");
		
		// 파라메터 받기
		String[] params = request.getParameterValues("post");
		
		ArrayList<Integer> postNums = new ArrayList<Integer>();
		for (String p : params)
			postNums.add(Integer.parseInt(p));
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>");
		
		// DB 접속해서 선택된 글들 삭제
		BoardDAO dao = new BoardDAO();
		for (int p : postNums)
		{
			int result = dao.deletePost(p);
			if (result != 1)
			{
				out.print("alert('잘못된 접근!');");
				out.print("history.back()");
				out.print("</script>");
				
				out.close();
				
				return null;
			}
		}
		
		// 게시글 관리 페이지로 이동
		out.print("alert('선택한 게시글 삭제가 완료 되었습니다.');");
		out.print("location.href='./BoardList.bo?flag=a';");
		out.print("</script>");
		
		out.close();
		
		return null;
	}
}
