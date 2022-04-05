<%@page import="com.project.cafe.member.db.MemberDTO"%>
<%@page import="com.project.cafe.member.db.MemberDAO"%>
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
<section class="MOD_SUBNAVIGATION1">
  <div data-layout="_r">
    <jsp:include page="../inc/leftNav.jsp"></jsp:include>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
      <h3>회원 상세 정보</h3><br>
      <%
      	String id = (String) session.getAttribute("id");
      	if (null == id)
      		response.sendRedirect("./login.me");
      	
      	MemberDTO dto = (MemberDTO)request.getAttribute("dto");
      	
      	String[] regdate = dto.getRegdate().toString().split(" ");
      %>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">회원번호 </label><input type="text" name="memberNum" id="memberNum" value="<%=dto.getMemberNum() %>" readonly>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">아이디 </label><input type="text" name="id" id="id" value="<%=dto.getId() %>" readonly>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">이름 </label><input id="MOD_TEXTFORM_NameField" type="text" name="name" class="name" value="<%=dto.getName()%>" readonly>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">생년월일 </label><input id="MOD_TEXTFORM_NameField" type="date" name="birth" class="birth" value="<%=dto.getBirth()%>" readonly>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">나이 </label><input id="MOD_TEXTFORM_NameField" type="text" name="age" class="age" value="<%=dto.getAge()%>" disabled>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">성별 </label><input class="radio" type="radio" name="gender" id="gender" value="남"
          <%if (dto.getGender().equals("남")) { %> 
          checked
          <%} %> readonly><label class="radioText">남</label> 
          <input class="radio" id="gender" type="radio" name="gender" value="여"
          <%if (dto.getGender().equals("여")) { %>
          checked
          <%} %> readonly><label class="radioText">여</label>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">우편번호 </label>
          <label class="phone">
            <input type="text" name="postalcode" id="postalcode" value="<%=dto.getPostalcode() %>" readonly>
          </label>
        </div>
        <span id="guide" style="color:#999;display:none"></span>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">도로명 주소 </label>
          <input type="text" id="roadAddress" name="roadAddress" value="<%=dto.getRoad_address() %>" readonly>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">상세 주소 </label>
		  <input type="text" id="detailAddress" name="detailAddress" value="<%=dto.getDetail_address()%>" readonly>
        </div>
        <div class="formRow" id="phoneArea">
          <label for="MOD_TEXTFORM_TelField">휴대폰 번호 </label>
          <label class="phone">
          <input id="phone1" type="tel" name="phone1" size="1" maxlength="3" value="<%=dto.getPhone().substring(0, 3) %>" oninput="tabCursor(1)" readonly> - 
          <input id="phone2" type="tel" name="phone2" size="3" maxlength="4" value="<%=dto.getPhone().substring(3, 7) %>" oninput="tabCursor(2)" readonly> - 
          <input id="phone3" type="tel" name="phone3" size="3" maxlength="4" value="<%=dto.getPhone().substring(7, 11) %>" readonly>
          </label>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_EmailField">이메일 </label><input type="email" name="email" id="email" value="<%=dto.getEmail()%>" readonly>
        </div>
        <div id="emailMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">가입일 </label><input type="text" name="regdate" id="regdate" value="<%=regdate[0] %>" readonly>
        </div>
      <form action="./AdminDeleteAction.me" method="post">
        <input type="hidden" name="member" value="<%=dto.getId()%>">
        <button type="submit" class="btn">회원삭제</button>
      </form><br>
      <button class="btn" onclick="history.back();">회원 목록 보기</button>
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
<script src="${pageContext.request.contextPath}/js/myPage.js?testNm=3"></script>
</body>

</html>