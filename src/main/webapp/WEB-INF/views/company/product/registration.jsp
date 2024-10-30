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

	<!-- daterangepicker CSS -->
	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

	<!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- 벤더 -->
    <script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
    <script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

    <!-- 테마 JS -->
    <script src="/resources/script/common/function/functions.js"></script>

	<!-- jQuery -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<!-- 날짜 선택 플러그인 -->
	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
	<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>

    <script src="/resources/script/company/product/registration.js"></script>
    <script src="https://js.bootpay.co.kr/bootpay-5.0.1.min.js" type="application/javascript"></script>
    <script src="/resources/script/common/Pay/payment.js"></script>

    <style>
        .list-group-item:hover {
            background-color: #f0f0f0; /* 마우스 오버 시 배경색 변경 */
            cursor: pointer; /* 마우스 포인터 변경 */
        }
        
        .flatpickr-day.startRange,
        .flatpickr-day.endRange {
            background: #007bff;
            color: white;
        }

        .flatpickr-day.inRange {
            background: #cce5ff; /* 범위 내 날짜의 배경색 */
            color: black; /* 범위 내 날짜의 글자색 */
        }

        .flatpickr-day.startRange {
            border-top-left-radius: 50%;
            border-bottom-left-radius: 50%;
        }

        .flatpickr-day.endRange {
            border-top-right-radius: 50%;
            border-bottom-right-radius: 50%;
        }
    </style>
</head>

