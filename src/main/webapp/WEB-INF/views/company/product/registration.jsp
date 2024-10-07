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
	<!-- 날짜 선택 플러그인 -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

    <!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- 벤더 -->
    <script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
    <script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

    <!-- 테마 JS -->
    <script src="/resources/script/common/function/functions.js"></script>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<!-- 날짜 선택 플러그인 -->
	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>

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
			<%
				String productName = request.getParameter("productName");
				if (productName.equals("프리미엄")) {
			%>
			<div class="mb-3">
				<label for="premiumDateRange" class="form-label">광고 날짜 선택</label>
				<input type="text" class="form-control" id="premiumDateRange" name="premiumDateRange" readonly>
			</div>
			<div class="mb-3">
				<label for="productSelect" class="form-label">광고 노출 시간대 선택</label>
				<select class="form-select" id="productSelect" name="displayTime">
					<option value="">광고 노출 시간대 선택</option>
					<option value="22:00~01:00">22:00~01:00</option>
					<option value="01:00~04:00">01:00~04:00</option>
					<option value="04:00~07:00">04:00~07:00</option>
					<option value="07:00~10:00">07:00~10:00</option>
					<option value="10:00~13:00">10:00~13:00</option>
					<option value="13:00~16:00">13:00~16:00</option>
					<option value="16:00~19:00">16:00~19:00</option>
					<option value="19:00~22:00">19:00~22:00</option>
				</select>
			</div>
			
			<%
				} else if (productName.equals("메인")) {
			%>
			<div class="mb-3">
				<label for="productSelect" class="form-label">광고 기간 선택</label>
				<select class="form-select" id="productSelect" name="durationDays">
					<option value="">광고 기간 선택</option>
					<option value="1">1일</option>
					<option value="3">3일</option>
					<option value="7">일주일</option>
				</select>
			</div>
			<div class="mb-3">
				<label for="mainDateRange" class="form-label">광고 날짜 선택</label>
				<input type="text" class="form-control" id="mainDateRange" name="mainDateRange" readonly>
			</div>
			<%
				} else if (productName.equals("배너")){
			%>
			<div class="mb-3">
				<label for="bannerProductSelect" class="form-label">광고 기간 선택</label>
				<select class="form-select" id="bannerProductSelect" name="durationDays">
					<option value="">광고 기간 선택</option>
					<option value="1">1일</option>
					<option value="3">3일</option>
					<option value="7">일주일</option>
				</select>
			</div>
			<div class="mb-3">
				<label for="bannerDateRange" class="form-label">광고 날짜 선택</label>
				<input type="text" class="form-control" id="bannerDateRange" name="bannerDateRange" readonly>
			</div>
			<%
				}else{
			%>
			<div class="mb-3">
				<label for="startDate" class="form-label">광고 시작 날짜</label>
				<input type="date" class="form-control" id="announceStartDate" name="announceStartDate">
			</div>
			<div class="mb-3">
				<label for="productSelect" class="form-label">광고 기간 선택</label>
				<select class="form-select" id="productSelect" name="durationMonths">
					<option value="">광고 기간 선택</option>
					<option value="1">1개월</option>
					<option value="3">3개월</option>
					<option value="6">6개월</option>
					<option value="12">1년</option>
				</select>
			</div>		
			<%
				}
			%>

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