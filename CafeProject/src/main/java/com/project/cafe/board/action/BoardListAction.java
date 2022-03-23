package com.project.cafe.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;

public class BoardListAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardListAction - execute() 호출");
		
		// BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		// DB에 저장된 글 갯수 가져오기
		int postCnt = dao.getPostCount();
		
		// 페이징 처리 알고리즘 /////////////////////
		// 한 페이지에 표시할 글 갯수
		int pageSize = 10;
		
		// 현재 페이지 정보가 몇 페이지인지 확인한 후 현재 페이지 정보를 가지고 이동
		String pageNum = request.getParameter("pageNum");
		if (null == pageNum)
		{
			// 페이지 정보가 없으면 첫페이지인 1로 설정
			pageNum = "1";
		}
		
		// 페이지 정보로 시작 행 번호 계산하기 1, 11, 21, 31, ...
		int curPage = Integer.parseInt(pageNum);
		int startRow = (curPage - 1) * pageSize + 1;
		
		// DB에서 글 목록 가져오기
		ArrayList<BoardDTO> postList = null;
		if (0 != postCnt)
		{
			// 한 페이지에 표시할 만큼만 글 목록을 가져오기
			postList = dao.getPostList(startRow, pageSize);
		}
		
		// 하단 페이징 처리하기 //////////////////////
		// 현재 글 갯수만큼 페이지 이동 번호 출력하기
		
		// 페이지 전체 블럭 갯수 계산
		int pageCnt = postCnt / pageSize + ((0 == postCnt % pageSize) ? 0 : 1);
		
		// 한 번에 보여줄 페이지 블럭 갯수
		int pageBlockCnt = 10;
		
		// 시작 페이지블록 번호 구하기 	1~10 => 1		11~20 => 11 ...
		int startBlock = ((curPage - 1) / pageBlockCnt) * pageBlockCnt + 1;
		
		// 끝 페이지블록 번호 구하기
		int endBlock = startBlock + pageBlockCnt - 1;
		if (endBlock > pageCnt)
			endBlock = pageCnt;
		// 하단 페이징 처리 끝 //////////////////////
		
		// request 영역에 글 목록 정보 저장
		request.setAttribute("postCnt", postCnt);
		System.out.println("postCnt : "+postCnt);
		request.setAttribute("postList", postList);
		
		// request 영역에 페이징 처리 정보 저장
		request.setAttribute("pageNum", pageNum);
		System.out.println("pageNum : "+pageNum);
		request.setAttribute("pageCnt", pageCnt);
		System.out.println("pageCnt : "+pageCnt);
		request.setAttribute("pageBlockCnt", pageBlockCnt);
		System.out.println("pageBlockCnt : "+pageBlockCnt);
		request.setAttribute("startBlock", startBlock);
		System.out.println("startBlock : "+startBlock);
		request.setAttribute("endBlock", endBlock);
		System.out.println("endBlock : "+endBlock);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./contents/boardList.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
}
