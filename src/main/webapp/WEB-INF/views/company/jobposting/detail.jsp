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
        <div class="container mt-5">
            <div class="card">
                <div class="card-header bg-light">
                    <h5 class="card-title mb-0">채용 공고 상세 정보</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <p><strong>카테고리:</strong> <span id="category"></span></p>
                            <p><strong>담당자 이메일:</strong> <span id="managerEmail"></span></p>
                            <p><strong>기간:</strong> <span id="period"></span></p>
                            <p><strong>채용 분류:</strong> <span id="recruitmentClass"></span></p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>채용 유형:</strong> <span id="recruitmentType"></span></p>
                            <p><strong>필요 기술:</strong> <span id="skill"></span></p>
                            <p><strong>시작일:</strong> <span id="startDate"></span></p>
                            <p><strong>상태:</strong> <span id="status"></span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

	<jsp:include page="/WEB-INF/views/company/footer/company_footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

    <script>
    document.addEventListener('DOMContentLoaded', function() {
        const urlParams = new URLSearchParams(window.location.search);
        const recruitmentIndex = urlParams.get('recruitmentIndex');
        const accessToken = localStorage.getItem('accessToken');

        if (!accessToken) {
            window.location.href = '/company/auth/login';
            return;
        }

        fetch(`/com/recruitment/${recruitmentIndex}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            const recruitment = data.data;
            document.getElementById('category').textContent = recruitment.category;
            document.getElementById('managerEmail').textContent = recruitment.managerEmail;
            document.getElementById('period').textContent = recruitment.period;
            document.getElementById('recruitmentClass').textContent = recruitment.recruitmentClass;
            document.getElementById('recruitmentType').textContent = recruitment.recruitmentType;
            document.getElementById('skill').textContent = recruitment.skill;
            document.getElementById('startDate').textContent = recruitment.startDate;
            document.getElementById('status').textContent = recruitment.status;
        })
        .catch(error => {
            console.error('채용 공고 상세 정보를 가져오는 중 오류 발생:', error);
            alert('채용 공고 상세 정보를 불러올 수 없습니다. 나중에 다시 시도해주세요.');
        });
    });
    </script>
</body>
</html>