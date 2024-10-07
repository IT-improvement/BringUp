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
                <div>
                    <div class="card border mb-4">
                    <div class="card-header bg-light border-bottom">
                        <h5 class="card-title mb-0">공고 검색</h5>
                    </div>
                    <div class="card-body">
                        <form id="search-form" class="row g-3">
                            <div class="col-md-4">
                                <label for="search-category" class="form-label">카테고리</label>
                                <select class="form-select" id="search-category">
                                    <option value="all">전체</option>
                                    <option value="title">공고 제목</option>
                                    <option value="type">공고 타입</option>
                                    <option value="recruitmentType">채용 형태</option>
                                    <option value="deadline">마감일</option>
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
                <div>
                    <div class="card border">
                        <div class="card-header bg-light border-bottom d-flex justify-content-between align-items-center">
                            <h5 class="card-title mb-0">공고목록</h5>
                            <button type="button" class="btn btn-primary" onclick="location.href='/company/jobpost/registration'">등록</button>
                        </div>
                        <div class="card-body p-3">
                            <div class="table-responsive">
                                <table class="table table-hover table-striped align-middle mb-0">
                                    <thead class="table-light text-center">
                                        <tr>
                                            <th>번호</th>
                                            <th>공고제목</th>
                                            <th>공고타입</th>
                                            <th>채용형태</th>
                                            <th>마감일</th>
                                            <th>조회수</th>
                                            <th>지원자수</th>
                                        </tr>
                                    </thead>
                                    <tbody id="recruitment-list-body" class="text-center">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>    
        </div>
    </main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>