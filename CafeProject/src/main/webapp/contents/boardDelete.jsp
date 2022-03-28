<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath }/js/jquery-3.6.0.js"></script>
</head>
<body>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	String pageNum = request.getParameter("pageNum");
%>
<input type="hidden" id="num" value="<%=num%>">
<input type="hidden" id="pageNum" value="<%=pageNum%>">
<script type="text/javascript">
	function deleteCheck()
	{
		var num = $('#num').val();
		var pageNum = $('#pageNum').val();
		if (confirm('정말 삭제 하시겠습니까?'))
			location.href='./BoardDelete.bo?num='+num+'&pageNum='+pageNum;
		else 
			history.back();
	}
	
	deleteCheck();
</script>
</body>
</html>