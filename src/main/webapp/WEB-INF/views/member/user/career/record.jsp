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
        .button-container {
            text-align: center;
            margin-top: 20px;
        }

        .action-button {
            padding: 10px 20px;
            font-size: 1em;
            margin: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .save-button {
            background-color: #4CAF50;
            color: white;
        }

        .ai-generate-button {
            background-color: #008CBA;
            color: white;
        }

        .education-section {
            margin-top: 40px;
        }

        .education-title {
            font-weight: bold;
            font-size: 1.5em;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-control {
            width: 100%;
            padding: 10px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .education-section-container {
            border: 1px solid #ccc;
            padding: 20px;
            margin-bottom: 30px;
            border-radius: 10px;
        }
    </style>

</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
        <main class="flex-grow-1">
            <h2>학력</h2>
            <!-- Education Section -->
            <div class="education-section">
                <!-- High School Education Form -->
                <div class="education-section-container">
                    <div class="form-group">
                        <label for="highschool-level">학력 수준</label>
                        <select id="highschool-level" class="form-control">
                            <option>고등학교 졸업</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="highschool-name">학교명</label>
                        <input type="text" id="highschool-name" class="form-control" placeholder="학교명 입력">
                    </div>

                    <div class="form-group">
                        <label for="highschool-graduate-status">졸업 여부</label>
                        <select id="highschool-graduate-status" class="form-control">
                            <option>졸업</option>
                            <option>재학 중</option>
                            <option>중퇴</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="highschool-entrance-date">입학 년월</label>
                        <input type="month" id="highschool-entrance-date" class="form-control">
                    </div>

                    <div class="form-group">
                        <label for="highschool-graduation-date">졸업 년월</label>
                        <input type="month" id="highschool-graduation-date" class="form-control">
                    </div>
                </div>

                <!-- University Education Form -->
                <div class="education-section-container">
                    <div class="form-group">
                        <label for="university-level">학력 수준</label>
                        <select id="university-level" class="form-control">
                            <option>대학 (4년) 졸업</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="university-type">대학 구분</label>
                        <select id="university-type" class="form-control">
                            <option>국립</option>
                            <option>사립</option>
                            <option>공립</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="university-name">학교명</label>
                        <input type="text" id="university-name" class="form-control" placeholder="학교명 입력">
                    </div>

                    <div class="form-group">
                        <label for="university-major">전공</label>
                        <input type="text" id="university-major" class="form-control" placeholder="전공 입력">
                    </div>

                    <div class="form-group">
                        <label for="university-graduate-status">졸업 여부</label>
                        <select id="university-graduate-status" class="form-control">
                            <option>졸업</option>
                            <option>재학 중</option>
                            <option>중퇴</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="university-entrance-date">입학 년월</label>
                        <input type="month" id="university-entrance-date" class="form-control">
                    </div>

                    <div class="form-group">
                        <label for="university-graduation-date">졸업 년월</label>
                        <input type="month" id="university-graduation-date" class="form-control">
                    </div>
                </div>

                <!-- Graduate School Education Form -->
                <div class="education-section-container">
                    <div class="form-group">
                        <label for="graduate-level">학력 수준</label>
                        <select id="graduate-level" class="form-control">
                            <option>대학 · 대학원 이상 졸업</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="graduate-school-name">학교명</label>
                        <input type="text" id="graduate-school-name" class="form-control" placeholder="학교명 입력">
                    </div>

                    <div class="form-group">
                        <label for="graduate-major">전공</label>
                        <input type="text" id="graduate-major" class="form-control" placeholder="전공 입력">
                    </div>

                    <div class="form-group">
                        <label for="graduate-graduate-status">졸업 여부</label>
                        <select id="graduate-graduate-status" class="form-control">
                            <option>졸업</option>
                            <option>재학 중</option>
                            <option>중퇴</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="graduate-entrance-date">입학 년월</label>
                        <input type="month" id="graduate-entrance-date" class="form-control">
                    </div>

                    <div class="form-group">
                        <label for="graduate-graduation-date">졸업 년월</label>
                        <input type="month" id="graduate-graduation-date" class="form-control">
                    </div>
                </div>

                <div class="button-container">
                    <button class="action-button save-button">저장</button>
                    <button class="action-button">취소</button>
                </div>
            </div>
        </main>
    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
