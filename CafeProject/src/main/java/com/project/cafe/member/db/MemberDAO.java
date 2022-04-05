package com.project.cafe.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO 
{
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	// DB 연결정보 준비
	private Connection getCon() throws Exception
	{
		// Context 객체 생성
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/cafe");
		con = ds.getConnection();
		System.out.println("DAO : 1.2. DB 연결 성공");
		System.out.println("DAO : " + con);
		
		return con;
	}
	
	// DB 자원해제
	public void CloseDB()
	{
		try 
		{
			if (null != rs) rs.close();
			if (null != pstmt) pstmt.close();
			if (null != con) con.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	// insertMember(dto)
	public void insertMember(MemberDTO dto)
	{
		System.out.println("insertMember(dto) 호출");
		
		// 1.2. DB 연결
		try 
		{
			con = getCon();
			
			// 3. sql 작성 & pstmt 연결
			sql = "insert into cafe_members(id, pass, name, birth, age, gender, postalcode, road_address, "
					+ "detail_address, phone, email, regdate) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPass());
			pstmt.setString(3, dto.getName());
			pstmt.setDate(4, dto.getBirth());
			pstmt.setInt(5, dto.getAge());
			pstmt.setString(6, dto.getGender());
			pstmt.setInt(7, dto.getPostalcode());
			pstmt.setString(8, dto.getRoad_address());
			pstmt.setString(9, dto.getDetail_address());
			pstmt.setString(10, dto.getPhone());
			pstmt.setString(11, dto.getEmail());
			pstmt.setTimestamp(12, dto.getRegdate());
			
			// 4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println("DAO : 회원가입 완료");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CloseDB();
		}
		
		System.out.println("DAO : insertMember(dto) 끝!");
	}
	// insertMember(dto)
	
	// isExist(id)
	public boolean isExist(String id)
	{
		System.out.println("DAO : isExist() 호출");
		
		try 
		{
			// 1. 2. DB 연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 연결
			sql = "select id from cafe_members where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			System.out.println("DAO : 아이디 정보 조회 완료");
			
			if (rs.next()) return true;
			else return false;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CloseDB();
		}
		
		System.out.println("DAO : isExist(id) 끝!!!");
		
		return true;
	}
	// isExist(id)
	
	// loginCheck(dto)
	public int loginCheck(MemberDTO dto)
	{
		System.out.println("DAO : loginCheck(dto) 호출");
		
		int result = -1; // 조회 실패
		try {
			// 1.2. DB 연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 생성
			sql = "select pass from cafe_members where id=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 결과 처리
			if (rs.next())
			{
				// 회원
				if (rs.getString("pass").equals(dto.getPass()))
				{
					// 비밀번호 일치
					result = 1;
				}
				else 
				{
					// 비밀번호 오류
					result = 0;
				}
			}
			else 
			{
				// 비회원
				result = -1;
			}
			
			System.out.println("DAO : 로그인 체크 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CloseDB();
		}
		
		System.out.println("result : " + result);
		return result;
	}
	// loginCheck(dto)
	
	// getMember(id)
	public MemberDTO getMember(String id)
	{
		System.out.println("DAO : getMember() 호출");
		
		MemberDTO dto = null;
		
		try {
			// 1.2. DB 연결
			con = getCon();

			// 3. sql 작성 & pstmt 객체 생성
			sql = "select * from cafe_members where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if (rs.next())
			{
				dto = new MemberDTO();
				
				dto.setPostalcode(rs.getInt("postalcode"));
				dto.setRoad_address(rs.getString("road_address"));
				dto.setDetail_address(rs.getString("detail_address"));
				dto.setAge(rs.getInt("age"));
				dto.setBirth(rs.getDate("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setGender(rs.getString("gender"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setMemberNum(rs.getInt("member_num"));
				dto.setPhone(rs.getString("phone"));
				dto.setRegdate(rs.getTimestamp("regdate"));
				
				System.out.println("DAO : 회원 데이터 저장 완료");
				System.out.println("DAO : " + dto);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CloseDB();
		}
		
		return dto;
	}
	// getMember(id)
	
	// updateMember(dto)
	public int updateMember(MemberDTO dto)
	{
		int result = -1;
		
		try {
			// 1.2 DB 연결
			// 회원 정보가 존재하는 지 먼저 확인한 후에 수정 동작 수행
			con = getCon();
			sql = "select * from cafe_members where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				if (rs.getString("id").equals(dto.getId()))
				{
					// 회원 정보가 있으면
					// 3. sql 작성 & pstmt 객체 생성
					sql = "update cafe_members set pass=?, name=?, birth=?, age=?, gender=?, postacode=?,"
							+ " road_address=?, detail_address=?, phone=?, email=? where id=?";
					pstmt = con.prepareStatement(sql);
					
					// ? 채우기
					pstmt.setString(1, dto.getPass());
					pstmt.setString(2, dto.getName());
					pstmt.setDate(3, dto.getBirth());
					pstmt.setInt(4, dto.getAge());
					pstmt.setString(5, dto.getGender());
					pstmt.setInt(6, dto.getPostalcode());
					pstmt.setString(7, dto.getRoad_address());
					pstmt.setString(8, dto.getDetail_address());
					pstmt.setString(9, dto.getPhone());
					pstmt.setString(10, dto.getEmail());
					pstmt.setString(11, dto.getId());
					
					// 4. sql 실행
					pstmt.executeUpdate();
					
					// 5. 데이터 처리
					// 수정 성공
					result = 1;
					System.out.println("DAO : 회원 정보 수정 완료");
				}
				else 
				{
					result = 0;
				}
			}
			else 
			{
				// 회원 정보 없음 - 비회원
				result = -1;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CloseDB();
		}
		
		return result;
	}
	// updateMember(dto)
	
	// deleteMember(dto)
	public int deleteMember(MemberDTO dto)
	{
		int result = -1;
		
		try {
			// 1.2 DB 연결
			con = getCon();
			
			// 3. sql 작성 & pstmt 생성
			sql = "select pass from cafe_members where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if (rs.next())
			{
				if (rs.getString("pass").equals(dto.getPass()))
				{
					// 삭제하는 쿼리문 실행
					sql = "delete from cafe_members where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, dto.getId());
					
					result = pstmt.executeUpdate();
					
					System.out.println("DAO : 회원정보 삭제 완료");
				}
				else
					result = 0;
			}
			else
				result = -1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CloseDB();
		}
		
		return result;
	}
	// deleteMember(dto)
	
	// getMemberList()
	public ArrayList<MemberDTO> getMemberList()
	{
		ArrayList<MemberDTO> memberList = new ArrayList<MemberDTO>();
		
		try {
			con = getCon();
			
			sql = "select * from cafe_members where id != 'admin'";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				MemberDTO dto = new MemberDTO();
				
				dto.setRoad_address(rs.getString("road_address"));
				dto.setDetail_address(rs.getString("detail_address"));
				dto.setAge(rs.getInt("age"));
				dto.setBirth(rs.getDate("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setGender(rs.getString("gender"));
				dto.setId(rs.getString("id"));
				dto.setMemberNum(rs.getInt("member_num"));
				dto.setName(rs.getString("name"));
				dto.setPhone(rs.getString("phone"));
				dto.setPostalcode(rs.getInt("postalcode"));
				dto.setRegdate(rs.getTimestamp("regdate"));
				
				memberList.add(dto);
			}
			
			System.out.println("DAO : 회원 전체 목록 저장 완료");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CloseDB();
		}
		
		return memberList;
	}
	// getMemberList()
	
	// deleteMember(id)
	public int deleteMember(String id)
	{
		int ret = -1;
		
		try {
			con = getCon();
			
			sql = "select * from cafe_members where id=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				sql = "delete from cafe_members where id=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, id);
				
				ret = pstmt.executeUpdate();
				
				System.out.println("DAO : 운영자가 회원정보 삭제 완료");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			CloseDB();
		}
		
		return ret;
	}
	// deleteMember(id)
}
