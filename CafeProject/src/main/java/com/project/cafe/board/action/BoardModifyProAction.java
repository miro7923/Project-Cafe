package com.project.cafe.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;

public class BoardModifyProAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardModifyProAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// DB에서 기존 글 정보를 찾은 다음 새 내용으로 수정
		FileUpload fu = new FileUpload();
		BoardDTO dto = fu.upload(request);
		
		int num = dto.getNum();

		BoardDAO dao = new BoardDAO();
		int result = dao.modifyPost(dto);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (1 != result)
		{
			// 해당 글 정보 없음
			out.println("<script>");
			out.println("alert('해당 글 정보 없음!');");
			out.println("location.href='./BoardList.bo';");
			out.println("</script>");
			
			return null;
		}
		
		// 게시글 내용 페이지로 이동
		out.println("<script>");
		out.println("location.href='./BoardContent.bo?num="+num+"&pageNum=1';");
		out.println("</script>");
		
		out.close();
		
		return null;
	}
}
