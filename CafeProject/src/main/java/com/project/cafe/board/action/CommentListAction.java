package com.project.cafe.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;

public class CommentListAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : CommentListAction - execute() 호출");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/BoardContent.bo?num=20&pageNum=1");
		forward.setRedirect(true);
		
		return forward;
	}
}
