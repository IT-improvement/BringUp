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

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- 메인 스타일시트 -->
    <!-- <link rel="stylesheet" type="text/css" href="/resources/style/member/user/파일명.css"> -->

    <!--  JS -->
    <!-- <script src="/resources/script/member/user/파일명.js"></script> -->

</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
<div class="container" style="max-width: 1260px;">
    <main class="flex-grow-1">
        <div>
            <div class="card border mb-4">
                <div class="card-header bg-light border-bottom">
                    <h5 class="card-title mb-0">공고 검색</h5>
                </div>
                <div class="card-body">
                    <form id="search-form" class="row g-3">
                        <div class="col-md-4">
                            <label for="search-category" class="form-label">카테고리</label>
                            <select class="form-select" id="search-category">
                                <option value="all">전체</option>
                                <option value="title">공고 제목</option>
                                <option value="type">공고 타입</option>
                                <option value="recruitmentType">채용 형태</option>
                                <option value="deadline">마감일</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="search-keyword" class="form-label">검색어</label>
                            <input type="text" class="form-control" id="search-keyword">
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100">검색</button>
                        </div>
                    </form>
                </div>
                <div class="container">
                    <div class="row g-4 p-3 flex-fill">
                        <!-- Card item START -->
                        <div class="col-sm-6 col-lg-3">
                            <div class="card bg-transparent">
                                <!-- Card img -->
                                <img class="card-img rounded" src="./assets/image/kakao_logo.jpg" alt="Card image">
                                <div class="card-body px-0 pt-3">
                                    <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">2024년 신규직원 공개채용 공고</a></h6>
                                    <!-- Card info -->
                                    <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                        <li class="nav-item">
                                            <a href="#" class="text-reset btn-link">(주)카카오</a>
                                        </li>
                                        <li class="nav-item">2024/05/02</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Card item END -->
                        <!-- Card item START -->
                        <div class="col-sm-6 col-lg-3">
                            <div class="card bg-transparent">
                                <!-- Card img -->
                                <img class="card-img rounded" src="./assets/image/naver_logo.png" alt="Card image">
                                <div class="card-body px-0 pt-3">
                                    <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">검색광고 공식대행사 채용관</a></h6>
                                    <!-- Card info -->
                                    <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                        <li class="nav-item">
                                            <a href="#" class="text-reset btn-link">네이버</a>
                                        </li>
                                        <li class="nav-item">2024/05/02</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Card item END -->
                        <!-- Card item START -->
                        <div class="col-sm-6 col-lg-3">
                            <div class="card bg-transparent">
                                <!-- Card img -->
                                <img class="card-img rounded" src="./assets/image/baemin_logo.jpg" alt="Card image">
                                <div class="card-body px-0 pt-3">
                                    <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">2024년 신규직원 공개채용 공고</a></h6>
                                    <!-- Card info -->
                                    <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                        <li class="nav-item">
                                            <a href="#" class="text-reset btn-link">(주)우아한형제들</a>
                                        </li>
                                        <li class="nav-item">2024/05/02</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-lg-3">
                            <div class="card bg-transparent">
                                <!-- Card img -->
                                <img class="card-img rounded" src="./assets/image/rsupport_logo.png" alt="Card image">
                                <div class="card-body px-0 pt-3">
                                    <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">2024년 신규직원 공개채용 공고</a></h6>
                                    <!-- Card info -->
                                    <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                        <li class="nav-item">
                                            <a href="#" class="text-reset btn-link">(주)알서포트</a>
                                        </li>
                                        <li class="nav-item">2024/05/02</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Card item END -->
                    </div> <!-- Row END -->
                </div>
                <div class="container">
                    <div class="row g-4 p-3 flex-fill">
                        <!-- Card item START -->
                        <div class="col-sm-6 col-lg-3">
                            <div class="card bg-transparent">
                                <!-- Card img -->
                                <img class="card-img rounded" src="./assets/image/evidnet_logo.jpeg" alt="Card image">
                                <div class="card-body px-0 pt-3">
                                    <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">2024년 신규직원 공개채용 공고</a></h6>
                                    <!-- Card info -->
                                    <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                        <li class="nav-item">
                                            <a href="#" class="text-reset btn-link">(주)에비드넷</a>
                                        </li>
                                        <li class="nav-item">2024/05/02</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Card item END -->
                        <!-- Card item START -->
                        <div class="col-sm-6 col-lg-3">
                            <div class="card bg-transparent">
                                <!-- Card img -->
                                <img class="card-img rounded" src="./assets/image/nexon_logo.jpg" alt="Card image">
                                <div class="card-body px-0 pt-3">
                                    <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">2024년 신규직원 공개채용 공고</a></h6>
                                    <!-- Card info -->
                                    <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                        <li class="nav-item">
                                            <a href="#" class="text-reset btn-link">(주)넥슨코리아</a>
                                        </li>
                                        <li class="nav-item">2024/05/02</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Card item END -->
                        <!-- Card item START -->
                        <div class="col-sm-6 col-lg-3">
                            <div class="card bg-transparent">
                                <!-- Card img -->
                                <img class="card-img rounded" src="./assets/image/unioncommunity_logo.jpg" alt="Card image">
                                <div class="card-body px-0 pt-3">
                                    <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">2024년 신규직원 공개채용 공고</a></h6>
                                    <!-- Card info -->
                                    <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                        <li class="nav-item">
                                            <a href="#" class="text-reset btn-link">(주)유니온커뮤니티</a>
                                        </li>
                                        <li class="nav-item">2024/05/02</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-lg-3">
                            <div class="card bg-transparent">
                                <!-- Card img -->
                                <img class="card-img rounded" src="./assets/image/coresecurity_logo.jpg" alt="Card image">
                                <div class="card-body px-0 pt-3">
                                    <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">2024년 신규직원 공개채용 공고</a></h6>
                                    <!-- Card info -->
                                    <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                        <li class="nav-item">
                                            <a href="#" class="text-reset btn-link">(주)코어시큐리티</a>
                                        </li>
                                        <li class="nav-item">2024/05/02</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Card item END -->
                    </div> <!-- Row END -->
                </div>
            </div>
        </div>
    </main>
</div>
</body>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
