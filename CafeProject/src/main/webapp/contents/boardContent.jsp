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
	String id = (String)session.getAttribute("id");
	
	boolean isLogin = false;
	if (null == id) isLogin = false;
	else isLogin = true;
	
	BoardDTO dto = (BoardDTO)request.getAttribute("dto");
	String pageNum = (String)request.getAttribute("pageNum");
%>
<section class="MOD_SUBNAVIGATION1">
  <div data-layout="_r">
    <jsp:include page="../inc/leftNav.jsp"></jsp:include>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
    	<h2>최신글 보기</h2>
	  	<p align="right"><button type="button" class="btn" id="writeBtn" 
	  		onclick="memberCheck(<%=isLogin%>);">글쓰기</button></p>
        <table class="type09">
        <colgroup>
          <col width="10%">
          <col width="45%">
          <col width="10%">
          <col width="20%">
          <col width="15%">
        </colgroup>
		<thead>
		<tr>
		<th scope="cols"><%=dto.getNum() %></th>
		<th scope="cols"><%=dto.getTitle() %></th>
		<th scope="cols"><%=dto.getId() %></th>
		<th scope="cols"><%=dto.getDate() %></th>
		<th scope="cols">조회수 <%=dto.getReadcount() %></th>
		</tr>
		</thead>
		<tbody>
			<tr>
			  <td colspan="5" style="white-space:pre-wrap; word-wrap:break-word; word-break: break-all;"><%=dto.getContent() %><br><br><br></td>
			</tr>
			<tr>
			  <td colspan="2">첨부파일</td>
			  <td colspan="3"><%=dto.getFile() %></td>
			</tr>
		</tbody>
		</table><br>
		<div id="boardPage">
		  <%if (null != id && id.equals(dto.getId())) { %>
		    <button type="button" class="btn" 
		      onclick="location.href='./BoardModify.bo?num=<%=dto.getNum()%>&pageNum=<%=pageNum%>';">수정하기</button>
		    <button type="button" class="btn" 
		      onclick="location.href='./BoardDeleteConfirm.bo?num=<%=dto.getNum()%>&pageNum=<%=pageNum%>';">삭제하기</button>
		  <%} %>
		  <button type="button" class="btn" 
		    onclick="location.href='./BoardReWrite.bo?num=<%=dto.getNum()%>&re_ref=<%=dto.getRe_ref()%>&re_lev=<%=dto.getRe_lev()%>&re_seq=<%=dto.getRe_seq()%>';">답글쓰기</button>
		  <button type="button" class="btn" 
		    onclick="location.href='./BoardList.bo?pageNum=<%=pageNum%>';">목록이동</button>
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