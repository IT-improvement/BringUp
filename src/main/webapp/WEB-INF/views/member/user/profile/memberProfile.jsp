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
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const accessToken = localStorage.getItem("accessToken");
            console.log("Access token: " + accessToken);
            const url = "/member/info"
            if (accessToken) {
                fetch(url, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer `+ accessToken
                    }
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        // 데이터를 폼에 맞춰 입력
                        const userEmail = data.data.userEmail ? data.data.userEmail : "정보없음";
                        const userName = data.data.userName ? data.data.userName : "정보없음";
                        const userAddress = data.data.userAddress ? data.data.userAddress : "정보없음";
                        const userPhonenumber = data.data.userPhonenumber ? data.data.userPhonenumber : "정보없음";
                        const userBirthday = data.data.userBirthday ? data.data.userBirthday : "정보없음";
                        // 프로필 이미지 설정
                        document.getElementById('userEmail').textContent = userEmail;
                        document.getElementById('userName').textContent = userName;
                        document.getElementById('userAddress').textContent = userAddress;
                        document.getElementById('userPhonenumber').textContent = userPhonenumber;
                        document.getElementById('userBirthday').textContent = userBirthday;
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            } else {
                console.log("토큰을 찾을 수 없습니다.");
            }
        });
    </script>
</head>

<body class="d-flex flex-column min-vh-100">
<!-- header-->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<main class="flex-grow-1 m-4">
    <div class="container" style="max-width: 1260px;">
        <p class="h1">프로필</p>
        <div class="m-4">
            <div class="mb-3">
                <p class="h5">아이디</p>
                <div class="alert alert-light p-3 mb-5" role="alert" id="userEmail"></div>
            </div>
            <div class="mb-3">
                <p class="h5">이름</p>
                <div class="alert alert-light p-3 mb-5" role="alert" id="userName"></div>
            </div>
            <div class="mb-3">
                <p class="h5">주소</p>
                <div class="alert alert-light p-3 mb-5" role="alert" id="userAddress"></div>
            </div>
            <div class="mb-3">
                <p class="h5">전화번호</p>
                <div class="alert alert-light p-3 mb-5" role="alert" id="userPhonenumber"></div>
            </div>
            <div class="mb-3">
                <p class="h5">생년월일</p>
                <div class="alert alert-light p-3 mb-5" role="alert" id="userBirthday"></div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
