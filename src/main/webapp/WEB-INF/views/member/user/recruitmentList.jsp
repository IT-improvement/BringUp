<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>BringUp</title>

	<!-- 메타 태그 -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- 파비콘 -->
	<link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

	<!-- 구글 폰트 -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&display=swap" rel="stylesheet">

	<!-- 플러그인 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/member/recruitment.css">

	<!-- jQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">
<!-- header-->
	<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<main class="flex-grow-1">
	<div class="container mt-5">
		<h2 class="section-title">추천 채용 공고</h2>
		<div id="recruitmentList" class="list-group recruitment-list">
			<!-- 미리 정의된 HTML 구조 -->
			<div class="list-group-item recruitment-item d-none" id="recruitmentTemplate">
				<div class="d-flex justify-content-between">
					<div>
						<h5 class="mb-1 recruitment-title"></h5>
						<p class="mb-1 recruitment-company"></p>
						<p class="mb-1 recruitment-type"></p>
						<p class="mb-1 recruitment-period"></p>
						<p class="mb-1 recruitment-skills"></p>
						<small class="text-muted recruitment-status"></small>
					</div>
					<div class="align-self-center">
						<a href="#" class="btn btn-outline-primary recruitment-link">공고 보기</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>

<!-- footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

<!-- 별도의 JavaScript 파일을 로드 -->
</body>
</html>
