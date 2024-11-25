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
        .user-info img {
            border: 2px solid #dee2e6; /* 프로필 이미지 테두리 */
        }

        .card {
            background-color: #f8f9fa;
            border: 1px solid #e9ecef;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .card:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }
        .education{
            border-top: solid 1px #d7dce5;
            padding-top: 2%;
        }
        .experience{
            border-top: solid 1px #d7dce5;
            padding-top: 2%;
        }
        .career-mt-4{
            display: flex;
            margin-top: 2%;
            gap: 5%;
        }
        .skills{
            padding-top: 2%;
            border-top: solid 1px #d7dce5;
        }
        #skill-search-container {
            position: relative;
        }

        #skill-search-results {
            max-height: 200px;
            overflow-y: auto;
            position: absolute;
            width: 100%;
            z-index: 1000;
            background: white;
            border-radius: 4px;
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

            <!-- 사용자 기본 정보 -->
            <section class="user-info">
                <div class="d-flex align-items-center justify-content-between">
                    <div class="d-flex align-items-center">
                        <img src="/resources/images/default-avatar.png" alt="프로필 이미지" class="rounded-circle border" width="100" height="100">
                        <div class="ms-3">
                            <h2>박주혁</h2>
                            <p class="text-muted">2007 (17세)</p>
                        </div>
                    </div>
                </div>
                <div class="career-mt-4">
                    <div class="d-flex align-items-center mb-2">
                        <i class="bi bi-envelope me-2"></i>
                        <p class="mb-0">ksi*****@gmail.com</p>
                    </div>
                    <div class="d-flex align-items-center mb-2">
                        <i class="bi bi-telephone me-2"></i>
                        <p class="mb-0">010-****-9815</p>
                    </div>
                    <div class="d-flex align-items-center">
                        <i class="bi bi-geo-alt me-2"></i>
                        <p class="mb-0">서울 용산구 신창동 77-41</p>
                    </div>
                    <div class="d-flex align-items-center">
                        <i class="bi bi-person-badge me-2"></i>
                        <p class="mb-0">병역: 군필</p> <!-- 병역사항 추가 -->

                    </div>
                </div>
            </section>

            <!-- 학력 섹션 -->
            <section class="education">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>학력 <span class="text-danger">*</span></h4>
                    <button class="btn btn-outline-primary btn-sm">+ 추가</button>
                </div>
                <div class="card mb-3">
                    <div class="card-body d-flex align-items-center justify-content-between">
                        <div>
                            <h5 class="mb-1">경민대학교 (4년제)</h5>
                            <p class="text-muted mb-1">컴퓨터소프트웨어과</p>
                            <p class="text-muted">졸업</p>
                        </div>
                        <button class="btn btn-sm btn-primary"><i class="bi bi-pencil"></i> 삭제</button>
                    </div>
                </div>
            </section>

            <section class="skills">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>스킬 <span class="text-danger">*</span></h4>
                    <button class="btn btn-outline-primary btn-sm" id="add-skill-btn">+ 스킬 추가</button>
                </div>
                <div id="skills-list" class="mb-3">
                    <p id="default-message" class="text-muted">스킬 추가를 눌러 자신의 스킬을 추가해 주세요!</p>

                    <!-- 스킬이 추가되면 여기에 표시 -->
                </div>
                <div id="skill-search-container" class="d-none">
                    <input type="text" class="form-control mb-2" id="skill-search-input" placeholder="코딩 언어를 검색하세요">
                    <ul class="list-group" id="skill-search-results">
                        <!-- 검색 결과가 여기에 표시 -->
                    </ul>
                </div>
            </section>

            <!-- 경력 섹션 -->
            <section class="experience">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>경력</h4>
                    <button class="btn btn-outline-primary btn-sm">+ 추가</button>
                </div>
                <div class="card mb-3">
                    <div class="card-body d-flex align-items-center justify-content-between">
                        <div>
                            <h5 class="mb-1">삼성 SDS</h5>
                            <p class="text-muted mb-1">2020/10 ~ 2024/2</p>
                            <p class="text-muted">대리</p>
                        </div>
                        <button class="btn btn-sm btn-primary"><i class="bi bi-pencil"></i> 삭제</button>
                    </div>
                </div>

            </section>
            <!-- 수상/어학/자격증 섹션 -->
            <section class="experience">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4>수상/어학/자격증</h4>
                    <button class="btn btn-outline-primary btn-sm">+ 추가</button>
                </div>
                <div class="card mb-3">
                    <div class="card-body d-flex align-items-center justify-content-between">
                        <div>
                            <h5 class="mb-1">정보처리기사</h5>
                            <p class="text-muted mb-1">2020/10</p>
                            <p class="text-muted">한국산업인력공단</p>
                        </div>
                        <button class="btn btn-sm btn-primary"><i class="bi bi-pencil"></i> 삭제</button>
                    </div>
                </div>

            </section>

        </main>


    </div>
