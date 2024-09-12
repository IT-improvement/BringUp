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

		<script src="/resources/script/common/notification/notification.js"></script>
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

		<!-- 메인 JS -->
		<!-- <script src="/resources/script/company/main.js"></script> -->
		<style>
			/* Layout Styles */
			body {
				display: flex;
				flex-direction: column;
				min-height: 100vh;
			}

			main {
				flex-grow: 1;
				padding: 70px;
			}

			/* 광고 1번과 로그인 폼 */
			.top-section {
				display: flex;
				gap: 20px;
				margin-bottom: 20px;
			}

			.ad1 {
				background-color: #ccc;
				text-align: center;
				height: 300px;
				flex: 3;
			}


			/* 로그인 스타일 */
			.login-box {
				width: 370px;
				padding: 20px;
				background-color: #f0f0f0;
				border-radius: 10px;
				box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
				font-family: Arial, sans-serif;
				text-align: center;
				margin: 20px auto;
			}

			.user-profile {
				display: flex;
				align-items: center;
				margin-bottom: 10px;
			}

			.user-profile img {
				border-radius: 50%;
				width: 50px;
				height: 50px;
				margin-right: 10px;
			}

			.user-info {
				text-align: left;
			}

			.user-info strong {
				font-size: 18px;
				display: block;
			}

			.user-info small {
				font-size: 14px;
				color: gray;
			}

			.status {
				display: flex;
				justify-content: space-between;
				margin: 10px 0;
				font-size: 14px;
			}

			.status .badge {
				background-color: #4caf50;
				color: white;
				padding: 5px 10px;
				border-radius: 15px;
				font-size: 12px;
			}

			.logout-btn {
				margin-top: 20px;
				background-color: #007bff;
				color: white;
				border: none;
				padding: 10px 20px;
				border-radius: 5px;
				cursor: pointer;
				width: 100%;
			}

			.menu {
				display: flex;
				justify-content: space-around;
				margin-top: 20px;
			}

			.menu div {
				text-align: center;
				font-size: 14px;
			}

			.menu div a {
				color: black;
				text-decoration: none;
			}

			/* 광고 2번과 레이블 */
			.main-content {
				display: flex;
				gap: 20px;
				margin-bottom: 20px;
			}

			.ad2 {
				background-color: #ccc;
				text-align: center;
				height: 450px;
				flex: 4;
			}

			.labels {
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				height: 300px;
				flex: 1;
			}

			.label {
				background-color: #ddd;
				height: 40px;
				text-align: center;
				line-height: 40px;
				margin-bottom: 10px;
			}

			/* 광고 3번과 무한 스크롤 */
			.ad3 {
				background-color: #ccc;
				height: 100px;
				text-align: center;
				margin-bottom: 20px;
			}

			.grid-container {
				display: grid;
				grid-template-columns: repeat(3, 1fr);
				grid-gap: 20px;
			}

			.grid-item {
				background-color: #ddd;
				text-align: center;
				padding: 20px;
			}

			.infinite-scroll {
				max-height: 400px;
				overflow-y: auto;
			}

		</style>

		<!-- JQuery -->
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	</head>
	<body>

	<!-- 헤더 -->
	<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

	<!-- 메인 콘텐츠 -->
	<main>
		<!-- 광고 1번과 로그인 폼 -->
		<div class="top-section">
			<div class="ad1">광고 1번</div>
			<div class="login-box">
				<div class="user-profile">
					<img src="https://via.placeholder.com/50" alt="프로필 사진">
					<div class="user-info">
						<strong></strong>
						<small></small>
					</div>
				</div>

				<div class="status">
					<span class="badge">999+</span>
					<span>내 정보</span>
				</div>

				<button class="logout-btn">로그아웃</button>

				<div class="menu">
					<div><a href="#">내 이력서</a></div>
					<div><a href="#">지원 이력</a></div>
					<div><a href="#">스크랩 공고</a></div>
					<div><a href="#"></a>받은 제안</div>
				</div>
			</div>
		</div>

		<!-- 광고 2번과 레이블 -->
		<div class="main-content">
			<div class="ad2">광고 2번</div>
			<div class="labels">
				<div class="label">레이블 1</div>
				<div class="label">레이블 2</div>
				<div class="label">레이블 3</div>
				<div class="label">레이블 4</div>
				<div class="label">레이블 5</div>
				<div class="label">레이블 6</div>
			</div>
		</div>

		<!-- 광고 3번과 무한 스크롤 -->
		<div class="infinite-scroll">
			<div class="ad3">광고 3번</div>

			<!-- 무한 스크롤 콘텐츠 -->
			<div class="grid-container">
				<div class="grid-item">Item 1</div>
				<div class="grid-item">Item 2</div>
				<div class="grid-item">Item 3</div>
				<div class="grid-item">Item 4</div>
				<div class="grid-item">Item 5</div>
				<div class="grid-item">Item 6</div>
				<!-- 추가 항목들 -->
			</div>
		</div>
	</main>

	<!-- 푸터 -->
	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

	<!-- 맨 위로 가기 버튼 -->
	<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

	<!-- 무한 스크롤 기능 -->
	<script>
		document.addEventListener('DOMContentLoaded', function () {
			const token = localStorage.getItem('accessToken');
			fetch('/main/memberInfo', {
				method: 'POST',  // POST 메서드로 변경
				headers: {
					'Authorization': `Bearer ` + token, // JWT 토큰 추가
					'Content-Type': 'application/json'
				}
			})
					.then(response => {
						if (!response.ok) {
							throw new Error('인증되지 않았습니다.');
						}
						return response.json();  // JSON 데이터를 추출합니다
					})
					.then(data => {
						// 가져온 데이터에서 이름과 이메일을 추출합니다
						const userName = data.data.userName;
						const userEmail = data.data.userEmail;

						// HTML 요소에 데이터를 삽입합니다
						document.querySelector('.user-info strong').textContent = userName;  // 이름 삽입
						document.querySelector('.user-info small').textContent = userEmail;  // 이메일 삽입
					})
					.catch(error => {
						console.error('Error:', error);
						alert('사용자 정보를 불러오는 중 오류가 발생했습니다.');
					});
		});

	</script>

	</body>
	</html>