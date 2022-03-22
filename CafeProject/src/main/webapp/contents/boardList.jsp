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
          <col width="60%">
          <col width="10%">
          <col width="10%">
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
		<tr>
		<th scope="row">번호</th>
		<td>내용이 들어갑니다.</td>
		<td>작성자</td>
		<td>작성일</td>
		<td>조회수</td>
		</tr>
		</tbody>
		</table>
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