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
    <script>
        document.addEventListener('DOMContentLoaded', function (){
            const accessToken = localStorage.getItem('accessToken');
            console.log("AccessToken : ", accessToken);

            if (!accessToken){
                window.location.href = "/member/Login";
                return;
            }

            let currentPage = 1;
            const itemsPerPage = 5;
            let totalItems = 0;
            let allData = [];
            let filteredData = [];

            function fetchData(){
                fetch('/mem/addCompany/list', {
                    method: "GET",
                    headers: {
                        'Authorization': `Bearer ` + accessToken,
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`HTTP error! status: ${"${response.status}"}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log("받은 데이터 : ", data);
                        allData = data || [];
                        filteredData = allData;
                        totalItems = allData.length;
                        renderPage(currentPage);

                        if (allData.length === 0) {
                            const bookmarkListBody = document.getElementById('bookmark-list-body');
                            bookmarkListBody.innerHTML = `
                                <tr>
                                    <td colspan="6" class="text-center">북마크한 기업 데이터가 없습니다.</td>
                                </tr>`;
                        }
                    })
                    .catch(error => {
                        console.error('북마크한 기업을 불러오는 중 오류 발생', error)
                        const bookmarkListBody = document.getElementById('bookmark-list-body');
                        if (bookmarkListBody) {
                            bookmarkListBody.innerHTML = '<tr><td colspan="6" class="text-center">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>';
                        }
                    });
            }

            function renderPage(page){
                const bookmarkListBody = document.getElementById('bookmark-list-body');
                bookmarkListBody.innerHTML = ``;

                const start = (page - 1) * itemsPerPage;
                const end = start + itemsPerPage;
                const pageData = filteredData.slice(start, end);

                pageData.forEach((bookmark, index) => {
                    const row = document.createElement('tr');
                    const number = bookmark.company.companyId;

                    row.innerHTML = `
						<td>${'${start + index + 1}'}</td>
						<td>${'${bookmark.company.companyName}'}</td>
					`;
                    row.style.cursor = 'pointer';
                    row.addEventListener('click', () => {
                        window.location.href = `` + number;
                    });
                    bookmarkListBody.appendChild(row);
                });
            }

            fetchData();
        });
    </script>

</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
<div class="container" style="max-width: 1260px;">
    <main class="flex-grow-1 m-4">
        <p class="h1">기업 북마크</p>
        <div class="py-4">
            <div class="card border bg-transparent rounded-3">
                <div class="card-header bg-transparent border-bottom p-3">
                    <div class="d-sm-flex justify-content-between align-items-center">
                        <h5 class="mb-2 mb-sm-0">목록<span id="jobCount" class="badge bg-primary bg-opacity-10 text-primary"></span></h5>
                        <a href="/member/company/list" class="btn btn-sm btn-primary mb-0">기업 리스트</a>
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
                                <th scope="col">기업 이름</th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody id="bookmark-list-body" class="border-top-0 text-center">
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
    </main>
</div>
</body>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>

