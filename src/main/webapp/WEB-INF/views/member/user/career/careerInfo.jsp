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
        // URL에서 cvIndex 추출
        const urlParams = new URLSearchParams(window.location.search);
        const cvIndex = urlParams.get("cvIndex");

        // cvIndex 로그로 확인
        console.log("Received cvIndex:", cvIndex);

        if (!cvIndex) {
            alert("유효하지 않은 이력서입니다.");
            return;
        }

        const apiUrl = `/cv/` + cvIndex;

        // 이력서 데이터 가져오기
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
                    console.log("Fetched CV data:", data);
                    renderCvData(data);
                } else {
                    console.error("Failed to fetch CV data:", data.message);
                    alert("이력서 데이터를 불러오는데 실패했습니다.");
                }
            })
            .catch(error => {
                console.error("Error fetching CV data:", error);
                alert("이력서를 불러오는 중 오류가 발생했습니다.");
            });

        // 데이터 렌더링 함수
        function renderCvData(data) {
            const { cv, cvSchool, cvCareer } = data;

            // 프로필 정보 렌더링
            document.getElementById("profileInfo").innerHTML = `
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h2 class="mb-2">${'${cv.name}'} <span class="badge bg-primary">${'${cv.mainCv ? "신입" : "경력"}'}</span></h2>
                    <p class="mb-0 text-muted">${'${cv.birthYear}'} (${'${new Date().getFullYear() - cv.birthYear}'}세)</p>
                    <small class="text-muted">기업이 이력서 열람/제안시 개인정보는 정상 노출됩니다.</small>
                </div>
            </div>
            <hr>
            <div class="d-flex flex-wrap">
                <div class="me-3">
                    <i class="bi bi-envelope-fill"></i> ${'${cv.email || "-"}'}
                </div>
                <div class="me-3">
                    <i class="bi bi-phone-fill"></i> ${'${cv.phone || "-"}'}
                </div>
                <div>
                    <i class="bi bi-geo-alt-fill"></i> ${'{cv.address || "-"}'}
                </div>
            </div>
        `;

            // 요약 정보 렌더링
            document.getElementById("summaryInfo").innerHTML = `
            <div class="d-flex justify-content-around text-center">
                <div>
                    <i class="bi bi-mortarboard-fill fs-4"></i>
                    <p class="mb-0">학력</p>
                    <strong>${'${cvSchool[0]?.schoolName || "-"}'}</strong>
                </div>
                <div>
                    <i class="bi bi-book-fill fs-4"></i>
                    <p class="mb-0">전공</p>
                    <strong>${'${cvSchool[0]?.major || "-"}'}</strong>
                </div>
                <div>
                    <i class="bi bi-briefcase-fill fs-4"></i>
                    <p class="mb-0">경력</p>
                    <strong>${'${cvCareer.length > 0 ? `${cvCareer[0].company} (${cvCareer[0].duration})` : "-"}'}</strong>
                </div>
                <div>
                    <i class="bi bi-handbag-fill fs-4"></i>
                    <p class="mb-0">희망연봉</p>
                    <strong>${'${cv.expectedSalary || "-"}'}</strong>
                </div>
                <div>
                    <i class="bi bi-folder-fill fs-4"></i>
                    <p class="mb-0">포트폴리오</p>
                    <strong>${'${cv.portfolio || "-"}'}</strong>
                </div>
            </div>
        `;

            // 학력 정보 렌더링
            const educationList = document.getElementById("educationList");
            educationList.innerHTML = cvSchool
                .map(
                    school => `
                <li>
                    <strong>${'${school.schoolName}'} (${'${school.degree}'})</strong>
                    <p class="mb-0">${'${school.major}'}</p>
                </li>
            `
                )
                .join("");

            // 경력 정보 렌더링
            const careerList = document.getElementById("careerList");
            careerList.innerHTML = cvCareer
                .map(
                    career => `
                <li>
                    <strong>${'${career.company}'}</strong>
                    <p class="mb-0">${'${career.startDate}'} - ${'${career.endDate}'} (${'${career.duration}'})</p>
                    <small class="text-muted">${'${career.position}'}</small>
                </li>
            `
                )
                .join("");
        }
    });

</script>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
