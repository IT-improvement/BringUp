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
			fetchReviews(); // 페이지 로드 시 회사 리뷰 데이터를 가져옴

			// 검색 기능 이벤트 리스너 추가
			const searchButton = document.querySelector(".search-container button");
			const searchInput = document.querySelector(".search-container input");
			searchButton.addEventListener("click", () => {
				const query = searchInput.value.trim();
				fetchReviews(query); // 검색어가 입력된 경우 그에 맞는 리뷰만 가져옴
			});

			// 엔터 키를 눌러서 검색 가능하도록 추가
			searchInput.addEventListener("keypress", (e) => {
				if (e.key === "Enter") {
					const query = searchInput.value.trim();
					fetchReviews(query); // 검색어에 맞는 리뷰를 가져옴
				}
			});

			const tableHeaders = document.querySelectorAll("th");
			tableHeaders.forEach((header, index) => {
				header.addEventListener("click", () => {
					sortTable(index);
				});
			});
		});

		function fetchReviews(query = "") {
			// 로딩 메시지 표시
			showLoading();
			const companyId = "${companyId}";
			document.getElementById("companyName").textContent = "면접 리뷰 - " + companyId;
			fetch("/member/interview/company/" + companyId) // 리뷰를 가져오는 API 엔드���인트
					.then(response => response.json())
					.then(data => {
						console.log(data);
						const reviews = data.data; // 데이터에서 리뷰 목록 추출
						if (!reviews || reviews.length === 0) {
							throw new Error("리뷰 데이터가 없습니다.");
						}
						const reviewTableBody = document.querySelector("tbody");
						reviewTableBody.innerHTML = ""; // 테이블 초기화

						// 검색어에 맞는 리뷰 필터링
						const filteredReviews = reviews.filter(review => {
							const companyName = review.companyName ? review.companyName.toLowerCase() : "";
							const title = review.interviewReviewTitle ? review.interviewReviewTitle.toLowerCase() : "";
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
							row.setAttribute("data-index", review.interviewReviewIndex);
							

							// 번호
							const indexCell = document.createElement("td");
							indexCell.textContent = review.interviewReviewIndex;
							row.appendChild(indexCell);

							// 제목
							const titleCell = document.createElement("td");
							titleCell.textContent = review.interviewReviewTitle;
							row.appendChild(titleCell);

							// 면접 분위기
							const ambienceCell = document.createElement("td");
							ambienceCell.textContent = review.ambience;
							row.appendChild(ambienceCell);

							// 면접 난이도
							const difficultyCell = document.createElement("td");
							difficultyCell.textContent = review.difficulty;
							row.appendChild(difficultyCell);

							// 사용자 이메일
							const userEmailCell = document.createElement("td");
							userEmailCell.textContent = review.userEmail;
							row.appendChild(userEmailCell);

							// 작성일자
							const dateCell = document.createElement("td");
							dateCell.textContent = review.interviewReviewDate;
							row.appendChild(dateCell);

							// 행 클릭 시 상세 정보 표시
							row.addEventListener("click", () => {
								showReviewDetails(review);
							});

							// 테이블에 추가
							reviewTableBody.appendChild(row);
						});
						
					})
					.catch(error => {
						hideLoading(); // 로딩 창 숨기기
						console.error("리뷰를 가져오는 중 오류 발생:", error);
						const reviewTableBody = document.querySelector("tbody");
						reviewTableBody.innerHTML = "<tr><td colspan='6'>등록된 리뷰가 없습니다.</td></tr>";
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

		function sortTable(columnIndex) {
			const table = document.querySelector("table");
			const tbody = table.querySelector("tbody");
			const rows = Array.from(tbody.rows);
			const isAscending = table.getAttribute("data-sort-order") === "asc";

			rows.sort((a, b) => {
				const cellA = a.cells[columnIndex].textContent.trim();
				const cellB = b.cells[columnIndex].textContent.trim();

				if (!isNaN(cellA) && !isNaN(cellB)) {
					return isAscending ? cellA - cellB : cellB - cellA;
				} else {
					return isAscending ? cellA.localeCompare(cellB) : cellB.localeCompare(cellA);
				}
			});

			table.setAttribute("data-sort-order", isAscending ? "desc" : "asc");
			tbody.append(...rows);
		}

		function deleteReview(reviewIndex) {
			console.log(reviewIndex);
		}

		function showReviewDetails(review) {
			const existingDetailRow = document.querySelector(`tr[data-detail-index="${review.interviewReviewIndex}"]`);

			if (existingDetailRow) {
				// 이미 열려 있는 경우 닫기
				existingDetailRow.remove();
				return;
			}

			// 기존에 열려 있는 세부 정보 행을 모두 제거
			const existingDetailRows = document.querySelectorAll("tr[data-detail-index]");
			existingDetailRows.forEach(row => row.remove());

			const row = document.querySelector(`tr[data-index="${review.interviewReviewIndex}"]`);
			if (!row) {
				console.error("해당 리뷰의 행을 찾을 수 없습니다. 리뷰 인덱스: " + review.interviewReviewIndex);
				return;
			}

			const detailRow = document.createElement("tr");
			detailRow.setAttribute("data-detail-index", review.interviewReviewIndex);
			const detailCell = document.createElement("td");
			detailCell.setAttribute("colspan", 6); // 테이블의 전체 열 수에 맞춰 조정
			detailCell.innerHTML = `
				<div class="card" style="margin: 10px 0; padding: 15px; border: 1px solid #ddd; border-radius: 8px;">
					<div class="card-body">
						<h5 class="card-title">리뷰 내용</h5>
						<p>${review.interviewReviewContent}</p>
					</div>
				</div>
			`;
			detailRow.appendChild(detailCell);

			row.insertAdjacentElement("afterend", detailRow);
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
			<div class="m_review-header">
				<a href="/member/company/detail/${companyId}" id="companyName" class="fs-3 fw-bold text-black text-decoration-none position-relative d-inline-block">
					<span class="position-absolute start-0 bottom-0 w-100 h-100 border-bottom border-3 border-black transition-all"></span>
				</a>
				<!-- 리뷰 작성 버튼 -->

				<div class="search-container">
					<input type="text" placeholder="검색어 입력..." />
					<button><i class="fas fa-search"></i></button>
				</div>

			</div>
			<div class="review-buttons">
				<a href="/member/createInterviewReview" class="btn btn-primary">리뷰 작성</a>
			</div>

			<table>
				<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>면접 분위기</th>
					<th>면접 난이도</th>
					<th>사용자</th>
					<th>작성일자</th>
				</tr>
				</thead>
				<tbody>
				<!-- 데이터가 스크립트로 추가될 곳 -->
				</tbody>
			</table>
		</div>
	</main>
</div>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>

</html>
