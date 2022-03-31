package com.project.cafe.board.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
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
		
		
		// 파일 업로드 먼저 하기
		// 1. 저장경로 생성
		ServletContext ctx = request.getServletContext();
		String realPath = ctx.getRealPath("/upload");
		
		// 2. 파일의 저장크기
		int maxSize = 10 * 1024 * 1024; // 10MB
		
		// 3. 파일업로드(객체 생성)
		MultipartRequest multi = new MultipartRequest(
				request, 
				realPath,
				maxSize,
				"utf-8",
				new DefaultFileRenamePolicy()
				);
		
		// 파라메터를 DTO에 저장
		BoardDTO dto = new BoardDTO();
		dto.setContent(multi.getParameter("content"));
		dto.setId(multi.getParameter("id"));
		dto.setTitle(multi.getParameter("title"));
		
		dto.setFile(multi.getFilesystemName("file"));
		
		// 사용자 ip주소 저장
		dto.setIp(request.getRemoteAddr());
		System.out.println("M : "+dto);
		
		// DB에 DTO 보내서 저장
		BoardDAO dao = new BoardDAO();
		dao.insertPost(dto);
		
		// 완료되면 글 목록 페이지로 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);
		
		return forward;
	}
}
