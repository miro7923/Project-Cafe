/**
 * 로그인 상태에 따라 top 메뉴 다르게 출력
 */

var $login = false;

function loginCheck() 
{
	$.ajax({
		type: 'POST',
		async: false,
		url: "./LoginCheck.me",
		data: {
			'id': $("#id").val(),
			'pass': $("#pass").val()
		},
		dataType: 'text',
		success: function(data) 
		{
			console.log("success data : " + data);
			if (1 == data) 
			{
				// 회원
				$login = true;
				alert("로그인 성공!");
				location.href = "./main.me";
			}
			else if (0 == data) 
			{
				// 비밀번호 오류
				alert("아이디 혹은 비밀번호가 일치하지 않습니다!");
				$login = false;
			}
			else 
			{
				// 비회원
				alert("회원이 아닙니다.");
				$login = false;
			}
		},
		error: function(data) 
		{
			console.log("error");
		}
	});
	
}

function finalCheck() 
{
	loginCheck();
	if ($login)
		return true;
	else
		return false;
}