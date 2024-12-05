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
    
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
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
        <script src="/resources/script/company/product/detail.js"></script>
    
    </head>
<body class="d-flex flex-column min-vh-100">

    <!-- 헤더 -->
    <jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <!-- 메인 콘텐츠 -->
    <main class="flex-grow-1 mt-5">
        <div class="container">
            <a href="/company/product/management" class="mb-3"><i class="bi bi-arrow-left"></i>돌아가기</a>
            <div class="card border-1 border-secondary rounded-3">
                <div class="card-body">
                    <div class="d-flex align-items-center justify-content-between mb-3">
                        <h2 class="card-title mb-0">상품 상세 정보</h2>
                        <div>
                            <button class="btn btn-danger" id="deleteButton">삭제</button>
                        </div>
                    </div>
                    <% String adType = (String)request.getAttribute("adType");%>
                    <div class="row" id="adInfo">
                        <div class="col-md-6">
                            <p class="card-text">상품 이름: <span id="adType"></span> 광고</p>
                            <p class="card-text">공고 제목: <span id="recruitmentTitle"></span></p>
                            <p class="card-text">광고 날짜: <span id="adPeriod"></span></p>
                            <% if (adType != null && adType.equals("premium")) { %>
                                <p class="card-text">광고 시간: <span id="timeSlot"></span></p>
                            <% } %>
                            <p class="card-text">클릭 수: <span id="clickCount"></span></p>
                            <p class="card-text">조회수: <span id="viewCount"></span></p>
                            <p class="card-text">상태: <span id="status"></span></p>
                        </div>
                        <div id="adImageContainer" class="col-md-6" style="display: none;">
                            <h5 class="mb-3">광고 이미지</h5>
                            <img id="adImage" alt="광고 이미지" class="img-fluid rounded">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 가기 버튼 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
