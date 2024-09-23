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
    <!-- <script src="/resources/script/company/jobposting/detail.js"></script> -->
     <script>
        document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    // URL에서 recruitmentIndex 파라미터를 가져옵니다.
    const urlParams = new URLSearchParams(window.location.search);
    const recruitmentIndex = urlParams.get('recruitmentIndex');
    if (!accessToken) {
        window.location.href = '/company/auth/login';
        return;
    }

    const url = `/com/recruitment/detail/`+recruitmentIndex;
    
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer `+accessToken,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        window.recruitmentData = data.data;
    })
    .catch(error => {
        console.error('채용 공고 상세 정보를 가져오는 중 오류 발생:', error);
        alert('채용 공고 상세 정보를 불러올 수 없습니다. 나중에 다시 시도해주세요.');
    });
});

function updateUI() {
    const recruitment = window.recruitmentData;
    document.getElementById('recruitmentTitle').textContent = recruitment.recruitmentTitle;
    document.getElementById('category').textContent = recruitment.category;
    document.getElementById('managerEmail').textContent = recruitment.managerEmail;
    document.getElementById('period').textContent = recruitment.period;
    document.getElementById('recruitmentClass').textContent = recruitment.recruitmentClass;
    document.getElementById('recruitmentType').textContent = recruitment.recruitmentType;
    document.getElementById('skill').textContent = recruitment.skill;
    document.getElementById('startDate').textContent = recruitment.startDate;
    document.getElementById('status').textContent = recruitment.status;
    
    if (recruitment.recruitment_img) {
        const img = document.createElement('img');
        img.src = recruitment.recruitment_img;
        img.alt = '채용 이미지';
        img.className = 'img-fluid';
        document.getElementById('recruitmentImage').appendChild(img);
    }

    document.getElementById('loadingIndicator').style.display = 'none';
    document.getElementById('recruitmentContent').style.display = 'block';
}
    </script>
</head>

<body class="d-flex flex-column min-vh-100">

    <jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="flex-grow-1">
        <div class="container mt-5 w-75">
            <div class="d-flex align-items-center mb-3">
                <img src="/resources/logos/logo.png" alt="로고 이미지" class="card-img-top w-30 me-1 rounded-circle border border-1 border-#C2C2C2">
                <span class="fs-6 fw-bold">기업 이름</span>
            </div>
            <div class="d-flex align-items-center mb-2">
                <h2>채용 공고 제목</h2>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <div id="carouselExampleIndicators" class="carousel slide mb-4" data-bs-ride="carousel" data-bs-interval="2000">
                        <div class="carousel-indicators">
                            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                        </div>
                        <div class="carousel-inner rounded-3 border border-1 border-#C2C2C2">
                            <div class="carousel-item active">
                                <img src="/resources/image/image.png" class="d-block w-100" alt="채용 공고 이미지 1" style="height: 280px;">
                            </div>
                            <div class="carousel-item">
                                <img src="/resources/image/image.png" class="d-block w-100" alt="채용 공고 이미지 2" style="height: 280px;">
                            </div>
                            <div class="carousel-item">
                                <img src="/resources/image/image.png" class="d-block w-100" alt="채용 공고 이미지 3" style="height: 280px;">
                            </div>
                        </div>
                        <button class="carousel-control-prev w-40" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev" style="height: 33%; top: 50%; transform: translateY(-50%); background-color: rgba(211, 211, 211, 0.5);">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">이전</span>
                        </button>
                        <button class="carousel-control-next w-40" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next" style="height: 33%; top: 50%; transform: translateY(-50%); background-color: rgba(211, 211, 211, 0.5);">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">다음</span>
                        </button>
                    </div>
                    <div class="mb-4">
                        <h3>기업 소개</h3>
                        <p>리얼타임 기반의 애드테크 No.1 기업, 버즈빌...</p>
                    </div>
                    <div class="mb-4">
                        <h3>업무 소개</h3>
                        <p>Web Product팀의 프론트 엔드 개발자는...</p>
                    </div>
                    <div class="mb-4">
                        <h3>자격 요건</h3>
                        <ul>
                            <li>기본적인 웹 프론트엔드 지식</li>
                            <li>기본적인 네트워크 지식</li>
                            <li>기본적인 데이터베이스 지식</li>
                            <li>기본적인 알고리즘과 자료구조에 대한 이해</li>
                            <li>Git을 이용한 협업 경험이 있는 분</li>
                            <li>새로운 기술과 도구에 대한 관심과 열정을 가진 분</li>
                        </ul>
                    </div>
                    <div class="mb-4">
                        <h3>우대 사항</h3>
                        <ul>
                            <li>광고 도메인에 대해 경험이 있으신 분</li>
                            <li>Monorepo에 대해 경험이 있으신 분...</li>
                        </ul>
                    </div>
                    <div class="mb-4">
                        <h3>복지 혜택</h3>
                        <ul>
                            <li>업계 최고의 복지 환경을 제공합니다...</li>
                        </ul>
                    </div>
                    <div class="mb-4">
                        <h3>근무 지역</h3>
                        <p>주소</p>
                        <div id="map" style="width:100%;height:350px;"></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card border border-1 border-dark align-items-center" style="position: sticky; top: 140px; height: 280px; border-radius: 15px;">
                        <div class="card-body d-flex flex-column justify-content-center w-75" style="height: 100%; position: relative;">
                            <div class="d-flex justify-content-end" style="position: absolute; top: 10px; right: -30px;">
                                <button class="btn btn-link p-0 me-2" style="color: #000; font-size: 1.5em;">
                                    <i class="bi bi-bookmark"></i>
                                </button>
                                <button class="btn btn-link p-0" style="color: #000; font-size: 1.5em;">
                                    <i class="bi bi-share"></i>
                                </button>
                            </div>
                            <div class="d-flex flex-column align-items-center text-center mt-4">
                                <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">
                                    <h5 class="card-title mb-0">경력</h5>
                                    <p class="card-text mb-0">4~8년차</p>
                                </div>
                                <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">
                                    <h5 class="card-title mb-0">최소 연봉</h5>
                                    <p class="card-text mb-0">1000만원</p>
                                </div>
                                <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">
                                    <h5 class="card-title mb-0">마감일</h5>
                                    <p class="card-text mb-0">채용 완료까지</p>
                                </div>
                            </div>
                            <a href="#" class="btn btn-primary mt-2 rounded-pill" style="background-color: #8BA5FF; border-color: #8BA5FF;">지원하기</a>
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