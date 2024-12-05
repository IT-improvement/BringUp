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
                .card, .overflow-hidden{
                    border-radius: 20px;

                }


                .card-body {
                    position: relative;
                    background: #e2e2e2;
                    border-radius: 20px;
                }
                .card-body .btn-danger {
                    position: absolute;
                    top: 10px; /* 버튼의 상단 간격 */
                    right: 10px; /* 버튼의 우측 간격 */
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
                .form-check-mt-3{

                }
                .user-info-section{
                    padding: 0%;
                    margin-top: 3%;

                }
                .form-check-label{
                    color: blue;

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
                    <!-- 페이지 제목 표시 영역 -->
                    <div class="container mt-4">
                        <h3 style="">이력서 작성</h3>
                        <div class="d-flex justify-content align-items-center">
                                <input type="text" id="resumeTitleInput" class="form-control me-2" placeholder="이력서 제목을 입력하세요" style="width: 100%;">
                        </div>
                    </div>
                    <hr>

                    <!-- 사용자 기본 정보 -->
                    <section class="user-info-section">
                        <div class="form-check-mt-3">
                            <input class="form-check-input" type="checkbox" id="main-cv-checkbox">
                            <label class="form-check-label" for="main-cv-checkbox">대표 이력서로 설정</label>
                        </div>
                        <div class="d-flex align-items-center justify-content-between">
                            <div class="d-flex align-items-center">
                                <img src="" alt="프로필 이미지" class="rounded-circle border" width="100" height="100">
                                <div class="ms-3">
                                    <h4 id="user-name">사용자 이름</h4>
                                    <p class="text-muted" id="user-birthday">생년월일</p>
                                </div>
                            </div>
                        </div>
                        <div class="career-mt-4">
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-envelope me-2"></i>
                                <p id="user-email" class="mb-0">이메일</p>
                            </div>
                            <div class="d-flex align-items-center mb-2">
                                <i class="bi bi-telephone me-2"></i>
                                <p id="user-phonenumber" class="mb-0">전화번호</p>
                            </div>
                            <div class="d-flex align-items-center">
                                <i class="bi bi-geo-alt me-2"></i>
                                <p id="user-address" class="mb-0">주소</p>
                            </div>
                            <div class="d-flex align-items-center">
                                <i class="bi bi-person-badge me-2"></i>
                                <p id="military-status" class="mb-0">병역 상태</p>
                            </div>
                        </div>
                    </section>

                    <!-- 학력 섹션 -->
                    <section class="education">

                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4>학력 <span class="text-danger">*</span></h4>
                            <button class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#educationModal">+ 추가</button>
                        </div>
                        <div id="schoolInfoList">
                            <!-- 선택된 학력이 여기에 카드 형태로 추가됩니다 -->

                        </div>
                    </section>

                    <!-- 학력 정보 모달 -->
                    <div class="modal fade" id="educationModal" tabindex="-1" aria-labelledby="educationModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="educationModalLabel">학력 정보</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div id="educationList" class="list-group">
                                        <p id="educationList-default-message" class="text-muted">추가를 눌러 자신의 학력을 추가해 주세요!</p>
                                        <!-- 학력 리스트가 여기에 동적으로 추가됩니다 -->
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                </div>
                            </div>
                        </div>
                    </div>





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
                            <button class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#careerModal">+ 추가</button>
                        </div>
                        <div id="careerInfoList">
                            <!-- 선택된 경력이 여기에 카드 형태로 추가됩니다 -->
                        </div>

                    </section>


                    <!-- 경력 정보 모달 -->
                    <div class="modal fade" id="careerModal" tabindex="-1" aria-labelledby="careerModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="careerModalLabel">경력 정보</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div id="careerList" class="list-group">
                                        <!-- 경력 리스트가 여기에 동적으로 추가됩니다 -->
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                </div>
                            </div>
                        </div>
                    </div>





                    <!-- 수상/어학/자격증 섹션 -->
                    <!-- 수상/자격증 섹션 -->
                    <section class="experience">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4>수상/어학/자격증</h4>
                            <button class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#awardCertificateModal">+ 추가</button>
                        </div>
                        <div id="awardCertificateList" class="list-group">
                            <!-- 추가된 수상/자격증 카드들이 여기에 표시됩니다 -->
                        </div>
                    </section>




                    <!-- 수상/자격증 모달 -->
                    <div class="modal fade" id="awardCertificateModal" tabindex="-1" aria-labelledby="awardCertificateModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="awardCertificateModalLabel">수상/자격증 추가</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <select class="form-select" id="modalFilterSelect" onchange="fetchAndFilterEntries()">
                                            <option value="all">전체</option>
                                            <option value="award">수상</option>
                                            <option value="certificate">자격증</option>
                                        </select>
                                    </div>
                                    <div id="modalEntryList" class="list-group">
                                        <!-- 필터된 데이터가 여기에 표시됩니다 -->
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 블로그 섹션 -->
                    <section class="experience">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4>블로그</h4>
                            <button class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#blogModal">+ 추가</button>
                        </div>
                        <div id="blogCardList">
                            <!-- 선택된 블로그가 여기에 카드 형태로 추가됩니다 -->
                        </div>
                    </section>

                    <!-- 블로그 모달 -->
                    <div class="modal fade" id="blogModal" tabindex="-1" aria-labelledby="blogModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="blogModalLabel">블로그 리스트</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div id="blogList" class="list-group">
                                        <!-- 블로그 리스트가 동적으로 추가됩니다 -->
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <section class="experience">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4>Git 레파지토리</h4>
                            <button class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#gitModal">+ 추가</button>
                        </div>
                        <div id="gitRepoList" class="list-group">
                            <!-- 추가된 Git 레파지토리가 여기에 표시됩니다 -->
                        </div>
                    </section>

                    <!-- Git 레파지토리 모달 -->
                    <div class="modal fade" id="gitModal" tabindex="-1" aria-labelledby="gitModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="gitModalLabel">Git 레파지토리 추가</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div id="gitRepoListModal" class="list-group">
                                        <!-- Git 레파지토리 리스트가 동적으로 추가됩니다 -->
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="text-center my-4">
                        <button id="saveResumeButton" class="btn btn-primary btn-lg">저장</button>
                    </div>


                </main>
            </div>
        </div>
        <script>

            let educationData = []; // 전체 학력 데이터를 저장
            // 수상 및 자격증 데이터 저장용 변수
            let awardCertificateData = []; // 전역 변수
            let currentFilter = "all"; // 필터 기본값

            let blogData = []; // 블로그 데이터를 저장할 배열
            console.log(blogData);

            let gitRepoData = [];


            document.addEventListener("DOMContentLoaded", () => {
                const saveResumeButton = document.getElementById("saveResumeButton");

                saveResumeButton.addEventListener("click", () => {
                    const accessToken = localStorage.getItem("accessToken");

                    if (!accessToken) {
                        alert("로그인이 필요합니다.");
                        window.location.href = "/member/login";
                        return;
                    }

                    // 제목
                    const title = document.getElementById("resumeTitleInput").value.trim();
                    if (!title) {
                        alert("이력서 제목을 입력하세요.");
                        return;
                    }

                    // 대표 이력서 여부
                    const mainCv = document.getElementById("main-cv-checkbox").checked;

                    // 학력 정보
                    const schoolCards = document.querySelectorAll("#schoolInfoList .card");
                    const cvSchool = Array.from(schoolCards).map(card => {
                        const id = card.id.replace("school-card-", "");
                        return parseInt(id, 10);
                    });

                    // 경력 정보
                    const careerCards = document.querySelectorAll("#careerInfoList .card");
                    const cvCareer = Array.from(careerCards).map(card => {
                        const id = card.id.replace("career-card-", "");
                        return parseInt(id, 10);
                    });

                    // 수상 및 자격증 정보
                    const awardCertificateCards = document.querySelectorAll("#awardCertificateList .card");
                    const cvAward = [];
                    const cvCertificate = [];
                    awardCertificateCards.forEach(card => {
                        const id = card.id.replace("selected-", "");
                        const type = card.querySelector("h5").textContent.includes("수상") ? "award" : "certificate";
                        if (type === "award") {
                            cvAward.push(parseInt(id, 10));
                        } else {
                            cvCertificate.push(parseInt(id, 10));
                        }
                    });

                    // 블로그 정보
                    const blogCards = document.querySelectorAll("#blogCardList .card");
                    const cvBlog = Array.from(blogCards).map(card => {
                        const id = card.id.replace("blog-card-", "");
                        return parseInt(id, 10);
                    });

                    // 스킬 정보
                    const skillBadges = document.querySelectorAll("#skills-list .badge");
                    const skill = Array.from(skillBadges).map(badge => badge.textContent);

                    // GitHub 레포지토리 정보 (단일 문자열로 연결)
                    const gitRepoCards = document.querySelectorAll("#gitRepoList .card");
                    const github = Array.from(gitRepoCards)
                        .map(card => {
                            const urlElement = card.querySelector("a");
                            return urlElement ? urlElement.href : "";
                        })
                        .join(","); // URL을 ','로 구분된 문자열로 변환

                    // 데이터 준비
                    const requestBody = {
                        mainCv,
                        title,
                        skill,
                        cvAward,
                        cvCertificate,
                        cvCareer,
                        cvBlog,
                        cvSchool,
                        github // 단일 문자열
                    };

                    console.log("Request Body:", requestBody);

                    // 서버로 데이터 전송
                    fetch("/cv/insert", {
                        method: "POST",
                        headers: {
                            Authorization: `Bearer ` + accessToken,
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(requestBody),
                    })
                        .then(response => response.json())
                        .then(data => {
                            if (data.code === "SU") {
                                alert("이력서가 성공적으로 저장되었습니다.");
                                window.location.reload();
                            } else {
                                console.error("이력서 저장 실패:", data);
                                alert("이력서 저장에 실패했습니다.");
                            }
                        })
                        .catch(error => {
                            console.error("Error while saving resume:", error);
                            alert("이력서를 저장하는 중 오류가 발생했습니다.");
                        });
                });
            });



            document.addEventListener("DOMContentLoaded", () => {
                const accessToken = localStorage.getItem("accessToken");

                if (!accessToken) {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/member/login"; // 로그인 페이지로 이동
                    return;
                }

                fetch("/member/careerInfo", {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ` + accessToken,
                        "Content-Type": "application/json",
                    },
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("사용자 정보를 가져오는 데 실패했습니다.");
                        }
                        return response.json();
                    })
                    .then(data => {
                        if (data.code === 200) {
                            const userInfo = data.data;

                            // 사용자 정보를 HTML에 채워넣음
                            document.getElementById("user-name").textContent = userInfo.userName;
                            document.getElementById("user-birthday").textContent = userInfo.userBirthday;
                            document.getElementById("user-email").textContent = userInfo.userEmail;
                            document.getElementById("user-phonenumber").textContent = userInfo.userPhonenumber;
                            document.getElementById("user-address").textContent = userInfo.userAddress;
                            document.getElementById("military-status").textContent = `병역: ` + userInfo.militaryStatus;
                        } else {
                            throw new Error(data.message || "사용자 정보를 처리하는 데 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        console.error("오류 발생:", error);
                        alert("사용자 정보를 불러오는 중 문제가 발생했습니다.");
                    });
            });

            // Git 레파지토리 데이터를 가져오는 함수
            function fetchGitRepoList() {
                const accessToken = localStorage.getItem("accessToken");

                if (!accessToken) {
                    alert("로그인이 필요합니다.");
                    return;
                }

                fetch("/github/user/repos", {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ` + accessToken,
                        "Content-Type": "application/json",
                    },
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Git 레파지토리를 불러오는 데 실패했습니다.");
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log("Git 레파지토리 데이터:", data);
                        gitRepoData = data; // 데이터를 전역 변수에 저장
                        displayGitRepoList(gitRepoData); // 데이터를 모달에 표시
                    })
                    .catch(error => {
                        console.error("Git 레파지토리 정보 불러오기 중 오류 발생:", error);
                        alert("Git 레파지토리 정보를 불러오는 중 문제가 발생했습니다.");
                    });
            }

            // Git 레파지토리 정보를 모달에 표시
            function displayGitRepoList(data) {
                const gitRepoListModal = document.getElementById("gitRepoListModal");
                gitRepoListModal.innerHTML = ""; // 기존 내용을 초기화

                if (!data || data.length === 0) {
                    gitRepoListModal.innerHTML =
                        "<p class='text-center text-muted'>등록된 Git 레파지토리가 없습니다.</p>";
                    return;
                }

                data.forEach(repo => {
                    const repoCard = createGitRepoCard(repo);
                    gitRepoListModal.appendChild(repoCard);
                });
            }

            // Git 레파지토리 카드를 생성하는 함수
            function createGitRepoCard(repo) {
                const card = document.createElement("div");
                card.className = "list-group-item d-flex justify-content-between align-items-center";
                const safeUrl = encodeURIComponent(repo.html_url);
                card.innerHTML = `
            <div>
                <h6>${'${repo.name}'}</h6>
                <p class="mb-0 text-muted">URL: <a href="${'${repo.html_url}'}" target="_blank">${'${repo.html_url}'}</a></p>
            </div>
            <button class="btn btn-outline-primary btn-sm" onclick="addGitRepoToResume('${'${safeUrl}'}')">추가</button>
        `;
                return card;
            }

            // Git 레파지토리를 이력서 카드 영역에 추가
            function addGitRepoToResume(encodedUrl) {
                // URL 디코딩
                const url = decodeURIComponent(encodedUrl);
                console.log(url);

                const gitRepoList = document.getElementById("gitRepoList");

                // 이미 추가된 레파지토리인지 확인
                if (document.getElementById(`git-repo-card-"${'${url}'}"`)) {
                    alert("이미 추가된 Git 레파지토리입니다.");
                    return;
                }

                // Git 레파지토리 카드 생성
                const card = document.createElement("div");
                card.className = "card mb-3";
                card.id = `git-repo-card-${'${url}'}`;
                card.innerHTML = `
            <div class="card-body">
                <h5>${'${name}'}</h5>
                <p>URL: <a href="${'${url}'}" target="_blank">${'${url}'}</a></p>
                <button class="btn btn-sm btn-danger" onclick="removeGitRepo('${'${url}'}')">삭제</button>
            </div>
        `;

                gitRepoList.appendChild(card);
            }

            // 선택된 Git 레파지토리를 삭제
            function removeGitRepo(name) {
                const card = document.getElementById(`git-repo-card-${'${name}'}`);
                if (card) {
                    card.remove();
                }
            }

            // 모달이 열릴 때 Git 레파지토리 데이터를 가져오기
            document.addEventListener("DOMContentLoaded", () => {
                const gitModal = document.getElementById("gitModal");
                gitModal.addEventListener("shown.bs.modal", () => {
                    fetchGitRepoList();
                });
            });


            // 블로그 데이터를 가져오는 함수
            function fetchBlogList() {
                const accessToken = localStorage.getItem("accessToken");

                if (!accessToken) {
                    alert("로그인이 필요합니다.");
                    console.error("Access Token이 없습니다.");
                    return;
                }

                console.log("Access Token: ", accessToken);

                fetch("/portfolio/blog/list", {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ` + accessToken,
                        "Content-Type": "application/json",
                    },
                })
                    .then(response => {
                        console.log("응답 상태 코드:", response.status);
                        if (!response.ok) {
                            console.error("응답 실패: ", response.status);
                            alert("블로그 정보를 불러오는 데 실패했습니다.");
                            return null;
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log("API 응답 데이터: ", data);
                        if (data && data.code === "SU") {
                            blogData = data.list || [];
                            if (blogData.length === 0) {
                                console.log("블로그 데이터가 비어 있습니다.");
                                alert("등록된 블로그 정보가 없습니다.");
                            } else {
                                console.log("블로그 데이터: ", blogData);
                                displayBlogList(blogData);
                            }
                        } else {
                            console.error("API 응답 오류: ", data);
                            alert("블로그 데이터를 처리하는 데 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        console.error("블로그 데이터 불러오기 중 오류 발생: ", error);
                        alert("블로그 정보를 불러오는 중 문제가 발생했습니다.");
                    });
            }

            // 블로그 정보를 모달에 표시
            function displayBlogList(data) {
                console.log("displayBlogList 호출됨. 데이터: ", data);

                const blogList = document.getElementById("blogList");
                blogList.innerHTML = ""; // 기존 내용을 초기화

                if (!data || data.length === 0) {
                    blogList.innerHTML =
                        "<p class='text-center text-muted'>등록된 블로그 정보가 없습니다.</p>";
                    console.log("블로그 데이터가 비어 있습니다.");
                    return;
                }

                data.forEach(blog => {
                    const blogCard = createBlogCard(blog);
                    blogList.appendChild(blogCard);
                });

                console.log("모달에 블로그 데이터가 표시되었습니다.");
            }

            // 블로그 카드를 생성하는 함수
            function createBlogCard(blog) {
                console.log("createBlogCard 호출됨. 블로그 데이터: ", blog);

                const card = document.createElement("div");
                card.className = "list-group-item d-flex justify-content-between align-items-center";
                card.innerHTML = `
            <div>
                <h6>URL: ${'${blog.url}'}</h6>
            </div>
            <button class="btn btn-outline-primary btn-sm" onclick="addBlogToResume(${'${blog.blogIndex}'})">추가</button>
        `;
                return card;
            }

            // 모달이 열릴 때 fetchBlogList 호출
            document.addEventListener("DOMContentLoaded", () => {
                const blogModal = document.getElementById("blogModal");
                blogModal.addEventListener("shown.bs.modal", () => {
                    console.log("블로그 모달이 열렸습니다.");
                    fetchBlogList();
                });


            });
            // 블로그를 이력서 카드 영역에 추가
            function addBlogToResume(blogIndex) {
                const blog = blogData.find(blogItem => blogItem.blogIndex === blogIndex);
                console.log(blogIndex);

                if (!blog) {
                    alert("블로그 데이터를 찾을 수 없습니다.");
                    return;
                }

                const blogCardList = document.getElementById("blogCardList");


                // 이미 추가된 블로그인지 확인
                if (document.getElementById(`blog-card-${'{blogIndex}'}`)) {
                    alert("이미 추가된 블로그입니다.");
                    return;
                }

                // 블로그 카드 생성
                const card = document.createElement("div");
                card.className = "card mb-3";
                card.id = `blog-card-${'${blogIndex}'}`;
                card.innerHTML = `
            <div class="card-body">
                <h5>URL: ${'${blog.url}'}</h5>

                <button class="btn btn-sm btn-danger" onclick="removeBlog(${'${blogIndex}'})">삭제</button>
            </div>
        `;

                blogCardList.appendChild(card);
            }

            // 선택된 블로그를 삭제
            function removeBlog(blogIndex) {
                const card = document.getElementById(`blog-card-${'${blogIndex}'}`);
                if (card) {
                    card.remove();
                } else {
                    alert("블로그 카드를 찾을 수 없습니다.");
                }
            }





            // 학력 데이터를 가져오는 함수
            function fetchEducationList() {
                const accessToken = localStorage.getItem("accessToken");

                if (!accessToken) {
                    alert("로그인이 필요합니다.");
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
                            educationData = data; // 데이터를 전역 변수에 저장
                            displayEducationList(data); // 데이터를 모달에 표시
                        } else {
                            alert("학력 정보를 불러오는 데 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        console.error("학력 정보 불러오기 중 오류 발생:", error);
                    });
            }

            // 학력 정보를 모달에 표시
            function displayEducationList(data) {
                const educationList = document.getElementById("educationList");
                educationList.innerHTML = ""; // 기존 내용을 초기화

                if (!data.universityList.length && !data.highSchool && !data.graduateList.length) {
                    educationList.innerHTML = "<p class='text-center text-muted'>등록된 학력 정보가 없습니다.</p>";
                    return;
                }

                // 고등학교 정보
                if (data.highSchool) {
                    const highSchoolCard = createEducationCard("고등학교", data.highSchool);
                    educationList.appendChild(highSchoolCard);
                }

                // 대학교 정보
                data.universityList.forEach(university => {
                    const universityCard = createEducationCard("대학교", university);
                    educationList.appendChild(universityCard);
                });

                 data.majoruniversityList.forEach(majoruniversity => {
                            const majoruniversityCard = createEducationCard("전문대", majoruniversity);
                            educationList.appendChild(majoruniversityCard);
                        });

                // 대학원 정보
                data.graduateList.forEach(graduate => {
                    const graduateCard = createEducationCard("대학원", graduate);
                    educationList.appendChild(graduateCard);
                });
            }

            // 학력 카드를 생성하는 함수
            function createEducationCard(type, school) {
                const formatDate = (dateStr) => {
                    if (!dateStr) return "현재";
                    const date = new Date(dateStr);
                    return `${'${date.getFullYear()}'}-${'${String(date.getMonth() + 1).padStart(2, `0`)}'}-${'${String(date.getDate()).padStart(2, `0`)}'}`;
                };

                const card = document.createElement("div");
                card.className = "list-group-item d-flex justify-content-between align-items-center";
                card.innerHTML = `
                <div>
                    <h6>${'${type}'}: ${'${school.schoolName}'}</h6>
                    <p class="mb-0 text-muted">소재지:${'${school.location}'}</p>
                    <p class="mb-0 text-muted">기간: ${'${formatDate(school.startDate)}'} ~ ${'${formatDate(school.endDate)}'}</p>
                </div>
                <button class="btn btn-outline-primary btn-sm" onclick="addEducationToResume(${'${school.schoolIndex}'})">추가</button>
            `;
                return card;
            }

            // 선택된 학력을 이력서 카드 영역에 추가
            function addEducationToResume(schoolIndex) {
                const school = findSchoolByIndex(schoolIndex); // 선택된 학력을 찾음

                if (!school) {
                    alert("학력을 찾을 수 없습니다.");
                    return;
                }

                const schoolInfoList = document.getElementById("schoolInfoList");

                // 이미 추가된 학력인지 확인
                if (document.getElementById(`school-card-${'${schoolIndex}'}`)) {
                    alert("이미 추가된 학력입니다.");
                    return;
                }
                const formatDate = (dateStr) => {
                    if (!dateStr) return "현재";
                    const date = new Date(dateStr);
                    return `${'${date.getFullYear()}'}-${'${String(date.getMonth() + 1).padStart(2, `0`)}'}-${'${String(date.getDate()).padStart(2, `0`)}'}`;
                };


                // 학력 카드 생성
                const card = document.createElement("div");
                card.className = "card mb-3";
                card.id = `school-card-${'${schoolIndex}'}`;
                card.innerHTML = `
                <div class="card-body">
                    <h5>${'${school.schoolName}'}</h5>
                    <p>소재지: ${'${school.location}'}</p>
                    <p>기간: ${'${school.startDate ? formatDate(school.startDate) : "정보 없음"}'} ~ ${'${school.endDate ? formatDate(school.endDate) : "현재"}'}</p>
                    <button class="btn btn-sm btn-danger" onclick="removeEducation(${'${schoolIndex}'})">삭제</button>
                </div>
            `;

                schoolInfoList.appendChild(card);
                new bootstrap.Modal(document.getElementById("modal")).hide(); // 모달 닫기
            }

            // 선택된 학력을 삭제
            function removeEducation(schoolIndex) {
                const card = document.getElementById(`school-card-${'${schoolIndex}'}`);
                if (card) {
                    card.remove();
                }
            }

            // 선택된 schoolIndex로 학력 데이터 찾기
            function findSchoolByIndex(schoolIndex) {
                if (educationData.highSchool && educationData.highSchool.schoolIndex === schoolIndex) {
                    return educationData.highSchool;
                }

                const university = educationData.universityList.find(u => u.schoolIndex === schoolIndex);
                if (university) return university;

                const majoruniversity = educationData.majoruniversityList.find(u => u.schoolIndex === schoolIndex);
                if (majoruniversity) return majoruniversity;

                const graduate = educationData.graduateList.find(g => g.schoolIndex === schoolIndex);
                if (graduate) return graduate;

                return null;
            }

            // 페이지 로드 시 학력 데이터를 가져오기
            document.addEventListener("DOMContentLoaded", () => {
                const educationAddButton = document.querySelector('.education button[data-bs-toggle="modal"]');
                if (educationAddButton) {
                    educationAddButton.addEventListener("click", () => {
                        fetchEducationList(); // 학력 정보를 가져오는 함수 호출
                    });
                } else {
                    console.error("학력 추가 버튼을 찾을 수 없습니다.");
                }
            });

            //경력
            let careerData = []; // 전체 경력 데이터를 저장

            // 경력 데이터를 가져오는 함수
            function fetchCareerList() {
                const accessToken = localStorage.getItem("accessToken");

                if (!accessToken) {
                    alert("로그인이 필요합니다.");
                    return;
                }

                fetch("/portfolio/career/list", {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ` + accessToken,
                        "Content-Type": "application/json",
                    },
                })
                    .then((response) => response.json())
                    .then((data) => {
                        if (data.code === "SU") {
                            careerData = data.list || []; // 데이터를 전역 변수에 저장
                            displayCareerList(data.list); // 데이터를 모달에 표시
                        } else {
                            alert("경력 정보를 불러오는 데 실패했습니다.");
                        }
                    })
                    .catch((error) => {
                        console.error("경력 정보 불러오기 중 오류 발생:", error);
                    });
            }

            // 경력 정보를 모달에 표시
            function displayCareerList(data) {
                const careerList = document.getElementById("careerList");
                careerList.innerHTML = ""; // 기존 내용을 초기화

                if (!data || data.length === 0) {
                    careerList.innerHTML =
                        "<p class='text-center text-muted'>등록된 경력 정보가 없습니다.</p>";
                    return;
                }

                data.forEach((career) => {
                    const careerCard = createCareerCard(career);
                    careerList.appendChild(careerCard);
                });
            }

            // 경력 카드를 생성하는 함수
            function createCareerCard(career) {
                const card = document.createElement("div");
                card.className =
                    "list-group-item d-flex justify-content-between align-items-center";
                card.innerHTML = `
                <div>
                    <h6>${'${career.companyName || "회사명 없음"}'}</h6>
                    <p class="mb-0 text-muted">직무: ${'${career.careerPosition || "직무 없음"}'}</p>
                    <p class="mb-0 text-muted">기간: ${'${career.careerStart || "시작 날짜 없음"}'} ~ ${'${career.careerEnd || "재직 중"}'}</p>
                </div>
                <button class="btn btn-outline-primary btn-sm" onclick="addCareerToResume(${'${career.careerIndex}'})">추가</button>
            `;
                return card;
            }

            // 선택된 경력을 이력서 카드 영역에 추가
            function addCareerToResume(careerIndex) {
                const career = findCareerByIndex(careerIndex); // 선택된 경력을 찾음

                if (!career) {
                    alert("경력을 찾을 수 없습니다.");
                    return;
                }

                const careerInfoList = document.getElementById("careerInfoList");

                // 이미 추가된 경력인지 확인
                if (document.getElementById(`career-card-${'${careerIndex}'}`)) {
                    alert("이미 추가된 경력입니다.");
                    return;
                }

                // 경력 카드 생성
                const card = document.createElement("div");
                card.className = "card mb-3";
                card.id = `career-card-${'${careerIndex}'}`;
                card.innerHTML = `
                <div class="card-body">
                    <h5>${'${career.companyName || "회사명 없음"}'}</h5>
                    <p>직무: ${'${career.careerPosition || "직무 없음"}'}</p>
                    <p>기간: ${'${career.careerStart || "시작 날짜 없음"}'} ~ ${'${career.careerEnd || "재직 중"}'}</p>
                    <button class="btn btn-sm btn-danger" onclick="removeCareer(${'${careerIndex}'})">삭제</button>
                </div>
            `;

                careerInfoList.appendChild(card);
                new bootstrap.Modal(document.getElementById("modal")).hide(); // 모달 닫기
            }

            // 선택된 경력을 삭제
            function removeCareer(careerIndex) {
                const card = document.getElementById(`career-card-${'${careerIndex}'}`);
                if (card) {
                    card.remove();
                }
            }

            // 선택된 careerIndex로 경력 데이터 찾기
            function findCareerByIndex(careerIndex) {
                return careerData.find((career) => career.careerIndex === careerIndex);
            }

            // 페이지 로드 시 경력 데이터를 가져오기
            document.addEventListener("DOMContentLoaded", () => {
                const careerAddButton = document.querySelector(
                    ".experience button[data-bs-toggle='modal']"
                );
                if (careerAddButton) {
                    careerAddButton.addEventListener("click", () => {
                        fetchCareerList(); // 경력 정보를 가져오는 함수 호출
                    });
                } else {
                    console.error("경력 추가 버튼을 찾을 수 없습니다.");
                }
            });



            // 샘플 데이터: 코딩 언어 목록
            const codingLanguages = ["JavaScript", "Python", "Java", "C#", "C++", "Ruby", "Go", "Swift", "PHP", "Kotlin"];
            const addSkillBtn = document.getElementById("add-skill-btn");
            const skillSearchContainer = document.getElementById("skill-search-container");
            const skillSearchInput = document.getElementById("skill-search-input");
            const skillSearchResults = document.getElementById("skill-search-results");
            const skillsList = document.getElementById("skills-list");
            const defaultMessage = document.getElementById("default-message");
            const saveSkillsBtn = document.getElementById("save-skills-btn");
            const mainCvCheckbox = document.getElementById("main-cv-checkbox");

            let selectedSkills = [];

            // 스킬 추가 버튼 클릭 이벤트
            addSkillBtn.addEventListener("click", () => {
                skillSearchContainer.classList.remove("d-none");
                skillSearchInput.value = "";
                skillSearchResults.innerHTML = "";
            });

            // 검색 입력 이벤트
            skillSearchInput.addEventListener("input", (e) => {
                const query = e.target.value.toLowerCase();
                skillSearchResults.innerHTML = "";

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
                if (!selectedSkills.includes(skill)) {
                    const skillBadge = document.createElement("span");
                    skillBadge.className = "badge bg-primary me-2";
                    skillBadge.textContent = skill;

                    const removeBtn = document.createElement("button");
                    removeBtn.className = "btn btn-sm btn-outline-danger ms-2";
                    removeBtn.textContent = "x";
                    removeBtn.addEventListener("click", () => {
                        skillsList.removeChild(skillBadgeContainer);
                        selectedSkills = selectedSkills.filter((s) => s !== skill);
                        if (selectedSkills.length === 0) {
                            defaultMessage.style.display = "block";
                        }
                    });

                    const skillBadgeContainer = document.createElement("div");
                    skillBadgeContainer.className = "d-inline-flex align-items-center mb-2";
                    skillBadgeContainer.appendChild(skillBadge);
                    skillBadgeContainer.appendChild(removeBtn);

                    skillsList.appendChild(skillBadgeContainer);
                    selectedSkills.push(skill);
                    defaultMessage.style.display = "none";
                    skillSearchContainer.classList.add("d-none");
                }
            }

            // 저장 버튼 클릭 이벤트
            saveSkillsBtn.addEventListener("click", () => {
                if (selectedSkills.length === 0) {
                    alert("스킬을 추가해주세요!");
                    return;
                }

                const requestBody = {
                    mainCv: mainCvCheckbox.checked,
                    skill: selectedSkills,
                    title: "사용자 이력서",
                    cvAward: [],
                    cvBlog: [],
                    cvCareer: [],
                    cvCertificate: [],
                    cvSchool: []
                };

                const accessToken = localStorage.getItem("accessToken");

                fetch('/cv/insert', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ` + accessToken,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestBody)
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.code === "SU") {
                            alert("이력서가 성공적으로 저장되었습니다.");
                            location.reload();
                        } else {
                            alert("이력서 저장에 실패했습니다.");
                        }
                    })
                    .catch(error => console.error('Error:', error));
            });

            //////
            // 필터링된 데이터 렌더링
            function fetchAndFilterEntries() {
                const filterSelect = document.getElementById("modalFilterSelect");
                currentFilter = filterSelect.value;

                // 필터에 따라 데이터 가져오기
                if (currentFilter === "award") {
                    // 수상 데이터를 가져옵니다.
                    awardCertificateData = []; // 기존 데이터 초기화
                    fetchAwardList();
                } else if (currentFilter === "certificate") {
                    // 자격증 데이터를 가져옵니다.
                    awardCertificateData = []; // 기존 데이터 초기화
                    fetchCertificateList();
                } else {
                    // 전체 데이터를 가져옵니다.
                    awardCertificateData = []; // 기존 데이터 초기화
                    fetchAwardList();
                    fetchCertificateList();
                }
                console.log("현재 필터:", currentFilter);
            }

            // DOMContentLoaded 시 필터 이벤트 리스너 추가
            document.addEventListener("DOMContentLoaded", () => {
                const filterSelect = document.getElementById("modalFilterSelect");
                filterSelect.addEventListener("change", fetchAndFilterEntries);

                // 초기 데이터는 전체 데이터를 가져옵니다.
                fetchAwardList();
                fetchCertificateList();
            });

            // 수상 데이터 가져오기
            function fetchAwardList() {
                const accessToken = localStorage.getItem("accessToken");

                if (!accessToken) {
                    console.error("Access token is missing. Please log in again.");
                    return; // 토큰이 없으면 요청 중단
                }

                fetch("/award/list", {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ` + accessToken,
                        "Content-Type": "application/json",
                    },
                })
                    .then((response) => {
                        if (!response.ok) throw new Error("Failed to fetch award data");
                        return response.json();
                    })
                    .then((data) => {
                        if (data.code === "SU" && Array.isArray(data.list)) {
                            const awards = data.list.map((item) => ({
                                id: item.id,
                                type: "award",
                                title: item.title || "제목 없음",
                                organization: item.organization || "기관 없음",
                                date: item.awardDate || "날짜 없음",
                                details: item.details || "세부사항 없음",
                            }));
                            awardCertificateData = [...awardCertificateData, ...awards];
                            renderModalEntries();
                        } else {
                            console.error("Invalid award API response format", data);
                        }
                    })
                    .catch((error) => console.error("Error fetching award data:", error));
            }

            // 자격증 데이터 가져오기
            function fetchCertificateList() {
                const accessToken = localStorage.getItem("accessToken");

                if (!accessToken) {
                    console.error("Access token is missing. Please log in again.");
                    return; // 토큰이 없으면 요청 중단
                }

                fetch("/mem/certificate/list", {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ` + accessToken,
                        "Content-Type": "application/json",
                    },
                })
                    .then((response) => {
                        if (!response.ok) throw new Error("Failed to fetch certificate data");
                        return response.json();
                    })
                    .then((data) => {
                        if (data.code === "SU" && Array.isArray(data.list)) {
                            const certificates = data.list.map((item) => ({
                                id: item.id,
                                type: "certificate",
                                title: item.title || "제목 없음",
                                organization: item.organization || "발급기관 없음",
                                date: item.date || "날짜 없음",
                                details: "상태: 발급 완료",
                            }));
                            awardCertificateData = [...awardCertificateData, ...certificates];
                            renderModalEntries();
                        } else {
                            console.error("Invalid certificate API response format", data);
                        }
                    })
                    .catch((error) => console.error("Error fetching certificate data:", error));
            }

            // 데이터 렌더링 함수
            function renderModalEntries() {
                const modalEntryList = document.getElementById("modalEntryList");
                modalEntryList.innerHTML = "";

                const filteredData = awardCertificateData.filter(
                    (item) => currentFilter === "all" || item.type === currentFilter
                );

                if (filteredData.length === 0) {
                    modalEntryList.innerHTML = "<p class='text-muted'>데이터가 없습니다.</p>";
                    return;
                }

                filteredData.forEach((entry) => {
                    const listItem = document.createElement("div");
                    listItem.className = "list-group-item d-flex justify-content-between align-items-center";
                    listItem.innerHTML = `
            <div>
               <h6>${'${entry.title}'} (${'${entry.type === "award" ? "수상" : "자격증"}'})</h6>
                            <p class="mb-0 text-muted">${'${entry.organization}'}</p>
                            <p class="mb-0 text-muted">${'${entry.date}'}</p>
                            <small class="text-muted">${'${entry.details}'}</small>
                        </div>
                        <button class="btn btn-sm btn-outline-primary" onclick="addToSelectedList(${'${entry.id}'})">추가</button>
            `;
                    modalEntryList.appendChild(listItem);
                });
            }

            // 선택된 데이터 추가
            function addToSelectedList(id) {
                const selectedItem = awardCertificateData.find((item) => item.id === id);
                if (!selectedItem) return;

                const selectedList = document.getElementById("awardCertificateList");
                if (document.getElementById(`selected-${'${id}'}`)) {
                    alert("이미 추가된 항목입니다.");
                    return;
                }

                const card = document.createElement("div");
                card.className = "card mb-3";
                card.id = `selected-${'${id}'}`;
                card.innerHTML = `
                    <div class="card-body">
                        <h5>${'${selectedItem.title}'} (${'${selectedItem.type === "award" ? "수상" : "자격증"}'})</h5>
                        <p>${'${selectedItem.organization}'}</p>
                        <p>${'${selectedItem.date}'}</p>
                        <small>${'${selectedItem.details}'}</small>
                        <button class="btn btn-sm btn-danger mt-2" onclick="removeSelected(${'${id}'})">삭제</button>
                    </div>
        `;
                selectedList.appendChild(card);
            }

            // 선택된 데이터 삭제
            function removeSelected(id) {
                const element = document.getElementById(`selected-${'${id}'}`);
                if (element) element.remove();
            }




        </script>
        <!-- Footer -->
        <jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
        </body>
        </html>
