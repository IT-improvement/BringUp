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

    <!-- 메인 JS -->
    <!-- <script src="/resources/script/company/profile.js"></script> -->
	<script>
		document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem("accessToken");
    console.log("Access token: " + accessToken);
    const url = "/com/companyInfo/post"
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
            const companyName = data.data.companyName ? data.data.companyName : "정보 없음";
            const representativeName = data.data.managerName ? data.data.managerName : "정보 없음";
            const address = data.data.companyAddress ? data.data.companyAddress : "정보 없음";
            const homepage = data.data.companyHomepage ? data.data.companyHomepage : "정보 없음";
            const industry = data.data.companyCategory ? data.data.companyCategory : "정보 없음";
            const employeeCount = data.data.companySize ? data.data.companySize : "정보 없음";
            const representativeEmail = data.data.managerEmail ? data.data.managerEmail : "정보 없음";
            const companyContent = data.data.companyContent ? data.data.companyContent : "정보 없음";
			const companyWelfare = data.data.companyWelfare ? data.data.companyWelfare : "정보 없음";
			const companyHistory = data.data.companyHistory ? data.data.companyHistory : "정보 없음";
			const companyVision = data.data.companyVision ? data.data.companyVision : "정보 없음";
			const companyFinancialStatements = data.data.companyFinancialStatements ? data.data.companyFinancialStatements : "정보 없음";
			// const companySalary = data.data.companySalary ? data.data.companySalary : "정보 없음";
			const companySubsidiary = data.data.companySubsidiary ? data.data.companySubsidiary : "정보 없음";
			
			function formatPhoneNumber(phoneNumber) {
                if (phoneNumber.length === 10) {
                    return phoneNumber.substring(0, 2) + "-" + phoneNumber.substring(2, 6) + "-" + phoneNumber.substring(6);
                } else if (phoneNumber.length === 11) {
                    return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7);
                }
                return phoneNumber;
            }

            const formattedCompanyPhoneNumber = formatPhoneNumber(data.data.companyPhonenumber);
            const phoneNumber = formattedCompanyPhoneNumber;

            const formattedManagerPhoneNumber = formatPhoneNumber(data.data.managerPhonenumber);
            const representativePhoneNumber = formattedManagerPhoneNumber;

			const establishDate = data.data.companyOpendate ? data.data.companyOpendate : "정보 없음";

			const scale = data.data.companyScale ? data.data.companyScale + "기업" : "정보 없음";

			const formattedLicense = data.data.companyLicense ? data.data.companyLicense.substring(0, 3) + "-" + data.data.companyLicense.substring(3, 5) + "-" + data.data.companyLicense.substring(5) : "정보 없음";
            const companyLicense = formattedLicense;
            // 프로필 이미지 설정
            const images = data.data.companyLogo ? data.data.companyLogo : "정보 없음";
			document.getElementById('profileImage').src = images;

            // 회사 이미지 처리
            const companyImagesString = data.data.companyImg ? data.data.companyImg : "";
            const companyImages = companyImagesString ? companyImagesString.split(',') : [];
            const swiperWrapper = document.querySelector('.swiper-wrapper');
            swiperWrapper.innerHTML = ''; // 기존 슬라이드 제거

            if (companyImages.length > 0) {
                companyImages.forEach((img, index) => {
                    const slide = document.createElement('div');
                    slide.className = 'swiper-slide';
                    const image = document.createElement('img');
                    image.src = img.trim(); // 앞뒤 공백 제거
                    image.alt = `회사 이미지 ${index + 1}`;
                    image.className = 'd-block w-100';
                    image.style.height = '100%';
                    image.style.objectFit = 'cover';
                    slide.appendChild(image);
                    swiperWrapper.appendChild(slide);
                });

                // 이미지가 1개 이상일 때만 Swiper 초기화
                if (companyImages.length > 1) {
                    initSwiper();
                } else {
                    // 이미지가 1개일 때 슬라이더 컨테이너 스타일 조정
                    document.querySelector('.swiper-container').style.pointerEvents = 'none';
                }
            } else {
                // 이미지가 없을 경우 대체 텍스트 표시
                const noImageSlide = document.createElement('div');
                noImageSlide.className = 'swiper-slide';
                noImageSlide.textContent = '등록된 회사 이미지가 없습니다.';
                noImageSlide.style.display = 'flex';
                noImageSlide.style.alignItems = 'center';
                noImageSlide.style.justifyContent = 'center';
                swiperWrapper.appendChild(noImageSlide);
            }

            // 각각의 id값에 맵핑
            document.getElementById('companyName').textContent = companyName;
            document.getElementById('representativeName').textContent = representativeName;
            document.getElementById('address').textContent = address;
            document.getElementById('homepage').textContent = homepage;
            document.getElementById('industry').textContent = industry;
            document.getElementById('employeeCount').textContent = employeeCount;
            document.getElementById('representativeEmail').textContent = representativeEmail;
            document.getElementById('representativePhoneNumber').textContent = representativePhoneNumber;
            document.getElementById('phoneNumber').textContent = phoneNumber;
            document.getElementById('establishDate').textContent = establishDate;
            document.getElementById('scale').textContent = scale;
            document.getElementById('companyLicense').textContent = companyLicense;
            document.getElementById('companyContent').textContent = companyContent;
			document.getElementById('companyWelfare').textContent = companyWelfare;
			document.getElementById('companyHistory').textContent = companyHistory;
			document.getElementById('companyVision').textContent = companyVision;
			document.getElementById('companyFinancialStatements').textContent = companyFinancialStatements;
			// document.getElementById('companySalary').textContent = companySalary;
			document.getElementById('companySubsidiary').textContent = companySubsidiary;
        })
        .catch(error => {
            console.error('Error:', error);
        });
    } else {
        console.log("토큰을 찾을 수 없습니다.");
    }
});

