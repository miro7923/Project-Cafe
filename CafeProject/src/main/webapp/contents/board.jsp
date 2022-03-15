<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<!-- Start Head -->
  <jsp:include page="../inc/top.jsp"></jsp:include>
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
<section class="MOD_SUBNAVIGATION1">
  <div data-layout="_r">
    <div data-layout="al16 al-o2 de-o1 de6 ec4">
      <nav class="MOD_SUBNAVIGATION1_Menu" data-theme="_bo2">
        <p class="MOD_SUBNAVIGATION1_Menutitle" data-theme="_bgs">Menu</p>
        <ul>
          <li><a href="#">최신글 보기</a></li>
          <li><a href="#">출석부</a></li>
          <li><a href="#">자유게시판</a></li>
          <li><a href="#">자료실</a></li>
<!--          <li><a href="#">Sub menu item 5</a></li>-->
<!--          <li><a href="#">Sub menu item 6</a></li>-->
<!--          <li><a href="#">Sub menu item 7</a></li>-->
        </ul>
      </nav>
    </div>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
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
		<tr>
		<th scope="row">번호</th>
		<td>내용이 들어갑니다.</td>
		<td>작성자</td>
		<td>작성일</td>
		<td>조회수</td>
		</tr>
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