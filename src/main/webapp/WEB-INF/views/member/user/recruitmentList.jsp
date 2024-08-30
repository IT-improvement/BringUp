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
	<link rel="stylesheet" type="text/css" href="/resources/style/member/recruitment.css">

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
	<!-- <script src="/resources/script/company/main.js"></script> -->

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">
<!-- header-->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<main class="flex-grow-1">
	<!-- 필터 바 섹션 -->
	<!-- 필터 바 섹션 -->
	<!-- 필터 바 섹션 -->
	<section class="ur_search-bar-container">
		<div class="ur_search-bar">
			<div class="ur_search-input">
				<input type="text" placeholder="회사/채용 공고로 검색" />
				<button class="ur_search-button">
					검색
				</button>
			</div>
			<div class="ur_filter-group">
				<!-- 필터 요소들 -->
				<div class="ur_filter-item" id="job-category-filter">
					<i class="bi bi-briefcase"></i>
					직군 · 직무
					<i class="bi bi-chevron-down"></i>
					<div class="ur_filter-dropdown" id="job-category-dropdown">
						<div class="ur_dropdown-section job-section">
							<h4>직군</h4>
							<ul>
								<li><input type="checkbox"> 전체</li>
								<li><input type="checkbox"> 개발</li>
								<li><input type="checkbox"> 게임개발</li>
								<li><input type="checkbox"> 디자인</li>
								<li><input type="checkbox"> 기획</li>
								<li><input type="checkbox"> 마케팅</li>
								<li><input type="checkbox"> 경영/인사</li>
								<li><input type="checkbox"> 영업</li>
							</ul>
						</div>
						<div class="ur_dropdown-section duty-section">
							<h4>직무</h4>
							<ul>
								<li><input type="checkbox"> 전체</li>
								<li><input type="checkbox"> 백엔드/서버 개발자</li>
								<li><input type="checkbox"> 프론트엔드/웹퍼블리셔</li>
								<li><input type="checkbox"> SW 엔지니어</li>
								<li><input type="checkbox"> 안드로이드 개발자</li>
								<li><input type="checkbox"> iOS 개발자</li>
								<li><input type="checkbox"> 크로스플랫폼 앱 개발자</li>
								<li><input type="checkbox"> 데이터 엔지니어</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="ur_filter-item" id="experience-location-filter">
					<i class="bi bi-geo-alt"></i>
					경력 · 지역
					<i class="bi bi-chevron-down"></i>
					<div class="ur_filter-dropdown" id="experience-location-dropdown">
						<div class="ur_dropdown-section experience-section">
							<h4>경력</h4>
							<ul>
								<li><input type="checkbox"> 전체</li>
								<li><input type="checkbox"> 경력무관</li>
								<li><input type="checkbox"> 인턴</li>
								<li><input type="checkbox"> 신입 (1년 이하)</li>
								<li><input type="checkbox"> 주니어 (1~3년)</li>
								<li><input type="checkbox"> 미들 (4~8년)</li>
								<li><input type="checkbox"> 시니어 (9년 이상)</li>
								<li><input type="checkbox"> Lead 레벨</li>
							</ul>
						</div>
						<div class="ur_dropdown-section location-section">
							<h4>지역</h4>
							<ul>
								<li><input type="checkbox"> 전체</li>
								<li><input type="checkbox"> 서울</li>
								<li><input type="checkbox"> 강남</li>
								<li><input type="checkbox"> 마포</li>
								<li><input type="checkbox"> 구로/가산</li>
								<li><input type="checkbox"> 경기</li>
								<li><input type="checkbox"> 판교/분당</li>
								<li><input type="checkbox"> 그 외</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="ur_tech-stack">
				<i class="bi bi-code-slash"></i>
				기술 스택
				<input type="text" placeholder="직무 스킬을 검색해보세요" />
			</div>
			<div class="ur_tech-tags">
				<span>Python</span>
				<span>JavaScript</span>
				<span>React</span>
				<span>Next.js</span>
				<span>SQL</span>
				<span>Slack</span>
				<span>JIRA</span>
				<span>Confluence</span>
			</div>
		</div>
	</section>

	<!-- 상단 섹션: 강조된 공고 -->
	<section class="container mt-5">
		<h2 class="section-title">이 공고 놓치지 마세요! 요즘 가장 핫한 공고 ⭐⭐⭐</h2>
		<div id="highlighted-recruitments" class="list-group recruitment-list">
			<!-- 강조된 공고가 여기에 추가됩니다 -->
		</div>
	</section>

	<!-- 전체 채용 정보 섹션 -->
	<section class="container mt-5">
		<h2 class="section-title">전체 채용 정보</h2>
		<div id="all-recruitments" class="list-group recruitment-list">
			<!-- 전체 채용 정보가 여기에 추가됩니다 -->
		</div>
	</section>
