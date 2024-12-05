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
		// 전역 변수 선언
		let companyReviews = [];
		let interviewReviews = []; // 면 ���한 새로운 변수

		document.addEventListener("DOMContentLoaded", function() {
			const content = document.querySelector("#companyReviewContent");
			if (!content) {
				console.error("리뷰 컨텐츠를 찾을 수 없습니다.");
				return;
			}
			fetchCompanyReviews();
			const companyTab = document.getElementById("companyTab");
			const interviewTab = document.getElementById("interviewTab");
			const companyReviewContent = document.getElementById("companyReviewContent");
			const interviewReviewContent = document.getElementById("interviewReviewContent");

			companyTab.addEventListener("click", () => {
				companyReviewContent.classList.remove("d-none");
				interviewReviewContent.classList.add("d-none");
				fetchCompanyReviews();
			});

			interviewTab.addEventListener("click", () => {
				companyReviewContent.classList.add("d-none");
				interviewReviewContent.classList.remove("d-none");
				fetchInterviewReviews();
			});

			// 기업 리뷰 검색 기능
			const companySearchInput = document.querySelector("#companyReviewContent input[type='text']");
			const companySearchButton = document.querySelector("#companyReviewContent .btn-primary");
			
			companySearchInput.addEventListener("keypress", function(e) {
				if (e.key === "Enter") {
					fetchCompanyReviews(this.value);
				}
			});
			
			companySearchButton.addEventListener("click", function() {
				fetchCompanyReviews(companySearchInput.value);
			});

			// 면접 리뷰 검색 기능
			const interviewSearchInput = document.querySelector("#interviewReviewContent input[type='text']");
			const interviewSearchButton = document.querySelector("#interviewReviewContent .btn-primary");
			
			interviewSearchInput.addEventListener("keypress", function(e) {
				if (e.key === "Enter") {
					fetchInterviewReviews(this.value);
				}
			});
			
			interviewSearchButton.addEventListener("click", function() {
				fetchInterviewReviews(interviewSearchInput.value);
			});

			// 검색창 초기화 기능
			companyTab.addEventListener("click", () => {
				companySearchInput.value = "";
				companyReviewContent.classList.remove("d-none");
				interviewReviewContent.classList.add("d-none");
				fetchCompanyReviews();
			});

			interviewTab.addEventListener("click", () => {
				interviewSearchInput.value = "";
				companyReviewContent.classList.add("d-none");
				interviewReviewContent.classList.remove("d-none");
				fetchInterviewReviews();
			});
		});

		function fetchCompanyReviews(query = "") {
			showLoading();
			const endpoint = "/com/c_reviews";

			fetch(endpoint, {
				method: "POST",
				headers: {
					"Authorization": "Bearer " + localStorage.getItem("accessToken")
				}
			})
			.then(response => response.json())
			.then(data => {
				console.log("기업 리뷰 데이터: ");
				console.log(data.data);

				companyReviews = data.data;
				const reviewTableBody = document.querySelector("#companyReviewTableBody");
				reviewTableBody.innerHTML = "";

				// 검색어에 맞는 리뷰 필터링
				const filteredCompanyReviews = companyReviews.filter(review => {
					if (!query) return true;
					const searchFields = [
						review.reviewTitle,
						review.userEmail,
						review.content
					].map(field => (field || "").toLowerCase());
					const lowerQuery = query.toLowerCase();
					return searchFields.some(field => field.includes(lowerQuery));
				});

				hideLoading();

				if (filteredCompanyReviews.length === 0) {
					const noDataRow = document.createElement("tr");
					const noDataCell = document.createElement("td");
					noDataCell.colSpan = 5;
					noDataCell.textContent = "검색 결과가 없습니다.";
					noDataCell.className = "text-center";
					noDataRow.appendChild(noDataCell);
					reviewTableBody.appendChild(noDataRow);
					return;
				}

				filteredCompanyReviews.forEach((review, index) => {
					// 템플릿 대신 새로운 행을 직접 생성
					const row = document.createElement("tr");
					row.setAttribute("data-index", index);
					row.innerHTML = `
						<td class="text-center" data-field="number">${"${index + 1}"}</td>
						<td class="text-center" data-field="title">${"${review.reviewTitle}"}</td>
						<td class="text-center" data-field="userEmail">${"${review.userEmail}"}</td>
						<td class="text-center" data-field="averageRating"></td>
						<td class="text-center" data-field="reviewDate">${"${review.reviewDate}"}</td>
					`;
					
					row.addEventListener("click", () => companyReviewDetail(index));

					const averageRating = (review.advancement + review.benefit + review.companyCulture + review.management + review.workLife) / 5;
					const starContainer = createStarContainer(averageRating);
					row.querySelector('[data-field="averageRating"]').appendChild(starContainer);

					reviewTableBody.appendChild(row);
				});
			})
			.catch(error => {
				hideLoading();
				console.error("리뷰를 가져오는 중 오류 발생:", error);
				alert("리뷰를 가져오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
			});
		}

		function createStarContainer(averageRating) {
			const starContainer = document.createElement("div");
				starContainer.className = "m_review-ratings d-flex justify-content-center";
				for (let i = 1; i <= 5; i++) {
					const star = document.createElement("i");
					star.className = "fa fa-star";
					if (i <= Math.floor(averageRating)) {
						star.style.color = "gold";
					} else if (i === Math.ceil(averageRating) && averageRating % 1 >= 0.5) {
						star.className += " fa-star-half-alt";
						star.style.color = "gold";
					} else {
						star.style.color = "#ccc";
					}
					starContainer.appendChild(star);
				}
				return starContainer;
		}

		function createStarRating(rating) {
			let stars = '';
			for (let i = 1; i <= 5; i++) {
				if (i <= Math.floor(rating)) {
					stars += '<i class="fa fa-star" style="color: gold;"></i>';
				} else if (i === Math.ceil(rating) && rating % 1 >= 0.5) {
					stars += '<i class="fa fa-star-half-alt" style="color: gold;"></i>';
				} else {
					stars += '<i class="fa fa-star" style="color: #ccc;"></i>';
				}
			}
			return stars;
		}

		function companyReviewDetail(reviewIndex) {
			const review = companyReviews[reviewIndex];
			console.log("선택된 리뷰 데이터:", review); // 데이터 확인용 로그
			
			const mainContent = document.querySelector("#companyReviewContent");
			const originalContent = mainContent.innerHTML;
			
			// 별점 생성
			const advancementStars = createStarRating(review.advancement || 0);
			const benefitStars = createStarRating(review.benefit || 0);
			const culturalStars = createStarRating(review.companyCulture || 0);
			const managementStars = createStarRating(review.management || 0);
			const workLifeStars = createStarRating(review.workLife || 0);
			
			// HTML 템플릿
			const detailHTML = `
				<div class="mb-4">
					<button class="btn btn-secondary" onclick="backToList('company')">
						<i class="fas fa-arrow-left me-2"></i>목록으로
					</button>
				</div>
				<div class="card">
					<div class="card-header d-flex justify-content-between align-items-center">
						<h3 class="mb-0">${"${review.reviewTitle || '제목 없음'}"}</h3>
						<button class="btn btn-sm btn-danger" onclick="reportReview(${"${review.reviewId}"})">
							<i class="fas fa-flag"></i> 신고
						</button>
					</div>
					<div class="card-body">
						<div class="row mb-4">
							<div class="col-md-6">
								<div class="mb-3">
									<h5>작성자</h5>
									<p>${"${review.userEmail || '알 수 없음'}"}</p>
								</div>
								<div>
									<h5>작성일</h5>
									<p>${"${review.reviewDate || '날짜 정보 없음'}"}</p>
								</div>
							</div>
						</div>
						<div class="review-ratings mb-4">
							<h5 class="mb-3">평가 항목</h5>
							<div class="row">
								<div class="col-md-6 mb-3">
									<div class="d-flex justify-content-between align-items-center">
										<span class="fw-bold">진행도:</span>
										<div class="m_review-ratings d-flex justify-content-center">
											${"${advancementStars}"}
										</div>
									</div>
								</div>
								<div class="col-md-6 mb-3">
									<div class="d-flex justify-content-between align-items-center">
										<span class="fw-bold">복지:</span>
										<div class="m_review-ratings d-flex justify-content-center">
											${"${benefitStars}"}
										</div>
									</div>
								</div>
								<div class="col-md-6 mb-3">
									<div class="d-flex justify-content-between align-items-center">
										<span class="fw-bold">회사 문화:</span>
										<div class="m_review-ratings d-flex justify-content-center">
											${"${culturalStars}"}
										</div>
									</div>
								</div>
								<div class="col-md-6 mb-3">
									<div class="d-flex justify-content-between align-items-center">
										<span class="fw-bold">경영:</span>
										<div class="m_review-ratings d-flex justify-content-center">
											${"${managementStars}"}
										</div>
									</div>
								</div>
								<div class="col-md-6 mb-3">
									<div class="d-flex justify-content-between align-items-center">
										<span class="fw-bold">WorkLifeBalance:</span>
										<div class="m_review-ratings d-flex justify-content-center">
											${"${workLifeStars}"}
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="review-content">
							<h5>리뷰 내용</h5>
							<p class="border p-3 rounded">${"${review.content || '내용이 없습니다.'}"}</p>
						</div>
					</div>
				</div>
			`;

			mainContent.innerHTML = detailHTML;
			mainContent.setAttribute('data-original-content', originalContent);
		}

		// 신고 기능 추가
		function reportReview(reviewId) {
			if(confirm('이 리뷰를 신고하시겠습니까?')) {
				// 신고 처리 로직 구현
				console.log('리뷰 신고:', reviewId);
				alert('신고가 접수되었습니다.');
			}
		}

		function fetchInterviewReviews(query = "") {
			showLoading();
			
			const endpoint = "/com/i_reviews";

			fetch(endpoint, {
				method: "POST",
				headers: {
					"Authorization": "Bearer " + localStorage.getItem("accessToken")
				}
			})
				.then(response => response.json())
				.then(data => {
					console.log("면접 리뷰 데이터: ");
					console.log(data.data);
					
					// 전역 변수에 데이터 저장
					interviewReviews = data.data;

					const reviewTableBody = document.querySelector("#interviewReviewTableBody");
					reviewTableBody.innerHTML = ""; // 테이블 초기화

					if (!data || !data.data || data.data.length === 0) {
						// 데이터가 없으면 테이블에 문구 추가
						const row = document.createElement("tr");
						const cell = document.createElement("td");
						cell.colSpan = 6;
						cell.textContent = "등록된 리뷰가 없습니다.";
						cell.className = "text-center";
						row.appendChild(cell);
						reviewTableBody.appendChild(row);
						hideLoading();
						return;
					}

					// 검색어에 맞는 리뷰 필터링
					const filteredInterviewReviews = interviewReviews.filter(review => {
						if (!query) return true;
						const searchFields = [
							review.reviewTitle,
							review.userEmail,
							review.content
						].map(field => (field || "").toLowerCase());
						const lowerQuery = query.toLowerCase();
						return searchFields.some(field => field.includes(lowerQuery));
					});

					// 로딩 메시지 숨기기
					hideLoading();

					if (filteredInterviewReviews.length === 0) {
						// 검색 결과가 없으면 테이블에 문구 추가
						const row = document.createElement("tr");
						const cell = document.createElement("td");
						cell.colSpan = 6; // 테이블의 전체 열을 하도록 설정
						cell.textContent = "검색 결과가 없습니다.";
						cell.className = "text-center";
						row.appendChild(cell);
						reviewTableBody.appendChild(row);
						return;
					}

					// 검색 결과를 테이블에 추가
					filteredInterviewReviews.forEach((review, index) => {
						const row = document.createElement("tr");
						row.setAttribute("data-index", index);
						row.addEventListener("click", function() {
							const reviewIndex = index;
							interviewReviewDetail(reviewIndex);
						});
						// 번호
						const numberCell = document.createElement("td");
						numberCell.textContent = index + 1;
						numberCell.className = "text-center";
						row.appendChild(numberCell);

						// 제목
						const titleCell = document.createElement("td");
						titleCell.textContent = review.reviewTitle;
						titleCell.className = "text-center";
						row.appendChild(titleCell);

						// 균 별점
						const averageCell = document.createElement("td");
						const averageRating = (review.ambience + review.difficulty) / 2;
						const averageStarContainer = document.createElement("div");
						averageStarContainer.className = "m_review-ratings d-flex justify-content-center";
						for (let i = 1; i <= 5; i++) {
							const star = document.createElement("i");
							star.className = "fa fa-star";
							if (i <= Math.floor(averageRating)) {
								star.style.color = "gold";
							} else if (i === Math.ceil(averageRating) && averageRating % 1 >= 0.5) {
								star.className += " fa-star-half-alt";
								star.style.color = "gold";
							} else {
								star.style.color = "#ccc";
							}
							averageStarContainer.appendChild(star);
						}
						averageCell.appendChild(averageStarContainer);
						averageCell.classList.add("text-center");
						row.appendChild(averageCell);

						// 작성자
						const userEmailCell = document.createElement("td");
						userEmailCell.textContent = review.userEmail;
						userEmailCell.className = "text-center";
						row.appendChild(userEmailCell);

						// 작성일자
						const dateCell = document.createElement("td");
						dateCell.textContent = review.reviewDate;
						dateCell.className = "text-center";
						row.appendChild(dateCell);

						reviewTableBody.appendChild(row);
					});
				})
				.catch(error => {
					hideLoading();
					console.error("리뷰를 가져오는 중 오류 발생:", error);
					alert("리뷰를 가져오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
				});
		}

		function interviewReviewDetail(interviewReviewIndex) {
			const review = interviewReviews[interviewReviewIndex];
			const mainContent = document.querySelector("#interviewReviewContent");
			
			const originalContent = mainContent.innerHTML;
			
			// 별점을 미리 생성
			const ambienceStars = createStarRating(review.ambience);
			const difficultyStars = createStarRating(review.difficulty);
			
			mainContent.innerHTML = `
				<div class="mb-4">
					<button class="btn btn-secondary" onclick="backToList('interview')">
						<i class="fas fa-arrow-left me-2"></i>목록으로
					</button>
				</div>
				<div class="card">
					<div class="card-header d-flex justify-content-between align-items-center">
						<h3 class="mb-0">${"${review.reviewTitle}"}</h3>
						<button class="btn btn-sm btn-danger"><i class="fas fa-flag"></i> 신고</button>
					</div>
					<div class="card-body">
						<div class="row mb-4">
							<div class="col-md-6">
								<div class="mb-3">
									<h5>작성자</h5>
									<p>${"${review.userEmail}"}</p>
								</div>
								<div>
									<h5>작성일</h5>
									<p>${"${review.reviewDate}"}</p>
								</div>
							</div>
						</div>
						<div class="review-ratings mb-4">
							<h5 class="mb-3">평가 항목</h5>
							<div class="row">
								<div class="col-md-6 mb-3">
									<div class="d-flex justify-content-between align-items-center">
										<span class="fw-bold">분위기:</span>
										<div class="m_review-ratings d-flex justify-content-center">
											${"${ambienceStars}"}
										</div>
									</div>
								</div>
								<div class="col-md-6 mb-3">
									<div class="d-flex justify-content-between align-items-center">
										<span class="fw-bold">난이도:</span>
										<div class="m_review-ratings d-flex justify-content-center">
											${"${difficultyStars}"}
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="review-content">
							<h5>리뷰 내용</h5>
							<p class="border p-3 rounded">${"${review.content}"}</p>
						</div>
					</div>
				</div>
			`;

			mainContent.setAttribute('data-original-content', originalContent);
		}

		// 목록으로 돌아가는 함수 추가
		function backToList(type) {
			const mainContent = document.querySelector(type === 'company' ? '#companyReviewContent' : '#interviewReviewContent');
			const originalContent = mainContent.getAttribute('data-original-content');
			
			if (originalContent) {
				mainContent.innerHTML = originalContent;
				
				// 리스트 새로고침
				if (type === 'company') {
					fetchCompanyReviews();
				} else {
					fetchInterviewReviews();
				}
			}
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
<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
<div class="container" style="max-width: 1260px;">
	<main class="flex-grow-1">
		<div class="m_review-container">
			<h1 class="my-4 ms-2">리뷰 관리</h1>
			<div class="d-flex">
				<div class="d-flex flex-column me-3">
					<button id="companyTab" class="btn btn-secondary active mb-2">기업 리뷰</button>
					<button id="interviewTab" class="btn btn-secondary">면접 리뷰</button>
				</div>

				<!-- 기업 리뷰 -->
				<div id="companyReviewContent" class="flex-grow-1">
					<div class="d-flex justify-content-between mb-3">
						<h2>기업 리뷰</h2>
					</div>
					<div class="mb-3">
						<div class="d-flex align-items-center">
							<input type="text" placeholder="검색어 입력..." class="form-control me-2" />
							<button class="btn btn-primary"><i class="fas fa-search"></i></button>
						</div>
					</div>
					<table id="reviewTable" class="table">
						<thead>
						<tr class="text-center">
							<th class="col-1">번호</th>
							<th class="col-5">제목</th>
							<th class="col-2">작성자</th>
							<th class="col-2">평균별점</th>
							<th class="col-2">작성일자</th>
						</tr>
						</thead>
						<tbody id="companyReviewTableBody">
							<tr id="companyReviewTemplate" class="d-none">
								<td class="text-center" data-field="number"></td>
								<td class="text-center" data-field="title"></td>
								<td class="text-center" data-field="userEmail"></td>
								<td class="text-center" data-field="averageRating"></td>
								<td class="text-center" data-field="reviewDate"></td>
							</tr>
						</tbody>
					</table>
				</div>

				<!-- 면접 리뷰 -->
				<div id="interviewReviewContent" class="flex-grow-1 d-none">
					<div class="d-flex justify-content-between mb-3">
						<h2>면접 리뷰</h2>
					</div>
					<div class="mb-3">
						<div class="d-flex align-items-center">
							<input type="text" placeholder="검색어 입력..." class="form-control me-2" />
							<button class="btn btn-primary"><i class="fas fa-search"></i></button>
						</div>
					</div>
					<table id="interviewReviewTable" class="table">
						<thead>
							<tr class="text-center">
								<th class="col-1">번호</th>
								<th class="col-4">제목</th>
								<th class="col-2">평균별점</th>
								<th class="col-1">작성자</th>
								<th class="col-2">작성일자</th>
							</tr>
						</thead>
						<tbody id="interviewReviewTableBody">
						<!-- 데이터가 스크립트로 추가될 곳 -->
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
