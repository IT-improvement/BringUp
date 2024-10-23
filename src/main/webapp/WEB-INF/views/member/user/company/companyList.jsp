
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

    <script>
        document.addEventListener('DOMContentLoaded', function(){
            document.body.addEventListener('click', function(event) {
                if (event.target.id === 'logoutButton' || event.target.closest('#logoutButton')) {
                    console.log("로그아웃 버튼 클릭");
                    event.preventDefault();
                    localStorage.removeItem('accessToken');
                    window.location.href = '/';
                }
            });

            const fetchCompanyLists = async () => {
                try {
                    const companyResponse = await fetch('/company/list', {
                        method : 'GET'
                    });
                    const companyData = await companyResponse.json();
                    if (companyData.data && companyData.data.length > 0) {
                        renderCompanies(companyData.data);
                    } else {
                        displayNoCompaniesMessage();
                    }
                }catch (error){
                    console.error("Error fetching companies : ", error);
                    displayErrorMessage();
                }
            };

            const renderCompanies = (allCompanies) => {
                let html = '';
                allCompanies.forEach(company => {
                    console.log(company);
                    html += `
                        <div class="col-sm-6 col-lg-3 company-card">
                        <div class="card bg-transparent">
                            <!-- Card img -->
                            <img class="card-img rounded" src=${"${company.companyLogo}"} alt="Card image" style="width: 200px; height: 200px;">
                            <div class="card-body px-0 pt-3">
                                <h6 class="card-title mb-0"><a href="#" class="btn-link text-reset fw-bold">${"${company.companyName}"}</a></h6>
                                <!-- Card info -->
                                <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                    <li class="nav-item">
                                        <a href="#" class="text-reset btn-link">${"${company.companyCategory}"}</a>
                                    </li>
                                    <li class="nav-item">${"${company.companyScale}"}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    `;
                });
                const companyListContainer = document.getElementById('company-list');
                companyListContainer.innerHTML = html;
            };

            const displayErrorMessage = () => {
                const companyListContainer = document.getElementById('company-list');
                companyListContainer.innerHTML = "<p>기업 리스트를 불러오는 중 오류가 발생했습니다.</p>";
            };

            // 데이터가 없는 경우 표시할 함수
            const displayNoCompaniesMessage = () => {
                const companyListContainer = document.getElementById('company-list');
                companyListContainer.innerHTML = "<p>등록된 기업 정보가 없습니다.</p>";
            };

            fetchCompanyLists();
        });
    </script>
</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
<div class="container" style="max-width: 1260px;">
    <main class="flex-grow-1 py-4">
        <div class="row g-4">
            <div>
                <div class="card border mb-4">
                    <div class="card-header bg-light border-bottom">
                        <h5 class="card-title mb-0">기업 검색</h5>
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
            <div class="container">
                <div class="row g-4 p-3 flex-fill company-list-card" id="company-list">

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
