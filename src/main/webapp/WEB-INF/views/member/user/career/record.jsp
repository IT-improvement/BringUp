    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="ko">
    <head>
        <title>BringUp</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="author" content="Webestica.com">
        <meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">
        <script src="/resources/script/common/darkmode/darkmode.js"></script>
        <link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
        <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/apexcharts/css/apexcharts.css">
        <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">
        <link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">
        <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
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
    <jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

    <div class="d-flex flex-grow-1">
        <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

        <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
            <main class="flex-grow-1">
                <h2>학력</h2>
                <div class="container p-4">
                    <div></div>
                    <div class="education-header">

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

    <script>
        // 학력 정보 표시 및 메시지 상태 조정
        function fetchSchoolInfo() {
            const accessToken = localStorage.getItem("accessToken");

            if (!accessToken) {
                alert("로그인이 필요합니다.");
                window.location.href = "/login";
                return;
            }

            fetch("/school/info/list", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ` + accessToken,
                    "Content-Type": "application/json"
                }
            })
                .then(response => response.json())
                .then(data => {
                    if (data.code === "SU") {
                        displaySchoolInfo(data);
                        const initialMessage = document.getElementById("initialMessage");
                        if (data.universityList.length === 0 && !data.highSchool && !data.graduateList.length) {
                            initialMessage.style.display = "block"; // 학력 내역이 없으면 메시지 표시
                        } else {
                            initialMessage.style.display = "none"; // 학력 내역이 있으면 메시지 숨기기
                        }
                    } else {
                        alert("학력 정보를 가져오는데 실패했습니다.");
                    }
                })
                .catch(error => console.error("Error:", error));
        }

        document.addEventListener("DOMContentLoaded", fetchSchoolInfo);

        function displaySchoolInfo(schoolData) {
            const container = document.getElementById("schoolInfoList");
            container.innerHTML = ''; // 초기화

            if (schoolData.highSchool) {
                container.innerHTML += renderSchoolCard("고등학교", schoolData.highSchool, ["schoolName", "location", "startStatus","endStatus", "startDate", "endDate"]);
            }

            schoolData.universityList.forEach(school => {
                container.innerHTML += renderSchoolCard("대학교", school, ["schoolName", "location", "department", "major", "grade", "maxGrade", "startDate", "endDate"]);
            });

            schoolData.graduateList.forEach(school => {
                container.innerHTML += renderSchoolCard("대학원", school, ["schoolName", "location", "department", "major", "grade", "maxGrade", "startDate", "endDate"]);
            });

            // 학력 내역이 없다면 메시지 표시
            if (container.children.length === 0) {
                document.getElementById("initialMessage").style.display = "block";
            }
        }

        function renderSchoolCard(schoolType, school, fields) {
            const lineClass = {
                "고등학교": "line-highschool",
                "전문대": "line-majoruniversity",
                "대학교": "line-university",
                "대학원": "line-graduate"
            }[schoolType] || "line"; // 기본적으로 line으로 설정

            const startDate = new Date(school.startDate).toLocaleDateString();
            const endDate = school.endDate ? new Date(school.endDate).toLocaleDateString() : "N/A";
            let fieldContents = '';
            if (fields.includes("schoolName") && school.schoolName) {
                fieldContents += `<p><strong>${'${school.schoolName}'}</strong></p>`;
            }
            if (fields.includes("location") && school.location) {
                fieldContents += `<p><strong>소재지:</strong> ${'${school.location}'}</p>`;
            }
            if (fields.includes("endStatus") && school.endStatus) {
                fieldContents += `<p><strong>졸업 여부:</strong> ${'${school.endStatus}'}</p>`;
            }
            if (fields.includes("department") && school.department) {
                fieldContents += `<p><strong>학과:</strong> ${'${school.department}'}</p>`;
            }
            if (fields.includes("major") && school.major) {
                fieldContents += `<p><strong>전공:</strong> ${'${school.major}'}</p>`;
            }
            if (fields.includes("grade") && school.grade && school.maxGrade) {
                fieldContents += `<p><strong>학점:</strong> ${'${school.grade}'} / ${'${school.maxGrade}'}</p>`;
            }
            if (fields.includes("startDate") && fields.includes("endDate") && school.startDate && school.endDate) {
                fieldContents += `<p><strong>기간:</strong> ${'${startDate}'} ~ ${'${endDate}'}</p>`;
            }

            return `
        <div class="card" id="school-card-${'${school.schoolIndex}'}">
            <div class="education-card-header">
                <span>${'${schoolType}'} (${'${school.type}'})</span>
                <span>
                    <i class="bi bi-pencil-square edit-icon" onclick="editSchoolInfo(${'${school.schoolIndex}'})"></i>
                    <i class="bi bi-trash delete-icon" onclick="deleteSchoolInfo(${'${school.schoolIndex}'})"></i>
                </span>
            </div>
            <div class="${'${lineClass}'}"></div>
            ${'${fieldContents}'}
        </div>`;
        }


        // 학력 추가/수정 폼 토글
        function toggleEducationForm() {
            const formContainer = document.getElementById("educationFormContainer");
            const initialMessage = document.getElementById("initialMessage");

            if (formContainer.style.display === "none") {
                formContainer.style.display = "block";
                initialMessage.style.display = "none"; // 초기 메시지 숨기기
            } else {
                formContainer.style.display = "none";
                if (document.getElementById("schoolInfoList").children.length === 0) {
                    initialMessage.style.display = "block"; // 학력 내역이 없을 때만 메시지 표시
                }
            }
        }
        document.addEventListener("DOMContentLoaded", fetchSchoolInfo);

        function updateFields() {
            const level = document.getElementById('educationLevel').value;
            const container = document.getElementById('fieldsContainer');
            const typeField = document.getElementById('educationType');
            container.innerHTML = ''; // 기존 필드 초기화

            if (level === 'highSchool') {
                typeField.value = "고등";
                container.innerHTML = `
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
                            <label for="startStatus" class="form-label">입학 여부</label>
                            <select class="form-select" id="startStatus" required>
                                <option value="">선택하세요</option>
                                <option value="입학">입학</option>
                                <option value="편입">편입</option>
                                <option value="조기 입학">조기 입학</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="endStatus" class="form-label">졸업 여부</label>
                            <select class="form-select" id="endStatus" required>
                                <option value="">선택하세요</option>
                                <option value="졸업">졸업</option>
                                <option value="중퇴">중퇴</option>
                                <option value="휴학">휴학</option>
                            </select>
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
            } else if (level === 'majorUniversity' || level === 'university') {
                typeField.value = level === 'majorUniversity' ? "전문" : "학사";
                container.innerHTML = `
                    <div class="row mb-3">
                        <div class="col-md-6 mb-3">
                            <label for="schoolName" class="form-label">학교명</label>
                            <input type="text" class="form-control" id="schoolName" placeholder="학교 이름" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="schoolLocation" class="form-label">소재지</label>
                            <input type="text" class="form-control" id="schoolLocation" placeholder="학교 위치" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="startStatus" class="form-label">입학 여부</label>
                            <select class="form-select" id="startStatus" required>
                                <option value="">선택하세요</option>
                                <option value="입학">입학</option>
                                <option value="편입">편입</option>
                                <option value="조기 입학">조기 입학</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="endStatus" class="form-label">졸업 여부</label>
                            <select class="form-select" id="endStatus" required>
                                <option value="">선택하세요</option>
                                <option value="졸업">졸업</option>
                                <option value="중퇴">중퇴</option>
                                <option value="휴학">휴학</option>
                            </select>
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
                typeField.value = "대학원";
                container.innerHTML = `
              <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">과정 선택</label>
                        <div class="graduate-options">
                              <div class="graduate-options">
                    <input type="radio" id="master" name="graduateType" value="석사" class="form-check-input" onclick="setGraduateType('석사')">
                    <label for="master" class="form-check-label">석사</label>
                    <input type="radio" id="doctor" name="graduateType" value="박사" class="form-check-input ms-3" onclick="setGraduateType('박사')">
                    <label for="doctor" class="form-check-label">박사</label>
                </div>
                        </div>
                    </div>
                </div>
 <div class="row mb-3">
                    <div class="col-md-6 mb-3">
                        <label for="schoolName" class="form-label">학교명</label>
                        <input type="text" class="form-control" id="schoolName" placeholder="학교 이름" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="schoolLocation" class="form-label">소재지</label>
                        <input type="text" class="form-control" id="schoolLocation" placeholder="학교 위치" required>
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
   <div class="col-md-6 mb-3">
                            <label for="startStatus" class="form-label">입학 여부</label>
                            <select class="form-select" id="startStatus" required>
                                <option value="">선택하세요</option>
                                <option value="입학">입학</option>
                                <option value="편입">편입</option>
                                <option value="조기 입학">조기 입학</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="endStatus" class="form-label">졸업 여부</label>
                            <select class="form-select" id="endStatus" required>
                                <option value="">선택하세요</option>
                                <option value="졸업">졸업</option>
                                <option value="중퇴">중퇴</option>
                                <option value="휴학">휴학</option>
                            </select>
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
            }
        }
        function setGraduateType(educationType) {
            document.getElementById('educationType').value = educationType;
            console.log(educationType);
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
                                <input type="text" class="form-control me-2" placeholder="학점 입력" required id="grade">
                                <select class="form-select" required id="maxGrade">
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
                        inputHTML = `<input type="text" class="form-control" placeholder="추가 전공 입력" required id="double_major">`;
                        break;
                    case '주/야간':
                        inputHTML = `
                            <select class="form-select" required id="attendanceType">
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
            const educationType = document.getElementById('educationType');
            console.log("Education Type:", educationType.value); // Console에 타입 확인


            const data = {
                type: document.getElementById('educationType').value,
                schoolName: document.getElementById('schoolName') ? document.getElementById('schoolName').value : null,
                location: document.getElementById('schoolLocation') ? document.getElementById('schoolLocation').value : null,
                startDate: document.getElementById('admissionDate') ? document.getElementById('admissionDate').value : null,
                endDate: document.getElementById('graduationDate') ? document.getElementById('graduationDate').value : null,
                department: document.getElementById('department') ? document.getElementById('department').value : null,
                major: document.getElementById('major') ?   document.getElementById('major').value : null,
                double_major: document.getElementById('double_major') ? document.getElementById('double_major').value : null,
                grade: document.getElementById('grade') ? document.getElementById('grade').value : null,
                maxGrade: document.getElementById('maxGrade') ? document.getElementById('maxGrade').value : null,
                startStatus: document.getElementById('startStatus') ? document.getElementById('startStatus').value : null,
                endStatus: document.getElementById('endStatus') ? document.getElementById('endStatus').value : null,
                attendanceType: document.getElementById('attendanceType') ? document.getElementById('attendanceType').value : null
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

        document.getElementById('educationForm').addEventListener('submit', function (event) {
            event.preventDefault();
            submitEducationForm();
        });

        function editSchoolInfo(schoolIndex) {
            // 학력 카드 내용을 수정할 수 있는 폼으로 대체
            const schoolCard = document.getElementById(`school-card-${'${schoolIndex}'}`);
            const schoolTypeText = schoolCard.querySelector('.education-card-header span').textContent;
            const schoolType = schoolTypeText.match(/\((.*?)\)/)[1];  // 괄호 안의 텍스트를 추출
            console.log()

            // 필드 내용이 학교 유형에 따라 다르게 보이도록 구현
            let editFormHTML = `

            <h3>학력 정보 수정</h3>
            <form id="editForm-${'${schoolIndex}'}}">
                  <input type="hidden" id="schoolIndex" value="${'${schoolIndex}'}">
            <input type="hidden" id="schoolType" value="${'${schoolType}'}"> <!-- schoolType을 히든 필드로 추가 -->
                <div class="row mb-3">
                    <div class="col-md-6 mb-3">
                        <label for="schoolName" class="form-label">학교명</label>
                        <input type="text" class="form-control" id="schoolName" placeholder="학교명 입력" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="schoolLocation" class="form-label">소재지</label>
                        <input type="text" class="form-control" id="schoolLocation" placeholder="소재지 입력" required>
                    </div>
                      <div class="col-md-6 mb-3">
                            <label for="startStatus" class="form-label">입학 여부</label>
                            <select class="form-select" id="startStatus" required>
                                <option value="">선택하세요</option>
                                <option value="입학">입학</option>
                                <option value="편입">편입</option>
                                <option value="조기 입학">조기 입학</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="endStatus" class="form-label">졸업 여부</label>
                            <select class="form-select" id="endStatus" required>
                                <option value="">선택하세요</option>
                                <option value="졸업">졸업</option>
                                <option value="중퇴">중퇴</option>
                                <option value="휴학">휴학</option>
                            </select>
                        </div>
                    `;
            console.log(schoolType);

            // 학교 유형에 따라 다르게 필드를 추가
            if (schoolType === '고등') {
                editFormHTML += `
                    <div class="col-md-6 mb-3">
                        <label for="admissionDate" class="form-label">입학 날짜</label>
                        <input type="date" class="form-control" id="admissionDate" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduationDate" class="form-label">졸업 날짜</label>
                        <input type="date" class="form-control" id="graduationDate">
                    </div>`;
            } else if (schoolType === '학사' || schoolType === '전문') {
                editFormHTML += `
                    <div class="col-md-6 mb-3">
                        <label for="department" class="form-label">학과</label>
                        <input type="text" class="form-control" id="department" placeholder="학과명 입력" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="major" class="form-label">전공</label>
                        <input type="text" class="form-control" id="major" placeholder="전공 입력" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="admissionDate" class="form-label">입학 날짜</label>
                        <input type="date" class="form-control" id="admissionDate" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduationDate" class="form-label">졸업 날짜</label>
                        <input type="date" class="form-control" id="graduationDate">
                    </div>
                    <div id="dynamicFields" class="row mb-3"></div>
                    <div class="row mb-3">
                        <div class="col-md-12 mb-3">
                          <button type="button" class="btn btn-outline-primary me-2" id="btn-학점" onclick="toggleField2('학점')">+ 학점</button>
                            <button type="button" class="btn btn-outline-primary me-2" id="btn-추가전공" onclick="toggleField2('추가전공')">+ 추가전공</button>
                            <button type="button" class="btn btn-outline-primary me-2" id="btn-주/야간" onclick="toggleField2('주/야간')">+ 주/야간</button>
                        </div>
                    </div>`;
            } else if (schoolType === '석사' || schoolType ==='박사') {
                editFormHTML += `
                   <div class="col-md-6 mb-3">
                        <label for="department" class="form-label">학과</label>
                        <input type="text" class="form-control" id="department" placeholder="학과명 입력" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="major" class="form-label">전공</label>
                        <input type="text" class="form-control" id="major" placeholder="전공 입력" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="admissionDate" class="form-label">입학 날짜</label>
                        <input type="date" class="form-control" id="admissionDate" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduationDate" class="form-label">졸업 날짜</label>
                        <input type="date" class="form-control" id="graduationDate">
                    </div>
                    <div id="dynamicFields" class="row mb-3"></div>
                    <div class="row mb-3">
                        <div class="col-md-12 mb-3">
                          <button type="button" class="btn btn-outline-primary me-2" id="btn-학점" onclick="toggleField2('학점')">+ 학점</button>
                            <button type="button" class="btn btn-outline-primary me-2" id="btn-추가전공" onclick="toggleField2('추가전공')">+ 추가전공</button>
                            <button type="button" class="btn btn-outline-primary me-2" id="btn-주/야간" onclick="toggleField2('주/야간')">+ 주/야간</button>
                        </div>
                    </div>`;
            }

            // 마무리 버튼들
            editFormHTML += `
                <div class="d-flex justify-content-end mt-3">
                    <button type="button" class="btn btn-secondary me-2" onclick="cancelEdit(${'${schoolIndex}'})">취소</button>
                    <button type="button" class="btn btn-primary" onclick="saveEdit()">저장</button>
                </div>
            </form>
      `;

            // 기존 학력 카드 내용을 수정 폼으로 변경
            schoolCard.innerHTML = editFormHTML;
        }

        function toggleField2(fieldName) {
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
                                <input type="text" class="form-control me-2" placeholder="학점 입력" required id="grade">
                                <select class="form-select" required id="maxGrade">
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
                        inputHTML = `<input type="text" class="form-control" placeholder="추가 전공 입력" required id="double_major">`;
                        break;
                    case '주/야간':
                        inputHTML = `
                            <select class="form-select" required id="attendanceType">
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

        function saveEdit() {
            const accessToken = localStorage.getItem("accessToken");

            if (!accessToken) {
            alert("로그인이 필요합니다.");
            window.location.href = "/login";
            return;
        }

            const data = {
                type: document.getElementById('schoolType').value, // schoolType을 데이터 객체에 포함
                schoolName: document.getElementById('schoolName') ? document.getElementById('schoolName').value : null,
                location: document.getElementById('schoolLocation') ? document.getElementById('schoolLocation').value : null,
                startDate: document.getElementById('admissionDate') ? document.getElementById('admissionDate').value : null,
                endDate: document.getElementById('graduationDate') ? document.getElementById('graduationDate').value : null,
                department: document.getElementById('department') ? document.getElementById('department').value : null,
                major: document.getElementById('major') ? document.getElementById('major').value : null,
                double_major: document.getElementById('double_major') ? document.getElementById('double_major').value : null,
                grade: document.getElementById('grade') ? document.getElementById('grade').value : null,
                maxGrade: document.getElementById('maxGrade') ? document.getElementById('maxGrade').value : null,
                startStatus: document.getElementById('startStatus') ? document.getElementById('startStatus').value : null,
                endStatus: document.getElementById('endStatus') ? document.getElementById('endStatus').value : null,
                attendanceType: document.getElementById('attendanceType') ? document.getElementById('attendanceType').value : null
        };

            // 수정된 학력 정보를 insert 컨트롤러로 다시 전송
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
            location.reload(); // 페이지를 새로고침하여 변경된 내용 반영
        } else {
            alert("학력 정보 저장 실패. 다시 시도해 주세요.");
        }
        })
            .catch(error => console.error("Error:", error));
        }

            function cancelEdit(schoolIndex) {
            // 학력 정보를 다시 불러와서 원래 내용을 표시합니다.
            fetchSchoolInfo();
        }

        function deleteSchoolInfo(schoolIndex) {
            if (!confirm("해당 학력 정보를 삭제하시겠습니까?")) {
                return;
            }

            fetch(`/school/delete/` + schoolIndex, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                }
            })
                .then(response => {
                    if (response.ok) {
                        alert("학력 정보가 삭제되었습니다.");
                        document.getElementById(`school-card-${'${schoolIndex}'}`).remove(); // 삭제된 카드 제거

                        // 학력 내역이 모두 삭제되었으면 초기 메시지 표시
                        if (document.getElementById("schoolInfoList").children.length === 0) {
                            document.getElementById("initialMessage").style.display = "block";
                        }
                    } else {
                        alert("학력 정보 삭제 실패. 다시 시도해 주세요.");
                    }
                })
                .catch(error => console.error("Error:", error));
        }
    </script>

    <jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true"/>
    </body>
    </html>
