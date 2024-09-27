<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>BringUp</title>

	<!-- 메타 태그 -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="author" content="Webestica.com">
	<meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">

	<!-- 다크 모드 -->
	<script src="/resources/script/common/darkmode/darkmode.js"></script>
	
	<!-- 파비콘 -->
	<link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

	<!-- 구글 폰트 -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

	<!-- 플러그인 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/apexcharts/css/apexcharts.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">
	

	<!-- 테마 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

	<!-- Bootstrap JS -->
	<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

	<!-- 벤더 -->
	<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
	<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

	<!-- 테마 JS -->
	<script src="/resources/script/common/function/functions.js"></script>

	<!-- JQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <!-- 메인 스타일시트 -->
    <link rel="stylesheet" type="text/css" href="/resources/style/member/potofolio.css">
	
    <!--  JS -->
	<!-- <script src="/resources/script/member/user/파일명.js"></script> -->

</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
	<main class="container" style="max-width: 1260px;">
		<div class="flex-grow-1">
			<div class="potofolio-container">
                <aside class="sidebar">
                    <h2 class="sidebar-title">포트폴리오</h2>
                    <ul class="sidebar-menu">
                        <li><a href="#">홈</a></li>
                        <li><a href="#">자기소개서</a></li>
                        <li><a href="#">학력 및 경력</a></li>
                        <li><a href="#">기술 스택</a></li>
                        <li><a href="#">포트폴리오 및 기타문서</a></li>
                        <li><a href="#">자격증</a></li>
                        <li><a href="#">연락처</a></li>
                    </ul>
                </aside>
            
                <div class="content">
                    <h2 class="content-title">포트폴리오 및 기타문서</h2>
            
                    <section class="section">
                        <h3 class="section-title">Github</h3>
                        <label for="github-repo">링크 :</label>
                        <input type="text" id="github-repo" placeholder="Github 리포지토리 링크">
                        <button type="button">리포지토리 선택</button>
                        <div class="repo-links">
                            <button type="button">리포지토리1</button>
                            <button type="button">리포지토리2</button>
                            <button type="button">리포지토리3</button>
                            <button type="button">리포지토리4</button>
                        </div>
                    </section>
            
                    <section class="section">
                        <h3 class="section-title">Notion</h3>
                        <label for="notion-link">링크 :</label>
                        <input type="text" id="notion-link" placeholder="Notion 링크">
                    </section>
            
                    <section class="section">
                        <h3 class="section-title">Blog</h3>
                        <label for="blog-link">링크 :</label>
                        <input type="text" id="blog-link" placeholder="블로그 링크">
                    </section>
            
                    <section class="section">
                        <h3 class="section-title">File</h3>
                        <label for="file-upload">첨부 파일 :</label>
                        <input type="file" id="file-upload">
                    </section>
                </div>
            </div>
		</div>
	</main>
</body>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
