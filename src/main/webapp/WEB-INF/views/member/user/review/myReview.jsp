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
	<link rel="stylesheet" type="text/css" href="/resources/style/member/companyReview.css">


	<!-- 테마 CSS -->
	<link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

	<!-- Bootstrap JS -->
	<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

	<!-- 벤더 -->
	<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
	<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

	<!-- 테마 JS -->
	<script src="/resources/script/common/function/functions.js"></script>

	<!-- JQuery -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<!-- 메인 스타일시트 -->
	<!-- <link rel="stylesheet" type="text/css" href="/resources/style/member/user/파일명.css"> -->

	<!--  JS -->
	<!-- <script src="/resources/script/member/user/파일명.js"></script> -->
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			const companyTab = document.getElementById("companyTab");
			const interviewTab = document.getElementById("interviewTab");
			const companyReviewContent = document.getElementById("companyReviewContent");
			const interviewReviewContent = document.getElementById("interviewReviewContent");

			companyTab.addEventListener("click", () => {
				companyReviewContent.classList.remove("d-none");
				interviewReviewContent.classList.add("d-none");
			});

			interviewTab.addEventListener("click", () => {
				companyReviewContent.classList.add("d-none");
				interviewReviewContent.classList.remove("d-none");
			});
		});

		function fetchCompanyReviews(query = "") {
			showLoading();
			
			const companyId = "${companyId}";
			const endpoint = "/member/company-review/" + companyId;

			fetch(endpoint)
				.then(response => response.json())
				.then(data => {
					const reviews = data.data; // 데이터에서 리뷰 목록 추출
					const reviewTableBody = document.querySelector("#reviewTable tbody");
					reviewTableBody.innerHTML = ""; // 테이블 초기화

					// 검색어에 맞는 리뷰 필터링
					const filteredReviews = reviews.filter(review => {
						const companyName = review.companyName.toLowerCase();
						const title = review.companyReviewTitle.toLowerCase();
						const lowerQuery = query.toLowerCase();
						return companyName.includes(lowerQuery) || title.includes(lowerQuery);
					});

					// 로딩 메시지 숨기기
					hideLoading();

					if (filteredReviews.length === 0) {
						// 검색 결과가 없으면 알림 메시지
						alert("검색 결과가 없습니다.");
						return;
					}

					// 검색 결과를 테이블에 추가
					filteredReviews.forEach(review => {
						const row = document.createElement("tr");
						document.getElementById("companyName").textContent = "나의 리뷰 - " + review.companyName;

						// 사용자 이메일
						const userEmailCell = document.createElement("td");
						userEmailCell.textContent = review.userEmail;
						row.appendChild(userEmailCell);

						// 제목
						const titleCell = document.createElement("td");
						titleCell.textContent = review.companyReviewTitle;
						row.appendChild(titleCell);

						// 평균 별점
						const ratingCell = document.createElement("td");
						const starContainer = document.createElement("div");
						starContainer.className = "m_review-ratings";

						for (let i = 1; i <= 5; i++) {
							const star = document.createElement("i");
							star.className = "fa fa-star";
							star.style.color = i <= review.averageRating ? "gold" : "#ccc"; // 별점에 따른 색상 설정
							starContainer.appendChild(star);
						}
						ratingCell.appendChild(starContainer);
						row.appendChild(ratingCell);

						// 작성일자
						const dateCell = document.createElement("td");
						dateCell.textContent = review.companyReviewDate;
						row.appendChild(dateCell);

						// 자세히 보기 버튼
						const detailButtonCell = document.createElement("td");
						const detailButton = document.createElement("a");
						detailButton.className = "details-button";
						detailButton.textContent = "자세히 보기";
						detailButton.href = `/member/m_reviewDetail/` + review.companyReviewIndex; // 각 리뷰에 맞는 상세보기 링크
						detailButtonCell.appendChild(detailButton);
						row.appendChild(detailButtonCell);

						// 테이블에 추가
						reviewTableBody.appendChild(row);
					});
				})
				.catch(error => {
					hideLoading();
					console.error("리뷰를 가져오는 중 오류 발생:", error);
				});
		}

		function fetchInterviewReviews(query = "") {
			showLoading();
			
			const companyId = "${companyId}";
			const endpoint = "/member/interview-review/" + companyId;

			fetch(endpoint)
				.then(response => response.json())
				.then(data => {
					const reviews = data.data; // 데이터에서 리뷰 목록 추출
					const reviewTableBody = document.querySelector("#interviewReviewTable tbody");
					reviewTableBody.innerHTML = ""; // 테이블 초기화

					// 검색어에 맞는 리뷰 필터링
					const filteredReviews = reviews.filter(review => {
						const companyName = review.companyName.toLowerCase();
						const title = review.interviewReviewTitle.toLowerCase();
						const lowerQuery = query.toLowerCase();
						return companyName.includes(lowerQuery) || title.includes(lowerQuery);
					});

					// 로딩 메시지 숨기기
					hideLoading();

					if (filteredReviews.length === 0) {
						// 검색 결과가 없으면 알림 메시지
						alert("검색 결과가 없습니다.");
						return;
					}

					// 검색 결과를 테이블에 추가
					filteredReviews.forEach(review => {
						const row = document.createElement("tr");
						document.getElementById("companyName").textContent = "나의 리뷰 - " + review.companyName;

						// 사용자 이메일
						const userEmailCell = document.createElement("td");
						userEmailCell.textContent = review.userEmail;
						row.appendChild(userEmailCell);

						// 제목
						const titleCell = document.createElement("td");
						titleCell.textContent = review.interviewReviewTitle;
						row.appendChild(titleCell);

						// 면접 분위기
						const atmosphereCell = document.createElement("td");
						atmosphereCell.textContent = review.interviewAtmosphere;
						row.appendChild(atmosphereCell);

						// 면접 난이도
						const difficultyCell = document.createElement("td");
						difficultyCell.textContent = review.interviewDifficulty;
						row.appendChild(difficultyCell);

						// 작성일자
						const dateCell = document.createElement("td");
						dateCell.textContent = review.interviewReviewDate;
						row.appendChild(dateCell);

						// 자세히 보기 버튼
						const detailButtonCell = document.createElement("td");
						const detailButton = document.createElement("a");
						detailButton.className = "details-button";
						detailButton.textContent = "자세히 보기";
						detailButton.href = `/member/m_reviewDetail/` + review.interviewReviewIndex; // 각 리뷰에 맞는 상세보기 링크
						detailButtonCell.appendChild(detailButton);
						row.appendChild(detailButtonCell);

						// 테이블에 추가
						reviewTableBody.appendChild(row);
					});
				})
				.catch(error => {
					hideLoading();
					console.error("리뷰를 가져오는 중 오류 발생:", error);
				});
		}

		// 로딩 창 표시 함수
		function showLoading() {
			const loadingElement = document.createElement("div");
				loadingElement.classList.add("loading-overlay");
				loadingElement.innerHTML = `<div class="spinner"></div><p>검색 중...</p>`;
				document.body.appendChild(loadingElement);
		}

		// 로딩 창 숨기기 함수
		function hideLoading() {
			const loadingElement = document.querySelector(".loading-overlay");
			if (loadingElement) {
				loadingElement.remove();
			}
		}
	</script>

