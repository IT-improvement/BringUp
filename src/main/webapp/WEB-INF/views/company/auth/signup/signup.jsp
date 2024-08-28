<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>기업 회원 가입</title>

    <!-- 메타 태그 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">
    <meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">

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

    <link rel="stylesheet" type="text/css" href="/resources/style/company/signup/signup.css">
</head>
<body>

<div class="signup-container">
    <div class="signup-wrapper">
        <a href="/" class="main-link">메인으로</a>
        <div class="signup-box">
            <h2 class="text-center mb-4">기업 회원 가입</h2>
            <form id="companySignupForm" name="companySignupForm">
                <div class="mb-3">
                    <label for="businessNumber" class="form-label"><i class="fas fa-building"></i> 사업자 번호</label>
                    <input type="text" class="form-control" id="businessNumber" name="businessNumber" placeholder="사업자 번호를 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label"><i class="fas fa-user"></i> 회사 대표 이름</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="회사명을 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="startDate" class="form-label"><i class="fas fa-calendar-alt"></i> 설립일</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">사업자 인증</button>
            </form>
            <a href="/company/auth/login" class="login-link">로그인하러가기</a>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- 벤더 -->
<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

<!-- 테마 JS -->
<script src="/resources/script/common/function/functions.js"></script>

<!-- 회원가입 JS -->
<script src="/resources/script/company/signupfirst.js"></script>

</body>
</html>