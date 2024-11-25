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
        document.addEventListener('DOMContentLoaded', function() {
            window.searchByCategory = (category) => {
                document.getElementById('search-category').value = 'category';
                document.getElementById('search-keyword').value = category;
                handleSearch(new Event('submit'));
            };

            window.searchByScale = (scale) => {
                document.getElementById('search-category').value = 'scale';
                document.getElementById('search-keyword').value = scale;
                handleSearch(new Event('submit'));
            };

            const fetchCompanyLists = async () => {
                try {
                    const companyResponse = await fetch('/company/list', {
                        method: 'GET'
                    });
                    const companyData = await companyResponse.json();
                    console.log(companyData);
                    return companyData.data;
                } catch (error) {
                    console.error("Error fetching companies : ", error);
                    displayErrorMessage();
                    return [];
                }
            };

            const renderCompanies = (allCompanies) => {
                let html = '';
                allCompanies.forEach(company => {
                    html += `
                    <div class="col-sm-6 col-lg-3 company-card">
                        <div class="card bg-transparent">
                            <img class="card-img rounded" src=${"${company.companyLogo}"} alt="Card image" style="width: 200px; height: 200px;">
                            <div class="card-body px-0 pt-3">
                                <h6 class="card-title mb-0"><a href="/member/company/detail/${"${company.companyId}"}" class="btn-link text-reset fw-bold">${"${company.companyName}"}</a></h6>
                                <ul class="nav nav-divider align-items-center text-uppercase small mt-2">
                                    <li class="nav-item">
                                        <a href="#" class="text-reset btn-link" onclick="event.preventDefault(); searchByCategory('${"${company.companyCategory}"}')">${"${company.companyCategory}"}</a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#" class="text-reset btn-link" onclick="event.preventDefault(); searchByScale('${"${company.companyScale}"}')">${"${company.companyScale}"}</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    `;
                });
                const companyListContainer = document.getElementById('company-list');
                companyListContainer.innerHTML = html;
            };

            const filterCompanies = (companies, category, keyword) => {
                return companies.filter(company => {
                    const matchesCategory = category === 'all' || 
                        (category === 'category' && company.companyCategory === keyword) ||
                        (category === 'scale' && company.companyScale === keyword) ||
                        (category === 'title' && company.companyName.includes(keyword));
                    return matchesCategory;
                });
            };

            const renderSearchResultMessage = (category, keyword) => {
                const categoryText = {
                    'all': '전체',
                    'title': '기업 이름',
                    'scale': '기업 규모',
                    'category': '기업 카테고리'
                }[category] || '전체';

                const message = keyword 
                    ? `${"${categoryText}"}에서 "${"${keyword}"}"에 대한 검색 결과`
                    : `${"${categoryText}"}에서 검색 결과가 없습니다.`;
                document.getElementById('search-result-message').textContent = message;
                document.getElementById('search-result-message').classList.add('fw-bold', 'fs-4');
            };

            const handleSearch = async (event) => {
                event.preventDefault();
                const category = document.getElementById('search-category').value;
                const keyword = document.getElementById('search-keyword').value.trim();
                const companyData = await fetchCompanyLists();
                const filteredCompanies = filterCompanies(companyData, category, keyword);
                renderSearchResultMessage(category, keyword);
                renderCompanies(filteredCompanies);
                document.getElementById('search-result-message-container').classList.remove('d-none');
            };

            window.searchReset = () => {
                document.getElementById('search-category').value = 'all';
                document.getElementById('search-keyword').value = '';
                fetchCompanyLists().then(renderCompanies);
                document.getElementById('search-result-message').textContent = '';
                document.getElementById('search-result-message-container').classList.add('d-none');
            };



            document.getElementById('search-form').addEventListener('submit', handleSearch);

            fetchCompanyLists().then(renderCompanies);

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
                                    <option value="title">기업 이름</option>
                                    <option value="scale">기업 규모</option>
                                    <option value="category">기업 카테고리</option>
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
                <div id="search-result-message-container" class="d-flex justify-content-between align-items-center mb-3 d-none">
                    <div id="search-result-message" class="mb-3"></div>
                    <a href="#" class="btn-link text-reset fw-bold" onclick="event.preventDefault(); searchReset()">전체 기업 보기</a>
                </div>
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
