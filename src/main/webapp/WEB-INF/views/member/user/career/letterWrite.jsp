<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>BringUp</title>

    <!-- Meta Tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">
    <meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">

    <!-- Dark Mode -->
    <script src="/resources/script/common/darkmode/darkmode.js"></script>

    <!-- Favicon -->
    <link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- Plugins CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/apexcharts/css/apexcharts.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">

    <!-- Theme CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

    <!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
        <main class="flex-grow-1">
            <!-- Search Bar -->
            <div class="input-group mb-3">
                <input type="text" class="form-control" value="박주혁님의 자소서" aria-label="자소서 검색" aria-describedby="button-search">
                <button class="btn btn-primary" type="button" id="button-search">확인</button>
            </div>

            <!-- 문항 Switch and Tip -->
            <div class="d-flex align-items-center mb-3">
                <span class="me-3">문항</span>
                <div class="btn-group" role="group" aria-label="문항 토글">
                    <input type="radio" class="btn-check" name="toggle-munhang" id="toggle-munhang-on" autocomplete="off">
                    <label class="btn btn-outline-primary" for="toggle-munhang-on">ON</label>

                    <input type="radio" class="btn-check" name="toggle-munhang" id="toggle-munhang-off" autocomplete="off" checked>
                    <label class="btn btn-outline-primary" for="toggle-munhang-off">OFF</label>
                </div>
                <span class="ms-2 text-muted">* 문항을 걸 수 있습니다.</span>
            </div>

            <!-- 내용 Section -->
            <div>
                <h5>내용</h5>
                <div class="card p-4 bg-light text-center">
                    <div class="mb-2 text-muted">TIP. 자소서 처음 쓰기 막막하다면? 키워드만 넣고 초안을 생성해보세요!</div>
                    <button class="btn btn-outline-primary mb-3">자소서 초안 자동생성하기 &gt;</button>
                    <div><a href="#" class="text-decoration-none">아니요, 직접 작성할게요. &gt;</a></div>
                </div>

                <!-- 글자 수 변경 Section -->
                <div class="d-flex justify-content-between mt-3">
                    <small class="text-muted">0 / 500 자 (공백포함)</small>
                    <button class="btn btn-outline-secondary">글자수 변경</button>
                </div>
            </div>
        </main>

    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
