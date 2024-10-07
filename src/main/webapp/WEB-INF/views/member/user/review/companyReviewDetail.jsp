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

        .md_negative-section-title {
            color: #dc3545;
        }

        .md_section-content {
            margin-top: 10px;
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
<div class="container" style="max-width: 1260px;">
    <main class="flex-grow-1">
        <div class="md_review-container">
            <div class="md_review-header">
                <div class="md_review-title">최근 투자 유치로 자금 안정화 되었음.</div>
                <div class="md_review-date">2024/02/22</div>
            </div>

            <div class="md_star-rating">
                ★★★★☆
            </div>

            <!-- 승진 기회 및 가능성 -->
            <div class="md_progress-bar-container">
                <div class="md_progress-bar-label">승진 기회 및 가능성</div>
                <div class="md_progress-bar">
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment"></div>
                </div>
            </div>

            <!-- 복지 및 급여 -->
            <div class="md_progress-bar-container">
                <div class="md_progress-bar-label">복지 및 급여</div>
                <div class="md_progress-bar">
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment"></div>
                    <div class="md_progress-segment"></div>
                </div>
            </div>

            <!-- 업무와 삶의 균형 -->
            <div class="md_progress-bar-container">
                <div class="md_progress-bar-label">업무와 삶의 균형</div>
                <div class="md_progress-bar">
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment"></div>
                    <div class="md_progress-segment"></div>
                    <div class="md_progress-segment"></div>
                </div>
            </div>

            <!-- 사내문화 -->
            <div class="md_progress-bar-container">
                <div class="md_progress-bar-label">사내문화</div>
                <div class="md_progress-bar">
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment"></div>
                </div>
            </div>

            <!-- 경영진 -->
            <div class="md_progress-bar-container">
                <div class="md_progress-bar-label">경영진</div>
                <div class="md_progress-bar">
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment active"></div>
                    <div class="md_progress-segment"></div>
                    <div class="md_progress-segment"></div>
                </div>
            </div>

            <!-- 장점 섹션 -->
            <div class="md_section-title">장점</div>
            <div class="md_section-content">
                10시 출근. 7시 칼퇴! 야근한다고 매우 해주는 분위기 아님.
            </div>

            <!-- 단점 섹션 -->
            <div class="md_section-title md_negative-section-title">단점</div>
            <div class="md_section-content">
                개인 간막이가 없어 개인공간을 선호하는 업무자면 적응이 필요함.
            </div>

            <!-- 경영진에 바라는 점 -->
            <div class="md_section-title">경영진에 바라는 점</div>
            <div class="md_section-content">
                사원들의 의견도 적극 반영해 주었으면 좋겠음. 이 기업은 1년 후 비추할 것이다.
            </div>
        </div>
    </main>
</div>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
