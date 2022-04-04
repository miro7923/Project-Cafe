package com.project.cafe.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;

public class CommentDeleteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : CommentDeleteAction - execute() 호출");
		
		// 파라메터 저장
		int num = Integer.parseInt(request.getParameter("num"));
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String pageNum = request.getParameter("pageNum");
		
		// DB 접속해서 삭제 연산 수행
		BoardDAO dao = new BoardDAO();
		int result = dao.deleteComment(num);
		
		// 페이지 이동
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (1 != result)
		{
			out.print("<script>");
			out.print("alert('잘못된 접근');");
			out.print("history.back();");
			out.print("</script>");
		}
		
		out.print("<script>");
		out.print("alert('댓글 삭제 완료!');");
		out.print("location.href='./BoardContent.bo?num="+post_num+"&pageNum="+pageNum+"';");
		out.print("</script>");
		
		out.close();
		
		return null;
	}
}
