<%@page import="com.project.cafe.board.db.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.project.cafe.member.db.MemberDTO"%>
<%@page import="com.project.cafe.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<!-- Start Head -->
  <jsp:include page="../inc/top.jsp"></jsp:include>
<!-- End Head -->

<body class="modern">

<!--
START MODULE AREA 2: Menu 1
-->
  <jsp:include page="../inc/subTop.jsp"></jsp:include>
<!--
END MODULE AREA 2: Menu 1
-->

<!--
START MODULE AREA 3: Text | Form
-->
<section class="MOD_SUBNAVIGATION1">
  <div data-layout="_r">
    <jsp:include page="../inc/adminLeftNav.jsp"></jsp:include>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
      <h3>게시글 관리 페이지</h3>
      <p style="font-size: medium;">글 제목을 클릭하면 내용을 볼 수 있습니다.</p><br>
      <%
      	String id = (String) session.getAttribute("id");
      	if (null == id)
      		response.sendRedirect("./login.me");
      	else 
      	{
      		if (!id.equals("admin"))
      		{
      			// 관리자 계정이 아니면 쫓아내기
      			session.invalidate();
      			response.sendRedirect("./login.me");
      		}
      	}

    	int postCnt = (int)request.getAttribute("postCnt");
    	ArrayList<BoardDTO> postList = (ArrayList<BoardDTO>)request.getAttribute("postList");
    	
    	String pageNum = (String)request.getAttribute("pageNum");
    	int pageCnt = (int)request.getAttribute("pageCnt");
    	int pageBlockCnt = (int)request.getAttribute("pageBlockCnt");
    	int startBlock = (int)request.getAttribute("startBlock");
    	int endBlock = (int)request.getAttribute("endBlock");
      %>
      <form action="./AdminDeleteAction.bo" method="post" onsubmit="return finalCheck();">
        <table class="type09">
          <tbody>
            <colgroup>
              <col width="5%">
              <col width="10%">
              <col width="40%">
              <col width="15%">
              <col width="">
            </colgroup>
            <tr>
              <th><input type="checkbox" id="selectAll"></th>
              <th>글번호</th>
              <th>글제목</th>
              <th>글쓴이</th>
              <th>작성일</th>
            </tr>
            <%if (!postList.isEmpty())
              {
                for (int i = 0; i < postList.size(); i++)
                  { %>
            <tr>
              <td><input type="checkbox" name="post" value="<%=postList.get(i).getNum() %>"></td>
              <td><%=postList.get(i).getNum() %></td>
              <td><a href="./BoardContent.bo?num=<%=postList.get(i).getNum()%>&pageNum=1"><%=postList.get(i).getTitle() %></a></td>
              <td><%=postList.get(i).getId() %></td>
              <td><%=postList.get(i).getDate() %></td>
            </tr>
            <% }} %>
          </tbody>
        </table><br>
        <div id="boardPage">
		  <%if (startBlock > pageBlockCnt) { %>
			  <a href="./BoardList.bo?flag=a&pageNum=<%=startBlock - pageBlockCnt%>">[이전]</a>
		  <%} %>
			
		  <%for (int i = startBlock; i <= endBlock; i++) { %>
			  <a href="./BoardList.bo?flag=a&pageNum=<%=i%>">[<%=i %>]</a>
		  <%} %>
			
		  <%if (endBlock > pageBlockCnt) { %>
			  <a href="./BoardList.bo?flag=a&pageNum=<%=startBlock + pageBlockCnt%>">[다음]</a>
		  <%} %>
		</div>
        <button type="submit" class="btn">게시글 삭제</button>
      </form>
    </div>
  </div>
</section>

<!--
END MODULE AREA 3: Text | Form
-->

<!--
START MODULE AREA 4: Footer 2
-->
  <jsp:include page="../inc/bottom.jsp"></jsp:include>
<!--
END MODULE AREA 4: Footer 2
-->

<script src="${pageContext.request.contextPath}/js/index.js"></script>
<script src="${pageContext.request.contextPath}/js/boardManagement.js?testNm=2"></script>
</body>

</html>