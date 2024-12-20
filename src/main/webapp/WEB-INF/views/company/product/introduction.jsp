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
    <script>
        function applyProduct(productName) {
            window.location.href = "/company/product/registration?productName=" + productName;
        }
    </script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>

	<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="container mt-5">
        <h2 class="mb-4">광고 상품</h2>
        
        <div class="mb-4 border">
            <div>
                <h3 class="mx-4 my-2">프리미엄</h3>
                <div class="border p-3 mb-3 mx-auto d-flex align-items-center justify-content-center" style="width: 577px; height: 353px; overflow: hidden;">
                    <img src="/resources/style/company/product/프리미엄광고.png" class="img-fluid" style="max-width: 100%; max-height: 100%; object-fit: contain;">
                </div>
                <div class="d-flex justify-content-end mx-4 my-2">
                    <button class="btn btn-primary" onclick="applyProduct('프리미엄')">신청하기</button>
                </div>
            </div>
        </div>
        
        <div class="mb-4 border">
            <div>
                <h3 class="mx-4 my-2">메인</h3>
                <div class="border p-3 mb-3 mx-auto d-flex align-items-center justify-content-center" style="width: 577px; height: 353px; overflow: hidden;">
                    <img src="/resources/style/company/product/메인광고.png" class="img-fluid" style="max-width: 100%; max-height: 100%; object-fit: contain;">
                </div>
                <div class="d-flex justify-content-end mx-4 my-2">
                    <button class="btn btn-primary" onclick="applyProduct('메인')">신청하기</button>
                </div>
            </div>
        </div>
        
        <div class="mb-4 border">
            <div>
                <h3 class="mx-4 my-2">배너</h3>
                <div class="border p-3 mb-3 mx-auto d-flex align-items-center justify-content-center" style="width: 577px; height: 353px; overflow: hidden;">
                    <img src="/resources/style/company/product/배너광고.png" class="img-fluid" style="max-width: 100%; max-height: 100%; object-fit: contain;">
                </div>
                <div class="d-flex justify-content-end mx-4 my-2">
                    <button class="btn btn-primary" onclick="applyProduct('배너')">신청하기</button>
                </div>
            </div>
        </div>
        <div class="mb-4 border">
            <div>
                <h3 class="mx-4 my-2">어나운스</h3>
                <div class="border p-3 mb-3 mx-auto d-flex align-items-center justify-content-center" style="width: 577px; height: 353px; overflow: hidden;">
                    <img src="/resources/style/company/product/어나운스광고.png" class="img-fluid" style="max-width: 100%; max-height: 100%; object-fit: contain;">
                </div>
                <div class="d-flex justify-content-end mx-4 my-2">
                    <button class="btn btn-primary" onclick="applyProduct('어나운스')">신청하기</button>
                </div>
            </div>
        </div>

    </main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
