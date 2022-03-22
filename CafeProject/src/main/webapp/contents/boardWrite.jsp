<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<!-- Start Head -->
  <jsp:include page="../inc/top.jsp"></jsp:include>
  <script src="${pageContext.request.contextPath }/js/boardWrite.js"></script>
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
    <jsp:include page="../inc/leftNav.jsp"></jsp:include>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
    	<h2>게시글 작성</h2>
    	<form name="write" action="./BoardWriteAction.bo" method="post" onsubmit="return finalCheck();">
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">제목 </label><input type="text" name="title" id="title">
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_MsgField">내용 </label>
          <textarea id="MOD_TEXTFORM_MsgField" name="content"></textarea>
        </div>

        <button type="submit" class="btn">글 등록</button>
      </form>
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