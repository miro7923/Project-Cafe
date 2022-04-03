/**
 * 회원가입 유효성 체크
 */
 
var $id = false;
var $pass = false;
var $passConfirm = false;
var $name = false;
var $birth = false;
var $gender = false;
var $postalCode = false;
var $city = false;
var $phone = false;
var $email = false;
var $validateNum;
var $checkValidate = false;
 
$(document).ready(function()
{
	idCheck();
	passCheck();
	passConfirm();
	nameCheck();
	birthCheck();
	emailCheck();
	getToken();
	checkValidateNum();	
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

function idCheck()
{
	$('#id').blur(function()
	{
		$.ajax({
			type: 'POST',
			async: false,
			url: './IdCheck.me',
			data: {
				'id': $('#id').val()
			},
			dataType: 'text',
			success: function(data) {
				if (data === 'true')
				{
					var userId = $('#id').val();
					if (5 > userId.length || 10 < userId.length)
					{
						$id = false;
						$('#idMsg').text('5자리 이상 10자리 이하로 입력해 주세요.');
						$('#idMsg').css('color', 'red');
					}
					else 
					{
						$id = true;
						$('#idMsg').text('사용할 수 있는 아이디입니다.');
						$('#idMsg').css('color', 'green');
					}
				}
				else 
				{
					$id = false;
					$('#idMsg').text('이미 존재하는 아이디입니다.');
					$('#idMsg').css('color', 'red');
				}
			},
			error: function(data) {
				console.log('error');
			}
		});
	});
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

// 생년월일 유효성 검사하는 함수
function birthCheck()
{
	// input 태그에서 type을 date로 지정해 주어 태그단에서 미리 유효한 날짜만 선택할 수 있게 하지만 첫 프로젝트라 연습 삼아 구현했다.
	$('.birth').blur(function()
	{
		var birth = $('.birth').val();
		
		var birthArr = birth.split('-');
		var year = birthArr[0];
		var month = birthArr[1];
		var day = birthArr[2];
		var today = new Date();
		var curYear = today.getFullYear();

		if (10 >= birth.length)
		{
			if (1900 > year || curYear < year) 
				$birth = false;
			else if (1 > month || 12 < month) 
				$birth = false;
			else if (1 > day || 31 < day) 
				$birth = false;
			else if (31 == day && (4 == month || 6 == month || 9 == month || 11 == month))
				$birth = false;
			else if (2 == month)
			{
				// 2월엔 윤년 여부 검사
				var leapYear = (0 == year % 4 && (0 != year % 100 || 0 == year % 400));
				if (29 < day || (29 == day && !leapYear))
					$birth = false;
				else 
					$birth = true;
			}
			else 
				$birth = true;
		}
		else 
			$birth = false;
	});
}

// 성별 선택 여부 확인하는 함수
function genderCheck()
{
	if ($('input[name=gender]').is(":checked"))
	{
		$gender = true;
		$('#genderMsg').text('');
	}	
	else
	{
		$gender = false;
		$('#genderMsg').text('성별을 선택하세요!');
		$('#genderMsg').css('color', 'red');	
	}
}

function postalCodeCheck()
{
	console.log('postalCode 확인함수 호출');
	if ($('#postalcode').val().length <= 0)
	{
		$postalCode = false;
		$('#postalCodeMsg').text('우편번호를 입력하세요!');
		$('#postalCodeMsg').css('color', 'red');
		$('#postalcode').focus();
	}
	else
	{
		$postalCode = true;
		$('#postalCodeMsg').text('');
	}
}

// 도시 선택 여부 확인하는 함수
function cityCheck()
{
	if ($('#roadAddress').val().length <= 0 || $('#detailAddress').val().length <= 0)
	{
		$city = false;
		$('#detailAddress').focus();
		$('#cityMsg').text("주소를 입력하세요!");
		$("#cityMsg").css("color", "red");
	}	
	else 
	{
		$city = true;
		$('#cityMsg').text('');
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

// 이메일 유효성 검사하는 함수
function emailCheck()
{
	$("#email").blur(function()
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
	});
}

// 마지막 제출 전에 유효성 검사하는 함수
function finalCheck() 
{
	var join = document.join;
		
	if (!$id)
	{
		join.id.focus();
		return false;
	}
		
	if (!$pass)
	{
		join.pass.focus();
		return false;
	}

	if (!$passConfirm)
	{
		join.confirm.focus();
		return false;
	}

	if (!$name)
	{
		join.name.focus();
		return false;
	}

	if (!$birth)
	{
		join.birth.focus();
		return false;
	}
	
	genderCheck();
	if (!$gender)
		return false;
		
	postalCodeCheck();
	if (!$postalCode)
		return false;
		
	cityCheck();
	if (!$city)
		return false;
	
	/*phoneCheck();
	if (!$phone)
	{
		join.phone1.focus();
		return false;
	}
	
	if (!$checkValidate)
	{
		join.validateNum.focus();
		return false;
	}*/
	
	if (!$email)
	{
		join.email.focus();
		return false;
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
					console.log("$validateNum : "+$validateNum);
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