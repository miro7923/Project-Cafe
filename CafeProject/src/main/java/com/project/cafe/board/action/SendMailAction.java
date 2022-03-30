package com.project.cafe.board.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;

public class SendMailAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : SendMailAction - execute() 호출");
		
		// 한글처리
		request.setCharacterEncoding("utf-8");
		
		// 파라미터 저장
		String sender = request.getParameter("email");
		String receiver = "mailserve637@gmail.com";
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String name = request.getParameter("name");
		
		StringBuilder sb = new StringBuilder();
		sb.append("이름 : ");
		sb.append(name);
		sb.append("<br>");
		sb.append("메일주소 : ");
		sb.append(sender);
		sb.append("<br>");
		sb.append("내용 : ");
		sb.append(content);
		
		// 메일 보내는 동작 수행
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			// 서버 정보를 Properies 객체에 저장
			Properties properties = System.getProperties();
			
			// Starttls Command를 사용할 수 있게 설정
			properties.put("mail.smtp.starttls.enable", "true");
			
			// SMTP 서버를 지정
			properties.put("mail.smtp.host", "smtp.gmail.com");
			
			// AUTH command를 사용하여 사용자 인증을 할 수 있게 하는 설정 부분
			properties.put("mail.smtp.auth", "true");
			
			// 서버 포트를 지정하는 부분
			properties.put("mail.smtp.port", "587");
			
			// 인증 정보 생성
			Authenticator auth = new GoogleAuthentication();
			
			// 메일을 전송하는 역할을 하는 단위인 Session 객체 생성
			Session s = Session.getDefaultInstance(properties, auth);
			
			// 생성한 Session 객체를 사용하여 전송할 Message 객체 생성
			Message message = new MimeMessage(s);
			
			// 메일을 송신할 송신 주소 생성
			Address senderAddr = new InternetAddress(sender);
			System.out.println("senderAddr : "+senderAddr.toString());
			
			// 메일을 수신할 수신 주소 생성
			Address receiverAddr = new InternetAddress(receiver);
			
			// 메일 전송에 필요한 값들 설정
			message.setHeader("content-type", "text/html; charset=utf-8");
			message.setFrom(senderAddr);
			message.addRecipient(Message.RecipientType.TO, receiverAddr);
			message.setSubject(title);
			message.setContent(sb.toString(), "text/html; charset=utf-8");
			message.setSentDate(new Date());
			
			// 메시지를 메일로 전송
			Transport.send(message);
			
			out.print("<script>");
			out.print("alert('메일이 정상적으로 전송되었습니다.');");
			out.print("location.href='./Contact.bo';");
			out.print("</script>");
		}
		catch (Exception e) {
			out.print("SMTP 서버가 잘못 설정되었거나 서비스에 문제가 있습니다.");
			e.printStackTrace();
		}
		
		return null;
	}
}
