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
    <!--<link rel="stylesheet" type="text/css" href="/resources/style/member/profile/profileSide.css">-->

    <!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- 벤더 -->
    <script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
    <script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

    <!-- 테마 JS -->
    <script src="/resources/script/common/function/functions.js"></script>

    <!-- 메인 JS -->
    <!-- <script src="/resources/script/company/main.js"></script> -->

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function (){
            const accessToken = localStorage.getItem('accessToken');
            console.log("Access token: " + accessToken);
            if (!accessToken){
                window.location.href = "/member/Login";
                return;
            }

            let currentPage = 1;
            const itemsPerPage = 5;
            let totalItems = 0;
            let allData = [];
            let filteredData = [];

            let statusCounts = {
                IN_PROGRESS: 0,
                PASSED: 0,
                FAILED: 0
            };

            function fetchData(){
                fetch('/member/applyList', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ` + accessToken,
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log("받은 데이터 : " + data);
                        allData = data.data;
                        filteredData = allData;
                        totalItems = allData.length;

                        countStatuses(allData);
                        renderStatusCounts();
                        renderPage(currentPage);
                    })
                    .catch(error => {
                        console.error('기업 북마크 목록을 가져오는 중 오류 발생 : ', error);
                        const recruitmentListBody = document.getElementById('apply-list-body');
                        if (recruitmentListBody) {
                            recruitmentListBody.innerHTML = '<tr><td colspan="6" class="text-center">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>';
                        }
                    });
            }

            function countStatuses(data) {
                statusCounts = { IN_PROGRESS: 0, PASSED: 0, FAILED: 0 };
                data.forEach(apply => {
                    if (apply.status in statusCounts) {
                        statusCounts[apply.status]++;
                    }
                });
            }

            function renderStatusCounts() {
                document.getElementById('in-progress-count').textContent = `${"${statusCounts.IN_PROGRESS}개"}`;
                document.getElementById('passed-count').textContent = `${"${statusCounts.PASSED}개"}`;
                document.getElementById('failed-count').textContent = `${"${statusCounts.FAILED}개"}`;
            }

            function renderPage(page){
                const applyListBody = document.getElementById('apply-list-body');
                applyListBody.innerHTML = '';

                const start = (page - 1) * itemsPerPage;
                const end = start + itemsPerPage;
                const pageData = filteredData.slice(start, end);

                pageData.forEach((apply, index) => {
                    const row = document.createElement('tr');
                    const number = apply.recruitmentIndex;

                    const applicationTypeText = apply.applicationType === 'RECRUITMENT' ? '일반' :
                        apply.applicationType === 'FREELANCER' ? '프리랜서' : '알 수 없음';

                    const statusText = apply.status === 'IN_PROGRESS' ? '진행 중' :
                        apply.status === 'PASSED' ? '합격' :
                            apply.status === 'FAILED' ? '불합격' : '알 수 없음';

                    row.innerHTML = `
						<td>${"${start + index + 1}"}</td>
						<td>${"${apply.cv.cvIndex}"}</td>
						<td>${"${apply.companyName}"}</td>
						<td>${"${apply.recruitmentTitle}"}</td>
						<td>${"${applicationTypeText}"}</td>
						<td>${"${statusText}"}</td>
						<td>${"${apply.applyCVDate}"}</td>
					`;
                    row.style.cursor = 'pointer';
                    row.addEventListener('click', () => {
                        window.location.href = `/member/recruitment/details/` + number;
                    });
                    applyListBody.appendChild(row);
                });
            }
            fetchData();
        });
    </script>
</head>

<body class="d-flex flex-column min-vh-100">
<!-- header-->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<main class="flex-grow-1 m-4">
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
                                    <h3 id="in-progress-count">0개</h3>
                                    <h6 class="mb-0">진행중인 공고</h6>
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
                                    <h3 id="passed-count">0개</h3>
                                    <h6 class="mb-0">합격</h6>
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
                                    <h3 id="failed-count">0개</h3>
                                    <h6 class="mb-0">불합격</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div class="card border bg-transparent rounded-3">
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
                                        <option value="공고 제목">이력서 번호</option>
                                        <option value="공고 타입">공고</option>
                                        <option value="모집 분야">회사</option>
                                        <option value="마감일">진행 상태</option>
                                    </select>
                                </form>
                            </div>
                        </div>
                        <div class="table-responsive border-0">
                            <table class="table align-middle p-4 mb-0 table-hover table-shrink text-center">
                                <thead class="table-dark">
                                <tr>
                                    <th scope="col">번호</th>
                                    <th scope="col">이력서 번호</th>
                                    <th scope="col">기업 이름</th>
                                    <th scope="col">공고 이름</th>
                                    <th scope="col">지원 타입</th>
                                    <th scope="col">진행 상태</th>
                                    <th scope="col">등록 일자</th>
                                </tr>
                                </thead>
                                <tbody id="apply-list-body" class="border-top-0 text-center">
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
</main>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
