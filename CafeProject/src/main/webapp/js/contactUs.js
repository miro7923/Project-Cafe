function finalCheck()
{
	var mail = document.mail;
	
	if (mail.name.value.length <= 0)
	{
		alert('이름을 입력하세요!');
		mail.name.focus();
		return false;
	}
	
	if (mail.email.value.length <= 0)
	{
		alert('연락 받을 이메일을 입력하세요!');
		mail.email.focus();
		return false;
	}
	
	if (mail.title.value.length <= 0)
	{
		alert('제목을 입력하세요!');
		mail.title.focus();
		return false;
	}
	
	if (mail.content.value.length <= 0)
	{
		alert('내용을 입력하세요!');
		mail.content.focus();
		return false;
	}
}