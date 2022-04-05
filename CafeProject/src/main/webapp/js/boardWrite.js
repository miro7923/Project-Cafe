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

function formatCheck()
{
	// 파일명에서 확장자만 추출하기
	var regex = new RegExp("(.*?)\.(jpg|jpeg|png|gif)");
	var maxSize = 10 * 1024 * 1024;
	
	var fileSize = $('#image')[0].files[0].size;
	if (fileSize > maxSize)
	{
		alert('5MB 이하만 첨부 가능합니다.');
		crossBrowsing();
	}
	
	if (!regex.test($('#image').val()))
	{
		alert('확장자가 jpeg, jpg, png, gif인 이미지 파일만 등록 가능합니다.');
		crossBrowsing();
	}
}

function crossBrowsing()
{
	var agent = navigator.userAgent.toLowerCase();
	
	// 크로스 브라우징 처리
	// IE일 때
	if (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1 ||
		agent.indexOf("msie") != -1)
		$('#image').replaceWith($('#image').clone(true));
	else // 그 외 브라우저
		$('#image').val('');	
}

function removeImg()
{
	$('input[name=imgUploadStatus]').val(false);
	$('#imgName').text('없음');
}

function removeFile()
{
	$('input[name=fileUploadStatus]').val(false);
	$('#fileName').text('없음');
}

function resetVal()
{
	alert('resetVal 호출');
	$('input[name=imgUploadStatus]').val(true);
	$('input[name=fileUploadStatus]').val(true);
}