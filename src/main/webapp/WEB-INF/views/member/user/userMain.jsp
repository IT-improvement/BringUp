<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>BringUp</title>

	<!-- 메타 태그 -->
	<meta charset="utf-8">

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

	<!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<!-- 메인 스타일시트 -->
	<!-- <link rel="stylesheet" type="text/css" href="/resources/style/member/user/파일명.css"> -->

	<!--  JS -->
	<!-- <script src="/resources/script/member/user/파일명.js"></script> -->
	
</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />
<script>
    $(document).ready(function() {
        const accessToken = localStorage.getItem("accessToken");
        if (accessToken) {
            $("#before-login").hide();
            $("#after-login").show();
            
            // 사용자 정보 가져오기 (예시)
            fetch('/member/name', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + accessToken
                }
            })
            .then(response => response.json())
            .then(data => {
                $("#card-title-after-login").text(data.data + "님 환영합니다!");
                $("#card-text-after-login").text("프리미엄 회원입니다.");
            })
            .catch(() => {
                $("#card-title-after-login").text("오류가 발생했습니다.");
                $("#card-text-after-login").text("다시 로그인해 주세요.");
            });
        } else {
            $("#before-login").show();
            $("#after-login").hide();
        }

		// 이미지 파일 경로 설정
        fetch('/main/advertisements', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("프리미엄 광고 이미지 요청 : "+data);
        })
        .catch(() => {
            console.log("광고 이미지를 가져오는 중 오류가 발생했습니다.");
        });


		fetch('/main/recruitmentImage', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("메인 광고 이미지 요청 : "+data);
        })
        .catch(() => {
            console.log("광고 이미지를 가져오는 중 오류가 발생했습니다.");
        });

		fetch('/main/ad3Advertisements', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("배너 광고 이미지 요청 : "+data);
        })
        .catch(() => {
            console.log("광고 이미지를 가져오는 중 오류가 발생했습니다.");
        });
        

		fetch('/main/recruitment', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("메인 공고 리스트 요청 : "+data);
        })
        .catch(() => {
            console.log("채용 공고 이미지를 가져오는 중 오류가 발생했습니다.");
        });

		
        // 로그인 버튼 클릭 이벤트
        $("#login-button").click(function() {
            window.location.href = "/member/Login";
        });
        
        // 회원가입 버튼 클릭 이벤트
        $("#register-button").click(function() {
            window.location.href = "/register";
        });
    });
</script>

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
    <div class="container" style="max-width: 1260px;">
        <main class="flex-grow-1 d-flex flex-column">
            <div class="advertising-container d-flex flex-column" style="height: 840px;">
				<div class="user-premium-container flex-grow-1.2 mt-2" style="flex: 1.2;">
					<div class="h-100">
						<img id="premium-image" class="img-fluid h-100 w-100 object-fit-cover">
					</div>
				</div>
				<div class="user-main-container d-flex flex-grow-0.75 mt-2" style="flex: 0.75;">
					<div class="image-container" style="flex: 3;">
						<img id="main-image" class="img-fluid h-100 object-fit-cover">
					</div>
					<div class="card-container" style="flex: 1;">
						<div class="card h-100" id="before-login">
							<div class="card-body bg-primary-subtle rounded-3 d-flex flex-column justify-content-center">
								<button id="login-button" class="btn btn-outline-primary mb-2">로그인</button>
								<button id="register-button" class="btn btn-outline-primary">회원가입</button>
							</div>
						</div>
						<div class="card h-100" id="after-login">
							<div class="card-body bg-primary-subtle rounded-3">
								<h5 id="card-title-after-login" class="card-title"></h5>
								<p id="card-text-after-login" class="card-text"></p>
							</div>
						</div>
					</div>
				</div>
				<div class="user-banner-container flex-grow-0.5 mt-2" style="flex: 0.5;">
					<div class="h-100">
						<img id="banner-image" class="img-fluid h-100 w-100 object-fit-cover">
					</div>
				</div>
			</div>
            <div class="user-recruit-container">
                <div class="d-flex flex-wrap justify-content-center">
                    <% for(int i = 0; i < 40; i++) { %>
                        <div class="card m-2" style="width: 23%; min-width: 200px; margin-bottom: 20px; overflow: hidden;">
                            <img alt="사용자 메인 이미지" class="card-img-top">
                            <div class="card-body">
                                <h5 class="card-title">카드 제목 <%= i + 1 %></h5>
                                <p class="card-text">카드 설명</p>
                            </div>
                        </div>
                    <% } %>
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
