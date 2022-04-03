package com.project.cafe.board.action;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;

public class BoardFileDownloadAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardFileDownloadAction - execute() 호출");
		
		// 전달된 파일 이름 저장
		String fileName = request.getParameter("file_name");
		
		// 서버에 업로드 된 폴더 찾기
		String savePath = "upload";
		
		// 서버에 업로드 된 파일의 위치 계산(물리적 경로)
		ServletContext ctx = request.getServletContext();
		String downloadPath = ctx.getRealPath(savePath);
		
		System.out.println("downloadPath : "+downloadPath);
		
		// 다운로드 할 파일의 전체 경로 계산
		// 사용자 OS에 따라 연결문자 구분
		String filePath = downloadPath + File.separator + fileName;
		String agent = request.getHeader("User-Agent");
		System.out.println("agent: "+agent);
		
//		if (agent.indexOf("Windows") != -1)
//			filePath = downloadPath + "\\" + fileName;
//		else
//			filePath = downloadPath + "/" + fileName;
		
		System.out.println("filePath : "+filePath);
		
		///////////////////////////////////////////
		
		// 파일을 저장하는 배열(버퍼)
		byte[] b = new byte[4096]; // 4kb
		
		// 데이터를 주고받을 수 있는 통로 만들기
		FileInputStream fis = new FileInputStream(filePath);
		
		// MIME 타입 얻어오기
		String mimeType = ctx.getMimeType(filePath);
		System.out.println("mimeType: "+mimeType);
		
		// MIME 타입 정보가 없는 경우
		if (mimeType == null)
		{
			// 실제로 잘 알려지지 않은 이진파일을 의미하며, 브라우저가 자동 실행하지 않고 실행여부를 질문한다.
			mimeType = "application/octet-stream";
		}
		
		// 브라우저가 전달받은 MIME 타입으로 응답정보 설정
		response.setContentType(mimeType);
		
		// IE / 그 외 나머지 브라우저 구분
		boolean ieBrowser = (agent.indexOf("MISE") > -1 || agent.indexOf("Trident") > -1);
		if (ieBrowser)
		{
			// 인터넷 익스플로러일 때
			// 인터넷 익스플로러에서는 한글 다운로드시 한글 인코딩이 깨져서 utf-8로 바꿔준다.
			// 인터넷 익스플로러에서는 공백문자 표시가 [+]라서 [%20]으로 바꿔준다.
			fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
		}
		else
		{
			// 그 외 브라우저
			// 브라우저 다운로드시 한글 깨짐 처리
			// 8859-1 < EUC-KR < MS949 < UTF-8
			// -> 방향으로 갈수록 인코딩 범위가 커짐 (한글처리할 수 있는 범위가 늘어남)
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		}
		
		// 브라우저에서 해석되는(미리보기 형태로 바로 볼 수 있는) 파일도 다운로드 형태로 처리가능하게 변경
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		
		// 다운로드할 파일 출력
		ServletOutputStream out = response.getOutputStream();
		
		int data = 0;
		// 데이터를 배열을 사용해 배열의 길이만큼 잘라서 data에 저장한다. EOF가 아닐 때까지
		while ((data = fis.read(b, 0, b.length)) != -1)
		{
			// 가져온 data 길이만큼 출력
			out.write(b, 0, data);
		}
		
		// ServletOutputStream은 버퍼의 길이가 다 찼을 때에만 데이터를 전달하기 때문에 
		// flush로 버퍼의 빈 공간을 채워서 전송함
		out.flush();
		
		out.close();
		fis.close();
		
		return null;
	}
}
