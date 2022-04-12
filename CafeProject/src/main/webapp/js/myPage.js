/**
 * 회원정보 수정 유효성 검사
 */

var $pass = false;
var $passConfirm = false;
var $name = false;
var $phone = false;
var $email = false;
var $changePh = false;
var $phone = false;
var $validateNum;
var $checkValidate = false;
var $email = false;

$(document).ready(function()
{
	passCheck();
	passConfirm();
	nameCheck();
	changePhone();
	phoneCheck();
	emailCheck();
	
	if ($changePh)
	{
		getToken();
		checkValidateNum();
	}
});

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

            /* var guideTextBox = document.getElementById("guide");
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
            } */
        }
    }).open();
}

function confirmToDelete()
{
	console.log('confirmToDelete() 호출');
	if (!confirm('정말 회원 탈퇴 하시겠습니까?'))
		return false;
	else
		return true;
}

// 비밀번호 유효성 검사하는 함수
function passCheck()
{
	$('#pass').blur(function()
	{
		var pass = $('#pass').val();
		var num = pass.search(/[0-9]/g);
		var eng = pass.search(/[a-z]/ig);
		var spe = pass.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

		if (8 > pass.length || 20 < pass.length)
		{
			$pass = false;
			$('#passMsg').text('8자 ~ 20자 이내로 입력해 주세요.');
			$('#passMsg').css('color', 'red');
		}
		else if (pass.search(/\s/) != -1)
		{
			$pass = false;
			$('#passMsg').text('공백 없이 입력해 주세요.');
			$('#passMsg').css('color', 'red');
		}
		else if (num < 0 || eng < 0 || spe < 0)
		{
			$pass = false;
			$('#passMsg').text('영문, 숫자, 특수문자를 포함해서 입력해 주세요.');
			$('#passMsg').css('color', 'red');
		}
		else 
		{
			$pass = true;
			$('#passMsg').text('사용 가능한 비밀번호 입니다!');
			$('#passMsg').css('color', 'green');
		}
	});
}

// 재입력한 비밀번호가 맞는지 확인하는 함수
function passConfirm()
{
	$('#confirm').blur(function() 
	{
		var pass = $('#confirm').val();

		if (0 >= pass.length)
		{
			$passConfirm = false;
			$('#confirmMsg').text('비밀번호를 한 번 더 입력해 주세요.');
			$('#confirmMsg').css('color', 'red');
		}
		else if ($('#pass').val() !== pass)
		{
			$passConfirm = false;
			$('#confirmMsg').text('비밀번호가 일치하지 않습니다.');
			$('#confirmMsg').css('color', 'red');
		}
		else
		{
			$passConfirm = true;
			$('#confirmMsg').text('비밀번호가 일치합니다!');
			$('#confirmMsg').css('color', 'green');
		}
	});
}

// 이름 입력 여부 확인하는 함수
function nameCheck()
{
	$('.name').blur(function()
	{
		var name = $('.name').val();

		if ("" === name)
		{
			$name = false;
			$('#nameMsg').text('이름을 입력해 주세요.');
			$('#nameMsg').css('color', 'red');
		}
		else 
		{
			$name = true;
			$('#nameMsg').text('');
		}
	});
}

function changePhoneBtnStatus()
{
	// 휴대폰 번호 변경 버튼을 클릭하면 부분만 새로고침
	$changePh = true;
	$("#phoneArea").load(location.href + " #phoneArea");
	$("#validateArea").load(location.href + " #validateArea");
	changePhone();
}

function changePhone()
{
	if ($changePh)
	{
		// 휴대폰 번호 변경 
		// 휴대폰변경 버튼을 누르면 휴대폰 번호 입력란과 인증번호 입력란을 활성화 시킴
		$("#phone1").removeAttr("readonly");
		$("#phone2").removeAttr("readonly");
		$("#phone3").removeAttr("readonly");
		$("#validateNum").removeAttr("disabled");
		$("#validateBtn").removeAttr("disabled");
		$("#requestBtn").html('휴대폰인증');
	}
	else 
	{
		// 초기값은 변경 불가능
		$("#phone1").attr("readonly", true);
		$("#phone2").attr("readonly", true);
		$("#phone3").attr("readonly", true);
		$("#validateNum").attr("disabled", true);
		$("#validateBtn").attr("disabled", true);
		$("#requestBtn").html('휴대폰변경');
	}
}

