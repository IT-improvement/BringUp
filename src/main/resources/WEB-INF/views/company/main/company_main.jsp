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

	<!-- Dark mode -->
	<script src="/test/assets/js/darkmode.js"></script>
    <script src="/test/assets/js/notification.js"></script>
	<!-- Favicon -->
	<link rel="shortcut icon" href="/assets/images/favicon.ico">

	<!-- Google Font -->
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

	<!-- Plugins CSS -->
	<link rel="stylesheet" type="text/css" href="/test/assets/vendor/font-awesome/css/all.min.css">
	<link rel="stylesheet" type="text/css" href="/test/assets/vendor/bootstrap-icons/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="/test/assets/vendor/apexcharts/css/apexcharts.css">
	<link rel="stylesheet" type="text/css" href="/test/assets/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">

	<!-- Theme CSS -->
	<link rel="stylesheet" type="text/css" href="/test/assets/css/style.css">

</head>
<body>
<jsp:include page="/views/company/header/company_header.jsp" flush="true" />

<jsp:include page="/views/company/main/company_main_content.jsp" flush="true" />


<jsp:include page="/views/company/footer/company_footer.jsp" flush="true" />

<!-- Back to top -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

<!-- =======================
JS libraries, plugins and custom scripts -->

<!-- Bootstrap JS -->
<script src="/test/assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- Vendors -->
<script src="/test/assets/vendor/apexcharts/js/apexcharts.min.js"></script>
<script src="/test/assets/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

<!-- Template Functions -->
<script src="/test/assets/js/functions.js"></script>

</body>
</html>