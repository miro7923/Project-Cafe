package com.project.cafe.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.member.db.MemberDAO;

public class AdminDeleteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : AdminDeleteAction - execute() 호출");
		
		// 파라미터 저장
		String[] members = request.getParameterValues("member");
		System.out.println("members size: "+members.length);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// DB 접속해서 삭제 연산
		MemberDAO dao = new MemberDAO();
		for (String id : members) 
		{
			System.out.println("id: "+id);
			int result = dao.deleteMember(id);
			if (result != 1)
			{
				out.print("<script>");
				out.print("alert('잘못된 접근!');");
				out.print("history.back();");
				out.print("</script>");
				
				out.close();
				
				return null;
			}
		}
		
		// 페이지 이동
		out.print("<script>");
		out.print("alert('선택한 회원 탈퇴가 완료 되었습니다.');");
		out.print("location.href='./MemberListAction.me';");
		out.print("</script>");
		
		out.close();
		
		return null;
	}
}
