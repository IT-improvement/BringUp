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

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- 메인 JS -->
    <script src="/resources/script/company/product/registration.js"></script>

</head>

<body class="d-flex flex-column min-vh-100">
	
	<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="container mt-4 mx-auto">
		<h3><%= request.getParameter("productName") %> 상품 신청</h3>
		<form id="advertisementForm" enctype="multipart/form-data">
			<div class="mb-3">
				<label for="selectedAdvertisement" class="form-label">공고 선택</label>
				<input type="text" class="form-control" placeholder="내 공고 선택" id="selectedAdvertisement" readonly>
				<input type="hidden" id="selectedRecruitmentIndex" name="recruitmentIndex">
			</div>
			<div class="mb-3">
				<label for="imageUpload" class="form-label">이미지 업로드</label>
				<input type="file" class="form-control" id="imageUpload" name="image" accept="image/*">
			</div>
			<div class="mb-3">
				<label for="productSelect" class="form-label">상품 선택</label>
				<select class="form-select" id="productSelect" name="displayTime">
					<option value="">상품 목록</option>
					<option value="7">7일</option>
					<option value="15">15일</option>
					<option value="30">30일</option>
				</select>
			</div>
			<div class="d-flex justify-content-between">
				<button type="button" class="btn btn-secondary" onclick="goBack()">돌아가기</button>
				<button type="button" class="btn btn-primary" onclick="submitForm()">신청하기</button>
			</div>
		</form>
    </main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

    <!-- 공고 목록 모달 -->
    <div class="modal fade" id="advertisementModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">공고 목록</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" style="height: 350px;"> <!-- 모달 본문의 높이를 고정 -->
                    <div class="mb-3 position-relative">
                        <i class="bi bi-search position-absolute top-50 start-0 translate-middle-y ms-3 fs-5 mx-3"></i>
                        <input type="text" class="form-control ps-5" id="searchAdvertisement" placeholder="검색어를 입력하세요">
                    </div>
					<h6>공고 리스트</h6>
                    <div id="advertisementList" class="list-group overflow-auto" >
                        <!-- 동적으로 리스트가 생성됩니다 -->
                    </div>
                    <nav aria-label="Page navigation example" class="mt-2">
                        <ul class="pagination justify-content-center" id="pagination">
                            <!-- 동적으로 페이징이 생성됩니다 -->
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</body>
</html>