</main>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

<script>
	$(document).ready(function() {
		const accessToken = localStorage.getItem('accessToken'); // 로컬 스토리지에서 JWT 토큰 가져오기
		console.log("Access Token:", accessToken);

		if (!accessToken) {
			console.error('액세스 토큰이 로컬 스토리지에 없습니다.');
			alert('로그인이 필요합니다.');
			return;
		}

		// 공고 데이터를 서버에서 가져옴
		$.ajax({
			url: '/recruitment/list',  // 서버 API 엔드포인트
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${accessToken}`,// JWT 토큰을 Authorization 헤더에 추가
				'Content-Type': 'application/json'
			},
			dataType: 'json',
			success: function(response) {
				let highlightedContainer = $('#highlighted-recruitments');
				let allContainer = $('#all-recruitments');

				// 데이터를 반복하여 HTML을 생성 및 추가
				response.forEach(function(recruitment) {
					let recruitmentItem = `
                    <div class="list-group-item recruitment-item">
                        <div class="d-flex justify-content-between">
                            <div>
                                <h5 class="mb-1 recruitment-title">${recruitment.recruitment_title}</h5>
                                <p class="mb-1 recruitment-company">회사 ID: ${recruitment.company_id}</p>
                                <p class="mb-1 recruitment-category">카테고리: ${recruitment.category}</p>
                                <p class="mb-1 recruitment-skill">기술 스택: ${recruitment.skill}</p>
                                <p class="mb-1 recruitment-type">고용 형태: ${recruitment.recruitment_type}</p>
                                <p class="mb-1 recruitment-period">기간: ${recruitment.period}</p>
                                <small class="text-muted recruitment-status">상태: ${recruitment.status}</small>
                            </div>
                            <div class="align-self-center">
                                <a href="/recruitment/details/${recruitment.recruitment_index}" class="btn btn-outline-primary recruitment-link">상세 내용</a>
                            </div>
                        </div>
                    </div>`;

					// 강조된 공고와 일반 공고를 구분해서 추가 (예: 상태가 '진행중'인 경우 강조)
					if (recruitment.status === '진행중') {
						highlightedContainer.append(recruitmentItem);
					} else {
						allContainer.append(recruitmentItem);
					}
				});
			},
			error: function(error) {
				console.error("Error fetching recruitment data:", error);
			}
		});
	});

	//필터 내리기
	$(document).ready(function() {
		$("#job-category-filter").click(function() {
			$("#job-category-dropdown").toggle();
		});

		$("#experience-location-filter").click(function() {
			$("#experience-location-dropdown").toggle();
		});

		// 클릭 외부를 클릭했을 때 드롭다운 닫기
		$(document).click(function(e) {
			if (!$(e.target).closest('#job-category-filter').length) {
				$("#job-category-dropdown").hide();
			}
			if (!$(e.target).closest('#experience-location-filter').length) {
				$("#experience-location-dropdown").hide();
			}
		});
	});

</script>
</body>
</html>