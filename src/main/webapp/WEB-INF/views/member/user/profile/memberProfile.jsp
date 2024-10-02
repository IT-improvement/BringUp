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
    <!--<link rel="stylesheet" type="text/css" href="/resources/style/member/profile/profileSide.css">-->

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
    <style>
        .profile-main{
            display: flex;
            width: 100%;
            min-width: 100%;
            transition: opacity 1s;
            opacity: 1;
        }
        .profile-input{
            display: grid;
            flex-direction: column-reverse;
            column-gap: 6.5rem;
            padding: 72px 104px 24px 104px;
            background-color: rgb(233, 236, 239);
            border-radius: 6px;
            position: relative;
            top: 0;
            margin: 32px auto;
            width: 100%;
            max-width: 80%;
            min-width: 0;
            height: fit-content;
            word-break: keep-all;
        }
        .profile-email{
            display: flex;
            height: 25px;
        }
        .profile-name{
            text-align: left;
            margin: 0 0 4px;
            font-size: 40px;
            line-height: 56px;
            font-weight: 700;
            color: rgb(33, 37, 42);
        }
        .profile-email-fixed{
            margin: 0;
            min-width: 40px;
            color: rgb(135,142,152);
            line-height: 25px;
        }
        .profile-email-variable{
            margin: 0 0 0 40px;
            line-height: 25px;
            color: rgb(33,37,42);
        }
        .profile-banner p{
            margin: 0;
            font-size: 18px;
        }
        .profile-banner a{
            text-decoration: none;
            color: #00796b;
            font-weight: bold;
        }
        .profile-applications, .profile-submitted-applications{
            background-color: #fff;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 8px;
            border: 1px solid #e5e5e5;
        }
        .profile-applications h2, .profile-submitted-applications h2{
            margin-top: 0
        }
        .profile-status{
            display: flex;
            justify-content: space-between;
        }
        .profile-status div{
            text-align: center;
        }
        .profile-status p{
            margin: 5px 0;
        }
        .profile-status span{
            font-size: 24px;
            font-weight: bold;
        }

    </style>
</head>

<body class="d-flex flex-column min-vh-100">
<!-- header-->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<main class="flex-grow-1">
    <div class="container" style="max-width: 1260px;">
        <div class="row">
            <div class="col">
                <div class="profile-main">
                    <div class="profile-input">
                        <div>
                            <dd class="profile-name">퉤</dd>
                        </div>
                        <div class="profile-email">
                            <dl class="profile-email-fixed">이메일</dl>
                            <dd class="profile-email-variable">111@naver.com</dd>
                        </div>
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
