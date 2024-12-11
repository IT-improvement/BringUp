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

    <!-- Plugins CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

    <!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <style>

    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <!-- Main Content -->
    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px; position: relative;">
        <main class="flex-grow-1 mt-4">
            <!-- 프로필 정보 -->
            <section class="card shadow-sm mb-4 p-4" id="profileInfo">
                <!-- 데이터가 동적으로 채워집니다 -->
            </section>

            <!-- 요약 정보 -->
            <section class="card shadow-sm mb-4 p-4" id="summaryInfo">
                <!-- 데이터가 동적으로 채워집니다 -->
            </section>

            <!-- 학력 정보 -->
            <section class="card shadow-sm mb-4 p-4" id="educationInfo">
                <h4>학력</h4>
                <hr>
                <ul class="list-unstyled" id="educationList">
                    <!-- 데이터가 동적으로 채워집니다 -->
                </ul>
            </section>

            <!-- 경력 정보 -->
            <section class="card shadow-sm mb-4 p-4" id="careerInfo">
                <h4>경력</h4>
                <hr>
                <ul class="list-unstyled" id="careerList">
                    <!-- 데이터가 동적으로 채워집니다 -->
                </ul>
            </section>
        </main>

    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const urlParams = new URLSearchParams(window.location.search);
        const cvIndex = urlParams.get("cvIndex");
        const accessToken = localStorage.getItem("accessToken");


        if (!cvIndex) {
            alert("유효하지 않은 이력서입니다.");
            return;
        }

        const apiUrl = `/cv/` + cvIndex;

        fetch(apiUrl, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch CV data.");
                }
                return response.json();
            })
            .then(data => {
                if (data.code === "SU") {
                    renderCvData(data);
                } else {

                }
            })
            .catch(error => {

            });

        function renderCvData(data) {
            const { cv, cvSchool, cvCareer, cvAward, cvCertificate } = data;
            const careerStatus = cvCareer && cvCareer.length > 0 ? "경력" : "신입";

            // 프로필 정보
            document.getElementById("profileInfo").innerHTML = `
        <div class="d-flex justify-content-between align-items-center">
            <div>
                <h2 class="mb-2">${'${cv.title}'} <span class="badge bg-primary">${'${careerStatus}'}</span></h2>
            </div>
        </div>
        <hr>
      `;

            // 요약 정보
            document.getElementById("summaryInfo").innerHTML = `
        <div class="d-flex justify-content-around text-center">
            <div>
                <i class="bi bi-mortarboard-fill fs-4"></i>
                <p class="mb-0">학력</p>
                <strong>${'${cvSchool.length > 0 ? cvSchool[0].schoolName : "-"}'}</strong>
            </div>
            <div>
                <i class="bi bi-briefcase-fill fs-4"></i>
                <p class="mb-0">경력</p>
                <strong>${'${cvCareer.length > 0 ? cvCareer[0].companyName : "-"}'}</strong>
            </div>
            <div>
                <i class="bi bi-trophy-fill fs-4"></i>
                <p class="mb-0">수상</p>
                <strong>${'${cvAward.length > 0 ? cvAward[0].title : "-"}'}</strong>
            </div>
            <div>
                <i class="bi bi-award-fill fs-4"></i>
                <p class="mb-0">자격증</p>
                <strong>${'${cvCertificate.length > 0 ? cvCertificate[0].title : "-"}'}</strong>
            </div>
        </div>`;

            // 학력 정보
            const educationList = document.getElementById("educationList");
            educationList.innerHTML = cvSchool.length
                ? cvSchool
                    .map(school => {
                        const startDate = formatDate(school.startDate);
                        const endDate = school.endDate ? formatDate(school.endDate) : "현재";

                        return `
                <li>
                    <strong>${'${school.schoolName}'} (${'${startDate}'} ~ ${'${endDate}'})</strong>
                    <p class="mb-0">${'${school.major ? school.major : ""}'} ${'${school.department ? school.department : ""}'}</p>
                    <small class="text-muted">${'${school.location ? school.location : ""}'}</small>
                </li>`;
                    })
                    .join("")
                : `<p class="text-muted">학력 정보가 없습니다.</p>`;

            // 경력 정보
            const careerList = document.getElementById("careerList");
            careerList.innerHTML = cvCareer.length
                ? cvCareer
                    .map(career => {
                        const startDate = formatDate(career.careerStart);
                        const endDate = career.careerEnd ? formatDate(career.careerEnd) : "현재";

                        return `
                <li class="mb-3">
                    <strong>${'${career.companyName}'}</strong> <span class="badge bg-secondary">${'${career.careerPosition}'}</span>
                    <p class="mb-1 text-muted">${'${startDate} ~ ${endDate}'}</p>
                    <p class="mb-0">${'${career.careerDepartment ? career.careerDepartment : ""}'}</p>
                    <small class="text-muted">${'${career.careerWork ? career.careerWork : ""}'}</small>
                </li>`;
                    })
                    .join("")
                : `<p class="text-muted">경력 정보가 없습니다.</p>`;
        }

        fetch("/portfolio/letter/info", {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + accessToken,
                "Content-Type": "application/json",
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch Letter data.");
                }
                return response.json();
            })
            .then(letterData => {
                if (letterData.code === "SU") {
                    renderLetterData(letterData);
                } else {
                    document.getElementById("careerInfo").insertAdjacentHTML(
                        "beforeend",
                        `<p class="text-muted">자소서 정보가 없습니다.</p>`
                    );
                }
            })
            .catch(error => {
                alert("자소서를 불러오는 중 오류가 발생했습니다.");
            });

        // 자소서 정보 렌더링
        function renderLetterData(letterData) {
            const { answer1, answer2, answer3 } = letterData;
            console.log(letterData);
            const careerInfoSection = document.getElementById("careerInfo");
            careerInfoSection.insertAdjacentHTML(
                "beforeend",
                `
            <hr>
            <h4>자소서</h4>
            <div class="card shadow-sm p-4 mb-4">
                <div>
                    <h5>질문 1</h5>
                    <p>${'${answer1 ? answer1 : "내용 없음"}'}</p>
                </div>
                <div>
                    <h5>질문 2</h5>
                    <p>${'${answer2 ? answer2 : "내용 없음"}'}</p>
                </div>
                <div>
                    <h5>질문 3</h5>
                    <p>${'${answer3 ? answer3 : "내용 없음"}'}</p>
                </div>
            </div>
        `
            );
        }

// 날짜 포맷 함수
        function formatDate(dateString) {
            if (!dateString) return "";
            const date = new Date(dateString);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, "0");
            const day = String(date.getDate()).padStart(2, "0");
            return `${'${year}'}-${'${month}'}-${'${day}'}`;
        }
    });

</script>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