<body class="d-flex flex-column min-vh-100">
	<%
		String productName = request.getParameter("productName");
	%>
	<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="container mt-4 mx-auto">
        <h3 class="mb-4">
            <%= productName %> 상품 신청
        </h3>
        <div class="d-flex flex-row justify-content-between gap-4">
            <div class="flex-grow-1" style="flex-basis: 70%;">
                <form id="advertisementForm" enctype="multipart/form-data">
                    <div id="advertisementInput" class="mb-3">
                        <label for="selectedAdvertisement" class="form-label">공고 선택</label>
                        <input type="text" class="form-control" placeholder="내 공고 선택" id="selectedAdvertisement" readonly>
                        <input type="hidden" id="selectedRecruitmentIndex" name="recruitmentIndex">
                    </div>
                    <% if (productName != null && !productName.equals("어나운스")) { %>
                    <div class="mb-2 d-flex align-items-center justify-content-between">
                        <label for="imageUpload" class="form-label mb-0 me-2">이미지 업로드</label>
                        <% if (productName.equals("프리미엄")) { %>
                        <span class="text-muted" style="font-size: 0.9em; white-space: nowrap;">(프리미엄 : 875x500px)</span>
                        <% } else if (productName.equals("메인")) { %>
                        <span class="text-muted" style="font-size: 0.9em; white-space: nowrap;">(메인 : 1228x320px)</span>
                        <% } else if (productName.equals("배너")) { %>
                        <span class="text-muted" style="font-size: 0.9em; white-space: nowrap;">(배너 : 1228x80px)</span>
                        <% } %>
                    </div>
                    <input type="file" class="form-control mb-3" id="imageUpload" name="image" accept="image/*">
                    <% } %>
                    <%
                        if (productName != null && productName.equals("프리미엄")) {
                    %>
                    <div class="mb-3">
                        <label for="premiumDateRange" class="form-label">프리미엄 광고 날짜 선택</label>
                        <input type="text" class="form-control" id="premiumDateRange" name="premiumDateRange" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="productSelect" class="form-label">프리미엄 광고 시간 선택</label>
                        <select class="form-select" id="productSelect" name="displayTime">
                            <option value="">시간 선택</option>
                            <option value="22:00 ~ 01:00">22:00 ~ 01:00</option>
                            <option value="01:00 ~ 04:00">01:00 ~ 04:00</option>
                            <option value="04:00 ~ 07:00">04:00 ~ 07:00</option>
                            <option value="07:00 ~ 10:00">07:00 ~ 10:00</option>
                            <option value="10:00 ~ 13:00">10:00 ~ 13:00</option>
                            <option value="13:00 ~ 16:00">13:00 ~ 16:00</option>
                            <option value="16:00 ~ 19:00">16:00 ~ 19:00</option>
                            <option value="19:00 ~ 22:00">19:00 ~ 22:00</option>
                        </select>
                    </div>
                    
                    <%
                        } else if (productName != null && productName.equals("메인")) {
                    %>
                    <div class="mb-3">
                        <label for="productSelect" class="form-label">메인 광고 기간 선택</label>
                        <select class="form-select" id="productSelect" name="durationDays">
                            <option value="">메인 광고 기간 선택</option>
                            <option value="1">1일</option>
                            <option value="3">3일</option>
                            <option value="7">일주일</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="mainDateRange" class="form-label">메인 광고 날짜 선택</label>
                        <input type="text" class="form-control" id="mainDateRange" name="mainDateRange" readonly>
                    </div>
                    <%
                        } else if (productName != null && productName.equals("배너")) {
                    %>
                    <div class="mb-3">
                        <label for="bannerProductSelect" class="form-label">배너 광고 기간 선택</label>
                        <select class="form-select" id="bannerProductSelect" name="durationDays">
                            <option value="">배너 광고 기간 선택</option>
                            <option value="1">1일</option>
                            <option value="3">3일</option>
                            <option value="7">일주일</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="bannerDateRange" class="form-label">배너 광고 날짜 선택</label>
                        <input type="text" class="form-control" id="bannerDateRange" name="bannerDateRange" readonly>
                    </div>
                    <%
                        } else {
                    %>
                    <div class="mb-3">
                        <label for="startDate" class="form-label">일반 광고 시작 날짜</label>
                        <input type="date" class="form-control" id="announceStartDate" name="announceStartDate">
                    </div>
                    <div class="mb-3">
                        <label for="productSelect" class="form-label">일반 광고 기간 선택</label>
                        <select class="form-select" id="productSelect" name="durationMonths">
                            <option value="">일반 광고 기간 선택</option>
                            <option value="1">1개월</option>
                            <option value="3">3개월</option>
                            <option value="6">6개월</option>
                            <option value="12">1년</option>
                        </select>
                    </div>		
                    <%
                        }
                    %>
                </form>
                <div class="mt-4">
                    
                </div>
            </div>
            <div class="flex-grow-0" style="flex-basis: 30%;">
                <div class="card border-1 border-secondary rounded-3">
                    <div class="card-body">
                        <h5 class="card-title">상품 정보</h5>
                        <p class="card-text">상품 이름: <%= productName %> 광고</p>
                        <p id="selectedAd" class="card-text">선택된 공고: </p>
                        <% if (productName.equals("프리미엄")) { %>
                            <p id="adDate" class="card-text">광고 날짜: </p>
                            <p id="adType" class="card-text">광고 유형: </p>
                            <p id="displayTime" class="card-text">시간: </p>
                        <% } else if (productName.equals("메인")) { %>
                            <p id="duration" class="card-text">광고 기간: </p>
                            <p id="adDate" class="card-text">광고 날짜: </p>
                        <% } else if (productName.equals("배너")) { %>
                            <p id="duration" class="card-text">광고 기간: </p>
                            <p id="adDate" class="card-text">광고 날짜: </p>
                        <% } else if (productName.equals("어나운스")) { %>
                            <p id="startDate" class="card-text">광고 시작일: </p>
                            <p id="duration" class="card-text">광고 기간: </p>
                        <% } %>
                        <p id="paymentAmount" class="card-text">결제 금액: </p>
                        <button type="button" class="btn btn-primary w-100 mt-3" id="paymentButton">신청하기</button>
                    </div>
                </div>
            </div>
        </div>
    </main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

    <!-- 공고 목록 모달 -->
    <div class="modal fade" id="advertisementModal" tabindex="-1" aria-labelledby="advertisementModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="advertisementModalLabel">공고 목록</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="text" class="form-control mb-3" id="searchAdvertisement" placeholder="검색어를 입력하세요">
                    <ul class="list-group" id="advertisementList">
                        <!-- 공 트가 여기에 추됩니다 -->
                    </ul>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
