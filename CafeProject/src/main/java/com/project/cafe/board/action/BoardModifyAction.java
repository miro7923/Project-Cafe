package com.project.cafe.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;

public class BoardModifyAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardModifyAction - execute() 호출");
		
		// 파라메터 저장
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		// DB에서 글 정보 가져오기
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.getPost(num);
		
		// request 영역에 글 정보 저장
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		
		// 페이지 이동정보 설정
		ActionForward forward = new ActionForward();
		forward.setPath("./contents/boardModify.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
}
