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
        .toggle-switch {
            position: relative;
            display: inline-block;
            width: 40px;
            height: 20px;
        }

        .toggle-switch input[type="checkbox"] {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .toggle-switch label {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: #ccc;
            border-radius: 20px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .toggle-switch label::before {
            content: "";
            position: absolute;
            width: 16px;
            height: 16px;
            left: 2px;
            bottom: 2px;
            background-color: white;
            border-radius: 50%;
            transition: transform 0.3s;
        }

        .toggle-switch input[type="checkbox"]:checked + label {
            background-color: #007bff;
        }

        .toggle-switch input[type="checkbox"]:checked + label::before {
            transform: translateX(20px);
        }
        .career-align-items-end{
            -webkit-box-align: end !important;
            -ms-flex-align: end !important;
            align-items: flex-end !important;
            justify-content: center;
        }
        .p-3{
            border-bottom: solid 1px;
        }
        .mt-4{
            border-top: solid 1px;
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
            <h2>경력</h2>
            <div class="container p-4">
                <!-- 경력 섹션 헤더 -->
                <div class="header d-flex justify-content-between align-items-center mb-4">
                    <div></div>
                    <div>
                        <span class="btn-add" onclick="toggleCareerForm()">+ 추가</span>
                    </div>
                </div>

                <!-- 안내 메시지 -->
                <div id="initialMessage" class="mt-3 p-3 text-center" style="border: 1px solid #ddd; border-radius: 5px; color: #888; ">
                    자격/어학/수상 내역을 입력해주세요
                </div>
                <!-- 경력 추가 폼 -->
                <div class="card mt-4" id="careerFormContainer" style="display: none; border: 1px solid #0056b3; padding: 20px; border-radius: 10px;">
                    <h3 class="form-title mb-4" style="font-size: 1.2rem; font-weight: bold; color: #0056b3;">경력 추가</h3>
                    <form id="careerForm" onsubmit="event.preventDefault(); addCareer();">
                        <div class="row mb-3">
                            <!-- 회사명 -->
                            <div class="col-md-6">
                                <label for="companyName" class="form-label">회사명 *</label>
                                <div class="input-group">
                                    <input type="text" id="companyName" class="form-control" placeholder="회사명을 입력하세요" required>
                                    <button type="button" class="btn btn-outline-secondary">🔍</button>
                                </div>
                            </div>

                            <!-- 재직중 토글 -->
                            <div class="col-md-6 d-flex career-align-items-end">
                                <div class="form-group">
                                    <label class="form-label mb-1">재직중</label>
                                    <div class="toggle-switch">
                                        <input type="checkbox" id="isWorkingToggle">
                                        <label for="isWorkingToggle"></label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <!-- 입사일 -->
                            <div class="col-md-6">
                                <label for="careerStartDate" class="form-label">입사일 *</label>
                                <input type="date" id="careerStartDate" class="form-control" required>
                            </div>

                            <!-- 퇴사일 -->
                            <div class="col-md-6">
                                <label for="careerEndDate" class="form-label">퇴사일</label>
                                <input type="date" id="careerEndDate" class="form-control">
                            </div>
                        </div>

                        <div class="row mb-3">
                            <!-- 직무 -->
                            <div class="col-md-6">
                                <label for="careerPosition" class="form-label">직무 *</label>
                                <input type="text" id="careerPosition" class="form-control" placeholder="직무를 입력하세요" required>
                            </div>

                            <!-- 근무부서 -->
                            <div class="col-md-6">
                                <label for="careerDepartment" class="form-label">근무부서</label>
                                <input type="text" id="careerDepartment" class="form-control" placeholder="부서를 입력하세요">
                            </div>
                        </div>

                        <div class="row mb-3">
                            <!-- 담당업무 -->
                            <div class="col-md-12">
                                <label for="careerWork" class="form-label">담당업무</label>
                                <textarea id="careerWork" class="form-control" rows="4" placeholder="담당 업무를 입력하세요"></textarea>
                                <small class="text-muted">
                                    - 상세한 업무 내용을 입력해주세요.<br>
                                    - 경력 프로젝트 내용을 적을 경우, 역할/참여기간/이슈/성과를 기준으로 요약해주세요!
                                </small>
                            </div>
                        </div>

                        <div class="form-actions d-flex justify-content-end">
                            <button type="button" class="btn btn-secondary me-2" onclick="toggleCareerForm()">취소</button>
                            <button type="submit" class="btn btn-primary" onclick="insertCareer()">저장</button>
                        </div>
                    </form>
                </div>



                <!-- 경력 목록 -->
                <div id="careerList" class="mt-4">
                    <!-- 경력 항목은 동적으로 추가됩니다 -->
                </div>
            </div>
        </main>
    </div>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        fetchCareerList();
    });
    function toggleCareerForm() {
        const formContainer = document.getElementById('careerFormContainer');
        document.getElementById('initialMessage').style.display = 'none';
        formContainer.style.display = formContainer.style.display === 'none' ? 'block' : 'none';
    }

    function insertCareer() {
        const accessToken = localStorage.getItem("accessToken");

        if (!accessToken) {
            alert("로그인이 필요합니다.");
            return;
        }

        // 입력값 가져오기
        const careerStart = document.getElementById("careerStartDate").value;
        const careerEnd = document.getElementById("careerEndDate").disabled ? null : document.getElementById("careerEndDate").value;
        const companyName = document.getElementById("companyName").value;
        const careerPosition = document.getElementById("careerPosition").value;
        const careerDepartment = document.getElementById("careerDepartment").value;
        const careerWork = document.getElementById("careerWork").value;

        // 요청 데이터 구성
        const careerData = {
            careerStart: careerStart,
            careerEnd: careerEnd,
            companyName: companyName,
            careerPosition: careerPosition,
            careerDepartment: careerDepartment,
            careerWork: careerWork
        };

        // API 요청
        fetch('/portfolio/career/insert', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ` + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(careerData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.code === "SU") {
                    alert("경력이 성공적으로 추가되었습니다.");
                    fetchCareerList(); // 리스트 갱신 함수 호출
                    toggleCareerForm(); // 폼 닫기
                } else {
                    alert("경력 추가에 실패했습니다. 다시 시도해주세요.");
                }
            })
            .catch(error => console.error('Error:', error));
    }

    // 경력 목록 가져오기
    function fetchCareerList() {
        const accessToken = localStorage.getItem("accessToken");

        if (!accessToken) {
            alert("로그인이 필요합니다.");
            return;
        }

        fetch('/portfolio/career/list', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ` + accessToken,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.code === "SU" && Array.isArray(data.list)) {
                    displayCareerList(data.list);
                } else {
                    console.error("Invalid response format:", data);
                    document.getElementById('careerList').innerHTML = '<p class="text-muted">경력 데이터를 불러올 수 없습니다.</p>';                }
            })
            .catch(error => console.error('Error fetching career list:', error));
    }

    // 경력 목록 표시
    function displayCareerList(careers) {
        const careerListContainer = document.getElementById('careerList');
        careerListContainer.innerHTML = ''; // 기존 목록 초기화

        if (careers.length === 0) {
            document.getElementById('initialMessage').style.display = 'block'; // 초기 메시지 표시
            return;
        }

        document.getElementById('initialMessage').style.display = 'none'; // 초기 메시지 숨기기

        careers.forEach(career => {
            const careerCard = document.createElement('div');
            careerCard.className = 'career-card mb-3 p-3 d-flex align-items-center justify-content-between ';
            careerCard.id = `career-${'${career.careerIndex}'}`;

            careerCard.innerHTML = `
            <div class="d-flex flex-column">
                <h5 class="mb-1">${'${career.companyName || "회사명 없음"}'}</h5>
                <div class="d-flex align-items-center">
                    <span class="me-3">${'${career.careerDepartment || "부서 없음"}'} / ${'${career.careerPosition || "직무 없음"}'}</span>
                    <span>재직 기간 : ${'${career.careerStart || "시작 날짜 없음"}'} ~ ${'${career.careerEnd || "재직 중"}'}</span>
                </div>
                <div class="text-muted">${'${career.careerWork || "업무 내용 없음"}'}</div>

            </div>
            <div class="d-flex align-items-center">
                <i class="bi bi-pencil-square edit-icon me-3" onclick="editCareer(${'${career.careerIndex}'})"></i>
                <i class="bi bi-trash delete-icon" onclick="deleteCareer(${'${career.careerIndex}'})"></i>
            </div>
        `;

            careerListContainer.appendChild(careerCard);
        });
    }

    // 경력 삭제
    function deleteCareer(index) {

        fetch(`/portfolio/career/delete?index=${'${index}'}`, {
            method: 'DELETE',
            headers: {
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.code === "SU") {
                    alert("경력이 삭제되었습니다.");
                    fetchCareerList(); // 리스트 갱신
                } else {
                    alert("경력 삭제에 실패했습니다.");
                }
            })
            .catch(error => console.error('Error:', error));
    }

    document.getElementById("isWorkingToggle").addEventListener("change", function () {
        const endDateField = document.getElementById("careerEndDate");
        if (this.checked) {
            endDateField.value = ""; // 재직 중일 경우 퇴사일 비우기
            endDateField.disabled = true; // 퇴사일 비활성화
        } else {
            endDateField.disabled = false; // 퇴사일 활성화
        }
    });

</script>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
