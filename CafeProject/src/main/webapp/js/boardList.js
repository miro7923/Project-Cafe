function memberCheck(flag)
{
	if (flag)
	{
		location.href = './boardWrite.bo';
	}
	else 
	{
		alert('로그인 페이지로 이동합니다.');
		location.href = './login.me';
	}
}