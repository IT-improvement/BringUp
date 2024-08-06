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

</head>
<body>
<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

<section class="py-4">
	<div class="container">
		<div class="row g-4">
			<div class="col-12">
				<div class="row g-4">
					<div class="col-sm-6 col-lg-3">
						<div class="card card-body border p-3">
							<div class="d-flex align-items-center">
								<div class="icon-xl fs-1 bg-dark bg-opacity-10 rounded-3 text-dark">
									<i class="bi bi-people-fill"></i>
								</div>
								<div class="ms-3">
									<h3>10명</h3>
									<h6 class="mb-0">지원자</h6>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-lg-3">
						<div class="card card-body border p-3">
							<div class="d-flex align-items-center">
								<div class="icon-xl fs-1 bg-primary bg-opacity-10 rounded-3 text-primary">
									<i class="bi bi-file-earmark-text-fill"></i>
								</div>
								<div class="ms-3">
									<h3>3개</h3>
									<h6 class="mb-0">공고</h6>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-lg-3">
						<div class="card card-body border p-3">
							<div class="d-flex align-items-center">
								<div class="icon-xl fs-1 bg-danger bg-opacity-10 rounded-3 text-danger">
									<i class="bi bi-suit-heart-fill"></i>
								</div>
								<div class="ms-3">
									<h3>100명</h3>
									<h6 class="mb-0">관심/스크랩</h6>
								</div>
							</div>
						</div>
					</div>

					<div class="col-sm-6 col-lg-3">
						<div class="card card-body border p-3">
							<div class="d-flex align-items-center">
								<div class="icon-xl fs-1 bg-success bg-opacity-10 rounded-3 text-success">
									<i class="bi bi-currency-dollar"></i>
								</div>
								<div class="ms-3">
									<h3>15만원</h3>
									<h6 class="mb-0">이번달 비용</h6>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-12">
				<div class="card border bg-transparent rounded-3">
					<div class="card-header bg-transparent border-bottom p-3">
						<div class="d-sm-flex justify-content-between align-items-center">
							<h5 class="mb-2 mb-sm-0">내 공고 <span id="jobCount" class="badge bg-primary bg-opacity-10 text-primary"></span></h5>
							<a href="/company/job_posting/registration.html" class="btn btn-sm btn-primary mb-0">Add New</a>
						</div>
					</div>
					<div class="card-body">
						<div class="row g-3 align-items-center justify-content-between mb-3">
							<div class="col-md-8">
								<form class="rounded position-relative" onsubmit="searchJobPostings(event)">
									<input id="searchInput" class="form-control pe-5 bg-transparent" type="search" placeholder="Search" aria-label="Search">
									<button class="btn bg-transparent border-0 px-2 py-0 position-absolute top-50 end-0 translate-middle-y" type="submit"><i class="fas fa-search fs-6 "></i></button>
								</form>
							</div>
							<div class="col-md-3">
								<form>
									<select id="categorySelect" class="form-select z-index-9 bg-transparent" aria-label=".form-select-sm">
										<option value="전체">전체</option>
										<option value="공고 제목">공고 제목</option>
										<option value="공고 타입">공고 타입</option>
										<option value="모집 분야">모집 분야</option>
										<option value="상태">상태</option>
									</select>
								</form>
							</div>
						</div>
						<div class="table-responsive border-0">
							<table class="table align-middle p-4 mb-0 table-hover table-shrink">
								<thead class="table-dark">
								<tr>
									<th scope="col" class="border-0 rounded-start">공고 제목</th>
									<th scope="col" class="border-0">공고 타입</th>
									<th scope="col" class="border-0">게시일</th>
									<th scope="col" class="border-0">모집 분야</th>
									<th scope="col" class="border-0">상태</th>
									<th scope="col" class="border-0 rounded-end">비고</th>
								</tr>
								</thead>
								<tbody class="border-top-0">
								</tbody>
							</table>
						</div>
						<div class="d-sm-flex justify-content-sm-between align-items-sm-center mt-4 mt-sm-3">
							<p id="totalEntries" class="mb-sm-0 text-center text-sm-start">총${jobPostings.length}</p>
							<nav class="mb-sm-0 d-flex justify-content-center" aria-label="navigation">
								<ul class="pagination pagination-sm pagination-bordered mb-0">
									<li class="page-item" id="prevPage">
										<a class="page-link" href="#" tabindex="-1" aria-disabled="true" onclick="changePage(currentPage - 1)">Prev</a>
									</li>
									<span id="paginationNumbers"></span>
									<li class="page-item" id="nextPage">
										<a class="page-link" href="#" onclick="changePage(currentPage + 1)">Next</a>
									</li>
								</ul>
							</nav>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>


<jsp:include page="/WEB-INF/views/company/footer/company_footer.jsp" flush="true" />

<!-- 맨 위로 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

<!-- =======================
JS 라이브러리, 플러그인 및 사용자 정의 스크립트 -->

<!-- Bootstrap JS -->
<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- 벤더 -->
<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

<!-- 테마 JS -->
<script src="/resources/script/common/function/functions.js"></script>

<!-- 메인 JS -->
<script src="/resources/script/company/main.js"></script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</body>
</html>