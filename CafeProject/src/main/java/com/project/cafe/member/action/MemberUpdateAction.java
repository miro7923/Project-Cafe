package com.project.cafe.member.action;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.member.db.MemberDAO;
import com.project.cafe.member.db.MemberDTO;

// 회원 정보 수정
public class MemberUpdateAction implements Action
{
	private int getAge(String birth)
	{
		int year = Integer.parseInt(birth.split("-")[0]);
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		
		return curYear - year;
	}
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : MemberUpdateAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 전달받은 파라미터 저장
		MemberDTO dto = new MemberDTO();
		dto.setAddress(request.getParameter("city"));
		
		// 나이 계산
		int age = getAge(request.getParameter("birth"));
		dto.setAge(age);
		
		dto.setBirth(Date.valueOf(request.getParameter("birth")));
		dto.setEmail(request.getParameter("email"));
		dto.setGender(request.getParameter("gender"));
		dto.setId(request.getParameter("id"));
		dto.setName(request.getParameter("name"));
		dto.setName(request.getParameter("name"));
		dto.setPass(request.getParameter("pass"));
		
		// 폰번호 3개 필드 합치기
		String phone = request.getParameter("phone1") + request.getParameter("phone2") + request.getParameter("phone3");
		dto.setPhone(phone);
		
		System.out.println("M : 수정된 회원 정보 저장");
		System.out.println("M : " + dto);
		
		// DAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// update 함수 호출
		int result = dao.updateMember(dto);
		
		System.out.println("M : result - " + result);
		System.out.println("M : 회원 정보 수정 완료");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>");
		out.print("alert('정보 수정이 완료되었습니다.');");
		out.print("location.href='./myPage.me';");
		out.print("</script>");
		
		out.close();
		
		return null;
	}
}
