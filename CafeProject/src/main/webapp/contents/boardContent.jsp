<%@page import="com.project.cafe.board.db.CommentDTO"%>
<%@page import="com.project.cafe.board.db.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
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
START MODULE AREA 3: Sub Navigation 1
-->
<%
	String id = (String)session.getAttribute("id");
	
	boolean isLogin = false;
	if (null == id) isLogin = false;
	else isLogin = true;
	
	BoardDTO dto = (BoardDTO)request.getAttribute("dto");
	String pageNum = (String)request.getAttribute("pageNum");
	ArrayList<CommentDTO> coList = (ArrayList<CommentDTO>)request.getAttribute("coList");
%>
<section class="MOD_SUBNAVIGATION1">
  <div data-layout="_r">
    <jsp:include page="../inc/leftNav.jsp"></jsp:include>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
    	<h2>자유게시판</h2>
	  	<p align="right"><button type="button" class="btn" id="writeBtn" 
	  		onclick="memberCheck(<%=isLogin%>);">글쓰기</button></p>
        <table class="type09">
        <colgroup>
          <col width="10%">
          <col width="45%">
          <col width="10%">
          <col width="20%">
          <col width="15%">
        </colgroup>
		<thead>
		<tr>
		<th scope="cols"><%=dto.getNum() %></th>
		<th scope="cols"><%=dto.getTitle() %> (<%=dto.getComment_count() %>)</th>
		<th scope="cols"><%=dto.getId() %></th>
		<th scope="cols"><%=dto.getDate() %></th>
		<th scope="cols">조회수 <%=dto.getReadcount() %></th>
		</tr>
		</thead>
		<tbody>
			<tr>
			  <td colspan="5" style="white-space:pre-wrap; word-wrap:break-word; word-break: break-all;"><%=dto.getContent() %><br><br>
			  <%if (dto.getImage() != null && !dto.getImage().equals("없음")) { %>
			  <img src="./BoardImgAction.bo?img_name=<%=dto.getImage() %>"><br><br><br></td>
			  <%} %>
			</tr>
			<tr>
			  <td colspan="2">첨부파일</td>
			  <td colspan="3">
			  <!-- 첨부파일이 있을 때에만 하이퍼링크 연결 -->
			  <%if (dto.getFile() == null || dto.getFile().equals("없음")) { %>
			    <%=dto.getFile() %>
			  <%}
			    else {%>
			    <a href="./BoardFileDownloadAction.bo?file_name=<%=dto.getFile() %>"><%=dto.getFile() %></a>
			    <%} %>
			  </td>
			</tr>
		</tbody>
		</table><br>
		
		<!-- 댓글 영역 -->  
		<div class="comments">
		  <h3>댓글</h3>
		  <input type="hidden" id="postNum" value="<%=dto.getNum()%>">
		  <p>
		  <%if (!coList.isEmpty()) {
			  for (int i = 0; i < coList.size(); i++) { %>
			<span class="comId">
		      <%=coList.get(i).getId() %>&nbsp;		
			</span>
		    <span class="comDate">
		      <%=coList.get(i).getCommentedDate() %>&nbsp;
		    </span>
		    <span>
		    <!-- 본인 글 일때만 수정/삭제 가능 -->
		    <%if (id != null && id.equals(coList.get(i).getId())) { %>
		      <a href="javascript:void(0);" onclick="showCommentBox(<%=i %>);" id="modify">수정&nbsp;</a>
		    </span>
		    <span>
		      <a href="javascript:void(0);" onclick="confirmDelete(<%=coList.get(i).getNum()%>, <%=coList.get(i).getPost_num()%>, <%=pageNum%>);">삭제&nbsp;</a>
		    </span>
		    <% } %>
		  </p>
		  <p class="comContent" id="comContent<%=i%>">
		    <%=coList.get(i).getContent() %>
		  </p>
		  <div style="display: none;" id="modifyComment<%=i%>">
		    <form action="./CommentModifyAction.bo?num=<%=coList.get(i).getNum()%>&post_num=<%=coList.get(i).getPost_num()%>&pageNum=<%=pageNum%>" method="post" onsubmit="return writeComment();">
		      <input type="hidden" name="id" value="<%=id%>">
		      <textarea id="comment" name="comment" rows="10" cols="80" maxlength="500"><%=coList.get(i).getContent() %></textarea>
		      <button type="submit" class="btn" id="commentBtn">입력</button>
		    </form><br>
		  </div><br>
		  <% }} %>
		<form action="./CommentWriteAction.bo?post_num=<%=dto.getNum() %>&pageNum=<%=pageNum %>" method="post" onsubmit="return writeComment();">
		  <input type="hidden" name="id" value="<%=id%>">
		  <textarea id="comment" name="comment" rows="10" placeholder="댓글 달기" maxlength="500"></textarea>
		  <button type="submit" class="btn" id="commentBtn">입력</button>
		</form>
		</div>
		<!-- 댓글 영역 -->
		
		<div id="boardPage">
		  <%if (null != id && id.equals(dto.getId())) { %>
		    <button type="button" class="btn" 
		      onclick="location.href='./BoardModify.bo?num=<%=dto.getNum()%>&pageNum=<%=pageNum%>';">수정하기</button>
		    <button type="button" class="btn" 
		      onclick="deleteCheck(<%=dto.getNum()%>, <%=pageNum%>);">삭제하기</button>
		  <%} %>
		  <button type="button" class="btn" 
		    onclick="location.href='./BoardReWrite.bo?num=<%=dto.getNum()%>&re_ref=<%=dto.getRe_ref()%>&re_lev=<%=dto.getRe_lev()%>&re_seq=<%=dto.getRe_seq()%>';">답글쓰기</button>
		  <button type="button" class="btn" 
		    onclick="location.href='./BoardList.bo?pageNum=<%=pageNum%>';">목록이동</button>
		</div>
    </div>
  </div>
</section>
<section id="commentArea">
</section>
<!--
END MODULE AREA 3: Sub Navigation 1
-->

<!--
START MODULE AREA 5: Footer 2
-->
  <jsp:include page="../inc/bottom.jsp"></jsp:include>
<!--
END MODULE AREA 5: Footer 2
-->

<script src="${pageContext.request.contextPath }/js/index.js"></script>
<script src="${pageContext.request.contextPath }/js/boardContent.js?testNm=2"></script>
</body>

</html>