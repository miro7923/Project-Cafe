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
    <div data-layout="al16 al-o2 de-o1 de6 ec4">
      <jsp:include page="../inc/leftNav.jsp"></jsp:include>
    </div>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
      <h3>회원가입</h3><br>
      <form name="join" action="./MemberJoinAction.me" method="post" onsubmit="return finalCheck();">
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">아이디 </label><input type="text" name="id" id="id" placeholder="5~10자 이내의 영문+숫자 조합">
        </div>
        <div id="idMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">비밀번호 </label><input type="password" name="pass" id="pass" placeholder="8~20자 영문+숫자+특수문자 조합">
        </div>
        <div id="passMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">비밀번호 확인 </label><input type="password" name="confirm" id="confirm" placeholder="비밀번호를 다시 입력하세요.">
        </div>
        <div id="confirmMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">이름 </label><input id="MOD_TEXTFORM_NameField" type="text" name="name" class="name" placeholder="이름을 입력하세요.">
        </div>
        <div id="nameMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">생년월일 </label><input id="MOD_TEXTFORM_NameField" type="date" name="birth" class="birth">
        </div>
        <div id="birthMsg"></div>
        <!-- <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">나이 </label><input id="MOD_TEXTFORM_NameField" type="text" name="age" class="age">
        </div>
        <div id="ageMsg"></div> -->
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">성별 </label><input class="radio" type="radio" name="gender" id="gender" value="남"><label class="radioText">남</label> 
          <input class="radio" id="gender" type="radio" name="gender" value="여"><label class="radioText">여</label>
        </div>
        <div id="genderMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">우편번호 </label>
          <label class="phone">
            <input type="text" name="postalcode" id="postalcode" placeholder="우편번호" readonly>
            <button id="postalBtn" class="btn" name="postalBtn" onclick="execDaumPostcode()">우편번호 찾기</button><br>
          </label>
        </div>
        <div id="postalCodeMsg"></div>
        <span id="guide" style="color:#999;display:none"></span>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">도로명 주소 </label>
          <input type="text" id="roadAddress" name="roadAddress" placeholder="도로명주소" readonly>
        </div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_NameField">상세 주소 </label>
		  <input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소">
        </div>
        <div id="cityMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_TelField">휴대폰 번호 </label>
          <label class="phone">
            <input id="phone1" type="tel" name="phone1" size="1" maxlength="3" oninput="tabCursor(1)"> - 
            <input id="phone2" type="tel" name="phone2" size="3" maxlength="4" oninput="tabCursor(2)"> - 
            <input id="phone3" type="tel" name="phone3" size="3" maxlength="4">
            <button id="requestBtn" class="btn" name="requestBtn">휴대폰인증</button>
          </label>
        </div>
        <div id="phoneMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_EmailField">인증번호 </label>
          <label class="phone">
          <input type="text" name="validateNum" id="validateNum">
          <button id="validateBtn" class="btn" name="validateBtn">인증번호 확인</button>
          </label>
        </div>
        <div id="validateMsg"></div>
        <div class="formRow">
          <label for="MOD_TEXTFORM_EmailField">이메일 </label><input type="email" name="email" id="email" placeholder="abc123@gmail.com">
        </div>
        <div id="emailMsg"></div>
<!--
        <div class="formRow">
          <label for="MOD_TEXTFORM_MsgField">Message </label><textarea id="MOD_TEXTFORM_MsgField" placeholder="Enter your message..."></textarea></div>
-->
        <button type="submit" class="btn">회원 가입</button>
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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="${pageContext.request.contextPath}/js/join.js?testNm=2"></script>
</body>

</html>