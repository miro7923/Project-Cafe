package com.project.cafe.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.member.db.MemberDAO;
import com.project.cafe.member.db.MemberDTO;

public class MemberDeleteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : MemberDeleteAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 전달된 정보 저장
		MemberDTO dto = new MemberDTO();
		dto.setId(request.getParameter("id"));
		dto.setPass(request.getParameter("pass"));
		
		// DB 연결해서 삭제
		MemberDAO dao = new MemberDAO();
		int result = dao.deleteMember(dto);
		
		// 삭제 완료 알림창 띄우고 메인페이지 이동
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (1 == result)
		{
			// 탈퇴가 완료되면 세션 초기화 및 메인으로 이동
			HttpSession session = request.getSession();
			session.invalidate();
			
			out.write("<script>");
			out.write("alert('회원 탈퇴가 완료되었습니다.');");
			out.write("location.href='./main.me';");
			out.write("</script>");
		}
		else 
		{
			out.write("<script>");
			out.write("alert('비밀번호가 일치하지 않습니다!');");
			out.write("history.back();");			
			out.write("</script>");
		}
		
		out.close();
		
		return null;
	}
}
