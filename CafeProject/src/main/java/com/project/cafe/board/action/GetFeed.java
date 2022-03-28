package com.project.cafe.board.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;

@WebServlet("/GetFeed.bo")
public class GetFeed extends HttpServlet 
{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("ajax 시작 - GetFeed");
		
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardDTO> list = dao.getPosts
				(Integer.parseInt(request.getParameter("cnt")), Integer.parseInt(request.getParameter("len")));
		
		// DB에서 가져온 데이터들을 json에 저장
		JSONArray feedList = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			JSONObject feed = new JSONObject();
			feed.put("title", list.get(i).getTitle());
			feed.put("content", list.get(i).getContent());
			
			feedList.add(feed);
		}
		
		// 클라이언트에게 데이터 보내기
		// 한글처리
		response.setCharacterEncoding("UTF-8");
		// json 데이터 넘김
		response.getWriter().print(feedList.toJSONString());
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doProcess(request, response);
	}
}
