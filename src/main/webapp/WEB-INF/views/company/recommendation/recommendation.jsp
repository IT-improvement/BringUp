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

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

	<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

	<main class="flex-grow-1">
		<div class="container my-5">
			<h2 class="mb-4">인재 추천</h2>

			<h3 class="mb-3">프리미엄</h3>
			<div id="premiumSection" class="d-flex justify-content-between mb-5">
			</div>

			<h3 class="mb-3">일반</h3>
			<div id="generalSection">
			</div>
		</div>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

	<script>
		$(document).ready(function () {
			const accessToken = localStorage.getItem("accessToken");

			if (!accessToken) {
				console.log("토큰이 없습니다. 로그인이 필요합니다.");
				window.location.href = "company/login"; // 로그인 페이지로 리다이렉트
				return;
			}

			// 멤버십 가입유저 랜덤 5개 추출 리스트
			$.ajax({
				url: '/com/headhunt/recommend',
				method: 'GET',
				headers: {
					'Authorization': `Bearer `+ accessToken
				},
				success: function (response) {
					let recommendations = response.data;
					recommendations.forEach(function (cv) {
						let card = `
                            <div class="card mx-2" style="width: 18rem;">
                                <img src="/static/logos/${cv.cvImage}" class="card-img-top" alt="CV Image">
                                <div class="card-body">
                                    <h5 class="card-title">${cv.education}</h5>
                                    <p class="card-text">${cv.userAddress}</p>
                                    <p class="card-text">${cv.skill}</p>
                                </div>
                            </div>`;
						$('#premiumSection').append(card);
					});
				},
				error: function (error) {
					console.log("Error fetching premium CVs: ", error);
				}
			});

			// CV 리스트업
			$.ajax({
				url: '/com/headhunt/list',
				method: 'GET',
				headers: {
					'Authorization': `Bearer `+ accessToken
				},
				success: function (response) {
					let allCVs = response.data;
					allCVs.forEach(function (cv) {
						let card = `
                            <div class="card mb-3">
                                <div class="row g-0">
                                    <div class="col-md-4">
                                        <img src="/static/logos/${cv.cvImage}" class="img-fluid rounded-start" alt="CV Image">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5 class="card-title">${cv.education}</h5>
                                            <p class="card-text">${cv.userAddress}</p>
                                            <p class="card-text">${cv.skill}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
						$('#generalSection').append(card);
					});
				},
				error: function (error) {
					console.log("Error fetching general CVs: ", error);
				}
			});
		});
	</script>

</body>
</html>