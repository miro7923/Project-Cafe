<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<!-- Start Head -->
  <jsp:include page="../inc/top.jsp"></jsp:include>
  <script src="${pageContext.request.contextPath }/js/main.js"></script>
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
<section class="MOD_SUBNAVIGATION1">
  <div data-layout="_r">
    <jsp:include page="../inc/leftNav.jsp"></jsp:include>
    <div data-layout="al-o1 de-o2 de10" class="MOD_SUBNAVIGATION1_Page">
      <h1>반갑습니다!</h1>
      <p class="MOD_SUBNAVIGATION1_Excerpt" data-theme="_ts2_bb2">오늘도 즐거운 하루 되세요☺️</p>
<!--
      <p> Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat
          non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
-->
      <img class="AP_article_image" src="https://unsplash.it/800/300/?random" alt="">
<!--
            <p> Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat
          non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
-->
      </div>
  </div>
</section>
<!--
END MODULE AREA 3: Sub Navigation 1
-->

<!--
START MODULE AREA 4: Article Block 1
-->
<section data-theme="_bgp">
  <div data-layout="_r" class="MOD_ARTICLEBLOCKS1">
    <div data-layout="al16 ec8" class="MOD_ARTICLEBLOCKS1_Cont">
    <h2>최신글</h2>
      <a href="#" class="MOD_ARTICLEBLOCKS1_BlockLarge">
        <div style="background-image:url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/96252/aperitif-large-object1-luca-bravo.jpg)" class="MOD_ARTICLEBLOCKS1_Img" role="img" aria-label="alt text"></div>
        <div class="MOD_ARTICLEBLOCKS1_Txt">
          <h3 class="MOD_ARTICLEBLOCKS1_Title" id="mainTitle1">Article Title</h3>
          <p class="MOD_ARTICLEBLOCKS1_Category" id="mainContent1">Category</p>
        </div>
      </a>
    </div>
    <div data-layout="al16 ch8 ec4" class="MOD_ARTICLEBLOCKS1_Cont">
      <a href="#" class="MOD_ARTICLEBLOCKS1_BlockSmall">
        <div style="background-image:url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/96252/aperitif-large-object1-luca-bravo.jpg)" class="MOD_ARTICLEBLOCKS1_Img" role="img" aria-label="alt text"></div>

        <div class="MOD_ARTICLEBLOCKS1_Txt">
          <h3 class="MOD_ARTICLEBLOCKS1_Title" id="mainTitle2">Article Title</h3>
          <p class="MOD_ARTICLEBLOCKS1_Category" id="mainContent2">Category</p>
        </div>
      </a>
    </div>
    <div data-layout="al16 ch8 ec4" class="MOD_ARTICLEBLOCKS1_Cont">
      <a href="#" class="MOD_ARTICLEBLOCKS1_BlockSmall">
        <div style="background-image:url(https://s3-us-west-2.amazonaws.com/s.cdpn.io/96252/aperitif-large-object1-luca-bravo.jpg)" class="MOD_ARTICLEBLOCKS1_Img" role="img" aria-label="alt text"></div>

        <div class="MOD_ARTICLEBLOCKS1_Txt">
          <h3 class="MOD_ARTICLEBLOCKS1_Title" id="mainTitle3">Article Title</h3>
          <p class="MOD_ARTICLEBLOCKS1_Category" id="mainContent3">Category</p>
        </div>
      </a>
    </div>
  </div>
</section>
<!--
END MODULE AREA 4: Article Block 1
-->

<!--
START MODULE AREA 5: Footer 2
-->
  <jsp:include page="../inc/bottom.jsp"></jsp:include>
<!--
END MODULE AREA 5: Footer 2
-->

<script src="${pageContext.request.contextPath }/js/index.js"></script>
</body>

</html>