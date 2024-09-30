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
	<link rel="stylesheet" type="text/css" href="/resources/style/member/main.css">

	<!--  JS -->
	<!-- <script src="/resources/script/member/user/파일명.js"></script> -->
	
</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />
<script>

    document.addEventListener('DOMContentLoaded', function() {
        // 이벤트 위임을 사용하여 로그아웃 버튼 클릭 이벤트 처리
        document.body.addEventListener('click', function(event) {
            if (event.target.id === 'logoutButton' || event.target.closest('#logoutButton')) {
                console.log("로그아웃 버튼 클릭");
                event.preventDefault();
                localStorage.removeItem('accessToken');
                window.location.href = '/';
            }
        });

        // 나머지 코드는 그대로 유지
        $(document).ready(function() {
            const accessToken = localStorage.getItem("accessToken");
            
            // 초기 상태에서 두 카드 모두 숨김
            document.getElementById("before-login-card").style.display = "none";
            document.getElementById("after-login-card").style.display = "none";
            
            if (accessToken) {
                // 액세스 토큰이 있을 경우
                document.getElementById("before-login-card").style.display = "none";
                document.getElementById("after-login-card").style.display = "block";
                
                fetch('/member/name', {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + accessToken
                    }
                })
                .then(response => response.json())
                .then(data => {
                    document.getElementById("card-title-after-login").textContent = data.data + "님 환영합니다!";
                    document.getElementById("card-text-after-login").textContent = "프리미엄 회원입니다.";
                })
                .catch(() => {
                    document.getElementById("card-title-after-login").textContent = "오류가 발생했습니다.";
                    document.getElementById("card-text-after-login").textContent = "다시 로그인해 주세요.";
                });
            } else {
                // 액세스 토큰이 없을 경우
                document.getElementById("before-login-card").style.display = "block";
                document.getElementById("after-login-card").style.display = "none";
            }
                
            
            
            fetch('/main/premium', {
                method: 'GET',
            })
            .then(response => response.json())
            .then(data => {
                console.log("프리미엄 광고 이미지 요청 결과:", data);
                const premiumImage = document.getElementById('premium-image');
                
                premiumImage.src = data[0].premiumImage;
                
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
                const bannerImage = document.getElementById('banner-image');
                bannerImage.src = data[0].bannerImage;
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
    });
</script>

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
    <div class="container" style="max-width: 1260px;">
        <main class="flex-grow-1 d-flex flex-column">
            <div class="advertising-container d-flex flex-column">
                <div class="d-flex my-4" style="height: 502px;">
                    <div class="user-premium-container h-100 position-relative border border-1 border-secondary" style="flex: 4.0889;">
                        <img id="premium-image" class="position-absolute w-100 h-100 object-fit-cover" alt="프리미엄 광고 이미지">
                    </div>
                    <div class="card-container ms-4" style="flex: 1.5;">
                        <div class="card h-100">
                            <div class="card-body border border-1 border-secondary d-flex flex-column justify-content-center mb-4" style="flex: 3;">
                                <h5 class="card-title mb-5">BringUp에서 채용 공고를<br>쉽게 찾아보세요!</h5>
                                <button id="view-jobs-button" class="btn btn-outline-primary mb-2 w-75 h-25 align-self-end" onclick="window.location.href='/member/recruitmentPage'">공고 보러가기</button>
                            </div>
                            <div class="card-body border border-1 border-secondary rounded-3 d-flex flex-column justify-content-center" style="flex: 2.3;">
                                <div id="before-login-card">
                                    <h6 class="card-title mb-4">로그인을 통해 <br>더 많은 서비스를 이용해보세요!</h6>
                                    <button id="login-button" class="btn btn-outline-primary w-100 mb-2 h-[60px]" onclick="window.location.href='/member/Login'">로그인</button>
                                    <div class="d-flex flex-row justify-content-center">
                                        <a href="/member/auth/findauth" id="findId-button" class="text-decoration-none text-secondary me-1">아이디 찾기</a>
                                        <p class="text-secondary me-1">|</p>
                                        <a href="/member/auth/findauth" id="findPw-button" class="text-decoration-none text-secondary me-1">비밀번호 찾기</a>
                                        <p class="text-secondary me-1">|</p>
                                        <a href="/member/join" id="register-button" class="text-decoration-none text-secondary me-1">회원가입</a>
                                    </div>
                                </div>
                                <div id="after-login-card">
                                    <div class="d-flex flex-row justify-content-between">
                                        <h5 id="card-title-after-login" class="align-self-center fw-bold"></h5>
                                        <div>
                                            <button id="mypage-button" class="btn btn-outline-secondary btn-xs" onclick="window.location.href='/member/memberProfile'"><i class="bi bi-gear"></i></button>
                                            <button id="logoutButton" class="btn btn-outline-danger btn-xs"><i class="bi bi-box-arrow-right"></i></button>
                                        </div>
                                    </div>
                                    <p id="card-text-after-login" class="mb-0"></p>
                                    <hr class="my-2">
                                    <a href="/member/AnnouncementRecruitment" class="d-flex flex-row justify-content-between align-items-center text-decoration-none text-secondary hover-underline">
                                        <p class="mb-0">지원한 공고</p>
                                        <p class="mb-0">0개</p>
                                    </a>
                                    <a href="/member/proposeRecruitment" class="d-flex flex-row justify-content-between align-items-center text-decoration-none text-secondary hover-underline">
                                        <p class="mb-0">제안 받은 공고</p>
                                        <p class="mb-0">0개</p>
                                    </a>
                                    <hr class="my-2">
                                    <div class="representative-resume-container">
                                        <h6 class="text-secondary mb-2">대표 이력서</h6>
                                        <a href="/member/resume/details/${data.resumeIndex}" id="representative-resume-container" class="d-flex align-items-center border border-1 border-secondary p-2">
                                            <img src="/resources/image/image.png" class="rounded-circle me-2" style="width: 40px; height: 40px;">
                                            <div>
                                                <p id="representative-resume-name" class="mb-0 fw-bold dynamic-text-color">N년차 개발자</p>
                                                <div id="representative-resume-skills" class="d-flex">
                                                    <span class="badge bg-primary me-1">JAVA</span>
                                                    <span class="badge bg-primary me-1">C</span>
                                                    <span class="badge bg-primary">C++</span>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="user-main-container d-flex flex-grow-2 mb-4 gap-2" style="flex: 2;">
                    <div class="image-container" style="flex: 3;">
                        <div id="main-image-slider" class="carousel slide h-100" data-bs-ride="carousel" data-bs-interval="5000">
                            <div class="carousel-indicators">
                            </div>
                            <div class="carousel-inner rounded-3 border border-1 border-#C2C2C2">
                            </div>
                            <button class="carousel-control-prev" type="button" data-bs-target="#main-image-slider" data-bs-slide="prev" style="left: 0; width: 40px; background-color: rgba(128, 128, 128, 0.05);">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">이전</span>
                            </button>
                            <button class="carousel-control-next" type="button" data-bs-target="#main-image-slider" data-bs-slide="next" style="right: 0; width: 40px; background-color: rgba(128, 128, 128, 0.05);">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">다음</span>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="user-banner-container d-flex border mb-4" style="height: 82px;">
                    <a href="/member/recruitment/details/${data.recruitmentIndex}" class="image-container w-100 h-100">
                        <img id="banner-image" class="w-100 h-100 object-fit-cover" alt="배너 광고 이미지">
                    </a>
                </div>
                <div class="user-recruit-container mb-4">
                    <h2 class="text-left mb-3 fw-bold">추천 공고</h2>
                    <div class="d-flex flex-wrap justify-content-between">
                    </div>
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