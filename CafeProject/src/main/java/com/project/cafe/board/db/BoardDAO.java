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
			sql = "insert into cafe_board(num, id, title, content, readcount, re_ref, re_lev, re_seq, date, ip, file) "
					+ "values(?,?,?,?,?,?,?,?,now(),?,?)";
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
			pstmt.setString(10, dto.getFile());
			
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
}
