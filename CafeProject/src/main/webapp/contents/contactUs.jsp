<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<!-- Start Head -->
  <jsp:include page="../inc/top.jsp"></jsp:include>
  <script src="${pageContext.request.contextPath }/js/contactUs.js"></script>
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
	/* String id = (String)session.getAttribute("id");
	if (null == id)
		response.sendRedirect("./login.me"); */
%>
<section>
  <div data-layout="_r">
    <!-- <div data-layout="de6 ec5 ec-n3">
      <p></p>
    </div> -->
    <div data-layout="de10 ec-half">
      <h3>Contact us</h3>
      <form name="mail" action="./SendMailAction.bo" method="post" onsubmit="return finalCheck();">
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">이름 </label><input id="MOD_TEXTFORM_NameField" type="text" name="name">
        </div>
        <div id="nameMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_EmailField">답변 받을 이메일 </label><input id="MOD_TEXTFORM_EmailField" type="email" name="email">
        </div>
        <div id="emailMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_TelField">제목 </label><input id="MOD_TEXTFORM_TelField" type="tel" name="title">
        </div>
        <div id="titleMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_MsgField">내용 </label><textarea id="MOD_TEXTFORM_MsgField" placeholder="내용을 입력하세요..." name="content"></textarea>
        </div>
        <div id></div>
        <button type="submit" class="btn">보내기</button>
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