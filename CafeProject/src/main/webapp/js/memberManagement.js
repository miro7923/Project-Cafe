$(document).ready(function()
{
	selectAll();
});

function selectAll()
{
	// 전체 선택 / 해제
	$('#selectAll').change(function()
	{		
		if ($('#selectAll').is(':checked'))
			$('input:checkbox[name=member]').prop('checked', true);
		else 
			$('input:checkbox[name=member]').prop('checked', false);
	});
}

function finalCheck()
{
	if ($('input:checkbox[name=member]:checked').length <= 0)
	{
		// 선택된 엘리먼트가 없으면 삭제할 회원이 없음
		alert('선택된 회원이 없습니다!');
		return false;
	}
	else 
	{
		// 선택된 회원이 있을 때
		if (confirm('선택한 회원들을 탈퇴 시키겠습니까?'))
			return true;
		else 
			return false;
	}
}