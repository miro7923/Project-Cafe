function memberCheck(flag)
{
	if (flag)
		location.href = './BoardWrite.bo';
	else 
	{
		alert('로그인 페이지로 이동합니다.');
		location.href = './login.me';
	}
}

function deleteCheck(num, pageNum)
{
	if (confirm('정말 삭제 하시겠습니까?'))
		location.href='./BoardDelete.bo?num='+num+'&pageNum='+pageNum;
}

function writeComment()
{
	if ($('#comment').val().length <= 0)
	{
		alert('내용을 입력하세요.');
		return false;
	}
	else
		return true;
}