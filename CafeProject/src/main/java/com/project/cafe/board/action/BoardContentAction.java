package com.project.cafe.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;
import com.project.cafe.board.db.CommentDTO;

public class BoardContentAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardContentAction - execute() 호출");
		
		// 전달받은 파라메터(글 번호, 페이지정보) 저장
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum"); // 페이지번호는 DB 처리할 때 사용하진 않아서 형변환 필요 X
		
		// DAO 객체 생성
		BoardDAO dao = new BoardDAO();

		// 글 조회수 1 증가
		dao.updateReadCount(num);
		
		// 글 정보 불러오기
		BoardDTO dto = dao.getPost(num);
		ArrayList<CommentDTO> coList = dao.getComments(num);
		
		// request 영역에 글 정보랑 페이지정보 저장
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("coList", coList);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./contents/boardContent.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
}
