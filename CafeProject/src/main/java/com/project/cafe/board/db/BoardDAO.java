package com.project.cafe.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO 
{
	// DB 연결동작 처리
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	// getCon()
	private Connection getCon() throws Exception
	{
		// 외부파일 불러오기 (META-INF/context.xml)
		Context ctxInit = new InitialContext();
		DataSource ds = (DataSource) ctxInit.lookup("java:comp/env/jdbc/cafe");
		con = ds.getConnection();
		
		System.out.println("DAO : 1.2. DB 연결 완료");
		
		return con;
	}
	// getCon()
	
	// closeDB()
	public void closeDB()
	{
		try {
			if (null != rs)	rs.close();
			if (null != pstmt) pstmt.close();
			if (null != con) con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	// closeDB()
	
	// insertPost(dto)
	public void insertPost(BoardDTO dto)
	{
		int postNum = 0; 
		
		try {
			// 1.2. DB 연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 객체
			// 이번 차례에 DB에 저장될 글번호 계산
			sql = "select max(num) from cafe_board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				postNum = rs.getInt(1) + 1;
			
			// 3. 데이터 삽입용 sql 작성 & pstmt 설정
			sql = "insert into cafe_board(num, id, title, content, readcount, re_ref, re_lev, re_seq, date, ip, "
					+ "file, image, comment_count, image_uid, file_uid) "
					+ "values(?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			// ? 채우기
			pstmt.setInt(1, postNum);
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, 0); // 처음에 조회수 0
			pstmt.setInt(6, postNum); // 답글의 그룹. 일반글의 글번호와 동일하게 만듦
			pstmt.setInt(7, 0); // 답글의 레벨. 처음엔 들여쓰기 없음
			pstmt.setInt(8, 0); // 답글의 순서. 처음엔 가장 최상단
			pstmt.setString(9, dto.getIp());
			pstmt.setInt(10, 0);
			
			if (dto.getFile() == null)
				pstmt.setString(10, "없음");
			else 
				pstmt.setString(10, dto.getFile());
			
			if (dto.getImage() == null)
				pstmt.setString(11, "없음");
			else
				pstmt.setString(11, dto.getImage());
			
			pstmt.setInt(12, 0);
			pstmt.setString(13, dto.getImage_uid());
			pstmt.setString(14, dto.getFile_uid());
			
			// 4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println("DAO : 게시글 DB 삽입 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
	}
	// insertPost(dto)
	
	// getPostCount()
	public int getPostCount()
	{
		int ret = 0;
		
		try {
			con = getCon();
			
			// 전체 글 갯수 가져오기
			sql = "select count(*) from cafe_board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				ret = rs.getInt(1);
			
			System.out.println("DAO : 총 글 갯수 " + ret);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return ret;
	}
	// getPostCount()
	
	// getPostList(startRow, pageSize)
	public ArrayList<BoardDTO> getPostList(int startRow, int pageSize)
	{
		ArrayList<BoardDTO> postList = new ArrayList<BoardDTO>();
		
		try {
			con = getCon();
			
			// 글 자르기 : limit 시작행-1, 갯수
			// 시작행-1부터 x개 만큼 가져온다.
			// 정렬 : re_ref(내림차순) / re_seq(오름차순)
			sql = "select * from cafe_board order by re_ref desc, re_seq asc limit ?,?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, startRow - 1); // 시작행 - 1
			pstmt.setInt(2, pageSize); // 갯수
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				// 글 1개의 정보(dto)에 저장한 후 배열에 저장
				BoardDTO dto = new BoardDTO();
				
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getDate("date"));
				dto.setFile(rs.getString("file"));
				dto.setId(rs.getString("id"));
				dto.setIp(rs.getString("ip"));
				dto.setNum(rs.getInt("num"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setTitle(rs.getString("title"));
				dto.setComment_count(rs.getInt("comment_count"));
				dto.setImage(rs.getString("image"));
				dto.setImage_uid(rs.getString("image_uid"));
				dto.setFile_uid(rs.getString("file_uid"));
				
				postList.add(dto);
			}
			
			System.out.println("DAO : 글 정보 저장 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return postList;
	}
	// getPostList(startRow, pageSize)
	
	// getPostList()
	public ArrayList<BoardDTO> getPostList()
	{
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			con = getCon();
			
			sql = "select * from cafe_board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setComment_count(rs.getInt("comment_count"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getDate("date"));
				dto.setFile(rs.getString("file"));
				dto.setFile_uid(rs.getString("file_uid"));
				dto.setId(rs.getString("id"));
				dto.setImage(rs.getString("image"));
				dto.setImage_uid(rs.getString("image_uid"));
				dto.setIp(rs.getString("ip"));
				dto.setNum(rs.getInt("num"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setTitle(rs.getString("title"));
				
				list.add(dto);
			}
			
			System.out.println("DAO : 게시글 목록 저장 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return list;
	}
	// getPostList()
	
	// updateReadCount(num)
	public void updateReadCount(int num)
	{
		try {
			con = getCon();
			
			sql = "update cafe_board set readcount = readcount + 1 where num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
			System.out.println("DAO : 조회수 1 증가 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
	}
	// updateReadCount(num)
	
	// getPost(num)
	public BoardDTO getPost(int num)
	{
		BoardDTO dto = null;
		
		try {
			con = getCon();
			
			sql = "select * from cafe_board where num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				dto = new BoardDTO();
				
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getDate("date"));
				dto.setFile(rs.getString("file"));
				dto.setFile_uid(rs.getString("file_uid"));
				dto.setId(rs.getString("id"));
				dto.setIp(rs.getString("ip"));
				dto.setNum(rs.getInt("num"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setTitle(rs.getString("title"));
				dto.setImage(rs.getString("image"));
				dto.setImage_uid(rs.getString("image_uid"));
				dto.setComment_count(rs.getInt("comment_count"));
				
				System.out.println("DAO : 글 1개 정보 저장 완료");
				System.out.println("DAO dto: "+dto.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return dto;
	}
	// getPost(num)
	
	// modifyPost(num)
	public int modifyPost(BoardDTO dto)
	{
		int ret = -1;
		
		try {
			con = getCon();
			
			// 번호로 해당 글 찾기
			sql = "select * from cafe_board where num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNum());
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				// update 동작 수행
				// 새로 수정한 글에서 파일 정보가 있으면 그걸로 수정 / 없으면 그대로 놔둠
				if (dto.getImage() != null && dto.getImage_uid() != null)
				{
					sql = "update cafe_board set image=?, image_uid=? where num=?";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, dto.getImage());
					pstmt.setString(2, dto.getImage_uid());
					pstmt.setInt(3, dto.getNum());
					
					pstmt.executeUpdate();
				}
				
				if (dto.getFile() != null && dto.getFile_uid() != null)
				{
					sql = "update cafe_board set file=?, file_uid=? where num=?";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, dto.getFile());
					pstmt.setString(2, dto.getFile_uid());
					pstmt.setInt(3, dto.getNum());
					
					pstmt.executeUpdate();
				}
				
				sql = "update cafe_board set title=?, content=? where num=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getContent());
				pstmt.setInt(3, dto.getNum());
				
				ret = pstmt.executeUpdate();
				
				System.out.println("DAO : 글 수정 완료");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return ret;
	}
	// modifyPost(num)
	
	// deletePost(num)
	public int deletePost(int num)
	{
		int ret = -1;
		
		try {
			con = getCon();
			
			// 삭제 대상 글 찾기
			sql = "select * from cafe_board where num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				// 댓글 삭제
				sql = "delete from cafe_comment where post_num=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, num);
				
				pstmt.executeUpdate();
				
				// 게시글 본문 삭제
				sql = "delete from cafe_board where num=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, num);
				
				ret = pstmt.executeUpdate();
				
				System.out.println("DAO : 글 삭제 완료");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return ret;
	}
	// deletePost(num)
	
	// reWritePost(dto)
	public void reWritePost(BoardDTO dto)
	{
		int curNum = 0; // 이번에 쓸 글 번호
		
		try {
			con = getCon();
			
			// 글 번호 계산
			sql = "select max(num) from cafe_board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				curNum = rs.getInt(1) + 1;
			
			System.out.println("DAO : 답글번호 : "+curNum);
			
			// 답글순서 재배치(seq 변경)
			// re_ref 가 같은 그룹내에서 update 동작 수행 - re_seq값이 기존값보다 큰 값이 있을 때 re_seq + 1
			// 없으면(0) 정렬할 게 없으니까 그냥 지나갈 것임
			sql = "update cafe_board set re_seq = re_seq + 1 where re_ref = ? and re_seq > ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getRe_ref());
			pstmt.setInt(2, dto.getRe_seq());
			
			int result = pstmt.executeUpdate();
			if (0 < result)
				System.out.println("DAO : 답글 순서 재배치");
			
			// 답글 저장 동작 수행
			sql = "insert into cafe_board(num, id, title, content, readcount, re_ref, re_lev, re_seq, date,"
					+ " ip, file, image, comment_count, image_uid, file_uid)"
					+ " values(?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, curNum);
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, 0);
			
			pstmt.setInt(6, dto.getRe_ref()); // 게시글 그룹
			pstmt.setInt(7, dto.getRe_lev() + 1); // 들여쓰기 - 기준글 + 1
			pstmt.setInt(8, dto.getRe_seq() + 1); // 그룹 내 순서 - 기준글 + 1
			
			pstmt.setString(9, dto.getIp());
			pstmt.setString(10, dto.getFile());
			pstmt.setString(11, dto.getImage());
			pstmt.setInt(12, 0);
			
			pstmt.setString(13, dto.getImage_uid());
			pstmt.setString(14, dto.getFile_uid());
			
			pstmt.executeUpdate();
			
			System.out.println("DAO : 답글 작성 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
	}
	// reWritePost(dto)
	
	// getPosts(cnt, len)
	public ArrayList<BoardDTO> getPosts(int cnt, int len)
	{
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			con = getCon();
			
			sql = "select * from cafe_board order by num desc limit ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, cnt);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				
				// 문자열이 정해진 길이보다 길면 일부만 저장
				String content = rs.getString("content");
				if (len < content.length())
				{
					content = content.substring(0, len);
					content = content.concat("...");
				}
				
				dto.setContent(content);
				dto.setImage(rs.getString("image"));
				
				list.add(dto);
			}
			
			System.out.println("DAO : 글 "+list.size()+"개 저장 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return list;
	}
	// getPosts(cnt, len)
	
	// getComments(num)
	public ArrayList<CommentDTO> getComments(int num)
	{
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		
		try {
			con = getCon();
			
			sql = "select * from cafe_comment where post_num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				CommentDTO dto = new CommentDTO();
				
				dto.setCommentedDate(rs.getDate("commented_date"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setIp(rs.getString("ip"));
				dto.setNum(rs.getInt("num"));
				dto.setPost_num(rs.getInt("post_num"));
				
				list.add(dto);
			}
			
			System.out.println("DAO : 댓글 목록 저장 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return list;
	}
	// getComments(num)
	
	// insertComment(postNum)
	public void insertComment(CommentDTO dto)
	{
		int lastNum = 0;

		try {
			con = getCon();
			
			// 게시글의 댓글 갯수 증가
			sql = "update cafe_board set comment_count = comment_count + 1 where num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getPost_num());
			
			pstmt.executeUpdate();
			
			System.out.println("DAO : 댓글 갯수 증가 완료");
			
			// 새 글 번호 찾기
			sql = "select max(num) from cafe_comment";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				lastNum = rs.getInt(1) + 1;
			
			// 댓글 삽입
			sql = "insert into cafe_comment(num, post_num, id, content, commented_date, ip)"
					+ " values(?,?,?,?,now(),?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, lastNum);
			pstmt.setInt(2, dto.getPost_num());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getIp());
			
			pstmt.executeUpdate();
			
			System.out.println("DAO : 댓글 삽입 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
	}
	// insertComment(postNum)
	
	// updateComment(dto)
	public int updateComment(CommentDTO dto)
	{
		int ret = -1;
		
		try {
			con = getCon();
			
			sql = "select * from cafe_comment where num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNum());
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				// 댓글이 존재하면 수정
				sql = "update cafe_comment set content=? where num=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, dto.getContent());
				pstmt.setInt(2, dto.getNum());
				
				ret = pstmt.executeUpdate();
				
				System.out.println("DAO : 댓글 수정 완료");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return ret;
	}
	// updateComment(dto)
	
	// deleteComment(num)
	public int deleteComment(int num)
	{
		int ret = -1;
		
		try {
			con = getCon();
			
			sql = "select * from cafe_comment where num=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				// 게시글의 댓글 갯수 감소
				sql = "update cafe_board set comment_count = comment_count - 1 where num=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, rs.getInt("post_num"));
				
				pstmt.executeUpdate();
				
				// 댓글 삭제
				sql = "delete from cafe_comment where num=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, num);
				
				ret = pstmt.executeUpdate();
				
				System.out.println("DAO : 댓글 삭제 완료");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		
		return ret;
	}
	// deleteComment(num)
}