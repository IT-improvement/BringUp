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
			margin: 20px;
		}

		.ad-image {
			width: 100%;
			height: 100%;
			object-fit: contain; /* 이미지 비율 유지 및 부모 영역에 맞춤 */
			transition: opacity 1s ease-in-out;
		}

		/* 이전, 다음 버튼 스타일 */
		.ad1 .prev-btn, .ad1 .next-btn {
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

		.ad1 .prev-btn {
			left: 10px;
		}

		.ad1 .next-btn {
			right: 10px;
		}

		/* 로그인 스타일 */
		.login-box {
			width: 600px;
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

			overflow: hidden; /* 이미지가 넘치는 것을 숨김 */
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

		.label:hover {
			background-color: #e9e9e9;
			box-shadow: 0 4px 12px rgba(0,0,0,0.2);
			opacity: 1;
		}

		.label.active {
			z-index: 10;
			opacity: 1;
		}

		.label.inactive {
			z-index: 0;
			opacity: 0.5;
		}

		/* 애니메이션이 부드럽게 적용되도록 변경 */
		.ad2-image {
			width: 100%;
			height: 100%;
			object-fit: cover; /* 이미지가 부모 요소의 크기에 맞게 조정됨 */
			object-position: center; /* 이미지 중앙을 기준으로 맞춤 */
			transition: opacity 0.5s ease-in-out;
			display: block; /* 항상 표시되도록 설정 */
		}

		.ad2-image.active {
			display: block;
			opacity: 1;
			object-fit: contain; /* 이미지 비율 유지 및 부모 영역에 맞춤 */
		}

		/* 광고 3번과 무한 스크롤 */
		.ad3 {
			background-color: #ccc;
			height: 150px;
			text-align: center;
			margin-bottom: 20px;
			margin-top: 20px;
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
		button.prev-btn, button.next-btn {
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
			<img class="ad-image" src="" alt="광고 이미지" style="display:none;">
			<button class="prev-btn">❮</button>
			<button class="next-btn">❯</button>
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
			<img class="ad2-image" src="http://localhost:8080/image/default.png" alt="광고 2 이미지 1">
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
		const token = localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken');
		const imageElement = document.querySelector('.ad-image');
		const prevButton = document.querySelector('.prev-btn');
		const nextButton = document.querySelector('.next-btn');
		let currentIndex = 0;
		let ads = [];

		// 광고 데이터를 가져오는 함수
		function fetchAdvertisements() {
			fetch('/main/advertisements', {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json'
				}
			})
					.then(response => {
						if (!response.ok) {
							throw new Error(`광고를 불러오지 못했습니다: ${response.status}`);
						}
						return response.json();
					})
					.then(data => {
						ads = data.map(ad => {
							// 광고 이미지 경로 설정
							// DB에서 제공한 경로를 그대로 사용합니다.
							let adImageSrc = ad.advertisementImage;

							// 상대 경로인 경우 절대 경로로 변경
							if (!adImageSrc.startsWith('/image/')) {
								adImageSrc = `http://localhost:8080/image/`+ adImageSrc;
							}

							return {
								...ad,
								advertisementImage: adImageSrc
							};
						});

						if (ads.length > 0) {
							displayImage(currentIndex); // 첫 번째 광고 이미지 표시
							startAutoSlide();  // 자동 슬라이드 시작
						} else {
							console.error('광고 데이터가 비어 있습니다.');
						}
					})
					.catch(error => {
						console.error('광고 데이터를 가져오는 중 오류 발생:', error);
					});
		}

		// 이미지를 표시하는 함수
		function displayImage(index) {
			if (ads.length === 0) {
				console.error('광고 데이터가 없습니다.');
				return;
			}

			// 광고 이미지 경로를 그대로 사용
			let adImageSrc = ads[index].advertisementImage;

			imageElement.src = adImageSrc;
			imageElement.style.display = 'block';
			console.log('이미지경로:', adImageSrc);
			imageElement.onerror = function() {
				console.error('이미지를 로드하는 중 오류 발생:', adImageSrc);
				alert('이미지를 불러오는 중 오류가 발생했습니다. 파일 경로를 확인해주세요.');
			};
		}
		/*// 자동 슬라이드 시작
		function startAutoSlide() {
			setInterval(() => {
				currentIndex = (currentIndex + 1) % ads.length;
				displayImage(currentIndex);
			}, 3000); // 3초마다 슬라이드 전환
		}*/

		// 이전 이미지로 이동
		prevButton.addEventListener('click', function () {
			currentIndex = (currentIndex - 1 + ads.length) % ads.length;
			displayImage(currentIndex);
		});

		// 다음 이미지로 이동
		nextButton.addEventListener('click', function () {
			currentIndex = (currentIndex + 1) % ads.length;
			displayImage(currentIndex);
		});

		// 페이지 로드 시 광고 데이터 가져오기
		fetchAdvertisements();
		// JWT 토큰을 사용하여 사용자 정보 가져오기

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
		let ad3CurrentIndex = 0;
		const ad3Images = document.querySelectorAll('.ad3-images');
		const ad3TotalImages = ad3Images.length;
		let ad3SlideInterval;

		// 광고 3번 이미지 슬라이드 쇼 함수
		function showNextAd3Image() {
			ad3Images[ad3CurrentIndex].style.display = 'none'; // 현재 이미지를 숨김
			ad3CurrentIndex = (ad3CurrentIndex + 1) % ad3TotalImages; // 다음 이미지 인덱스 계산
			ad3Images[ad3CurrentIndex].style.display = 'block'; // 다음 이미지를 표시
		}

		// 3초마다 showNextAd3Image 함수 호출
		function startAd3SlideShow() {
			ad3SlideInterval = setInterval(showNextAd3Image, 3000); // 3초마다 광고 3 이미지 변경
		}

		// 처음 광고 3 슬라이드 쇼 시작
		startAd3SlideShow();
	});


</script>

</body>
</html>
