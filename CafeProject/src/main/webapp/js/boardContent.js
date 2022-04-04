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

function showCommentBox(idx)
{
	var con = document.getElementById('modify' + idx);
	var comBlock = document.getElementById('modifyComment' + idx);
	if (comBlock.style.display == 'none')
	{
		comBlock.style.display = 'inline-block';
		con.innerHTML = '취소';
	}
	else 
	{
		comBlock.style.display = 'none';
		con.innerHTML = '수정';
	}
}

function confirmDelete(num, postNum, pageNum)
{
	if (confirm('정말 삭제 하시겠습니까?'))
		location.href = './CommentDeleteAction.bo?num='+num+'&post_num='+postNum+'&pageNum='+pageNum;
}