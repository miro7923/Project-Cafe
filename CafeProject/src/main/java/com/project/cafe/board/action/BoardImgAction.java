package com.project.cafe.board.action;

import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;

public class BoardImgAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardImgAction - execute() 호출");
		
		// 전달된 이미지 파일 이름 저장
		String originImgName = request.getParameter("img_name");
		String thumbnailName = request.getParameter("thumbnail");
		String imgName = null;
		
		// 서버에 업로드 된 폴더 찾기
		String savePath = null;
		if (originImgName != null)
		{
			savePath = "images/origins";
			imgName = originImgName;
		}
		else 
		{
			savePath = "images/thumbnails";
			imgName = thumbnailName;
		}
		
		System.out.println("BoardImgAction - imgName: "+imgName);
		
		// 서버에 업로드 된 파일 위치 계산
		ServletContext ctx = request.getServletContext();
		String downloadPath = ctx.getRealPath(savePath);
		
		// 다운로드 할 이미지의 전체 경로 계산
		// 사용자 OS에 따라 연결자 구분
		String imgPath = downloadPath + File.separator + imgName;
		
		// 파일 저장 배열
		byte[] b = new byte[4096];
		
		// 데이터 주고받을 통로 생성
		FileInputStream fis = new FileInputStream(imgPath);
		
		// MIME 타입 정보 얻어오기
		String mimeType = ctx.getMimeType(imgPath);
		
		if (mimeType == null)
			mimeType = "application/octet-stream";
		
		// 전달받은 MIME 타입으로 브라우저 정보 설정
		response.setContentType(mimeType);
		
		String agent = request.getHeader("User-Agent");
		// 인터넷 익스플로러 일 때 구분해서 처리
		boolean ieBrowser = (agent.indexOf("MISE") > -1 || agent.indexOf("Trident") > -1);
		if (ieBrowser)
			imgName = URLEncoder.encode(imgName, "utf-8").replaceAll("\\+", "%20");
		else 
			imgName = new String(imgName.getBytes("utf-8"), "iso-8859-1");
		
		// 응답정보 설정
		response.setHeader("Content-Disposition", mimeType);
		
		ParameterBlock pb = new ParameterBlock();
		pb.add(imgPath);
		
		
		// 출력스트림 생성
		ServletOutputStream out = response.getOutputStream();
		
		int data = 0;
		while ((data = fis.read(b, 0, b.length)) != -1)
			out.write(b, 0, data);
		
		out.flush();
		
		out.close();
		fis.close();
		
		return null;
	}
}
