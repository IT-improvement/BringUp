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
    <link rel="stylesheet" type="text/css" href="/resources/style/member/profile/profileSide.css">

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
<!-- header-->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<main class="flex-grow-1">
    <aside class="profile-side">
        <ul class="profile-ul">
            <li class="profile-li">
                <a class="profile-a" href="#">MY 홈</a>
            </li>
            <li class="profile-li">
                <a class="profile-a" href="#">이력서</a>
            </li>
            <li class="profile-li">
                <a class="profile-a" href="#">지원한 이력서</a>
            </li>
            <li class="profile-li">
                <a class="profile-a" href="#">북마크한 채용 공고</a>
            </li>
            <li class="profile-li">
                <a class="profile-a" href="#">북마크한 기업</a>
            </li>
            <li class="profile-li">
                <a class="profile-a" href="#">제안받은 공고</a>
            </li>
            <li class="profile-li">
                <a class="profile-a" href="#">설정</a>
            </li>
        </ul>
    </aside>
    <section class>
        <section class="profile-dashboard">
            <div class="profile-banner">
                <p>자동 경력 블로그기로 커리어를 관리해 보세요!</p>
                <a href="#">내 커리어 기록하러 가기</a>
            </div>
            <div class="profile-applications">
                <h2>작성 중인 지원서</h2>
                <p>작성 중인 지원서가 없어요. 관심있는 공고에 지원해 보세요.</p>
                <button>채용 공고 탐색하기</button>
            </div>
            <div class="profile-submitted-applications">
                <h2>제출한 지원서</h2>
                <div class="profile-status">
                    <div>
                        <p>서류지원</p>
                        <span>0</span>
                    </div>
                    <div>
                        <p>진행중</p>
                        <span>0</span>
                    </div>
                    <div>
                        <p>최종합격</p>
                        <span>0</span>
                    </div>
                </div>
            </div>
        </section>
    </section>
</main>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
