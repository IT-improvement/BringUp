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
	<link rel="stylesheet" type="text/css" href="/resources/style/company/jobposting/management/management.css">

</head>
<body>
<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />


<div class="recruitment-list">
    <h2 class="list-title">공고 목록</h2>
    <div class="table-container">
        <table class="recruitment-table">
            <thead>
                <tr>
                    <th class="table-header">공고 ID</th>
                    <th class="table-header">담당자 Email</th>
                    <th class="table-header">채용형태</th>
                    <th class="table-header">직군</th>
                    <th class="table-header">기술스택</th>
                    <th class="table-header">시작날짜</th>
                    <th class="table-header">기간</th>
                    <th class="table-header">상태값</th>
                    <th class="table-header">광고등급</th>
                </tr>
            </thead>
            <tbody id="recruitment-list-body">
            </tbody>
        </table>
    </div>
</div>



<jsp:include page="/WEB-INF/views/company/footer/company_footer.jsp" flush="true" />

<!-- 맨 위로 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

<!-- =======================
JS 라이브러리, 플러그인 및 사용자 정의 스크립트 -->

<!-- Bootstrap JS -->
<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- 벤더 -->
<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

<!-- 테마 JS -->
<script src="/resources/script/common/function/functions.js"></script>

<script src="/resources/script/company/jobposting/management.js"></script>

</body>
</html>