</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
<div class="container" style="max-width: 1260px;">
	<main class="flex-grow-1">
		<div class="m_review-container">
			<h1 class="my-4 ms-2">나의 리뷰</h1>
			<div class="d-flex">
				<div class="d-flex flex-column me-3">
					<button id="companyTab" class="btn btn-secondary active mb-2">기업 리뷰</button>
					<button id="interviewTab" class="btn btn-secondary">면접 리뷰</button>
				</div>

				<!-- 기업 리뷰 -->
				<div id="companyReviewContent" class="flex-grow-1">
					<div class="d-flex justify-content-between mb-3">
						<h2>기업 리뷰</h2>
						<a href="/member/createReview" class="btn btn-primary float-end">리뷰 작성</a>
					</div>
					<div class="mb-3">
						<div class="d-flex align-items-center">
							<input type="text" placeholder="검색어 입력..." class="form-control me-2" />
							<button class="btn btn-primary"><i class="fas fa-search"></i></button>
						</div>
					</div>
					<table id="reviewTable" class="table text-center">
						<thead>
						<tr>
							<th class="col-1">번호</th>
							<th class="col-5">제목</th>
							<th class="col-2">평균별점</th>
							<th class="col-2">작성일자</th>
						</tr>
						</thead>
						<tbody>
						<!-- 데이터가 스크립트로 추가될 곳 -->
						</tbody>
					</table>
				</div>

				<!-- 면접 리뷰 -->
				<div id="interviewReviewContent" class="flex-grow-1 d-none">
					<div class="d-flex justify-content-between mb-3">
						<h2>면접 리뷰</h2>
						<a href="/member/createInterviewReview/${companyId}" class="btn btn-primary float-end">리뷰 작성</a>
						
					</div>
					<div class="mb-3">
						<div class="d-flex align-items-center">
							<input type="text" placeholder="검색어 입력..." class="form-control me-2" />
							<button class="btn btn-primary"><i class="fas fa-search"></i></button>
						</div>
					</div>
					<table id="interviewReviewTable" class="table text-center">
						<thead>
							<tr>
								<th class="col-1">번호</th>
								<th class="col-5">제목</th>
								<th class="col-2">면접 분위기</th>
								<th class="col-2">면접 난이도</th>
								<th class="col-2">작성일자</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</main>
</div>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>

</html>
