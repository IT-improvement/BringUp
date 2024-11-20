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
        /* 기본 카드 스타일 */
        .card {
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 1rem;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s ease, box-shadow 0.2s ease;
            background-color: #fff;
            border: 1px solid blue;

        }


        /* 각 학교 유형에 따른 line 색상 */
        .line-highschool {
            border-bottom: 1px solid #FF6347; /* 고등학교 - 토마토 색상 */
            margin: 0.5rem 0;
        }

        .line-majoruniversity {
            border-bottom: 1px solid #4682B4; /* 전문대 - 강철색 */
            margin: 0.5rem 0;
        }

        .line-university {
            border-bottom: 1px solid #32CD32; /* 대학교 - 라임색 */
            margin: 0.5rem 0;
        }

        .line-graduate {
            border-bottom: 1px solid #8A2BE2; /* 대학원 - 보라색 */
            margin: 0.5rem 0;
        }
        .education-header { font-size: 1.5rem; font-weight: bold; color: #333; margin-bottom: 1rem; display: flex; justify-content: space-between; align-items: center; }
        .btn-add { font-size: 1rem; color: #007bff; cursor: pointer; text-decoration: underline; }
        .edit-icon, .delete-icon { font-size: 1.2rem; color: blue; cursor: pointer; margin-left: 0.5rem; }
        .edit-icon:hover, .delete-icon:hover { color: #0056b3; }
        .education-card-header { display: flex; justify-content: space-between; align-items: center; font-weight: bold; color: #333; margin-bottom: 0.5rem; }
        .line { border-bottom: 1px solid #007bff; margin: 0.5rem 0; }
        .graduate-options { display: flex; align-items: center; gap: 10px; }
        .row-horizontal { display: flex; flex-wrap: wrap; gap: 1rem; }
        .row-horizontal .col { flex: 1 1 45%; min-width: 200px; }
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
            <div class="container p-4">
                <div class="education-header">
                    경력
                    <span class="btn-add" onclick="toggleEducationForm()">+ 추가</span>
                </div>
                <div id="initialMessage" class="mt-3 p-3 text-center" style="border: 1px solid #ddd; border-radius: 5px; color: #888;">
                    학력 내역을 입력해주세요
                </div>

                <!-- 학력 추가 폼 -->
                <div class="card" id="educationFormContainer" style="display: none;">
                    <h3>학력 정보 추가</h3>
                    <form id="educationForm">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="educationLevel" class="form-label">학력 구분</label>
                                <select class="form-select" id="educationLevel" onchange="updateFields()" required>
                                    <option value="">선택하세요</option>
                                    <option value="highSchool">고등학교 졸업</option>
                                    <option value="majorUniversity">전문대 (4년제)</option>
                                    <option value="university">대학교 (4년제)</option>
                                    <option value="graduate">대학원</option>

                                </select>
                            </div>
                        </div>
                        <input type="hidden" id="educationType" name="type" value="">
                        <div id="fieldsContainer">
                            <!-- 학력에 따라 동적으로 변경되는 필드들이 여기에 추가됩니다 -->
                        </div>
                        <div class="d-flex justify-content-end">
                            <button type="reset" class="btn btn-secondary me-2" onclick="toggleEducationForm()">취소</button>
                            <button type="submit" class="btn btn-primary">저장</button>
                        </div>
                    </form>
                </div>

                <!-- 학력 목록 -->
                <div id="schoolInfoList"></div>
            </div>
        </main>
    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
