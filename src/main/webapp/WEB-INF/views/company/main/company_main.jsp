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

    <!-- 추가된 스타일 -->
    <link rel="stylesheet" type="text/css" href="/resources/style/company/main/main.css"></link>

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
</head>

<body class="d-flex flex-column min-vh-100">

	<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="flex-grow-1">
        <section class="py-4">
            <div class="container" style="max-width: 1260px;">
                <div class="row g-4">
                    <div class="col-12">
                        <div class="row g-4">
                            <div class="col-sm-6 col-lg-4">
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
                            <div class="col-sm-6 col-lg-4">
                                <div class="card card-body border p-3">
                                    <div class="d-flex align-items-center">
                                        <div class="icon-xl fs-1 bg-primary bg-opacity-10 rounded-3 text-primary">
                                            <i class="bi bi-file-earmark-text-fill"></i>
                                        </div>
                                        <div class="ms-3">
                                            <h3 id="jobCount"></h3>
                                            <h6 class="mb-0">공고</h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-lg-4">
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
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="card border bg-transparent rounded-3">
                            <div class="card-header bg-transparent border-bottom p-3">
                                <div class="d-sm-flex justify-content-between align-items-center">
                                    <h5 class="mb-2 mb-sm-0">내 공고 <span id="jobCount" class="badge bg-primary bg-opacity-10 text-primary"></span></h5>
                                    <a href="/company/jobpost/registration" class="btn btn-sm btn-primary mb-0">추가</a>
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
                                            <select id="categorySelect" class="form-select z-index-9 bg-transparent align-items-center" aria-label=".form-select-sm">
                                                <option value="전체">전체</option>
                                                <option value="공고 제목">공고 제목</option>
                                                <option value="공고 타입">공고 타입</option>
                                                <option value="모집 분야">모집 분야</option>
                                                <option value="마감일">마감일</option>
                                                <option value="조회수">조회수</option>
                                                <option value="지원자수">지원자수</option>
                                            </select>
                                        </form>
                                    </div>
                                </div>
                                <div class="table-responsive border-0">
                                    <table class="table align-middle p-4 mb-0 table-hover table-shrink text-center">
                                        <thead class="table-dark">
                                        <tr>
                                            <th scope="col">번호</th>
                                            <th scope="col">공고 제목</th>
                                            <th scope="col">공고 타입</th>
                                            <th scope="col">모집 분야</th>
                                            <th scope="col">마감일</th>
                                            <th scope="col">조회수</th>
                                            <th scope="col">지원자수</th>
                                        </tr>
                                        </thead>
                                        <tbody id="recruitment-list-body" class="border-top-0 text-center">
                                        </tbody>
                                    </table>
                                </div>
                                <div class="d-sm-flex justify-content-sm-between align-items-sm-center mt-4 mt-sm-3">
                                    <p id="totalEntries" class="mb-sm-0 text-center text-sm-start"></p>
                                    <nav class="mb-sm-0 d-flex justify-content-center" aria-label="Page navigation">
                                        <ul class="pagination pagination-sm pagination-bordered mb-0" id="paginationContainer">
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>