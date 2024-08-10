<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>기업 회원 가입 - 2단계</title>

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

    <link rel="stylesheet" type="text/css" href="/resources/style/company/signup/signupsecond.css">
</head>
<body>

<div class="signupsecond-container">
    <div class="signupsecond-wrapper">
        <a href="/" class="main-link">메인으로</a>
        <div class="signupsecond-box">
            <h2 class="text-center mb-4">기업 회원 가입 - 2단계</h2>
            <form id="companySignupsecondForm" name="companySignupsecondForm" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="c_name" class="form-label"><i class="fas fa-building"></i> 회사명</label>
                    <input type="text" class="form-control" id="c_name" name="c_name" placeholder="회사명을 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="company_email" class="form-label"><i class="fas fa-envelope"></i> 회사 이메일</label>
                    <input type="email" class="form-control" id="company_email" name="company_email" placeholder="회사 이메일을 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label"><i class="fas fa-lock"></i> 비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="c_logo" class="form-label"><i class="fas fa-image"></i> 회사 로고</label>
                    <input type="file" class="form-control" id="c_logo" name="c_logo" accept="image/*" required>
                </div>
                <div class="mb-3">
                    <label for="c_address" class="form-label"><i class="fas fa-map-marker-alt"></i> 회사 주소</label>
                    <input type="text" class="form-control" id="c_address" name="c_address" placeholder="회사 주소를 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="c_phone" class="form-label"><i class="fas fa-phone"></i> 회사 전화번호</label>
                    <input type="tel" class="form-control" id="c_phone" name="c_phone" placeholder="회사 전화번호를 입력하세요" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">회원가입 완료</button>
            </form>
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
<script src="/resources/script/company/signupsecond.js"></script>

</body>
</html>