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
		
		request.setCharacterEncoding("utf-8");
		
		FileUpload fu = new FileUpload();
		BoardDTO dto = fu.upload(request);
		
		// DB에 DTO 보내서 저장
		BoardDAO dao = new BoardDAO();
		dao.insertPost(dto);
		
		// 완료되면 글 목록 페이지로 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo?flag=n");
		forward.setRedirect(true);
		
		return forward;
	}
}
