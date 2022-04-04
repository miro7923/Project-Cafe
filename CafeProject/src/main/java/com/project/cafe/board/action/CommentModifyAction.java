package com.project.cafe.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.CommentDTO;

public class CommentModifyAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : CommentModifyAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("utf-8");
		
		// 파라메터 저장
		int num = Integer.parseInt(request.getParameter("num"));
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String pageNum = request.getParameter("pageNum");
		String comment = request.getParameter("comment");
		
		CommentDTO dto = new CommentDTO();
		dto.setNum(num);
		dto.setPost_num(post_num);
		dto.setContent(comment);
		
		System.out.println("M : commentDTO: "+dto);
		
		// DB 접속 및 update 실행
		BoardDAO dao = new BoardDAO();
		int result = dao.updateComment(dto);
		
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
		out.print("location.href='./BoardContent.bo?num="+post_num+"&pageNum="+pageNum+";'");
		out.print("</script>");
		
		out.close();
		
		return null;
	}
}
