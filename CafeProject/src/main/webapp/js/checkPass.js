/**
 * 회원 비밀번호 한 번 더 확인 후 정보 수정
 */

function finalCheck()
{
	var checkPass = document.checkPass;
	
	if (0 >= checkPass.pass.value.length)
	{
		// 비밀번호가 입력되지 않았으면 false
		alert("비밀번호를 입력해 주세요!");
		checkPass.pass.focus();
		return false;
	}
}