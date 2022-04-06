package com.project.cafe.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;

public class GetFeed implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("ajax 시작_GetFeed - execute() 호출");
		
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardDTO> list = dao.getPosts
				(Integer.parseInt(request.getParameter("cnt")), Integer.parseInt(request.getParameter("len")));
		
		// DB에서 가져온 데이터들을 json에 저장
		JSONArray feedList = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			JSONObject feed = new JSONObject();
			feed.put("num", list.get(i).getNum());
			feed.put("title", list.get(i).getTitle());
			feed.put("content", list.get(i).getContent());
			feed.put("image", list.get(i).getImage_uid());
			
			feedList.add(feed);
		}
		
		// 클라이언트에게 데이터 보내기
		// 한글처리
		response.setCharacterEncoding("UTF-8");
		// json 데이터 넘김
		response.getWriter().print(feedList.toJSONString());
		response.getWriter().close();
		
		return null;
	}
}
