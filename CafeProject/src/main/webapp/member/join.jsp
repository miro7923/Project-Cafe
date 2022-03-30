<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<!-- Start Head -->
  <jsp:include page="../inc/top.jsp"></jsp:include>
  <script src="${pageContext.request.contextPath}/js/join.js?testNm=3"></script>
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
      <h3>회원가입</h3><br>
      <form name="join" action="./MemberJoinAction.me" method="post" onsubmit="return finalCheck();"><!-- 빌드 때 onsubmit=return finalCheck() 추가하기! -->
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

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postalcode').value = data.zonecode;
            document.getElementById("roadAddress").value = roadAddr;
            // document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            /*if(roadAddr !== ''){
                document.getElementById("sample4_extraAddress").value = extraRoadAddr;
            } else {
                document.getElementById("sample4_extraAddress").value = '';
            }*/

            var guideTextBox = document.getElementById("guide");
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if(data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';

            } else if(data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }
        }
    }).open();
}
</script>

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

<script src="./js/index.js"></script>
</body>

</html>