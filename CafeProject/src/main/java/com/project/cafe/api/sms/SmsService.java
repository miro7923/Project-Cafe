package com.project.cafe.api.sms;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SmsService 
{
	private String makeSignature(String method, String url, String timestamp, String accessKey, String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException
	{
		String space = " ";					// one space
		String newLine = "\n";					// new line
		
		String message = new StringBuilder()
				.append(method)
				.append(space)
				.append(url)
				.append(newLine)
				.append(timestamp)
				.append(newLine)
				.append(accessKey)
				.toString();
		


		SecretKeySpec signingKey;
		String encodeBase64String;
		try {
			signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
			encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
		} 
		catch (Exception e) {
			encodeBase64String = e.toString();
		}
		
	  return encodeBase64String;
	}
	
//	{
//    "type":"(SMS | LMS | MMS)", // sms 타입
//    "contentType":"(COMM | AD)", // 메세지 타입
//    "countryCode":"string", // 국가번호
//    "from":"string",		// 발신번호
//    "subject":"string",		// 기본 메시지 제목
//    "content":"string",		// 기본 메시지 내용
//    "messages":[			// 메시지 정보(Object) - 최대 1000개
//        {
//            "to":"string",	// 수신번호(String)
//            "subject":"string", // 개별 메시지 제목(String)
//            "content":"string"  // 개별 메시지 내용(String)
//        }
//    ]
//}
	
	@SuppressWarnings("unchecked")
	public int sendSms(String dstPhoneNumber)
	{
		System.out.println("sendSms() 호출");
		
		String method = "POST";					// method
		String smsUrl = "https://sens.apigw.ntruss.com";	// url (include query string)
		String requestUrl = "/sms/v2/services/";
		String requestUrlType = "/messages";
		String accessKey = "G5DvGW59hLwnFpWannwz";			// access key id (from portal or Sub Account)
		String secretKey = "BqwH3p2H8cNeGtDwHfFoebDFFdtOHjFYE70YPoPG";
		String serviceId = "ncp:sms:kr:275504224302:join-sms";
		String timestamp = Long.toString(System.currentTimeMillis());			// current timestamp (epoch)
		
		requestUrl += serviceId + requestUrlType;
		String apiUrl = smsUrl + requestUrl;
		
		// 요청 바디 생성
		JSONObject bodyJson = new JSONObject();
		JSONObject toJson = new JSONObject();
		JSONArray toArr = new JSONArray();
		
		toJson.put("to", dstPhoneNumber);
		toArr.add(toJson);
		
		bodyJson.put("type", "SMS");
		bodyJson.put("contentType", "COMM");
		bodyJson.put("countryCode", "82");
		bodyJson.put("from", "01029775074");
		bodyJson.put("subject", "[Web 발신]");

		// 난수 생성
		int min = 1000;
		int max = 9999;
		int validateNum = (int) (Math.random() * (max - min + 1)) + min;
		bodyJson.put("content", "인증번호 : " + Integer.toString(validateNum));
		System.out.println("validateNum : " + validateNum);
		
		bodyJson.put("messages", toArr);
		
		String body = bodyJson.toString();
		
		System.out.println("body : " + body);
		
		try {
			URL url = new URL(apiUrl);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("x-ncp-apigw-timestamp", timestamp);
			con.setRequestProperty("x-ncp-iam-access-key", accessKey);
			con.setRequestProperty("x-ncp-apigw-signature-v2", makeSignature(method, requestUrl, timestamp, accessKey, secretKey));
			con.setRequestMethod(method);
			con.setDoOutput(true);
			DataOutputStream dos = new DataOutputStream(con.getOutputStream());
			
			dos.write(body.getBytes());
			dos.flush();
			dos.close();
			
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.println("responseCode : " + responseCode);
			if (202 == responseCode)
			{
				// 정상호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			else 
			{
				// 에러발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			while (null != (inputLine = br.readLine()))
				response.append(inputLine);
			
			br.close();
			
			System.out.println(response.toString());
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("sendSms() 끝");
		
		return validateNum;
	}
}
