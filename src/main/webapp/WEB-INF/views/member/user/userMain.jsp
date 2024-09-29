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

        function logout() {
            localStorage.removeItem("accessToken");
            window.location.href = "/";
        }
		
        fetch('/main/premium', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("프리미엄 광고 이미지 요청 결과:", data);
            const premiumImage = document.getElementById('premium-image');
            premiumImage.src = data.premiumImage;
            
            const premiumLink = document.createElement('a');
            premiumLink.href = `/member/recruitment/details/${data.recruitmentIndex}`;
            premiumLink.appendChild(premiumImage);
            
            const premiumContainer = document.querySelector('.user-premium-container');
            premiumContainer.innerHTML = '';
            premiumContainer.appendChild(premiumLink);
        })
        .catch((error) => {
            console.error("광고 이미지를 가져오는 중 오류가 발생했습니다:", error);
        });


		// 메인 이미지 슬라이더
		fetch('/main/main', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(data => {
			console.log("메인 광고 이미지 요청 : ", data);
			const carouselInner = document.querySelector('#main-image-slider .carousel-inner');
			const carouselIndicators = document.querySelector('#main-image-slider .carousel-indicators');
			updateCarousel(data, carouselInner, carouselIndicators, '#main-image-slider', '메인 광고 이미지');
		})
		.catch((error) => {
			console.error("메인 광고 이미지를 가져오는 중 오류가 발생했습니다:", error);
		});

		// 배너 이미지 슬라이더
		fetch('/main/banner', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(data => {
			console.log("배너 광고 이미지 요청 : ", data);
			const carouselInner = document.querySelector('#banner-image-slider .carousel-inner');
			const carouselIndicators = document.querySelector('#banner-image-slider .carousel-indicators');
			updateCarousel(data, carouselInner, carouselIndicators, '#banner-image-slider', '배너 광고 이미지');
		})
		.catch((error) => {
			console.error("배너 광고 이미지를 가져오는 중 오류가 발생했습니다:", error);
		});

		// 캐러셀 업데이트 함수
		function updateCarousel(data, carouselInner, carouselIndicators, targetId, altText) {
			carouselInner.innerHTML = '';
			carouselIndicators.innerHTML = '';
			
			data.forEach((image, index) => {
				// 캐러셀 아이템 생성
				const carouselItem = document.createElement('div');
				carouselItem.className = "carousel-item" + (index === 0 ? " active" : "");
				
				const img = document.createElement('img');
				img.src = image.mainImage || image.bannerImage; // mainImage 또는 bannerImage 사용
				img.className = 'd-block w-100';
				img.alt = `${altText} ${index + 1}`;
				if (targetId === '#main-image-slider') {
					img.style.height = '320px';
				}
				
				const link = document.createElement('a');
				link.href = `/member/recruitment/details/${image.recruitmentIndex}`;
				link.appendChild(img);
				carouselItem.appendChild(link);
				
				carouselInner.appendChild(carouselItem);

				// 인디케이터 생성
				const button = document.createElement('button');
				button.type = 'button';
				button.setAttribute('data-bs-target', targetId);
				button.setAttribute('data-bs-slide-to', index.toString());
				if (index === 0) {
					button.className = 'active';
					button.setAttribute('aria-current', 'true');
				}
				button.setAttribute('aria-label', `Slide ${index + 1}`);
				carouselIndicators.appendChild(button);
			});
		}

		
		// 데이터 가져오기 및 카드 업데이트
		fetch('/main/list', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(data => {
			console.log("메인 공고 리스트 요청 : ", data);
			updateRecruitmentCards(data);
		})
		.catch((error) => {
			console.error("채용 공고 정보를 가져오는 중 오류가 발생했습니다:", error);
		});

		function updateRecruitmentCards(recruitmentData) {
			const recruitContainer = document.querySelector('.user-recruit-container .d-flex');
			recruitContainer.innerHTML = '';

			recruitmentData.forEach((item, index) => {
				const card = document.createElement('div');
				card.className = 'card m-2';
				card.style = 'width: 21%; min-width: 200px; margin-bottom: 20px; border: 1px solid transparent; transition: all 0.3s ease; cursor: pointer;';

				card.innerHTML = `
					<a href="/member/recruitment/details/${"${item.recruitmentIndex}"}" style="text-decoration: none; color: inherit;">
						<div style="height: 200px; overflow: hidden; position: relative;">
							<img src="${"${item.companyImg}"}" class="position-absolute w-100 h-100 object-fit-cover">
						</div>
						<div class="card-body p-2">
							<h6 class="card-title mb-1" style="font-size: 0.9rem; font-weight: bold;">${"${item.recruitmentTitle}"}</h6>
							<p class="card-text mb-1" style="font-size: 0.8rem; color: #666;">${"${item.companyName}"}</p>
							<p class="card-text mb-0 skill-tags" style="font-size: 0.75rem; color: #888;"></p>
						</div>
					</a>
				`;

				recruitContainer.appendChild(card);

				// 마우스 오버 이벤트 추가
				card.addEventListener('mouseover', function() {
					this.style.borderColor = '#ddd';
					this.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
					this.style.backgroundColor = '#f8f9fa';
				});
				card.addEventListener('mouseout', function() {
					this.style.borderColor = 'transparent';
					this.style.boxShadow = 'none';
					this.style.backgroundColor = 'transparent';
				});

				// 스킬 태그 추가
				const skillTags = card.querySelector('.skill-tags');
				item.skill.split(',').forEach(skill => {
					const span = document.createElement('span');
					span.className = 'badge bg-light text-dark me-1';
					span.textContent = skill.trim();
					skillTags.appendChild(span);
				});
			});

			// 빈 카드 추가 (4의 배수로 맞추기 위해)
			const emptyCardsNeeded = 4 - (recruitmentData.length % 4);
			if (emptyCardsNeeded < 4) {
				for (let i = 0; i < emptyCardsNeeded; i++) {
					const emptyCard = document.createElement('div');
					emptyCard.className = 'card m-2 invisible';
					emptyCard.style = 'width: 21%; min-width: 200px; margin-bottom: 20px;';
					recruitContainer.appendChild(emptyCard);
				}
			}
		}

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
                <div class="d-flex flex-grow-1.2 mt-2" style="flex: 1.2; height: 480px;">
                    <div class="user-premium-container h-100 position-relative" style="flex: 3;">
                        <img id="premium-image" class="position-absolute w-100 h-100 object-fit-cover">
                    </div>
                    <div class="card-container ms-2" style="flex: 1.5;">
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
                                <button id="logout-button" class="btn btn-outline-primary" onclick="logout()">로그아웃</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="user-banner-container d-flex flex-grow-0.15 border mt-2" style="flex: 0.15; height: 50px;">
                    <div class="image-container w-100 h-100">
                        <div id="banner-image-slider" class="carousel slide h-100" data-bs-ride="carousel" data-bs-interval="5000">
                            <div class="carousel-indicators" style="display: none;">
                            </div>
                            <div class="carousel-inner border border-1 border-#C2C2C2 banner h-100">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="user-main-container d-flex flex-grow-0.75 mt-2 gap-2" style="flex: 0.75;">
                    <div class="image-container" style="flex: 3;">
                        <div id="main-image-slider" class="carousel slide h-100" data-bs-ride="carousel" data-bs-interval="5000">
                            <div class="carousel-indicators">
                            </div>
                            <div class="carousel-inner rounded-3 border border-1 border-#C2C2C2 h-100">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="user-recruit-container mt-4">
                <h2 class="text-left mb-3 fw-bold">추천 공고</h2>
                <div class="d-flex flex-wrap justify-content-between">
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