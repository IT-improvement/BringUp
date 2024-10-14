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

	<!-- jQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<style>
		/* 모집 공고 아이템의 기본 스타일 */
		.recruitment-item {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 20px;
			border: 1px solid #ddd;
			border-radius: 5px;
			margin-bottom: 20px;
			background-color: white;
			box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
			transition: transform 0.3s ease, box-shadow 0.3s ease;
		}

		.recruitment-item:hover {
			transform: translateY(-5px);
			box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
		}

		/* 공고 내용 세로 배치 */
		.recruitment-content {
			display: flex;
			flex-direction: column;  /* 세로 정렬 */
		}

		/* 버튼을 오른쪽 끝에 배치 */
		.recruitment-button {
			display: flex;
			justify-content: flex-end;  /* 오른쪽 정렬 */
			align-items: center;
			min-width: 120px;  /* 버튼 크기에 따라 최소 너비 설정 */
		}

		/* 버튼 스타일 */
		.btn-outline-primary {
			border: 1px solid #007bff;
			color: #007bff;
			padding: 8px 15px;
			border-radius: 5px;
			font-size: 14px;
			transition: all 0.3s ease;
		}

		.btn-outline-primary:hover {
			background-color: #007bff;
			color: white;
		}

		/* 반응형 처리 */
		@media (max-width: 768px) {
			.recruitment-item {
				flex-direction: column;
				align-items: flex-start;
			}

			.recruitment-button {
				justify-content: flex-start;
				margin-top: 10px;
			}
		}


	</style>
</head>

<body class="d-flex flex-column min-vh-100">
<!-- header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<main class="flex-grow-1">
	<section class="container mt-5">
		<h2 class="section-title">TOP 100 공고</h2>
		<div id="all-recruitments" class="list-group recruitment-list">
			<!-- 전체 채용 정보가 여기에 추가됩니다 -->
		</div>
	</section>
</main>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

<script>
	document.addEventListener('DOMContentLoaded', function() {
		const url = "/recruitment/topList"; // Top 100 공고 리스트를 가져오는 API 엔드포인트

		// 공고 리스트를 가져오기
		fetch(url, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json' // JSON 형식으로 요청
			}
		})
				.then(response => {
					if (!response.ok) {
						throw new Error('Network response was not ok');
					}
					return response.json();
				})
				.then(data => {
					if (Array.isArray(data)) {
						renderRecruitmentList(data);
					} else if (Array.isArray(data.data)) {
						renderRecruitmentList(data.data);
					} else {
						console.error("Unexpected data format:", data);
					}
				})
				.catch(error => {
					console.error('Error fetching recruitment data:', error);
				});
	});

	function renderRecruitmentList(recruitments) {
		const allContainer = document.getElementById('all-recruitments');

		recruitments.forEach(function(recruitment) {
			const recruitmentTitle = recruitment.recruitmentTitle || '제목 없음';
			const category = recruitment.category || '정보 없음';
			const skill = recruitment.skill || '정보 없음';
			const recruitmentType = recruitment.recruitmentType || '정보 없음';
			const period = recruitment.period || '정보 없음';

			// 모집 공고 아이템 생성
			const recruitmentItem = document.createElement('div');
			recruitmentItem.className = 'recruitment-item';

			// 제목, 카테고리, 기술 스택 등 정보를 포함하는 content div 생성
			const contentDiv = document.createElement('div');
			contentDiv.className = 'recruitment-content';

			const titleElement = document.createElement('h5');
			titleElement.className = 'recruitment-title mb-2';
			titleElement.textContent = recruitmentTitle;

			const categoryElement = document.createElement('p');
			categoryElement.className = 'recruitment-info mb-1';
			categoryElement.textContent = '카테고리: ' + category;

			const skillElement = document.createElement('p');
			skillElement.className = 'recruitment-info mb-1';
			skillElement.textContent = '기술 스택: ' + skill;

			const recruitmentTypeElement = document.createElement('p');
			recruitmentTypeElement.className = 'recruitment-info mb-1';
			recruitmentTypeElement.textContent = '고용 형태: ' + recruitmentType;

			const periodElement = document.createElement('p');
			periodElement.className = 'recruitment-info mb-1';
			periodElement.textContent = '기간: ' + period;

			// 내용을 contentDiv에 추가
			contentDiv.appendChild(titleElement);
			contentDiv.appendChild(categoryElement);
			contentDiv.appendChild(skillElement);
			contentDiv.appendChild(recruitmentTypeElement);
			contentDiv.appendChild(periodElement);

			// 상세보기 버튼을 포함하는 div 생성
			const buttonDiv = document.createElement('div');
			buttonDiv.className = 'recruitment-button';

			const linkElement = document.createElement('a');
			// 공고 인덱스를 포함한 상세 페이지로의 링크 설정
			if (recruitment.recruitmentIndex) {
				linkElement.href = `/member/recruitment/details/` + recruitment.recruitmentIndex;
			} else {
				console.error("recruitmentIndex is null for recruitment:", recruitment);
				return; // 인덱스가 없는 경우 렌더링을 중단
			}

			linkElement.className = 'btn btn-outline-primary';
			linkElement.textContent = '상세 내용';

			// 버튼을 buttonDiv에 추가
			buttonDiv.appendChild(linkElement);

			// recruitmentItem에 contentDiv와 buttonDiv 추가
			recruitmentItem.appendChild(contentDiv);
			recruitmentItem.appendChild(buttonDiv);

			// 전체 컨테이너에 모집 공고 아이템 추가
			allContainer.appendChild(recruitmentItem);
		});
	}
</script>
</body>
</html>
