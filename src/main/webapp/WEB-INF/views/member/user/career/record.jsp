<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>BringUp</title>

    <!-- 메타 태그 및 스타일 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">
    <meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">
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
        .card { box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1); border-radius: 8px; }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- 사이드바 -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
        <main class="flex-grow-1">
            <div class="container p-4">
                <h2 class="mb-4">학력 <span class="text-danger">(필수)</span></h2>
                <div class="card p-4">
                    <form id="educationForm">
                        <div class="row mb-3">
                            <div class="col-md-6 mb-3">
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
                            <button type="reset" class="btn btn-secondary me-2">취소</button>
                            <button type="submit" class="btn btn-primary">저장</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>

<script>

    function updateFields() {
        const level = document.getElementById('educationLevel').value;
        const container = document.getElementById('fieldsContainer');
        const typeField = document.getElementById('educationType');
        container.innerHTML = ''; // 기존 필드 초기화

        let fieldsHTML = '';

        if (level === 'highSchool') {
            typeField.value = "고등";
            fieldsHTML += `
                <div class="row mb-3">
                    <div class="col-md-6 mb-3">
                        <label for="schoolName" class="form-label">학교명</label>
                        <input type="text" class="form-control" id="schoolName" placeholder="고등학교 이름" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="schoolLocation" class="form-label">소재지</label>
                        <input type="text" class="form-control" id="schoolLocation" placeholder="고등학교 위치" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="admissionDate" class="form-label">입학 날짜</label>
                        <input type="date" class="form-control" id="admissionDate" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduationDate" class="form-label">졸업 날짜</label>
                        <input type="date" class="form-control" id="graduationDate" required>
                    </div>
                </div>`;
        } else if (level === 'majorUniversity') {
            typeField.value = "전문";
            fieldsHTML += `
                <div class="row mb-3">
                    <div class="col-md-6 mb-3">
                        <label for="universityName" class="form-label">학교명</label>
                        <input type="text" class="form-control" id="schoolName" placeholder="전문대 이름" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="universityLocation" class="form-label">소재지</label>
                        <input type="text" class="form-control" id="universityLocation" placeholder="전문대 위치" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="admissionDate" class="form-label">입학 날짜</label>
                        <input type="date" class="form-control" id="admissionDate" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduationDate" class="form-label">졸업 날짜</label>
                        <input type="date" class="form-control" id="graduationDate">
                    </div>
                </div>`;
        } else if (level === 'university') {
            typeField.value = "학사";
            fieldsHTML += `
                <div class="row mb-3">
                    <div class="col-md-6 mb-3">
                        <label for="universityName" class="form-label">학교명</label>
                        <input type="text" class="form-control" id="schoolName" placeholder="대학교 이름" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="universityLocation" class="form-label">소재지</label>
                        <input type="text" class="form-control" id="universityLocation" placeholder="대학교 위치" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="department" class="form-label">학과</label>
                        <input type="text" class="form-control" id="department" placeholder="학과" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="major" class="form-label">전공</label>
                        <input type="text" class="form-control" id="major" placeholder="전공명" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="admissionDate" class="form-label">입학 날짜</label>
                        <input type="date" class="form-control" id="admissionDate" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduationDate" class="form-label">졸업 날짜</label>
                        <input type="date" class="form-control" id="graduationDate">
                    </div>
                </div>
                <div id="dynamicFields" class="row mb-3"></div>
                <div class="row mb-3">
                    <div class="col-md-12 mb-3">
                        <button type="button" class="btn btn-outline-primary me-2" id="btn-학점" onclick="toggleField('학점')">+ 학점</button>
                        <button type="button" class="btn btn-outline-primary me-2" id="btn-추가전공" onclick="toggleField('추가전공')">+ 추가전공</button>
                        <button type="button" class="btn btn-outline-primary me-2" id="btn-주/야간" onclick="toggleField('주/야간')">+ 주/야간</button>
                    </div>
                </div>`;
        } else if (level === 'graduate') {
            typeField.value = "석사";
            fieldsHTML += `
                <div class="row mb-3">
                    <div class="col-md-6 mb-3">
                        <label for="graduateSchoolName" class="form-label">학교명</label>
                        <input type="text" class="form-control" id="schoolName" placeholder="대학원 이름" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduateMajor" class="form-label">전공</label>
                        <input type="text" class="form-control" id="graduateMajor" placeholder="전공명" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduateAdmissionDate" class="form-label">입학 날짜</label>
                        <input type="date" class="form-control" id="graduateAdmissionDate" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduateGraduationDate" class="form-label">졸업 날짜</label>
                        <input type="date" class="form-control" id="graduateGraduationDate">
                    </div>
                </div>`;
        }

        container.innerHTML = fieldsHTML;
    }

    function toggleField(fieldName) {
        const fieldId = `${'${fieldName}'}-field`;
        const existingField = document.getElementById(fieldId);
        const button = document.getElementById(`btn-${'${fieldName}'}`);

        if (existingField) {
            existingField.remove();
            button.innerHTML = `+ ${'${fieldName}'}`;
            button.classList.remove('btn-outline-danger');
            button.classList.add('btn-outline-primary');
        } else {
            const dynamicFields = document.getElementById('dynamicFields');
            const div = document.createElement('div');
            div.className = 'col-md-6 mb-3';
            div.id = fieldId;

            let inputHTML = '';
            switch (fieldName) {
                case '학점':
                    inputHTML = `
                        <div class="d-flex">
                            <input type="text" class="form-control me-2" placeholder="학점 입력" required id="maxGrade">
                            <select class="form-select" required>
                                <option value="">기준학점</option>
                                <option value="4.0">4.0</option>
                                <option value="4.3">4.3</option>
                                <option value="4.5">4.5</option>
                                <option value="5.0">5.0</option>
                                <option value="7.0">7.0</option>
                                <option value="100">100</option>
                            </select>
                        </div>`;
                    break;
                case '추가전공':
                    inputHTML = `<input type="text" class="form-control" placeholder="추가 전공 입력" required>`;
                    break;
                case '주/야간':
                    inputHTML = `
                        <select class="form-select" required>
                            <option value="">선택하세요</option>
                            <option value="주간">주간</option>
                            <option value="야간">야간</option>
                        </select>`;
                    break;
            }

            div.innerHTML = `<label class="form-label">${'${fieldName}'}</label>${'${inputHTML}'}`;
            dynamicFields.appendChild(div);

            button.innerHTML = `- ${'${fieldName}'}`;
            button.classList.remove('btn-outline-primary');
            button.classList.add('btn-outline-danger');
        }
    }

    function submitEducationForm() {
        const accessToken = localStorage.getItem("accessToken");

        if (!accessToken) {
            alert("로그인이 필요합니다.");
            window.location.href = "/login";
            return;
        }

        const data = {
            type: document.getElementById('educationType').value,
            schoolName: document.getElementById('schoolName') ? document.getElementById('schoolName').value : null,
            location: document.getElementById('schoolLocation') ? document.getElementById('schoolLocation').value :
                document.getElementById('universityLocation') ? document.getElementById('universityLocation').value : null,
            startDate: document.getElementById('admissionDate') ? document.getElementById('admissionDate').value : null,
            endDate: document.getElementById('graduationDate') ? document.getElementById('graduationDate').value : null,
            department: document.getElementById('department') ? document.getElementById('department').value : null,
            major: document.getElementById('major') ? document.getElementById('major').value :
                document.getElementById('graduateMajor') ? document.getElementById('graduateMajor').value : null,
            grade: document.getElementById('grade') ? document.getElementById('grade').value : null,
            maxGrade: document.getElementById('maxGrade') ? document.getElementById('maxGrade').value : null
        };

        fetch("/school/insert", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ` + accessToken,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    alert("학력 정보가 저장되었습니다.");
                    location.reload();
                } else {
                    alert("학력 정보 저장 실패. 다시 시도해 주세요.");
                }
            })
            .catch(error => console.error("Error:", error));
    }

    document.getElementById('educationForm').addEventListener('submit', function(event) {
        event.preventDefault();
        submitEducationForm();
    });
</script>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
