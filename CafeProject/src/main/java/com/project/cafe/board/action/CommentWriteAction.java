package com.project.cafe.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.CommentDTO;

public class CommentWriteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : CommentWriteAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("utf-8");
		
		// 파라메터 저장
		String id = request.getParameter("id");
		String comment = request.getParameter("comment");
		int postNum = Integer.parseInt(request.getParameter("post_num"));
		String pageNum = request.getParameter("pageNum");
		
		CommentDTO dto = new CommentDTO();
		dto.setContent(comment);
		dto.setId(id);
		dto.setIp(request.getRemoteAddr());
		dto.setPost_num(postNum);
		
		// DB 연결해서 댓글 저장
		BoardDAO dao = new BoardDAO();
		dao.insertComment(dto);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardContent.bo?num="+postNum+"&pageNum="+pageNum);
		forward.setRedirect(true);
		
		return forward;
	}
}