</div>
<script>
    // 샘플 데이터: 코딩 언어 목록
    const codingLanguages = ["JavaScript", "Python", "Java", "C#", "C++", "Ruby", "Go", "Swift", "PHP", "Kotlin"];

    // DOM 요소
    const addSkillBtn = document.getElementById("add-skill-btn");
    const skillSearchContainer = document.getElementById("skill-search-container");
    const skillSearchInput = document.getElementById("skill-search-input");
    const skillSearchResults = document.getElementById("skill-search-results");
    const skillsList = document.getElementById("skills-list");
    const defaultMessage = document.getElementById("default-message");
    const saveSkillsBtn = document.getElementById("save-skills-btn");

    // 스킬 추가 버튼 클릭 이벤트
    addSkillBtn.addEventListener("click", () => {
        skillSearchContainer.classList.remove("d-none"); // 검색 창 표시
        skillSearchInput.value = ""; // 검색 창 초기화
        skillSearchResults.innerHTML = ""; // 결과 목록 초기화
    });

    // 검색 입력 이벤트
    skillSearchInput.addEventListener("input", (e) => {
        const query = e.target.value.toLowerCase();
        skillSearchResults.innerHTML = ""; // 기존 결과 초기화

        if (query) {
            const filteredLanguages = codingLanguages.filter((lang) =>
                lang.toLowerCase().includes(query)
            );

            filteredLanguages.forEach((lang) => {
                const li = document.createElement("li");
                li.textContent = lang;
                li.className = "list-group-item list-group-item-action";
                li.addEventListener("click", () => addSkill(lang));
                skillSearchResults.appendChild(li);
            });
        }
    });

    // 스킬 추가 함수
    function addSkill(skill) {
        const skillBadge = document.createElement("span");
        skillBadge.className = "badge bg-primary me-2";
        skillBadge.textContent = skill;

        const removeBtn = document.createElement("button");
        removeBtn.className = "btn btn-sm btn-outline-danger ms-2";
        removeBtn.textContent = "x";
        removeBtn.addEventListener("click", () => {
            skillsList.removeChild(skillBadgeContainer);
            if (skillsList.children.length === 0) {
                defaultMessage.style.display = "block"; // 디폴트 메시지 다시 표시
            }
        });

        const skillBadgeContainer = document.createElement("div");
        skillBadgeContainer.className = "d-inline-flex align-items-center mb-2";
        skillBadgeContainer.appendChild(skillBadge);
        skillBadgeContainer.appendChild(removeBtn);

        skillsList.appendChild(skillBadgeContainer);
        skillSearchContainer.classList.add("d-none"); // 검색 창 숨김
    }

    // 저장 버튼 클릭 이벤트
    saveSkillsBtn.addEventListener("click", () => {
        const selectedSkills = [];
        skillsList.querySelectorAll(".badge").forEach((badge) => {
            selectedSkills.push(badge.textContent);
        });
        alert("저장된 스킬: " + selectedSkills.join(", "));
    });

</script>
<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
