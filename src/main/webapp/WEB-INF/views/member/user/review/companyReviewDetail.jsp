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

        <style>
            .md_review-container {
                max-width: 800px;
                margin: 0 auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
            }
            .md_review-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }
            .md_review-title {
                font-size: 24px;
                font-weight: bold;
            }
            .md_review-date {
                color: #888;
            }
            .md_star-rating {
                color: #ffa500;
                font-size: 18px;
            }
            .md_progress-bar-container {
                margin: 15px 0;
            }
            .md_progress-bar-label {
                font-size: 14px;
                font-weight: bold;
                margin-bottom: 5px;
            }
            .md_progress-bar {
                display: flex;
                gap: 2px;
            }
            .md_progress-segment {
                width: 20%;
                height: 10px;
                background-color: #f1f1f1;
                border-radius: 2px;
            }
            .md_progress-segment.active {
                background-color: #28a745;
            }
            .md_section-title {
                font-weight: bold;
                margin-top: 20px;
                color: #28a745;
            }
        </style>
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const accessToken = localStorage.getItem('accessToken');
                if (!accessToken) {
                    window.location.href = '/member/Login';
                    alert("로그인을 하셔야합니다");

                }
            });

        </script>
    </head>
    <body>

    <!-- 헤더 -->
    <jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

    <!-- 메인 콘텐츠 -->
    <body class="d-flex flex-column min-vh-100">

    <!-- 메인 콘텐츠 -->
    <div class="container" style="max-width: 1260px;">
        <main class="flex-grow-1">
            <div class="md_review-container" id="review-container">
                <div class="md_review-header">
                    <div class="md_review-title" id="review-title">리뷰 제목</div>
                    <div class="md_review-date" id="review-date">리뷰 날짜</div>
                </div>

                <div class="md_star-rating" id="star-rating">★★★★☆</div>

                <!-- 승진 기회 및 가능성 -->
                <div class="md_progress-bar-container">
                    <div class="md_progress-bar-label">승진 기회 및 가능성</div>
                    <div class="md_progress-bar" id="advancement-bar"></div>
                </div>

                <!-- 복지 및 급여 -->
                <div class="md_progress-bar-container">
                    <div class="md_progress-bar-label">복지 및 급여</div>
                    <div class="md_progress-bar" id="benefit-bar"></div>
                </div>

                <!-- 업무와 삶의 균형 -->
                <div class="md_progress-bar-container">
                    <div class="md_progress-bar-label">업무와 삶의 균형</div>
                    <div class="md_progress-bar" id="work-life-bar"></div>
                </div>

                <!-- 사내문화 -->
                <div class="md_progress-bar-container">
                    <div class="md_progress-bar-label">사내문화</div>
                    <div class="md_progress-bar" id="company-culture-bar"></div>
                </div>

                <!-- 경영진 -->
                <div class="md_progress-bar-container">
                    <div class="md_progress-bar-label">경영진</div>
                    <div class="md_progress-bar" id="management-bar"></div>
                </div>

                <!-- 리뷰 내용 -->
                <div class="md_section-title">리뷰 내용</div>
                <div class="md_section-content" id="content"></div>
            </div>
        </main>
    </div>

    </body>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const path = window.location.pathname;
            const reviewId = path.split('/').pop(); // URL에서 마지막 부분 (recruitmentId) 추출
            const reviewUrl = `/member/reviewDetail/`  + reviewId;

            fetch(reviewUrl)
                .then(response => response.json())
                .then(data => {
                    const review = data.data;

                    document.getElementById('review-title').textContent = review.companyReviewTitle;
                    document.getElementById('review-date').textContent = review.companyReviewDate;
                    document.getElementById('star-rating').textContent = '★'.repeat(Math.round(review.averageRating)) + '☆'.repeat(5 - Math.round(review.averageRating));

                    updateProgressBar('advancement-bar', review.advancement);
                    updateProgressBar('benefit-bar', review.benefit);
                    updateProgressBar('work-life-bar', review.workLife);
                    updateProgressBar('company-culture-bar', review.companyCulture);
                    updateProgressBar('management-bar', review.management);

                    document.getElementById('content').textContent = review.content;
                })
                .catch(error => console.error('Error fetching review:', error));

            function updateProgressBar(barId, value) {
                const progressBar = document.getElementById(barId);
                progressBar.innerHTML = '';
                for (let i = 0; i < 5; i++) {
                    const segment = document.createElement('div');
                    segment.classList.add('md_progress-segment');
                    if (i < value) {
                        segment.classList.add('active');
                    }
                    progressBar.appendChild(segment);
                }
            }
        });
    </script>
    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 가기 버튼 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

    </body>
    </html>
