<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 테마 CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/member/login.css">
    <title>로그인</title>
</head>
<body>
<div class="container">
    <h2>로그인</h2>
    <form id="userLoginForm">
        <div class="form-group">
            <label for="userEmail">이메일</label>
            <input type="text" id="userEmail" name="userEmail" required>
        </div>
        <div class="form-group">
            <label for="userPassword">비밀번호</label>
            <input type="password" id="userPassword" name="userPassword" required>
        </div>
        <button type="submit" class="btn">로그인</button>
    </form>
    <div class="links">
        <a href="/company/login">컴퍼니 로그인</a> <!-- 컴퍼니 로그인 페이지로 이동 -->
        <a href="#">비밀번호 찾기</a> <!-- 비밀번호 찾기 페이지로 이동 -->
        <a href="/member/join">회원가입</a> <!-- 회원가입 페이지로 이동 -->
    </div>
</div>
<script src="/resources/script/member/userLogin.js"></script>
</body>
</html>
