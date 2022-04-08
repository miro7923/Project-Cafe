package com.project.cafe.board.action;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import com.project.cafe.board.db.BoardDTO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FileUpload 
{
	public BoardDTO upload(HttpServletRequest request) throws Exception
	{
		System.out.println("upload() 호출 ");
		
		BoardDTO dto = new BoardDTO();
		
		ServletContext ctx = request.getServletContext();
		
		// 파일의 저장크기
		int maxSize = 10 * 1024 * 1024; // 10MB
		
		MultipartParser mp = new MultipartParser(request, maxSize);
		mp.setEncoding("utf-8");
		Part part;

        // 기존에 저장되 있던 파일 정보를 저장할 변수
		String imgUploadStatus = null;
		String oldImgPath = null;
		String oldImgName = null;
		
		String fileUploadStatus = null;
		String oldFilePath = null;
		String oldFileName = null;
		
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
				else if (name.equals("num"))
					dto.setNum(Integer.parseInt(value));
				else if (name.equals("re_lev"))
					dto.setRe_lev(Integer.parseInt(value));
				else if (name.equals("re_ref"))
					dto.setRe_ref(Integer.parseInt(value));
				else if (name.equals("re_seq"))
					dto.setRe_seq(Integer.parseInt(value));
				else if (name.equals("oldImgName"))
					oldImgName = value;
				else if (name.equals("oldFileName"))
					oldFileName = value;
				else if (name.equals("fileUploadStatus"))
					fileUploadStatus = value;
				else if (name.equals("imgUploadStatus"))
					imgUploadStatus = value;
				else if (name.equals("oldImgPath"))
					oldImgPath = value;
				else if (name.equals("oldFilePath"))
					oldFilePath = value;
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
				if (filePart.getFileName() != null)
				{
					// 중복 파일명 처리 - 실제 참고할 파일은 rename된 파일이름 쓰기
					filePart.setRenamePolicy(new DefaultFileRenamePolicy());

					// 지정한 경로에 파일 쓰기
					String file = filePart.getFileName();
					filePart.writeTo(originDir);
					dto.setImage(file);
					dto.setImage_uid(filePart.getFileName());
					
					// 원본 이미지 파일을 이용해서 썸네일을 만들어 저장
					// 크기 지정 후 지정 경로에 저장
					// 썸네일 파일명은 중복처리된 파일명과 같게 만든다.
					Thumbnails.of(new File(originDir.getPath() + File.separator + filePart.getFileName()))
						.size(300, 400)
						.toFiles(thumbnailDir, Rename.NO_CHANGE);
					
					System.out.println("file system name: "+filePart.getFileName());
					System.out.println("img name: "+file);
				}
				else
				{
//					dto.setImage("없음");
//					dto.setImage_uid("없음");
					
					System.out.println("첨부된 이미지 없음");
				}
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
					filePart.setRenamePolicy(new DefaultFileRenamePolicy());
					filePart.writeTo(dir); // 지정한 경로에 파일 쓰기
					dto.setFile(file);
					dto.setFile_uid(filePart.getFileName());
					
					System.out.println("file system name: "+filePart.getFileName());
					System.out.println("file name: "+file);
				}
				else
				{
//					dto.setFile("없음");
//					dto.setFile_uid("없음");
					
					System.out.println("첨부된 파일 없음");
				}
			}
		}
		
		// 사용자 ip주소 저장
		dto.setIp(request.getRemoteAddr());
		
		System.out.println("imgUpload: "+imgUploadStatus);
		if (imgUploadStatus != null)
		{
			if (imgUploadStatus.equals("false"))
			{
		        // 등록된 이미지를 삭제하는 경우
				dto.setImage("없음");
				dto.setImage_uid("없음");
				File oldImg = new File(oldImgPath);
				if (oldImg.exists()) oldImg.delete();
				
				// 썸네일도 삭제한다.
				File oldThumbnail = new File(ctx.getRealPath("/thumbnail") + File.separator + oldImgName);
				if (oldThumbnail.exists()) oldThumbnail.delete();
			}
			else 
			{
		        // 삭제하지 않는 경우
				// 만약 dto의 파일명 필드가 비어 있으면 기존 파일명을 저장한다.
				System.out.println("getImage(): "+dto.getImage());
				if (dto.getImage() == null)
				{
					dto.setImage(oldImgName);
					dto.setImage_uid(oldImgName);
				}
			}
		}
		
		System.out.println("fileUpload: "+fileUploadStatus);
		if (fileUploadStatus != null)
		{
			if (fileUploadStatus.equals("false"))
			{
				dto.setFile("없음");
				dto.setFile_uid("없음");
				File oldFile = new File(oldFilePath);
				if (oldFile.exists()) oldFile.delete();
			}
			else 
			{
				System.out.println("getFile(): "+dto.getFile());
				if (dto.getFile() == null)
				{
					dto.setFile(oldFileName);
					dto.setFile_uid(oldFileName);
				}
			}
		}
		
		System.out.println("M : "+dto);
		
		return dto;
	}
}
