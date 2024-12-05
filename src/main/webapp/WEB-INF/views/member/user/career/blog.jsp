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

    <style>
        .blog-list {
            margin-top: 20px;
            width: 100%;
            max-width: 800px;
            margin-left: auto;
            margin-right: auto;
        }

        .blog-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }

        .blog-item input {
            margin-right: 10px;
            width: 100%;
        }

        .blog-item button {
            margin-left: 10px;
            width: 10%;
        }

        .add-blog-btn {
            margin-bottom: 20px;
            display: block;
            margin-left: auto;
            margin-right: 0; /* 오른쪽 정렬 */
        }


    </style>

    <script>
        $(document).ready(function() {
            let blogCount = 1; // 예시 블로그 하나를 표시하기 위해 1로 시작

            // 페이지 로드 시 기본 예시 추가 (수정 불가)
            $('#blogList').append(
                `<div class="blog-item" id="blog-0">

                    <input type="text" value="https://example.com" class="form-control" readonly>
                    <button class="btn btn-danger btn-sm" onclick="removeBlog(0)">삭제</button>
                </div>`
            );

            // 블로그 추가 버튼을 클릭하면 입력 폼이 나타남
            $('#addBlogBtn').click(function() {
                blogCount++;
                const newBlog = $(`
                    <div class="blog-item" id="blog-${'${blogCount}'}">

                        <input type="text" placeholder="블로그 URL" class="form-control">
                        <button class="btn btn-danger btn-sm">삭제</button>
                    </div>
                `);

                // 삭제 버튼에 클릭 이벤트 바인딩
                newBlog.find('button').click(function() {
                    $(this).parent().remove();
                });

                $('#blogList').append(newBlog);
            });
        });

        // 예시 블로그 삭제 함수
        function removeBlog(id) {
            $('#blog-' + id).remove();
        }
    </script>

</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <!-- Main Content -->
    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
        <main class="flex-grow-1">
            <h1>나의 블로그</h1>

            <!-- 블로그 추가 및 삭제 리스트 -->
            <div class="blog-list">
                <button id="addBlogBtn" class="btn btn-primary add-blog-btn">블로그 추가</button>
                <div id="blogList">
                    <!-- 동적으로 블로그 입력 폼이 추가되는 곳 -->
                </div>
            </div>

        </main>
    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
