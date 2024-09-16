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

	<style>
		/* Layout Styles */
		body {
			display: flex;
			flex-direction: column;
			min-height: 100vh;
		}

		main {
			flex-grow: 1;
			/* padding: 70px; */
			padding-top: 30px;
			padding-right: 100px;
			padding-left: 100px;
		}

		/* 광고 1번과 로그인 폼 */
		.top-section {
			display: flex;
			gap: 20px;
			margin-bottom: 20px;
		}

		.ad1 {
			position: relative;
			width: 100%;
			height: 300px;
			overflow: hidden;
			text-align: center;
			flex: 3;
			margin: 20px;
		}

		.ad-images {
			position: absolute;
			width: 100%;
			height: 100%;
			object-fit: cover;
			transition: transform 1s ease-in-out;
			transform: translateX(100%); /* 기본적으로 오른쪽으로 이동 */
		}

		.ad-images.active {
			transform: translateX(0); /* 활성화된 이미지는 제자리에 표시 */
		}

		.ad-images.inactive {
			transform: translateX(-100%); /* 나간 이미지는 왼쪽으로 사라짐 */
		}

		/* 이전, 다음 버튼 스타일 */
		.ad1 .prev, .ad1 .next {
			position: absolute;
			top: 50%;
			transform: translateY(-50%);
			background-color: rgba(0, 0, 0, 0.5);
			color: white;
			padding: 10px;
			border: none;
			cursor: pointer;
			font-size: 18px;
			border-radius: 5px;
		}

		.ad1 .prev {
			left: 10px;
		}

		.ad1 .next {
			right: 10px;
		}

		/* 로그인 스타일 */
		.login-box {
			width: 370px;
			padding: 20px;
			background-color: #f0f0f0;
			border-radius: 10px;
			box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
			font-family: Arial, sans-serif;
			text-align: center;
			margin: 20px auto;
		}

		.user-profile {
			display: flex;
			align-items: center;
			margin-bottom: 10px;
		}

		.user-profile img {
			border-radius: 50%;
			width: 50px;
			height: 50px;
			margin-right: 10px;
		}

		.user-info {
			text-align: left;
		}

		.user-info strong {
			font-size: 18px;
			display: block;
		}

		.user-info small {
			font-size: 14px;
			color: gray;
		}

		.status {
			display: flex;
			justify-content: space-between;
			margin: 10px 0;
			font-size: 14px;
		}

		.status .badge {
			background-color: #4caf50;
			color: white;
			padding: 5px 10px;
			border-radius: 15px;
			font-size: 12px;
		}

		.logout-btn {
			margin-top: 80px;
			background-color: #007bff;
			color: white;
			border: none;
			padding: 10px 20px;
			border-radius: 5px;
			cursor: pointer;
			width: 100%;
		}

		.menu {
			display: flex;
			justify-content: space-around;
			margin-top: 20px;
		}

		.menu div {
			text-align: center;
			font-size: 14px;
		}

		.menu div a {
			color: black;
			text-decoration: none;
		}

		/* 광고 2번과 레이블 */
		.main-content {
			position: relative;
			width: 100%;
		}


		.ad2 {
			background-color: #ccc;
			text-align: center;
			height: 450px;
			position: relative;
			z-index: 1; /* 광고 2는 레이블보다 뒤에 위치 */
			margin-left: 120px;
		}

		.labels {
			position: absolute;
			top: 0;
			left: 0;
			display: flex;
			flex-direction: column;
			width: 160px;
			height: 100%; /* 광고 2번과 같은 높이로 설정 */
		}

		.label {
			background-color: #f9f9f9;
			height: 75px;
			text-align: center;
			line-height: 75px;
			cursor: pointer;
			border: 1px solid #ddd;
			box-shadow: 0 2px 8px rgba(0,0,0,0.1);
			transition: opacity 0.3s ease-in-out; /* 간단한 opacity 전환 */
			z-index: 1;
			opacity: 0.8;
			font-size: 16px;
			font-weight: bold;
			color: #333;
		}

		.label.active {
			background-color: #cce5ff; /* 연한 파란색 배경 */
			color: #0056b3; /* 텍스트 색상 */
			z-index: 10;
			opacity: 1;
		}

		.label:hover {
			background-color: #e9e9e9; /* Hover 시 밝게 */
			box-shadow: 0 4px 12px rgba(0,0,0,0.2);
			opacity: 1;
		}

		.label.inactive {
			z-index: 0;
			opacity: 0.5;
		}

		/* 애니메이션이 부드럽게 적용되도록 변경 */
		.ad2-image {
			display: none;
			opacity: 0;
			transition: opacity 0.5s ease-in-out; /* fade in/out 애니메이션 */
		}

		.ad2-image.active {
			display: block;
			opacity: 1;
		}

		/* 광고 3번과 무한 스크롤 */
		.ad3 {
			background-color: #ccc;
			height: 150px;
			text-align: center;
			margin-bottom: 20px;
			margin-top: 20px;
			overflow: hidden;
			position: relative;
		}

		.ad3-images {
			position: absolute;
			width: 100%;
			height: 100%;
			object-fit: cover;
			transition: transform 1s ease-in-out;
			transform: translateX(100%);
		}

		.ad3-images.active {
			transform: translateX(0);
		}

		.ad3-images.inactive {
			transform: translateX(-100%);
		}
		.grid-container {
			display: grid;
			grid-template-columns: repeat(3, 1fr);
			grid-gap: 20px;
		}

		.grid-item {
			background-color: #ddd;
			text-align: center;
			padding: 20px;
		}

		.infinite-scroll {
			max-height: 400px;
			overflow-y: auto;
		}

		/* 버튼 클릭 시 반짝이는 효과 */
		button.prev, button.next {
			background-color: rgba(0, 0, 0, 0.5);
			color: white;
			padding: 10px;
			border: none;
			cursor: pointer;
			font-size: 18px;
			border-radius: 5px;
			transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
		}


	</style>

	<!-- JQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<main>
	<!-- 광고 1번과 로그인 폼 -->
	<div class="top-section">
		<div class="ad1">
			<img class="ad-images" src="https://via.placeholder.com/800x300?text=Ad1" alt="광고 이미지 1">
			<img class="ad-images" src="https://via.placeholder.com/800x300?text=Ad2" alt="광고 이미지 2" style="display:none;">
			<img class="ad-images" src="https://via.placeholder.com/800x300?text=Ad3" alt="광고 이미지 3" style="display:none;">

			<!-- 이전, 다음 버튼 추가 -->
			<button class="prev">❮</button>
			<button class="next">❯</button>
		</div>

		<div class="login-box">
			<div class="user-profile">
				<img src="https://via.placeholder.com/50" alt="프로필 사진">
				<div class="user-info">
					<strong></strong>
					<small></small>
				</div>
			</div>

			<div class="status">
				<span class="badge">999+</span>
				<span>내 정보</span>
			</div>

			<button class="logout-btn">로그아웃</button>

			<div class="menu">
				<div><a href="#">내 이력서</a></div>
				<div><a href="#">지원 이력</a></div>
				<div><a href="#">스크랩 공고</a></div>
				<div><a href="#">받은 제안</a></div>
			</div>
		</div>
	</div>

	<div class="main-content">
		<div class="ad2">
			<img class="ad2-image" src="https://via.placeholder.com/800x300?text=광고+2+이미지1" alt="광고 2 이미지 1">
			<img class="ad2-image" src="https://via.placeholder.com/800x300?text=광고+2+이미지2" alt="광고 2 이미지 2" style="display:none;">
			<img class="ad2-image" src="https://via.placeholder.com/800x300?text=광고+2+이미지3" alt="광고 2 이미지 3" style="display:none;">
		</div>

		<div class="labels">
			<button class="label inactive" data-index="0">광고 1</button>
			<button class="label inactive" data-index="1">광고 2</button>
			<button class="label inactive" data-index="2">광고 3</button>
			<button class="label inactive" data-index="3">광고 4</button>
			<button class="label inactive" data-index="4">광고 5</button>
			<button class="label inactive" data-index="5">광고 6</button>
		</div>
	</div>

	<!-- 광고 3번과 무한 스크롤 -->
	<div class="infinite-scroll">
		<div class="ad3">
			<img class="ad3-images" src="https://via.placeholder.com/800x100?text=광고3-1" alt="광고 3 이미지 1">
			<img class="ad3-images" src="https://via.placeholder.com/800x100?text=광고3-2" alt="광고 3 이미지 2" style="display:none;">
			<img class="ad3-images" src="https://via.placeholder.com/800x100?text=광고3-3" alt="광고 3 이미지 3" style="display:none;">
		</div>

		<!-- 무한 스크롤 콘텐츠 -->
		<div class="grid-container">
			<div class="grid-item">Item 1</div>
			<div class="grid-item">Item 2</div>
			<div class="grid-item">Item 3</div>
			<div class="grid-item">Item 4</div>
			<div class="grid-item">Item 5</div>
			<div class="grid-item">Item 6</div>
		</div>
	</div>
