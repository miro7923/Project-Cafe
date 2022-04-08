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
      <h3>마이페이지</h3><br>
      <%
      	String id = (String) session.getAttribute("id");
      	if (null == id)
      		response.sendRedirect("./login.me");
      	
      	MemberDTO dto = (MemberDTO)session.getAttribute("dto");
      	
      	String[] regdate = dto.getRegdate().toString().split(" ");
      %>
      <form name="myPage" action="./MemberUpdateAction.me" method="post" onsubmit="return finalCheck();">
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">회원번호 </label><input type="text" name="memberNum" id="memberNum" value="<%=dto.getMemberNum() %>" readonly>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">아이디 </label><input type="text" name="id" id="id" value="<%=dto.getId() %>" readonly>
        </div>
        <div id="idMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">비밀번호 </label><input type="password" name="pass" id="pass">
        </div>
        <div id="passMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">비밀번호 확인 </label><input type="password" name="confirm" id="confirm">
        </div>
        <div id="confirmMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">이름 </label><input id="MOD_TEXTFORM_NameField" type="text" name="name" class="name" value="<%=dto.getName()%>">
        </div>
        <div id="nameMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">생년월일 </label><input id="MOD_TEXTFORM_NameField" type="date" name="birth" class="birth" value="<%=dto.getBirth()%>">
        </div>
        <div id="birthMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">나이 </label><input id="MOD_TEXTFORM_NameField" type="text" name="age" class="age" value="<%=dto.getAge()%>" disabled>
        </div>
        <div id="ageMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">성별 </label><input class="radio" type="radio" name="gender" id="gender" value="남"
          <%if (dto.getGender().equals("남")) { %> 
          checked
          <%} %>><label class="radioText">남</label> 
          <input class="radio" id="gender" type="radio" name="gender" value="여"
          <%if (dto.getGender().equals("여")) { %>
          checked
          <%} %>><label class="radioText">여</label>
        </div>
        <div id="genderMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">우편번호 </label>
          <label class="phone">
            <input type="text" name="postalcode" id="postalcode" value="<%=dto.getPostalcode() %>" readonly>
            <button id="postalBtn" class="btn" name="postalBtn" onclick="execDaumPostcode()">우편번호 찾기</button><br>
          </label>
        </div>
        <div id="postalCodeMsg"></div>
        <span id="guide" style="color:#999;display:none"></span>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">도로명 주소 </label>
          <input type="text" id="roadAddress" name="roadAddress" value="<%=dto.getRoad_address() %>" readonly>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">상세 주소 </label>
		  <input type="text" id="detailAddress" name="detailAddress" value="<%=dto.getDetail_address()%>">
        </div>
        <div id="cityMsg"></div>
        <div class="formRow" id="phoneArea">
          <label for="MOD_TEXTFORM_TelField">휴대폰 번호 </label>
          <label class="phone">
          <input id="phone1" type="tel" name="phone1" size="1" maxlength="3" value="<%=dto.getPhone().substring(0, 3) %>" oninput="tabCursor(1)"> - 
          <input id="phone2" type="tel" name="phone2" size="3" maxlength="4" value="<%=dto.getPhone().substring(3, 7) %>" oninput="tabCursor(2)"> - 
          <input id="phone3" type="tel" name="phone3" size="3" maxlength="4" value="<%=dto.getPhone().substring(7, 11) %>">
          <button id="requestBtn" class="btn" type="button" name="requestBtn" onclick="changePhoneBtnStatus();"></button>
          </label>
        </div>
        <div id="phoneMsg"></div>
        <div class="formRow" id="validateArea">
          <label for="MOD_TEXTFORM_EmailField">인증번호 </label>
          <label class="phone">
          <input type="text" name="validateNum" id="validateNum">
          <button id="validateBtn" class="btn" type="button" name="validateBtn">인증번호 확인</button>
          </label>
        </div>
        <div id="validateMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_EmailField">이메일 </label><input type="email" name="email" id="email" value="<%=dto.getEmail()%>">
        </div>
        <div id="emailMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">가입일 </label><input type="text" name="regdate" id="regdate" value="<%=regdate[0] %>" readonly>
        </div>
        <button type="submit" class="btn">회원 정보 수정</button>
      </form><br>
      <form action="./delete.me" method="post">
        <input type="hidden" name="id" value="<%=dto.getId()%>">
        <input type="hidden" name="pass" value="<%=dto.getPass()%>">
        <button type="submit" class="btn">회원탈퇴</button>
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
<script src="${pageContext.request.contextPath}/js/myPage.js?testNm=4"></script>
</body>

</html>