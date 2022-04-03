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
START MODULE AREA 3: Text | Form
-->
<section>
  <div data-layout="_r">
<!--
    <div data-layout="de6 ec5 ec-n3">
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
        dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
    </div>
-->
    <div data-layout="de10 ec-half">
      <!-- 회원정보 수정 페이지로 가기 전에 비밀번호 한 번 더 확인 후 이동 -->
      <h3>회원탈퇴</h3>
      <%
      	String id = (String)session.getAttribute("id");
      	if (null == id)
      		response.sendRedirect("./login.me");
      %>
      <form name="checkPass" action="./MemberDeleteAction.me" method="post" onsubmit="return finalCheck();">
      	<input type="hidden" value="<%=id%>" name="id">
      	<input type="hidden" value="delete" name="actionType">
        <div class="formRow">
          탈퇴하시려면 비밀번호를 한 번 더 입력해 주세요.
        </div><br>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">비밀번호 </label><input id="pass" type="password" name="pass">
        </div>
        <button type="submit" class="btn">확인</button>
      </form>
    </div>
  </div>
</section>
<!--
END MODULE AREA 3: Text | Form
-->

<!--
START MODULE AREA 4: Footer 2
-->
  <jsp:include page="../inc/bottom.jsp"></jsp:include>
<!--
END MODULE AREA 4: Footer 2
-->

<script src="${pageContext.request.contextPath}/js/index.js"></script>
<script src="${pageContext.request.contextPath}/js/checkPass.js?testNm=3"></script>
</body>

</html>