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
        .careerList-mb-5 {
            padding-top: 0;
            padding-bottom: 0;
        }

        /* 공통 카드 스타일 */
        .card,
        .list-group-item {
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        /* 마우스 오버 효과 */
        .card:hover,
        .list-group-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        /* 대표 이력서 스타일 */
        .card-main {
            background-color: #eff5ff;
            border: 1px solid #cce4ff;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            position: relative;
        }

        /* 일반 이력서 스타일 */
        .list-group-item {
            border: 1px solid #e0e0e0;
            margin-bottom: 15px;
            border-radius: 8px;
        }

        .list-group-item h5 {
            font-size: 1rem;
            font-weight: bold;
        }

        /* 이력서 작성 버튼 */
        .create-resume-btn {

            background-color: #007bff;
            color: white;
            padding: 8px 12px;
            border-radius: 5px;
            font-weight: bold;
            text-decoration: none;
            margin-left: 85%;
        }

        .create-resume-btn:hover {
            background-color: #0056b3;
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <!-- Main Content -->
    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px; position: relative;">
        <main class="flex-grow-1">
            <h2>대표 이력서</h2>
            <div class="container mt-4">
                <!-- 대표 이력서 -->
                <section class="careerList-mb-5">

                    <div id="mainCvContainer">
                        <!-- 대표 이력서가 여기에 동적으로 렌더링됩니다 -->
                    </div>
                </section>



                <!-- 일반 이력서 목록 -->
                <section class="careerList-mb-5">
                    <a href="/member/myCareer" class="create-resume-btn">이력서 작성</a>

                    <h4>일반 이력서</h4>
                    <div id="generalCvContainer" class="list-group" style="border-top: solid 1px; padding-top: 3%; border-radius: 0">
                        <!-- 일반 이력서가 여기에 동적으로 렌더링됩니다 -->
                    </div>

                </section>


            </div>
        </main>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const accessToken = localStorage.getItem("accessToken");

        if (!accessToken) {
            alert("로그인이 필요합니다.");
            window.location.href = "/member/Login";
            return;
        }

        fetch("/cv/list", {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + accessToken,
                "Content-Type": "application/json",
            },
        })
            .then(response => response.json())
            .then(data => {
                if (data.code === "SU") {
                    renderCvList(data.cvList);
                } else {
                    alert("이력서 목록을 불러오는 데 실패했습니다.");
                }
            })
            .catch(error => {
                console.error("오류 발생:", error);
                alert("이력서를 불러오는 중 문제가 발생했습니다.");
            });
    });

    function renderCvList(cvList) {
        const mainCvContainer = document.getElementById("mainCvContainer");
        const generalCvContainer = document.getElementById("generalCvContainer");

        cvList.forEach(cv => {
            const skills = cv.skill.split(",").map(skill => `<span class="badge bg-secondary me-1">${'${skill}'}</span>`).join("");

            if (cv.mainCv) {
                // 대표 이력서
                mainCvContainer.innerHTML = `
                    <div class="card card-main shadow-sm">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="card-title mb-2">${'${cv.title}'}</h5>
                                <div class="mb-2">${'${skills}'}</div>
                                <div class="badge bg-primary text-white">대표 이력서</div>
                            </div>
                            <div class="btn-group">
                                <button class="btn btn-outline-danger btn-sm">삭제하기</button>
                        <button class="btn btn-outline-primary btn-sm" onclick="redirectToDetail(${'${cv.cvIndex}'})">상세보기</button>

                            </div>
                        </div>

                    </div>
                `;
            } else {
                // 일반 이력서
                const generalCvCard = document.createElement("div");
                generalCvCard.className = "list-group-item p-4";
                generalCvCard.innerHTML = `

                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h5>${'${cv.title}'}</h5>
                            <div>${'${skills}'}</div>
                        </div>
                        <div class="btn-group">
                            <button class="btn btn-outline-danger btn-sm">삭제하기</button>
                        <button class="btn btn-outline-primary btn-sm" onclick="redirectToDetail(${'${cv.cvIndex}'})">상세보기</button>

                        </div>
                    </div>
                `;
                generalCvContainer.appendChild(generalCvCard);
            }
        });
    }

    // 상세보기 페이지로 이동하는 함수
        function redirectToDetail(cvIndex) {
        window.location.href = `/member/careerDetail?cvIndex=` + cvIndex;
    }
</script>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
