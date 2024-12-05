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
    <!--<script src="/resources/script/member/recruitmentDetail.js"></script>-->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const path = window.location.pathname;
            const recruitmentIndex = path.split('/').pop();
            console.log("공고 번호 : ", recruitmentIndex)

            if (!recruitmentIndex) {
                console.error('recruitmentId is null or undefined');
                return;
            }

            const fetchUrl = `/recruitment/detail/` + recruitmentIndex;

            fetch(fetchUrl, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ` + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    if (!data || !data.data) {
                        console.error('데이터를 불러오지 못했습니다.');
                        return;
                    }

                    const recruitmentData = data.data;
                    document.getElementById('recruitmentTitle').textContent = recruitmentData.r_title;


                    // 회사 정보와 채용 정보 업데이트
                    document.getElementById('c_logo').src = recruitmentData.c_logo;
                    document.getElementById('c_name').textContent = recruitmentData.c_name;
                    document.getElementById('c_intro').textContent = recruitmentData.c_intro;
                    document.getElementById('r_workdetail').textContent = recruitmentData.r_workdetail;
                    document.getElementById('r_requirement').textContent = recruitmentData.r_requirement;
                    document.getElementById('r_hospitality').textContent = recruitmentData.r_hospitality;
                    document.getElementById('r_career').textContent = recruitmentData.r_career;
                    document.getElementById('r_salary').textContent = recruitmentData.r_salary;
                    document.getElementById('r_period').textContent = recruitmentData.r_period;
                    document.getElementById('c_welfare').textContent = recruitmentData.c_welfare;
                    document.getElementById('c_address').textContent = recruitmentData.c_address;

                    // 캐러셀 이미지 업데이트
                    const carouselInner = document.querySelector('.carousel-inner');
                    const carouselIndicators = document.querySelector('.carousel-indicators');
                    recruitmentData.c_img.forEach((imgSrc, index) => {
                        const item = document.createElement('div');
                        item.classList.add('carousel-item', index === 0 ? 'active' : '');

                        const img = document.createElement('img');
                        img.src = imgSrc;
                        img.classList.add('d-block', 'w-100');
                        img.alt = `Slide ${index + 1}`;
                        img.style.height = '280px';

                        item.appendChild(img);
                        carouselInner.appendChild(item);

                        const indicator = document.createElement('button');
                        indicator.type = 'button';
                        indicator.setAttribute('data-bs-target', '#carouselExampleIndicators');
                        indicator.setAttribute('data-bs-slide-to', index.toString());
                        if (index === 0) indicator.classList.add('active');
                        carouselIndicators.appendChild(indicator);
                    });

                    fetch(`/recruitment/visit/`+recruitmentIndex, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' }
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(`Failed to save visit record: ${'${response.status}'}`);
                            }
                            console.log('Visit record saved successfully');
                        })
                        .catch(error => console.error('Error saving visit record:', error));
                })
                .catch(error => console.error('Error:', error));
        });
        document.addEventListener('DOMContentLoaded', function () {
            const bookmarkButton = document.getElementById('bookmarkButton');
            const bookmarkIcon = document.getElementById('bookmarkIcon');
            const path = window.location.pathname;
            const recruitmentIndex = path.split('/').pop();
            const accessToken = localStorage.getItem('accessToken');
            console.log("Access token: " + accessToken);

            if (!recruitmentIndex) {
                console.error('recruitmentId is null or undefined');
                return;
            }

            let isBookmarked = false;

            // 서버에서 북마크 상태 가져오기
            fetch('/recruitment/isBookmarked/' + recruitmentIndex, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ` + accessToken,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {
                    isBookmarked = data.bookmarked;
                    updateBookmarkIcon();
                })
                .catch(error => console.error('Error fetching bookmark status:', error));

            // 북마크 버튼 클릭 이벤트
            bookmarkButton.addEventListener('click', function () {
                isBookmarked = !isBookmarked;

                fetch('/recruitment/toggleBookmark', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ` + accessToken,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ recruitmentId: recruitmentIndex, bookmarked: isBookmarked })
                })
                    .then(response => {
                        if (!response.ok) throw new Error('Failed to toggle bookmark');
                        return response.json();
                    })
                    .then(() => updateBookmarkIcon())
                    .catch(error => console.error('Error toggling bookmark:', error));
            });

            // 북마크 아이콘 업데이트
            function updateBookmarkIcon() {
                if (isBookmarked) {
                    bookmarkIcon.classList.add('active');
                    bookmarkIcon.classList.remove('bi-bookmark');
                    bookmarkIcon.classList.add('bi-bookmark-fill');
                } else {
                    bookmarkIcon.classList.remove('active');
                    bookmarkIcon.classList.add('bi-bookmark');
                    bookmarkIcon.classList.remove('bi-bookmark-fill');
                }
            }
        });


    </script>
    <style>
        #bookmarkButton {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            background-color: transparent;
        }

        #bookmarkButton:hover {
            background-color: rgba(0, 0, 0, 0.1);
        }

        #bookmarkIcon {
            font-size: 20px;
            color: #6c757d; /* 기본 색상 */
        }

        #bookmarkIcon.active {
            color: #f00; /* 북마크 활성화 상태 색상 */
        }
        .w-100{
            align-items: center;
        }
    </style>
</head>

<body class="d-flex flex-column min-vh-100">

    <jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

    <main class="flex-grow-1 container mt-5 w-75">
        <div class="d-flex align-items-center mb-3">
            <img id="c_logo" class="card-img-top w-30 m-1 rounded-circle border border-1 border-#C2C2C2" alt="회사 로고">
            <span id="c_name" class="fs-6 fw-bold">회사 이름</span>
        </div>
        <div class="d-flex align-items-center mb-2">
            <h2 id="recruitmentTitle" class="fs-1 fw-bold">채용 공고 제목</h2>
        </div>

        <div class="row">
            <div class="col-md-8">
                <!-- 캐러셀 영역 -->
                <div id="carouselExampleIndicators" class="carousel slide mb-4" data-bs-ride="carousel" data-bs-interval="false">
                    <div class="carousel-indicators"></div>
                    <div class="carousel-inner rounded-3 border border-1 border-#C2C2C2"></div>
                </div>

                <!-- 기업 소개, 업무 소개, 자격 요건, 우대 사항, 복지 혜택, 근무 지역 -->
                <div class="mb-4"><h3>기업 소개</h3><p id="c_intro"></p></div>
                <div class="mb-4"><h3>업무 소개</h3><p id="r_workdetail"></p></div>
                <div class="mb-4"><h3>자격 요건</h3><ul id="r_requirement"></ul></div>
                <div class="mb-4"><h3>우대 사항</h3><p id="r_hospitality"></p></div>
                <div class="mb-4"><h3>복지 혜택</h3><p id="c_welfare"></p></div>
                <div class="mb-4"><h3>근무 지역</h3><p id="c_address">주소</p></div>
            </div>

            <!-- 오른쪽 카드 (경력, 연봉, 마감일 등) -->
            <div class="col-md-4">
                <div class="card border border-1 border-dark align-items-center" style="position: sticky; top: 140px; height: 280px; border-radius: 15px;">
                    <div class="card-body d-flex flex-column justify-content-center w-75">

                        <div class="d-flex flex-column align-items-center text-center mt-4">
                            <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">

                                <h5 class="card-title mb-0">경력</h5><p id="r_career" class="card-text mb-0"></p>
                            </div>
                            <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">
                                <h5 class="card-title mb-0">최소 연봉</h5><p id="r_salary" class="card-text mb-0"></p>
                            </div>
                            <div class="d-flex justify-content-between w-100 border-bottom pb-2 mb-2">
                                <h5 class="card-title mb-0">마감일</h5><p class="card-text mb-0" id="r_period"></p>
                            </div>
                        </div>
                        <a href="#" class="btn btn-primary mt-2 rounded-pill">지원하기</a>

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