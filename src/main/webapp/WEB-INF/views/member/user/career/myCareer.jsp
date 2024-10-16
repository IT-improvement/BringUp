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

    <!-- Custom Styles -->
    <style>
        /* Sidebar Styling */
        .sidebar {
            background-color: #f7f8f9 !important;
            padding: 10px;
            height: calc(100vh - 150px); /* 100vh에서 헤더와 푸터 높이를 뺀 값 */
            position: fixed;
            width: 220px;
        }

        .main-content {
            margin-left: 240px;
            padding-top: 20px;
        }

        .nav-link {
            cursor: pointer;
            display: flex;
            align-items: center;
            padding: 10px 15px;
            color: #6c757d;
            text-align: center;
            transition: background-color 0.3s, color 0.3s;
        }

        .nav-link.active {
            background-color: #eaf3ff;
            color: #007bff;
            border-radius: 5px;
        }

        .nav-link i {
            margin-right: 10px;
            font-size: 30px;
        }

        .collapse-icon {
            transition: transform 0.3s ease;
            margin-left: 60px;
        }

        .collapse-icon[aria-expanded="true"] {
            transform: rotate(180deg);
        }

        .sidebar-content {
            margin-left: 10px;
            width: 100%;
        }

        .collapse {
            transition: height 0.3s ease;
        }

        /* Hover effect for links */
        .nav-link:hover {
            background-color: #d8e8ff;
            color: #007bff;
        }

        .sidebar-icon {
            margin-left: 60px;
        }

        /* Small icon and text styles for collapsed content */
        .small-link {
            font-size: 14px; /* Smaller text size */
            padding: 5px 15px; /* Adjust padding */
        }

        .small-link i {
            font-size: 18px; /* Smaller icon size */
            margin-right: 5px; /* Adjust icon margin */
        }
    </style>

    <script>
        $(document).ready(function() {
            // Collapse icon toggle
            $('#resumeCollapse').on('show.bs.collapse', function () {
                $('#resumeIcon').addClass('bi-caret-up-fill').removeClass('bi-caret-down-fill');
            }).on('hide.bs.collapse', function () {
                $('#resumeIcon').addClass('bi-caret-down-fill').removeClass('bi-caret-up-fill');
            });

            // Add active class on click
            $('.nav-link').on('click', function() {
                $('.nav-link').removeClass('active');
                $(this).addClass('active');
            });
        });
    </script>
</head>
<body>

<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- Main Content -->
<body class="d-flex flex-column min-vh-100">
<div class="row">
    <!-- Sidebar -->
    <aside class="sidebar">
        <div class="sidebar-content">
            <nav>
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="#"><i class="bi bi-file-earmark-person"></i> 이력서</a> <!-- 이력서 아이콘 설정 -->
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-bs-toggle="collapse" href="#portfolioCollapse" role="button" aria-expanded="false" aria-controls="portfolioCollapse">
                            <i class="bi bi-folder-symlink"></i> 포트폴리오 <!-- 포트폴리오 아이콘 설정 -->
                            <div class="collapse-icon">
                                <span id="portfolioIcon" class="arrow-icon">▼</span> <!-- 펼침 아이콘 -->
                            </div>
                        </a>
                        <div class="collapse" id="portfolioCollapse">
                            <ul class="nav flex-column ms-3">
                                <!-- 포트폴리오 내부 아이템 -->
                                <li class="nav-item">
                                    <a class="nav-link small-link" href="#"><i class="bi bi-github"></i> Git</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link small-link" href="#"><i class="bi bi-journal-code"></i> Notion</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link small-link" href="#"><i class="bi bi-file-earmark"></i> 블로그</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link small-link" href="#"><i class="bi bi-folder"></i> 파일</a>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <!-- Larger items outside of the collapse -->
                    <li class="nav-item">
                        <a class="nav-link" href="#"><i class="bi bi-pencil-square"></i> 자소서</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#"><i class="bi bi-briefcase"></i> 이력</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#"><i class="bi bi-bar-chart"></i> 경력</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#"><i class="bi bi-award"></i> 어학 / 자격증</a>
                    </li>
                </ul>
            </nav>
        </div>
    </aside>

    <!-- Main Section -->
    <div class="container main-content" style="max-width: 1260px;">
        <main class="flex-grow-1">
            <h1>내이력서</h1>
            <p>여기에 이력서 내용이 표시됩니다.</p>
        </main>
    </div>
</div>
</body>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- Back to Top Button -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
