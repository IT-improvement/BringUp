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

    <!-- 헤드헌트 JS -->
    <script src="/resources/script/company/headhunt.js"></script>

    <!-- 헤드헌트 CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/company/headhunt/headhunt.css">

    <!-- jQuery (필요한 경우) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="d-flex flex-column min-vh-100">

<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

<main class="flex-grow-1 m-4">
    <div class="headhunt-list container" style="max-width: 1260px;">
        <h2>저장된 후보자 목록</h2>

        <div id="savedCVSection" class="user-card-list">
            <!-- 저장된 후보자 카드가 표시될 공간 -->
        </div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const accessToken = localStorage.getItem("accessToken");

        // 토큰이 없는 경우 로그인 페이지로 리다이렉트
        if (!accessToken) {
            console.log("토큰이 없습니다. 로그인이 필요합니다.");
            window.location.href = "/company/login";
            return;
        }

        // 로딩 중 메시지 표시
        const savedCVSection = document.getElementById('savedCVSection');
        savedCVSection.innerHTML = "<p>로딩 중...</p>";

        // 저장된 후보자 목록을 가져오는 함수
        const fetchSavedCVs = async () => {
            try {
                const response = await fetch('/com/headhunt/list/saved', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${accessToken}`,  // Bearer 토큰 설정
                        'Content-Type': 'application/json'
                    }
                });

                // 토큰이 유효하지 않을 경우 재로그인 유도
                if (response.status === 401) {
                    console.log("토큰이 유효하지 않습니다. 다시 로그인해주세요.");
                    window.location.href = "/company/login";
                    return;
                }

                // 응답 데이터 처리
                if (response.ok) {
                    const data = await response.json();
                    let savedCVs = data.data;

                    if (savedCVs && savedCVs.length > 0) {
                        renderSavedCVs(savedCVs);  // 저장된 후보자 목록 렌더링
                    } else {
                        savedCVSection.innerHTML = "<p>저장된 후보자가 없습니다.</p>";
                    }
                } else {
                    savedCVSection.innerHTML = "<p>저장된 후보자가 없습니다.</p>";
                }

            } catch (error) {
                console.error("Error fetching saved CVs: ", error);
                savedCVSection.innerHTML = "<p>저장된 후보자가 없습니다.</p>";
            }
        };

        // 저장된 후보자 목록을 화면에 렌더링하는 함수
        const renderSavedCVs = (savedCVs) => {
            let html = '';

            savedCVs.forEach(cv => {
                const imgSrc = cv.cvImage ? `/resources${cv.cvImage}` : '/resources/image/default.png';

                html += `
                <div class="user-card">
                    <div class="user-card-content">
                        <img src="${imgSrc}" alt="CV Image" class="user-image">
                        <div class="user-info">
                            <p class="user-name">${cv.userName}</p>
                            <p class="user-education">최종학력: ${cv.education}</p>
                            <p class="user-address">주소: ${cv.userAddress}</p>
                            <p class="user-skill">기술 스택: ${cv.skill}</p>
                        </div>
                        <div class="user-actions">
                            <button class="btn btn-secondary">이직 제안하기</button>
                        </div>
                    </div>
                </div>`;
            });

            // DOM 업데이트
            savedCVSection.innerHTML = html;
        };

        // 저장된 후보자 목록 가져오기
        fetchSavedCVs();
    });
</script>

</body>
</html>