function initSwiper() {
    new Swiper('.swiper-container', {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        touchEventsTarget: 'container',
        touchRatio: 1,
        touchAngle: 45,
        grabCursor: true,
        keyboard: {
            enabled: true,
        },
        mousewheel: false, // 마우스 휠 비활성화
    });
}
	</script>
	<!-- Swiper JS -->
	<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

<main class="flex-grow-1 m-4">
	<div class="company-profile container" style="max-width: 1260px;">
		<h2>회사 정보</h2>
		<div class="d-flex align-items-center justify-content-between mb-3">
			<div class="d-flex align-items-center">
				<div class="rounded-circle overflow-hidden border border-secondary" style="width: 47px; height: 47px; position: relative;">
					<img src="" id="profileImage" alt="프로필 이미지" 
						style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
				</div>
				<p id="companyName" style="margin-left: 10px; margin-bottom: 0; display: flex; align-items: center; height: 47px; font-size: 24px;"></p>
			</div>
			<div class="d-flex align-items-center">
				<button class="btn btn-secondary" onclick="location.href='/company/auth/updateAuth'"><i class="bi bi-pencil-square"></i></button>
			</div>
		</div>
		<!-- Swiper CSS -->
		<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
		<!-- 이미지 슬라이더 컨테이너 -->
		<div class="swiper-container mb-4" style="width: 100%; max-width: 1260px; height: 480px; border-radius: 20px; border: 1px solid #ddd; overflow: hidden;">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="" alt="회사 이미지 1" class="d-block w-100" style="height: 100%; object-fit: cover;">
				</div>
				<div class="swiper-slide">
					<img src="" alt="회사 이미지 2" class="d-block w-100" style="height: 100%; object-fit: cover;">
				</div>
				<div class="swiper-slide">
					<img src="" alt="회사 이미지 3" class="d-block w-100" style="height: 100%; object-fit: cover;">
				</div>
			</div>
		</div>
		<div class="d-flex justify-content-between mb-3">
			<div class="flex-fill m-2">
				<label for="companyLicense" class="form-label">사업자등록번호</label>
				<p id="companyLicense" class="mb-0"></p>
			</div>
			<div class="flex-fill m-2">
				<label for="scale" class="form-label">기업 규모</label>
				<p id="scale" class="mb-0"></p>
			</div>
			<div class="flex-fill m-2">
				<label for="establishDate" class="form-label">설립일</label>
				<p id="establishDate" class="mb-0"></p>
			</div>
				<div class="m-3">
				<label for="representativePhoneNumber" class="form-label">대표 전화번호</label>
				<p id="representativePhoneNumber"></p>
			</div>
		</div>
		<div class="d-flex mb-3">
			<div class="flex-grow-1 me-3">
			<h3>기업 소개</h3>
				<p id="companyContent"></p>
				<h3>복지</h3>
				<p id="companyWelfare"></p>
				<h3>연혁</h3>
				<p id="companyHistory"></p>
				<h3>회사 비전</h3>
				<p id="companyVision"></p>
				<h3>재무제표</h3>
				<p id="companyFinancialStatements"></p>
				<h3>직급 별 연봉 정보</h3>
				<p id="companySalary"></p>
				<h3>계열사</h3>
				<p id="companySubsidiary"></p>
			</div>
			<div class="d-flex flex-column" style="width: 380px; height: 710px;">
				<div class="border border-#ddd rounded mb-3 p-3" style="height: 320px;">
					<div class="mb-3 d-flex">
						<label for="representativeName" class="form-label m-0" style="width: 120px; flex-shrink: 0;">회사 대표</label>
						<p id="representativeName" class="m-0" style="flex-grow: 1;"></p>
					</div>
				<div class="mb-3 d-flex">
					<label for="representativeEmail" class="form-label m-0" style="width: 120px; flex-shrink: 0;">대표 이메일</label>
				<p id="representativeEmail" class="m-0" style="flex-grow: 1;"></p>
				</div>
					<div class="mb-3 d-flex">
					<label for="phoneNumber" class="form-label m-0" style="width: 120px; flex-shrink: 0;">전화번호</label>
				<p id="phoneNumber" class="m-0" style="flex-grow: 1;"></p>
					</div>
				<div class="mb-3 d-flex">
						<label for="industry" class="form-label m-0" style="width: 120px; flex-shrink: 0;">업종</label>
						<p id="industry" class="m-0" style="flex-grow: 1;"></p>
				</div>
				<div class="mb-3 d-flex">
					<label for="employeeCount" class="form-label m-0" style="width: 120px; flex-shrink: 0;">직원 수</label>
						<p id="employeeCount" class="m-0" style="flex-grow: 1;"></p>
					</div>
				<div class="mb-3 d-flex">
					<label for="homepage" class="form-label m-0" style="width: 120px; flex-shrink: 0;">홈페이지 주소</label>
					<p id="homepage" class="m-0" style="flex-grow: 1;"></p>
				</div>
				<div class="mb-3 d-flex">
					<label for="address" class="form-label m-0" style="width: 120px; flex-shrink: 0;">주소</label>
				<p id="address" class="m-0" style="flex-grow: 1;"></p>
					</div>
			</div>
			<div class="border border-#ddd rounded" style="height: 380px;">지도</div>
		</div>
	</div>
</main>
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
