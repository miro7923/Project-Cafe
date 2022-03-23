package com.project.cafe.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;

public class BoardWriteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardWriteAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 파라메터를 DTO에 저장
		BoardDTO dto = new BoardDTO();
		dto.setContent(request.getParameter("content"));
		dto.setId(request.getParameter("id"));
		dto.setTitle(request.getParameter("title"));
		
		// 사용자 ip주소 저장
		dto.setIp(request.getRemoteAddr());
		System.out.println("M : "+dto);
		
		// DB에 DTO 보내서 저장
		BoardDAO dao = new BoardDAO();
		dao.insertPost(dto);
		
		// 완료되면 글 목록 페이지로 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./boardList.bo");
		forward.setRedirect(true);
		
		System.out.println("M : 글쓰기 완료. 페이지정보 리턴");
		
		return forward;
	}
}
