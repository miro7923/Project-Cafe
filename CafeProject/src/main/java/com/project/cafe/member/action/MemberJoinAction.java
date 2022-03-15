package com.project.cafe.member.action;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.member.db.MemberDAO;
import com.project.cafe.member.db.MemberDTO;

// 회원가입 처리동작 수행
// model 객체로 pro 페이지 역할을 한다.
public class MemberJoinAction implements Action
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
		System.out.println("M : MemberJoinAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 전달받은 파라미터 저장 (JSP 페이지가 아니므로 액션태그는 쓸 수 없고 setter를 이용해 저장한다)
		MemberDTO dto = new MemberDTO();
		System.out.println("id set");
		System.out.println("pass set");
		System.out.println("name set");
		System.out.println("city set");
		System.out.println("birth set");
		System.out.println("email set");
		System.out.println("gender set");
		
		// 입력받은 생년월일로 나이 계산
		int age = getAge(request.getParameter("birth"));
		dto.setAge(age);
		
		// 폰번호 3개 필드 합친 후 저장
		StringBuilder sb = new StringBuilder();
		sb.append(request.getParameter("phone1"));
		sb.append(request.getParameter("phone2"));
		sb.append(request.getParameter("phone3"));
		dto.setPhone(sb.toString());
		
		// 날짜 정보 추가 저장
		dto.setRegdate(new Timestamp(System.currentTimeMillis()));
		
		System.out.println("M : 전달된 회원 정보 저장");
		System.out.println("M : " + dto);
		
		// DAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// 회원가입 메서드 호출
		dao.insertMember(dto);
		
		System.out.println("M : 회원가입 완료");
		
		// 페이지 이동 (로그인 페이지로 - ./login.me)
		ActionForward forward = new ActionForward();
		forward.setPath("./login.me");
		forward.setRedirect(true); // Action 페이지를 노출하지 않고 가상 주소를 보여줘야 하니까 true로 설정해서 주소줄에 표시되는 주소를 바꾼다.
		
		return forward;
	}
}
