
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>포트폴리오</title>
    <link rel="stylesheet" href="/resources/style/member/potofolio.css">
</head>
<body>
<div class="container">
    <aside class="sidebar">
        <h2 class="sidebar-title">포트폴리오</h2>
        <ul class="sidebar-menu">
            <li><a href="#">홈</a></li>
            <li><a href="#">자기소개서</a></li>
            <li><a href="#">학력 및 경력</a></li>
            <li><a href="#">기술 스택</a></li>
            <li><a href="#">포트폴리오 및 기타문서</a></li>
            <li><a href="#">자격증</a></li>
            <li><a href="#">연락처</a></li>
        </ul>
    </aside>

    <main class="content">
        <h2 class="content-title">포트폴리오 및 기타문서</h2>

        <section class="section">
            <h3 class="section-title">Github</h3>
            <label for="github-repo">링크 :</label>
            <input type="text" id="github-repo" placeholder="Github 리포지토리 링크">
            <button type="button">리포지토리 선택</button>
            <div class="repo-links">
                <button type="button">리포지토리1</button>
                <button type="button">리포지토리2</button>
                <button type="button">리포지토리3</button>
                <button type="button">리포지토리4</button>
            </div>
        </section>

        <section class="section">
            <h3 class="section-title">Notion</h3>
            <label for="notion-link">링크 :</label>
            <input type="text" id="notion-link" placeholder="Notion 링크">
        </section>

        <section class="section">
            <h3 class="section-title">Blog</h3>
            <label for="blog-link">링크 :</label>
            <input type="text" id="blog-link" placeholder="블로그 링크">
        </section>

        <section class="section">
            <h3 class="section-title">File</h3>
            <label for="file-upload">첨부 파일 :</label>
            <input type="file" id="file-upload">
        </section>
    </main>
</div>
</body>
</html>