// 휴대폰 번호 유효성 검사하는 함수
function phoneCheck()
{
	var first = $("#phone1").val();
	var second = $("#phone2").val();
	var third = $("#phone3").val();
	
	if ("010" == first && 4 == second.length && 4 == third.length)
	{
		$phone = true;
		$("#phoneMsg").text("");
	}	
	else 
	{
		$phone = false;
		$("#phoneMsg").text("휴대폰 번호 형식이 올바르지 않습니다!");
		$("#phoneMsg").css("color", "red");
	}
}

function getToken()
{
	$('#requestBtn').click(function()
	{
		phoneCheck();
		if ($phone)
		{
			var phoneNum = $('#phone1').val() + $('#phone2').val() + $('#phone3').val();
			$.ajax({
				type: 'POST',
				async: false,
				url: './validatePhone.me',
				data: {
					'phone': phoneNum
				},
				dataType: 'text',
				success: function(data)
				{
					alert('인증번호 전송 완료!');
					$validateNum = data;
				}
			});					
		}
	});
}

function checkValidateNum()
{
	$('#validateBtn').click(function()
	{
		var userInput = $("#validateNum").val();
		if ($validateNum === userInput)
		{
			$checkValidate = true;
			$("#validateMsg").text("인증이 완료되었습니다!");
			$("#validateMsg").css("color", "green");
			$("#validateBtn").prop("disabled", true);
			$("#requestBtn").prop("disabled", true);
			$("#validateBtn").css("background", "gray");
			$("#requestBtn").css("background", "gray");
		}
		else 
		{
			$checkValidate = false;
			$("#validateMsg").text("인증번호가 일치하지 않습니다.");
			$("#validateMsg").css("color", "red");
		}
	});
}

// 휴대폰 번호 필드 1, 2번 칸에서 지정된 숫자만큼 입력하면 다음 칸으로 커서 넘기는 함수
function tabCursor(section)
{
	var ph = 0;
	switch (section)
	{
		case 1:
			ph = $("#phone1").val();
			if (3 === ph.length)
				$("#phone2").focus();
			break;
		
		case 2:
			ph = $("#phone2").val();
			if (4 === ph.length)
				$("#phone3").focus();
			break;
	}
}

// 이메일 유효성 검사하는 함수
function emailCheck()
{
	var email = $("#email").val();
	var regEmail = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
	if (regEmail.test(email))
	{
		$email = true;
		$("#emailMsg").text("");
	}
	else 
	{
		$email = false;
		$("#emailMsg").text("이메일 형식이 올바르지 않습니다!");
		$("#emailMsg").css("color", "red");
	}
}

function finalCheck()
{
	var myPage = document.myPage;
	
	if (!$pass)
	{
		// 비밀번호가 입력되지 않았으면 false
		alert('비밀번호를 확인해 주세요!');
		myPage.pass.focus();
		return false;
	}
	
	passConfirm();
	if (!$passConfirm)
	{
		// 비밀번호 확인이 일치하지 않으면 false
		alert('비밀번호를 정확하게 입력했는지 확인해 주세요!');
		myPage.confirm.focus();
		return false;
	}
	
	phoneCheck();
	if ($changePh && (!$phone || !$checkValidate))
	{
		// 휴대폰 번호를 바꾸는 상태인데 휴대폰 번호가 올바르지 않거나 인증을 하지 않았으면 false
		myPage.phone1.focus();
		return false;
	}
	
	emailCheck();
	if (!$email)
	{
		myPage.email.focus();
		return false;
	}
}