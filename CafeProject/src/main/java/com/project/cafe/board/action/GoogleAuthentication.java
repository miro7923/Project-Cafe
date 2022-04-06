package com.project.cafe.board.action;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GoogleAuthentication extends Authenticator 
{
	PasswordAuthentication passAuth;
	
	public GoogleAuthentication()
	{
		// 첫번째 인자는 구글 아이디, 두번째는 비밀번호
		passAuth = new PasswordAuthentication("mailserve637", "sbymavsyvyzzhnwy");
	}
	
	public PasswordAuthentication getPasswordAuthentication() {return passAuth;}
}
