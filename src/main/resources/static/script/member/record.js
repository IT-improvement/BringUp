// 학력 정보 가져와서 표시하기
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
            } else {
                alert("학력 정보를 가져오는데 실패했습니다.");
            }
        })
        .catch(error => console.error("Error:", error));
}

function displaySchoolInfo(schoolData) {
    const container = document.getElementById("schoolInfoList");
    container.innerHTML = ''; // 초기화

    if (schoolData.highSchool) {
        container.innerHTML += renderSchoolCard("고등학교", schoolData.highSchool, ["schoolName", "location", "startDate", "endDate"]);
    }

    schoolData.universityList.forEach(school => {
        container.innerHTML += renderSchoolCard("대학교", school, ["schoolName", "location", "department", "major", "grade", "maxGrade", "startDate", "endDate"]);
    });

    schoolData.graduateList.forEach(school => {
        container.innerHTML += renderSchoolCard("대학원", school, ["schoolName", "location", "department", "major", "grade", "maxGrade", "startDate", "endDate"]);
    });
}

function renderSchoolCard(schoolType, school, fields) {
    const startDate = new Date(school.startDate).toLocaleDateString();
    const endDate = school.endDate ? new Date(school.endDate).toLocaleDateString() : "N/A";
    let fieldContents = '';

    // 필드 리스트에 따라 동적으로 내용을 생성
    if (fields.includes("schoolName")) fieldContents += `<p><strong>학교명:</strong> ${school.schoolName}</p>`;
    if (fields.includes("location")) fieldContents += `<p><strong>소재지:</strong> ${'${school.location || "N/A"}'}</p>`;
    if (fields.includes("department")) fieldContents += `<p><strong>학과:</strong> ${'${school.department || "N/A"}'}</p>`;
    if (fields.includes("major")) fieldContents += `<p><strong>전공:</strong> ${'${school.major || "N/A"}'}</p>`;
    if (fields.includes("grade")) fieldContents += `<p><strong>학점:</strong> ${'${school.grade || "N/A"}'} / ${'${school.maxGrade || "N/A"}'}</p>`;
    if (fields.includes("startDate") && fields.includes("endDate")) fieldContents += `<p><strong>기간:</strong> ${    '${startDate}'} ~ ${'${endDate}'}</p>`;


    return `
    <div class="card">
        <div class="education-card-header">
            <span>${'${schoolType}'} (${'${school.type}'})</span>
            <span>
                <i class="bi bi-pencil-square edit-icon" onclick="editSchoolInfo(${'${school.id}'})"></i>
                <i class="bi bi-trash delete-icon" onclick="deleteSchoolInfo(${'${school.id}'})"></i>
            </span>
        </div>
        <div class="line"></div>
        ${'${fieldContents}'}
    </div>`;
}

// 학력 추가 / 수정 폼 토글
function toggleEducationForm() {
    const formContainer = document.getElementById("educationFormContainer");
    formContainer.style.display = formContainer.style.display === "none" ? "block" : "none";
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
                        <label for="admissionDate" class="form-label">입학 날짜</label>
                        <input type="date" class="form-control" id="admissionDate" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="graduationDate" class="form-label">졸업 날짜</label>
                        <input type="date" class="form-control" id="graduationDate" required>
                    </div>
                </div>`;
    } else if (level === 'majorUniversity' || level === 'university' || level === 'graduate') {
        typeField.value = level === 'majorUniversity' ? "전문" : level === 'university' ? "학사" : "석사";
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
    }
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

    const data = {
        type: document.getElementById('educationType').value,
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