</main>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

<!-- 이미지 슬라이드 쇼 스크립트 -->
<script>
	document.addEventListener('DOMContentLoaded', function () {
		let currentIndex = 0;
		const images = document.querySelectorAll('.ad-images');
		const totalImages = images.length;
		let slideInterval;

		// 이미지 슬라이드 애니메이션 함수
		function showNextImage() {
			images[currentIndex].classList.remove('active');
			images[currentIndex].classList.add('inactive'); // 현재 이미지를 왼쪽으로 스크롤 아웃
			currentIndex = (currentIndex + 1) % totalImages; // 다음 이미지 인덱스 계산
			images[currentIndex].classList.remove('inactive');
			images[currentIndex].classList.add('active'); // 다음 이미지를 왼쪽에서 스크롤 인
		}

		// 3초마다 showNextImage 함수 호출
		function startSlideShow() {
			slideInterval = setInterval(showNextImage, 3000);
		}

		// 처음 슬라이드 쇼 시작
		startSlideShow();

		// 이전, 다음 버튼을 이용한 수동 슬라이드
		document.querySelector('.prev').addEventListener('click', function() {
			images[currentIndex].classList.remove('active');
			images[currentIndex].classList.add('inactive');
			currentIndex = (currentIndex - 1 + totalImages) % totalImages;
			images[currentIndex].classList.remove('inactive');
			images[currentIndex].classList.add('active');
		});

		document.querySelector('.next').addEventListener('click', function() {
			images[currentIndex].classList.remove('active');
			images[currentIndex].classList.add('inactive');
			currentIndex = (currentIndex + 1) % totalImages;
			images[currentIndex].classList.remove('inactive');
			images[currentIndex].classList.add('active');
		});
		// JWT 토큰을 사용하여 사용자 정보 가져오기
		const token = localStorage.getItem('accessToken');
		fetch('/main/memberInfo', {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ` + token,
				'Content-Type': 'application/json'
			}
		})
				.then(response => {
					if (!response.ok) {
						throw new Error('인증되지 않았습니다.');
					}
					return response.json();
				})
				.then(data => {
					const userName = data.data.userName;
					const userEmail = data.data.userEmail;

					document.querySelector('.user-info strong').textContent = userName;
					document.querySelector('.user-info small').textContent = userEmail;
				})
				.catch(error => {
					console.error('Error:', error);
					alert('사용자 정보를 불러오는 중 오류가 발생했습니다.');
				});
		// 광고 2 이미지 목록
		const ad2Images = document.querySelectorAll('.ad2-image');

		// 모든 레이블 버튼에 클릭 이벤트 추가
		const labels = document.querySelectorAll('.label');
		labels.forEach(label => {
			label.addEventListener('click', function() {
				// 모든 광고 이미지를 숨기고 클릭한 레이블에 해당하는 이미지를 표시
				const index = this.getAttribute('data-index');
				ad2Images.forEach((img, imgIndex) => {
					img.style.display = imgIndex == index ? 'block' : 'none';
				});
			});
		});
	});
	document.addEventListener('DOMContentLoaded', function () {
		// 광고 2 이미지 목록
		const ad2Images = document.querySelectorAll('.ad2-image');

		// 모든 레이블 버튼에 클릭 이벤트 추가
		const labels = document.querySelectorAll('.label');
		labels.forEach(label => {
			label.addEventListener('click', function() {
				// 모든 광고 이미지를 숨기고 클릭한 레이블에 해당하는 이미지를 표시
				const index = this.getAttribute('data-index');
				ad2Images.forEach((img, imgIndex) => {
					img.style.display = imgIndex == index ? 'block' : 'none';
				});
			});
		});
	});

	//중앙광고 사이드 레이블
	document.addEventListener('DOMContentLoaded', function () {
		const labels = document.querySelectorAll('.label');
		const ad2Images = document.querySelectorAll('.ad2-image');

		// 첫 번째 레이블과 이미지 기본 활성화
		labels[0].classList.add('active');
		ad2Images[0].classList.add('active');

		// 모든 레이블 버튼에 클릭 이벤트 추가
		labels.forEach(label => {
			label.addEventListener('click', function() {
				const index = this.getAttribute('data-index');

				// 클릭된 레이블을 앞으로 보내고 나머지는 뒤로 보냄
				labels.forEach((otherLabel, otherIndex) => {
					if (otherIndex != index) {
						otherLabel.classList.remove('active');
						otherLabel.classList.add('inactive');
					} else {
						otherLabel.classList.remove('inactive');
						otherLabel.classList.add('active');
					}
				});

				// 광고 이미지 전환, active 클래스로 fade in/out 처리
				ad2Images.forEach((img, imgIndex) => {
					if (imgIndex == index) {
						img.classList.add('active');
					} else {
						img.classList.remove('active');
					}
				});
			});
		});
	});

	//3번 광고
	document.addEventListener('DOMContentLoaded', function () {
		// 광고 3번 슬라이드 쇼
		let ad3CurrentIndex = 0;
		const ad3Images = document.querySelectorAll('.ad3-images');
		const ad3TotalImages = ad3Images.length;
		let ad3SlideInterval;

		function showNextAd3Image() {
			ad3Images[ad3CurrentIndex].classList.remove('active');
			ad3Images[ad3CurrentIndex].classList.add('inactive');
			ad3CurrentIndex = (ad3CurrentIndex + 1) % ad3TotalImages;
			ad3Images[ad3CurrentIndex].classList.remove('inactive');
			ad3Images[ad3CurrentIndex].classList.add('active');
		}

		function startAd3SlideShow() {
			ad3SlideInterval = setInterval(showNextAd3Image, 3000);
		}

		startAd3SlideShow();
	});

</script>

</body>
</html>
