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

    <!-- 메인 JS -->
    <script src="/resources/script/company/jobposting/management.js"></script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

	<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="flex-grow-1 py-4">
		<div class="container" style="max-width: 1260px;">
            <div class="row g-4">
                <!-- 검색 섹션 수정 -->
                <div class="col-12 mb-4">
                    <div class="card border">
                        <div class="card-header border-bottom">
                            <h5 class="card-header-title">공고 검색</h5>
                        </div>
                        <div class="card-body">
                            <form id="search-form" class="row g-3">
                                <div class="col-md-4">
                                    <label for="search-category" class="form-label">카테고리</label>
                                    <select class="form-select" id="search-category">
                                        <option value="jobPostingId">공고 ID</option>
                                        <option value="managerEmail">담당자 Email</option>
                                        <option value="employmentType">채용형태</option>
                                        <option value="jobGroup">직군</option>
                                        <option value="techStack">기술스택</option>
                                        <option value="startDate">시작날짜</option>
                                        <option value="duration">기간</option>
                                        <option value="status">상태값</option>
                                        <option value="adGrade">광고등급</option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label for="search-keyword" class="form-label">검색어</label>
                                    <input type="text" class="form-control" id="search-keyword">
                                </div>
                                <div class="col-md-2 d-flex align-items-end">
                                    <button type="submit" class="btn btn-primary w-100">검색</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                
                <!-- 기존 공고 목록 -->
                <div class="col-12">
                    <div class="card border">
                        <div class="card-header border-bottom">
                            <h5 class="card-header-title">공고 목록</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive border-0">
                                <table class="table table-hover align-middle p-4 mb-0 table-shrink">
                                    <thead class="table-light">
                                        <tr>
                                            <th scope="col" class="border-0 rounded-start">공고 ID</th>
                                            <th scope="col" class="border-0">담당자 Email</th>
                                            <th scope="col" class="border-0">채용형태</th>
                                            <th scope="col" class="border-0">직군</th>
                                            <th scope="col" class="border-0">기술스택</th>
                                            <th scope="col" class="border-0">시작날짜</th>
                                            <th scope="col" class="border-0">기간</th>
                                            <th scope="col" class="border-0">상태값</th>
                                            <th scope="col" class="border-0 rounded-end">광고등급</th>
                                        </tr>
                                    </thead>
                                    <tbody id="recruitment-list-body">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

	<jsp:include page="/WEB-INF/views/company/footer/company_footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>