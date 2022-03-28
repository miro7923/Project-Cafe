package com.project.cafe.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;

public class BoardReWriteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardReWriteAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 파라메터 저장
		BoardDTO dto = new BoardDTO();
		dto.setContent(request.getParameter("content"));
		dto.setId(request.getParameter("id"));
		dto.setIp(request.getRemoteAddr());
		dto.setNum(Integer.parseInt(request.getParameter("num")));
		dto.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		dto.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		dto.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));
		dto.setReadcount(0);
		dto.setTitle(request.getParameter("title"));
		
		// DB 연결해서 글 저장
		BoardDAO dao = new BoardDAO();
		dao.reWritePost(dto);
		
		// 게시글 목록으로 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);
		
		return forward;
	}

}
