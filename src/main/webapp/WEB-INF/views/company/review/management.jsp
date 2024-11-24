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
					const reviews = data.data; // 데이터에서 리뷰 목록 추출
					const reviewTableBody = document.querySelector("#reviewTable tbody");
					reviewTableBody.innerHTML = ""; // 테이블 초기화

					// 검색어에 맞는 리뷰 필터링
					const filteredReviews = reviews.filter(review => {
						const companyName = review.companyName ? review.companyName.toLowerCase() : "";
						const title = review.companyReviewTitle ? review.companyReviewTitle.toLowerCase() : "";
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
					filteredReviews.forEach((review, index) => {
						const row = document.createElement("tr");
						row.setAttribute("data-index", index);
						row.addEventListener("click", () => companyReviewDetail(index));

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

						// 작성자
						const userEmailCell = document.createElement("td");
						userEmailCell.textContent = review.userEmail;
						userEmailCell.className = "text-center";
						row.appendChild(userEmailCell);

						// 평균 별점
						const averageRating = (review.advancement + review.benefit + review.companyCulture + review.management + review.workLife) / 5;
						const ratingCell = document.createElement("td");
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
						ratingCell.appendChild(starContainer);
						row.appendChild(ratingCell);

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
				});
		}

		function companyReviewDetail(reviewIndex, tabType) {
			const existingDetailRow = document.querySelector(`tr[data-detail-index="${"${reviewIndex}"}"]`);

			if (existingDetailRow) {
				// 이미 열려 있는 경우 닫기
				existingDetailRow.remove();
				return;
			}

			// 기존에 열려 있는 세부 정보 행을 모두 제거
			const existingDetailRows = document.querySelectorAll("tr[data-detail-index]");
			existingDetailRows.forEach(row => row.remove());

			const row = document.querySelector(`tr[data-index="${"${reviewIndex}"}"]`);
			if (!row) {
				console.error("해당 리뷰의 행을 찾을 수 없습니다. 리뷰 인덱스: " + reviewIndex);
				return;
			}

			const detailRow = document.createElement("tr");
			detailRow.setAttribute("data-detail-index", reviewIndex);
			const detailCell = document.createElement("td");
			detailCell.setAttribute("colspan", 6); // 테이블의 전체 열 수에 맞춰 조정

			let cardContent = "";
			switch (tabType) {
				case "overview":
					cardContent = `<h5 class="card-title">리뷰 개요</h5><p id="reviewOverview-${"${reviewIndex}"}"></p>`;
					break;
				case "details":
					cardContent = `<h5 class="card-title">리뷰 세부사항</h5><p id="reviewDetails-${"${reviewIndex}"}"></p>`;
					break;
				case "comments":
					cardContent = `<h5 class="card-title">리뷰 댓글</h5><p id="reviewComments-${"${reviewIndex}"}"></p>`;
					break;
				default:
					cardContent = `<h5 class="card-title">리뷰 내용</h5><p id="reviewContent-${"${reviewIndex}"}"></p>`;
			}

			detailCell.innerHTML = `
				<div class="card" style="margin: 10px 0; padding: 15px; border: 1px solid #ddd; border-radius: 8px;">
					<div class="card-body">
						${cardContent}
					</div>
				</div>
			`;
			detailRow.appendChild(detailCell);

			row.insertAdjacentElement("afterend", detailRow);
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
					const reviewTableBody = document.querySelector("#interviewReviewTable tbody");
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

					const reviews = data.data; // 데이터에서 리뷰 목록 추출

					// 검색어에 맞는 리뷰 필터링
					const filteredReviews = reviews.filter(review => {
						const companyName = review.companyName ? review.companyName.toLowerCase() : "";
						const title = review.reviewTitle ? review.reviewTitle.toLowerCase() : "";
						const lowerQuery = query.toLowerCase();
						return companyName.includes(lowerQuery) || title.includes(lowerQuery);
					});

					// 로딩 메시지 숨기기
					hideLoading();

					if (filteredReviews.length === 0) {
						// 검색 결과가 없으면 테이블에 문구 추가
						const row = document.createElement("tr");
						const cell = document.createElement("td");
						cell.colSpan = 6; // 테이블의 전체 열을 차지하도록 설정
						cell.textContent = "검색 결과가 없습니다.";
						cell.className = "text-center";
						row.appendChild(cell);
						reviewTableBody.appendChild(row);
						return;
					}

					// 검색 결과를 테이블에 추가
					filteredReviews.forEach((review, index) => {
						const row = document.createElement("tr");

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

						// 평균 별점
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
						<tbody>
						<!-- 데이터가 스크립트로 추가될 곳 -->
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
