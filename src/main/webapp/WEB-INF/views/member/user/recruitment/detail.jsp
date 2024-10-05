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
    <script src="/resources/script/member/recruitmentDetail.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

    <jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

    <main class="flex-grow-1">
        <div class="container mt-5 w-75">
            <div class="d-flex align-items-center mb-3">
                <img id="c_logo" class="card-img-top w-30 m-1 rounded-circle border border-1 border-#C2C2C2">
                <span id="c_name" class="fs-6 fw-bold"></span>
            </div>
            <div class="d-flex align-items-center mb-2">
                <h2 id="recruitmentTitle" class="fs-1 fw-bold">채용 공고 제목</h2>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <div id="carouselExampleIndicators" class="carousel slide mb-4" data-bs-ride="carousel" data-bs-interval="2000">
                        <div class="carousel-indicators">
                        </div>
                        <div class="carousel-inner rounded-3 border border-1 border-#C2C2C2">
                        </div>
                    </div>
                    <div class="mb-4">
                        <h3>기업 소개</h3>
                        <p id="c_intro"></p>
                    </div>
                    <div class="mb-4">
                        <h3>업무 소개</h3>
                        <p id="r_workdetail"></p>
                    </div>
                    <div class="mb-4">
                        <h3>자격 요건</h3>
                        <ul id="r_requirement">
                        </ul>
                    </div>
                    <div class="mb-4">
                        <h3>우대 사항</h3>
                        <p id="r_hospitality"></p>
                    </div>
                    <div class="mb-4">
                        <h3>복지 혜택</h3>
                        <p id="c_welfare"></p>
                    </div>
                    <div class="mb-4">
                        <h3>근무 지역</h3>
                        <p id="c_address">주소</p>
                        <div id="map" style="width:100%;height:350px;"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card border border-1 border-dark align-items-center" style="position: sticky; top: 140px; height: 280px; border-radius: 15px;">
                        <div class="card-body d-flex flex-column justify-content-center w-75" style="height: 100%; position: relative;">
                            <div class="d-flex justify-content-end" style="position: absolute; top: 10px; right: -30px;">
                                <button class="btn btn-link p-0 me-2" style="color: #000; font-size: 1.5em;">
                                    <i class="bi bi-bookmark"></i>
                                </button>
                                <button class="btn btn-link p-0" style="color: #000; font-size: 1.5em;">
                                    <i class="bi bi-share"></i>
                                </button>
                            </div>
                            <div class="d-flex flex-column align-items-center text-center mt-4">
                                <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">
                                    <h5 class="card-title mb-0">경력</h5>
                                    <p id="r_career" class="card-text mb-0"></p>
                                </div>
                                <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">
                                    <h5 class="card-title mb-0">최소 연봉</h5>
                                    <p id="r_salary" class="card-text mb-0"></p>
                                </div>
                                <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">
                                    <h5 class="card-title mb-0">마감일</h5>
                                    <p class="card-text mb-0" id="r_period"></p>
                                </div>
                            </div>
                            <a href="#" class="btn btn-primary mt-2 rounded-pill" style="background-color: #8BA5FF; border-color: #8BA5FF;">지원하기</a>
                        </div>
                    </div>  
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>
    
</body>
</html>