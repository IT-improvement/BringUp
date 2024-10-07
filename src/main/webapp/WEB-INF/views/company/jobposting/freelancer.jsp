<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>Freelancer 프로젝트 상세</title>

    <!-- 메타 태그 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">

    <!-- 파비콘 -->
    <link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

    <!-- 구글 폰트 -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- 플러그인 CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">

    <!-- 테마 CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

    <!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- 벤더 -->
    <script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

    <!-- 테마 JS -->
    <script src="/resources/script/common/function/functions.js"></script>

    <!-- 프리랜서 프로젝트 JS -->
    <script src="/resources/script/company/freelancer.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

<main class="flex-grow-1">
    <div class="container mt-3 w-75">
        <div class="d-flex align-items-center mb-2 text-decoration-none" onclick="location.href='/company/auth/profile'" onmouseover="this.style.cursor='pointer'">
            <img id="c_logo" class="card-img-top w-40 m-1 rounded-circle border border-1 border-#C2C2C2">
            <span id="c_name" class="fs-5 fw-bold"></span>
        </div>
        <div class="d-flex align-items-center mb-1">
            <h2 id="projectTitle" class="fs-1 fw-bold">프로젝트 제목</h2>
        </div>
        <div class="row">
            <div class="col-md-8">
                <div class="mb-4">
                    <h3>프로젝트 설명</h3>
                    <p id="projectDescription"></p>
                </div>
                <div class="mb-4">
                    <h3>예상 개발 기간</h3>
                    <p id="expectedDuration"></p>
                </div>
                <div class="mb-4">
                    <h3>필수 기술</h3>
                    <p id="requiredSkills"></p>
                </div>
                <div class="mb-4">
                    <h3>우대 기술</h3>
                    <p id="preferredSkills"></p>
                </div>
                <div class="mb-4">
                    <h3>근무 조건</h3>
                    <p id="workConditions"></p>
                </div>
                <div class="mb-4">
                    <h3>예상 비용</h3>
                    <p id="expectedCost"></p>
                </div>
                <div class="mb-4">
                    <h3>프로젝트 상태</h3>
                    <p id="status"></p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card border border-1 align-items-center" style="position: sticky; top: 140px; height: 280px; border-radius: 15px;" >
                    <div class="card-body d-flex flex-column justify-content-center w-75" style="height: 100%; position: relative;">
                        <div class="d-flex flex-column align-items-center text-center mt-4">
                            <div class="d-flex justify-content-between w-100 border-bottom mb-2">
                                <h6 class="card-title mb-0">마감일</h6>
                                <p id="projectPeriod" class="card-text mb-0 small"></p>
                            </div>
                            <div class="d-flex justify-content-between w-100 border-bottom mb-2">
                                <h6 class="card-title mb-0">지원자 수</h6>
                                <p id="projectApplicantCount" class="card-text mb-0 small"></p>
                            </div>
                            <div class="d-flex justify-content-between w-100 border-bottom mb-2">
                                <h6 class="card-title mb-0">생성 날짜</h6>
                                <p id="createDay" class="card-text mb-0 small"></p>
                            </div>
                        </div>
                        <a href="#" class="btn btn-primary mt-2 rounded-pill align-items-center" style="background-color: #8BA5FF; border-color: #8BA5FF;">지원자 목록</a>
                        <a href="#" class="btn btn-primary mt-2 rounded-pill align-items-center" style="background-color: #FF8BA5; border-color: #FF8BA5;">삭제하기</a>
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