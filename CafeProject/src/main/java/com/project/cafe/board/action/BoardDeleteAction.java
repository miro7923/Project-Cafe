package com.project.cafe.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;

public class BoardDeleteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardDeleteAction - execute() 호출");
		
		// 파라메터 저장
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		// DB 연결해서 해당 글 삭제
		BoardDAO dao = new BoardDAO();
		int result = dao.deletePost(num);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 글 리스트 페이지로 이동
		if (1 != result)
		{
			// 해당 게시글 정보 없음
			out.println("<script>");
			out.println("alert('해당 게시글 정보가 없습니다!');");
			out.println("location.href='./BoardList.bo';");
			out.println("</script>");
			
			out.close();
			
			return null;
		}

		out.println("<script>");
		out.println("alert('게시글 삭제가 완료되었습니다.');");
		out.println("location.href='./BoardList.bo?flag=n&pageNum="+pageNum+"';");
		out.println("</script>");
		
		out.close();
		
		return null;
	}
}
