package com.project.cafe.board.action;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import com.project.cafe.action.Action;
import com.project.cafe.action.ActionForward;
import com.project.cafe.board.db.BoardDAO;
import com.project.cafe.board.db.BoardDTO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class BoardWriteAction implements Action 
{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		System.out.println("M : BoardWriteAction - execute() 호출");
		
		request.setCharacterEncoding("utf-8");
		
		BoardDTO dto = new BoardDTO();
		
		ServletContext ctx = request.getServletContext();
		
		// 파일의 저장크기
		int maxSize = 10 * 1024 * 1024; // 10MB
		
		MultipartParser mp = new MultipartParser(request, maxSize);
		mp.setEncoding("utf-8");
		Part part;
		
		while ((part = mp.readNextPart()) != null)
		{
			// form 태그로 저장된 파라미터를 읽어옴
			String name = part.getName();
			
			if (part.isParam())
			{
				// 파일이 아닐 때
				ParamPart param = (ParamPart)part;
				String value = param.getStringValue();
				
				System.out.println("param name :" + name + " value : " + value);
				
				// 각 파라미터에 맞춰 dto에 저장
				if (name.equals("id"))
					dto.setId(value);
				else if (name.equals("title"))
					dto.setTitle(value);
				else if (name.equals("content"))
					dto.setContent(value);
			}
			else if (part.isFile() && name.equals("image"))
			{
				// 이미지 파일일 때
				// 이미지 저장 경로 지정
				File dir = new File(ctx.getRealPath("/images"));
				File originDir = new File(dir + File.separator + "origins"); // 원본 이미지 저장 경로
				System.out.println("originDir: "+originDir);
				File thumbnailDir = new File(dir + File.separator + "thumbnails"); // 썸네일 저장 경로
				System.out.println("thumbnailDir: "+ thumbnailDir);
				
				// 경로가 없으면 생성
				if (!dir.isDirectory()) dir.mkdir();
				if (!originDir.isDirectory()) originDir.mkdir();
				if (!thumbnailDir.isDirectory()) thumbnailDir.mkdir();
				
				FilePart filePart = (FilePart) part;
				String file = filePart.getFileName();
				if (file != null)
				{
					// 지정한 경로에 파일 쓰기
					filePart.writeTo(originDir);
					dto.setImage(file);
					
					// 원본 이미지 파일을 이용해서 썸네일을 만들어 저장
					// 크기 지정 후 지정 경로에 저장
					Thumbnails.of(new File(originDir.getPath() + File.separator + file))
						.size(300, 400)
						.toFiles(thumbnailDir, Rename.NO_CHANGE);
					
					System.out.println("img name: "+file);
				}
				else 
					System.out.println("image; name: " + name + "; EMPTY");
			}
			else if (part.isFile() && name.equals("file"))
			{
				// 일반 파일일 때
				File dir = new File(ctx.getRealPath("/upload"));
				
				// 경로 없으면 생성
				if (!dir.isDirectory()) dir.mkdir();
				
				FilePart filePart = (FilePart) part;
				String file = filePart.getFileName();
				if (file != null)
				{
					filePart.writeTo(dir); // 지정한 경로에 파일 쓰기
					dto.setFile(file);
					System.out.println("file name: "+file);
				}
				else
					System.out.println("file; name: " + name + "; EMPTY");
			}
		}
		
		// 사용자 ip주소 저장
		dto.setIp(request.getRemoteAddr());
		System.out.println("M : "+dto);
		
		// DB에 DTO 보내서 저장
		BoardDAO dao = new BoardDAO();
		dao.insertPost(dto);
		
		// 완료되면 글 목록 페이지로 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);
		
		return forward;
	}
}
