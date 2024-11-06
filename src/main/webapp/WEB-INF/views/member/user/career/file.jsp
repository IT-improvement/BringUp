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


    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <style>
        .upload-container {
            margin-top: 20px;
            padding: 20px;
            border: 2px dashed #ddd;
            text-align: center;
            background-color: #f9f9f9;
        }

        .upload-container h2 {
            font-size: 18px;
            margin-bottom: 15px;
        }

        .upload-btn-wrapper {
            position: relative;
            overflow: hidden;
            display: inline-block;
        }

        .upload-btn {
            border: 2px solid #007bff;
            color: #007bff;
            background-color: white;
            padding: 10px 20px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
        }

        .upload-btn-wrapper input[type="file"] {
            font-size: 100px;
            position: absolute;
            left: 0;
            top: 0;
            opacity: 0;
        }

        .file-list {
            margin-top: 20px;
        }

        .file-item {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 10px;
            background-color: #f1f1f1;
            border-radius: 5px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .file-item span {
            flex-grow: 1;
        }

        .file-item button {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
            border-radius: 3px;
        }

        .save-btn {
            position: absolute;
            top: 20px;
            right: 30px;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
        }

        .save-btn:hover {
            background-color: #218838;
        }
    </style>

    <script>
        $(document).ready(function() {
            $('#fileInput').on('change', function() {
                let files = this.files;
                let fileList = $('#fileList');
                fileList.empty(); // 이전 파일 목록 초기화

                // 파일 목록 추가
                for (let i = 0; i < files.length; i++) {
                    let fileItem = $('<div class="file-item"></div>');
                    let fileName = $('<span></span>').text(files[i].name);
                    let removeBtn = $('<button>삭제</button>').click(function() {
                        $(this).parent().remove(); // 파일 항목 삭제
                    });

                    fileItem.append(fileName).append(removeBtn);
                    fileList.append(fileItem);
                }
            });

            // 저장 버튼 클릭 시 동작
            $('#saveBtn').click(function() {
                alert('파일을 저장합니다.');
            });
        });
    </script>
</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <!-- Main Content -->
    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px; position: relative;">
        <main class="flex-grow-1">
            <h1>파일 업로드</h1>

            <!-- 저장 버튼 -->
            <button id="saveBtn" class="save-btn">저장하기</button>

            <!-- 파일 업로드 컨테이너 -->
            <div class="upload-container">
                <h2>새 미디어 업로드하기</h2>
                <div class="upload-btn-wrapper">
                    <button class="upload-btn">파일 선택</button>
                    <input type="file" id="fileInput" multiple>
                </div>
                <p>여기에 파일을 드래그하거나 파일 선택 버튼을 클릭하세요. 최대 업로드 파일 크기: 미정</p>
            </div>
            <!-- 업로드된 파일 목록 -->
            <div id="fileList" class="file-list">
                <!-- 동적으로 파일 목록이 추가됨 -->
            </div>
        </main>
    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
