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
		.repository-card {
			background-color: #f9f9f9; /* 카드 배경색 */
			transition: transform 0.2s, box-shadow 0.2s;
			padding: 20px;
		}

		.repository-card:hover {
			transform: translateY(-5px); /* 마우스 오버 시 살짝 올라오게 */
			box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 마우스 오버 시 그림자 효과 */
		}

		.delete-btn {
			margin-left: 10px;
			display: inline-block;
		}

		.card-body {
			display: flex;
			justify-content: space-between;
			align-items: center;
		}

		.card-body div {
			flex-grow: 1;
		}

		.badge, .delete-btn {
			margin-left: 10px;
		}

		.d-flex.flex-grow-1 {
			min-height: calc(100vh - 60px); /* 헤더 높이 제외 */
		}

		.ms-sidebar {
			height: calc(100vh - 60px); /* 헤더와 푸터 사이 공간 */
		}
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
			<h1>이력서</h1>

		</main>
	</div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
