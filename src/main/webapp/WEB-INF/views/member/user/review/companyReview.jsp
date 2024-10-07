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
		});

		function fetchReviews() {
			fetch("/member/m_reviews") // 리뷰를 가져오는 API 엔드포인트
					.then(response => response.json())
					.then(data => {
						const reviews = data.data; // 데이터에서 리뷰 목록 추출
						const reviewTableBody = document.querySelector("tbody");

						reviews.forEach(review => {
							const row = document.createElement("tr");

							// 회사 이름
							const companyNameCell = document.createElement("td");
							companyNameCell.textContent = review.companyName;
							row.appendChild(companyNameCell);

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
							detailButton.href = "/member/m_reviewDetail?reviewId=" + review.companyReviewIndex; // 각 리뷰에 맞는 상세보기 링크
							detailButtonCell.appendChild(detailButton);
							row.appendChild(detailButtonCell);

							// 테이블에 추가
							reviewTableBody.appendChild(row);
						});
					})
					.catch(error => {
						console.error("리뷰를 가져오는 중 오류 발생:", error);
					});
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
				<h1>전체 기업 리뷰</h1>
				<div class="search-container">
					<input type="text" placeholder="검색어 입력..." />
					<button><i class="fas fa-search"></i></button>
				</div>
			</div>

			<table>
				<thead>
				<tr>
					<th>회사 이름</th>
					<th>사용자</th>
					<th>제목</th>
					<th>평균별점</th>
					<th>작성일자</th>
					<th>자세히 보기</th>
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
