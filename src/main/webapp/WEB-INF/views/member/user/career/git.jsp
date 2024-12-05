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
        /* 모달 스타일링 */
        .modal-body input {
            width: 100%;
            padding: 8px;
            margin-top: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <!-- Modal for GitHub Token Input -->
    <div class="modal fade" id="githubTokenModal" tabindex="-1" aria-labelledby="githubTokenModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="githubTokenModalLabel">GitHub 토큰 입력</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>GitHub 토큰이 없습니다. 아래에 입력해 주세요.</p>
                    <input type="text" id="githubTokenInput" placeholder="GitHub 토큰을 입력하세요">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveGitHubToken()">저장</button>
                </div>
            </div>
        </div>
    </div>

    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
        <main class="flex-grow-1">
            <h1>내 레파지토리</h1>
            <div class="repository-list">
                <!-- 레파지토리 카드들 -->
            </div>
        </main>
    </div>
</div>
<script>
    $(document).ready(function () {
        const accessToken = localStorage.getItem("accessToken");

        if (!accessToken) {
            alert("로그인이 필요합니다.");
            window.location.href = "/member/Login";
            return;
        }

        checkGitHubToken(accessToken);
    });

    function checkGitHubToken(accessToken) {


        fetch("/github/user", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + accessToken
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("GitHub 요청 실패");
                }
                return response.json();
            })
            .then(data => {
                loadRepositories(accessToken); // GitHub 사용자 데이터 성공적으로 로드
            })
            .catch(error => {
                console.error("Error:", error.message);
                $('#githubTokenModal').modal('show'); // 토큰이 없거나 유효하지 않을 경우 모달 표시
            });
    }

    function saveGitHubToken() {
        let githubToken = $("#githubTokenInput").val().trim();
        const accessToken = localStorage.getItem("accessToken");

        if (!githubToken) {
            alert("GitHub 토큰을 입력해주세요.");
            return;
        }

        fetch(`/github/insert/` + githubToken, {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + accessToken,
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                if (!response.ok) {
                    if (response.status === 401) {
                        alert("GitHub 토큰 인증 실패. 다시 시도해주세요.");
                    } else {
                        alert("GitHub 토큰 저장 중 오류가 발생했습니다.");
                    }
                    throw new Error("GitHub 토큰 저장 실패");
                }
                alert("GitHub 토큰이 성공적으로 저장되었습니다.");
                $('#githubTokenModal').modal('hide');
                location.reload(); // 저장 후 페이지 리로드
            })
            .catch(error => console.error("GitHub 토큰 저장 오류:", error.message));
    }


    function loadRepositories(accessToken) {
        const cleanToken = accessToken.replace(/^"|"$/g, ""); // 앞뒤 따옴표 제거

        fetch(`/github/user/repos`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + cleanToken
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("레포지토리 로드 실패");
                }
                return response.json();
            })
            .then(repos => {
                const repositoryList = $(".repository-list");
                repositoryList.empty();

                if (repos.length > 0) {
                    repos.forEach(repo => {
                        const repoCard = `
                        <div class="repository-card">
                                   <h5>${'${repo.name}'}</h5>
                            <p>${'${repo.description || "설명이 없습니다."}'}</p>
                            <a href="${'${repo.html_url}'}" target="_blank">레포지토리 보기</a>
                        </div>
                    `;
                        repositoryList.append(repoCard);
                    });
                } else {
                    repositoryList.html("<p>레포지토리가 없습니다.</p>");
                }
            })
            .catch(error => console.error("Error loading repositories:", error.message));
    }

</script>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
