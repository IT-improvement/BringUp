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


	<style>
		.m_review-container {
			max-width: 1260px;
			margin: 0 auto;
		}
		.m_review-header {
			padding: 20px;
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		.m_review-header h1 {
			font-size: 32px;
			font-weight: bold;
		}
		.search-container {
			position: relative;
		}
		.search-container input[type="text"] {
			padding: 10px;
			font-size: 14px;
			border-radius: 4px;
			border: 1px solid #ddd;
			width: 250px;
		}
		.search-container button {
			position: absolute;
			right: 0;
			padding: 10px;
			background-color: #007bff;
			color: white;
			border: none;
			border-radius: 0 4px 4px 0;
			cursor: pointer;
		}
		.search-container button:hover {
			background-color: #0056b3;
		}
		table {
			width: 100%;
			border-collapse: collapse;
			margin-top: 20px;
			text-align: left;
		}
		th, td {
			padding: 15px;
			border-bottom: 1px solid #ddd;
		}
		th {
			background-color: #f4f4f4;
			font-weight: bold;
		}
		td {
			vertical-align: middle;
		}
		.m_review-ratings {
			display: flex;
			justify-content: start;
		}
		.stars i {
			margin-right: 5px;
		}
		.details-button, .apply-button {
			background-color: #007bff;
			color: white;
			padding: 5px 15px;
			border: none;
			border-radius: 4px;
			cursor: pointer;
			transition: background-color 0.3s ease;
			margin-left: 10px;
		}
		.details-button:hover, .apply-button:hover {
			background-color: #0056b3;
		}
	</style>
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			document.querySelectorAll('.details-button').forEach(button => {
				button.addEventListener('click', function() {

					const accessToken = localStorage.getItem('accessToken'); // 로컬 스토리지에서 토큰 가져오기
					console.log('토큰:', accessToken);
					if (accessToken) {
						// 서버에 GET 요청을 보냄
						fetch(`/member/m_reviewDetail`, {
							method: 'GET',
							headers: {
								'Authorization': `Bearer ` + accessToken, // Bearer 토큰 추가
								'Content-Type': 'application/json'  // 헤더에 JSON 형식 명시
							}
						})
								.then(response => {
									if (response.ok) {
										// 요청 성공 시 상세 페이지로 이동
										window.location.href = `/member/m_reviewDetail`;
									} else {
										alert('리뷰 페이지로 이동하는 중 오류가 발생했습니다.');
									}
								})
								.catch(error => {
									console.error('오류 발생:', error);
									alert('서버 요청 중 문제가 발생했습니다.');
								});
					} else {
						alert("로그인 토큰이 없습니다. 로그인 해주세요.");
					}
				});
			});
		});
	</script>
</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
	<div class="container" style="max-width: 1260px;">
		<main class="flex-grow-1">

			<div class="m_review-container ">
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
					<!-- 리뷰 항목 1 -->
					<tr>
						<td>회사1</td>
						<td>사용자1</td>
						<td>좋은 회사입니다</td>
						<td>
							<div class="m_review-ratings">
					<span class="stars">
						<i class="fa fa-star" style="color: gold;"></i>
						<i class="fa fa-star" style="color: gold;"></i>
						<i class="fa fa-star" style="color: gold;"></i>
						<i class="fa fa-star" style="color: gold;"></i>
						<i class="fa fa-star" style="color: #ccc;"></i>
					</span>
							</div>
						</td>
						<td>2024-10-03</td>
						<td>
							<button class="details-button" id="reviewId">자세히 보기</button>
						</td>
					</tr>

					<!-- 리뷰 항목 2 -->
					<tr>
						<td>회사2</td>
						<td>사용자2</td>
						<td>훌륭한 복지와 환경</td>
						<td>
							<div class="m_review-ratings">
					<span class="stars">
						<i class="fa fa-star" style="color: gold;"></i>
						<i class="fa fa-star" style="color: gold;"></i>
						<i class="fa fa-star" style="color: gold;"></i>
						<i class="fa fa-star" style="color: #ccc;"></i>
						<i class="fa fa-star" style="color: #ccc;"></i>
					</span>
							</div>
						</td>
						<td>2024-10-02</td>
						<td>
							<button class="details-button">자세히 보기</button>
						</td>
					</tr>
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
