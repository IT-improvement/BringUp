<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>BringUp</title>

	<!-- Meta Tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="author" content="Webestica.com">
	<meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">

	<!-- Dark Mode -->
	<script src="/resources/script/common/darkmode/darkmode.js"></script>

	<!-- Favicon -->
	<link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

	<!-- Google Fonts -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

	<!-- Plugins CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/apexcharts/css/apexcharts.css">
	<link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">

	<!-- Theme CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

	<!-- Bootstrap JS -->
	<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

	<!-- JQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<style>

	</style>

</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
	<!-- Sidebar -->
	<jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

	<div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
		<main class="flex-grow-1">
			<div class="container py-4">
				<h2>자소서 관리</h2>
				<div class="alert alert-info mt-4" role="alert">
					📝 초안 자동생성, 맞춤법 및 표검검사, 마무리 코칭까지 해주는
				</div>

				<!-- 자소서 등록하기 Section -->
				<div class="d-flex justify-content-end mb-3">
					<button class="btn btn-primary me-2">파일로 등록</button>
					<button class="btn btn-primary">자소서 등록하기</button>
				</div>

				<!-- 자소서 List Section -->
				<div class="card mb-4">
					<div class="card-body">
						<h6 class="card-title">박주혁님의 자소서_240314 11:17</h6>
						<p class="card-text">
							[대학생의 DBMS 경험으로 미래를 열다] DBMS에 대해 관심을 가지며 3학년 때 여러 개의 프로젝트를 경험해 보았습니다.
							DBMS를 활용하여 데이터베이스를 구축하고 관리하는 과정에서 많은 것을 배우게 되었습니다.
						</p>
						<small class="text-muted">2024.03.14 11:18:32 수정</small>
						<!-- Action Buttons -->
						<div class="d-flex justify-content-end">
							<button class="btn btn-outline-secondary me-2">수정하기</button>
							<button class="btn btn-outline-secondary">AI 자소서 코칭받기</button>
						</div>
					</div>
				</div>

				<!-- Search Section -->
				<div class="d-flex justify-content-end mb-3">
					<input type="text" class="form-control me-2" style="max-width: 300px;" placeholder="자기소개서 제목, 문항, 내용으로 검색해보세요">
					<button class="btn btn-outline-secondary">검색</button>
				</div>
			</div>
		</main>

	</div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
