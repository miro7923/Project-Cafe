<%@page import="com.project.cafe.board.db.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<!-- Start Head -->
  <jsp:include page="../inc/top.jsp"></jsp:include>
  <script src="${pageContext.request.contextPath }/js/boardList.js"></script>
<!-- End Head -->

<body class="modern">

<!--
START MODULE AREA 2: Menu 1
-->
  <jsp:include page="../inc/subTop.jsp"></jsp:include>
<!--
END MODULE AREA 2: Menu 1
-->

<!--
START MODULE AREA 3: Sub Navigation 1
-->
<%
	System.out.println("boardList.jsp 호출");

	String id = (String)session.getAttribute("id");
	
	boolean isLogin = false;
	if (null == id) isLogin = false;
	else isLogin = true;
	
	// 글 목록과 페이지 정보 저장
	int postCnt = (int)request.getAttribute("postCnt");
	ArrayList<BoardDTO> postList = (ArrayList<BoardDTO>)request.getAttribute("postList");
	
	String pageNum = (String)request.getAttribute("pageNum");
	int pageCnt = (int)request.getAttribute("pageCnt");
	int pageBlockCnt = (int)request.getAttribute("pageBlockCnt");
	int startBlock = (int)request.getAttribute("startBlock");
	int endBlock = (int)request.getAttribute("endBlock");
%>
<section class="MOD_SUBNAVIGATION1">
  <div data-layout="_r">
    <jsp:include page="../inc/leftNav.jsp"></jsp:include>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
    	<h2>최신글 보기 (전체 글 갯수 : <%=postCnt %>)</h2>
	  	<p align="right"><button type="button" class="btn" id="writeBtn" 
	  		onclick="memberCheck(<%=isLogin%>);">글쓰기</button></p>
        <table class="type09">
        <colgroup>
          <col width="10%">
          <col width="50%">
          <col width="10%">
          <col width="20%">
          <col width="10%">
        </colgroup>
		<thead>
		<tr>
		<th scope="cols">No.</th>
		<th scope="cols">제목</th>
		<th scope="cols">작성자</th>
		<th scope="cols">작성일</th>
		<th scope="cols">조회수</th>
		</tr>
		</thead>
		<tbody>
		<%
			if (null != postList) {
			for (int i = 0; i < postList.size(); i++) 
			{
				BoardDTO dto = postList.get(i);
		%>
			<tr>
			<th scope="row"><%=dto.getNum() %></th>
			<td>
				<a href="#"><%=dto.getTitle() %></a>
			</td>
			<td><%=dto.getId() %></td>
			<td><%=dto.getDate() %></td>
			<td><%=dto.getReadcount() %></td>
			</tr>
		<%}} %>
		</tbody>
		</table><br>
		<div id="boardPage">
			<%if (startBlock > pageBlockCnt) { %>
				<a href="./boardList.bo?pageNum=<%=startBlock - pageBlockCnt%>">[이전]</a>
			<%} %>
			
			<%for (int i = startBlock; i <= endBlock; i++) { %>
				<a href="./boardList.bo?pageNum=<%=i%>">[<%=i %>]</a>
			<%} %>
			
			<%if (endBlock < pageBlockCnt && postCnt > pageBlockCnt) { %>
				<a href="./boardList.bo?pageNum=<%=startBlock + pageBlockCnt%>">[다음]</a>
			<%} %>
		</div>
      </div>
  </div>
</section>
<!--
END MODULE AREA 3: Sub Navigation 1
-->

<!--
START MODULE AREA 5: Footer 2
-->
  <jsp:include page="../inc/bottom.jsp"></jsp:include>
<!--
END MODULE AREA 5: Footer 2
-->

<script src="${pageContext.request.contextPath }/js/index.js"></script>
</body>

</html>