package com.project.cafe.api.sms;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;

public class ValidatePhone implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		response.setContentType("text/html; charset=utf-8");
		
		SmsService smsService = new SmsService();
		PrintWriter out = response.getWriter();
		
		int validateNum = smsService.sendSms(request.getParameter("phone"));
		out.print(validateNum);
		
		out.close();
		
		return null;
	}
}
