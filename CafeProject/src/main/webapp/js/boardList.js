function memberCheck(flag)
{
	if (flag)
	{
		location.href = './BoardWrite.bo';
	}
	else 
	{
		alert('로그인 페이지로 이동합니다.');
		location.href = './login.me';
	}
}

function deleteCheck()
{
	var num = $('#num').val();
	var pageNum = $('#pageNum').val();
	if (confirm('정말 삭제 하시겠습니까?'))
		location.href='./BoardDelete.bo?num='+num+'&pageNum='+pageNum;
	else 
		history.back();
}