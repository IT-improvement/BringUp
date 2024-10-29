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
        .blog-container {
            max-width: 90%;
            margin: auto;
            padding: 2%;
        }
        .blog-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2%;
        }
        .search-container input {
            padding: 5px;
            border-radius: 5px;
            border: 1px solid #ddd;
            width: 100%;
            max-width: 40%;
        }
        .search-container button {
            margin-left: 1%;
            padding: 5px 2%;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 2%;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 1%;
            text-align: left;
        }
        table th {
            background-color: #f4f4f4;
        }
        .delete-btn {
            color: #fff;
            background-color: #dc3545;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }
        /* 모달 스타일 */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }
        .modal-content {
            background-color: #fff;
            margin: 5% auto;
            padding: 2%;
            border-radius: 8px;
            max-width: 80%;
            width: 35%;
        }
        .modal-header, .modal-footer {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .modal-header h5 {
            margin: 0;
        }
        .modal-footer {
            justify-content: flex-end;
        }
    </style>

    <script>
        $(document).ready(function() {
            const accessToken = localStorage.getItem("accessToken");

            fetchBlogList(accessToken);

            $('#addBlogBtn').click(function() {
                $('#blogModal').show();
            });

            $('#closeModal').click(function() {
                $('#blogModal').hide();
            });

            $('#saveBlogButton').click(function() {
                const url = $('#blogUrl').val();
                if (url) {
                    saveBlogUrl(url, accessToken);
                    $('#blogModal').hide();
                } else {
                    alert("URL을 입력해주세요.");
                }
            });
        });

        function fetchBlogList(accessToken) {
            fetch("/portfolio/blog/list", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ` + accessToken,
                    "Content-Type": "application/json"
                }
            })
                .then(response => response.json())
                .then(data => displayBlogList(data.list))
                .catch(error => console.error("Error fetching blog list:", error));
        }

        function displayBlogList(blogs) {
            const tbody = document.querySelector("tbody");
            tbody.innerHTML = "";

            if (!blogs || blogs.length === 0) {
                const row = document.createElement("tr");
                const cell = document.createElement("td");
                cell.colSpan = 2;
                cell.textContent = "블로그가 없습니다.";
                row.appendChild(cell);
                tbody.appendChild(row);
                return;
            }

            blogs.forEach(blog => {
                const row = document.createElement("tr");

                const urlCell = document.createElement("td");
                const link = document.createElement("a");
                link.href = blog.url;
                link.target = "_blank";
                link.textContent = blog.url;
                urlCell.appendChild(link);
                row.appendChild(urlCell);

                const deleteCell = document.createElement("td");
                const deleteButton = document.createElement("button");
                deleteButton.classList.add("delete-btn");
                deleteButton.textContent = "삭제";
                deleteButton.onclick = function() {
                    deleteBlog(blog.blogIndex);
                };
                deleteCell.appendChild(deleteButton);
                row.appendChild(deleteCell);

                tbody.appendChild(row);
            });
        }

        function saveBlogUrl(url, accessToken) {
            fetch("/portfolio/blog/insert", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ` + accessToken,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ url: url })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.code === "SU") {
                        alert("블로그 URL이 성공적으로 추가되었습니다.");
                        location.reload();
                    } else {
                        alert("블로그 URL 추가 실패: " + data.message);
                    }
                })
                .catch(error => {
                    console.error("Error saving blog URL:", error);
                    alert("저장 중 오류가 발생했습니다.");
                });
        }

        function deleteBlog(blogIndex) {
            if (!confirm("정말로 이 블로그 URL을 삭제하시겠습니까?")) return;

            fetch(`/portfolio/blog/delete?index=` + blogIndex, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                }
            })
                .then(response => response.json())
                .then(data => {
                    if (data.code === "SU") {
                        alert("블로그 URL이 성공적으로 삭제되었습니다.");
                        location.reload();
                    } else {
                        alert("블로그 URL 삭제 실패: " + data.message);
                    }
                })
                .catch(error => {
                    console.error("Error deleting blog URL:", error);
                    alert("삭제 중 오류가 발생했습니다.");
                });
        }
    </script>
</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <div class="container ms-main-content" style="max-width: 90%; margin: 0 auto;">
        <main class="flex-grow-1">
            <div class="blog-container">
                <div class="blog-header">
                    <h1>나의 블로그 리스트</h1>
                    <div class="search-container">
                        <input type="text" placeholder="검색어 입력...">
                        <button class="btn btn-secondary"><i class="fas fa-search"></i> 검색</button>
                        <button id="addBlogBtn" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> 추가 및 삭제</button>
                    </div>
                </div>

                <table>
                    <thead>
                    <tr>
                        <th>URL</th>
                        <th>삭제</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- 블로그 리스트가 여기에 추가됩니다 -->
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>

<!-- 블로그 추가 모달 -->
<div id="blogModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h5>블로그 URL 추가</h5>
            <button id="closeModal" style="border: none; background: none; font-size: 1.2em;">&times;</button>
        </div>
        <div class="modal-body">
            <input type="text" id="blogUrl" placeholder="블로그 URL을 입력하세요" class="form-control">
        </div>
        <div class="modal-footer">
            <button id="saveBlogButton" class="btn btn-primary">저장</button>
        </div>
    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
