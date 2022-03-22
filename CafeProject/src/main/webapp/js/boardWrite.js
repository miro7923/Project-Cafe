function finalCheck()
{
	var write = document.write;
	
	if (0 >= write.title.value.length)
	{
		// 제목이 입력되지 않았으면 false
		alert("제목을 입력해 주세요!");
		write.title.focus();
		return false;
	}
	
	if (0 >= write.content.value.length)
	{
		// 내용이 입력되지 않았으면 false
		alert('내용을 입력해 주세요!');
		write.content.focus();
		return false;
	}
}