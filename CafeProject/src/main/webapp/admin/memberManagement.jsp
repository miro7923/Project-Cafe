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
      <h3>회원관리 페이지</h3>
      <p style="font-size: medium;">회원 아이디를 클릭하면 상세 정보를 볼 수 있습니다.</p><br>
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
      	
      	ArrayList<MemberDTO> memberList = (ArrayList<MemberDTO>)request.getAttribute("memberList");
      %>
      <form action="./AdminDeleteAction.me" method="post" onsubmit="return finalCheck();">
        <table class="type09">
          <tbody>
            <colgroup>
              <col width="5%">
              <col width="10%">
              <col width="10%">
              <col width="35%">
              <col width="">
            </colgroup>
            <tr>
              <th><input type="checkbox" id="selectAll"></th>
              <th>아이디</th>
              <th>이름</th>
              <th>이메일</th>
              <th>가입일</th>
            </tr>
            <%if (!memberList.isEmpty())
              {
                for (int i = 0; i < memberList.size(); i++)
                  { %>
            <tr>
              <td><input type="checkbox" name="member" value="<%=memberList.get(i).getId() %>"></td>
              <td><a href="./MemberInfoAction.me?id=<%=memberList.get(i).getId()%>"><%=memberList.get(i).getId() %></a></td>
              <td><%=memberList.get(i).getName() %></td>
              <td><%=memberList.get(i).getEmail() %></td>
              <td><%=memberList.get(i).getRegdate() %></td>
            </tr>
            <% }} %>
          </tbody>
        </table><br>
        <button type="submit" class="btn">회원 삭제</button>
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
<script src="${pageContext.request.contextPath}/js/memberManagement.js?testNm=2"></script>
</body>

</html>