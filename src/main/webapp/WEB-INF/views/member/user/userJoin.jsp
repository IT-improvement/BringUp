<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" type="text/css" href="/resources/style/member/join.css">

</head>
<body>
<div class="container">
    <h2>회원가입</h2>
    <form id="userJoin">
        <div class="form-group">
            <label for="userEmail">이메일</label>
            <div class="input-group">
                <input type="email" id="userEmail" name="userEmail" required>
                <button type="button" id="checkEmailBtn">중복 체크</button>
            </div>
            <span id="emailCheckResult"></span>
        </div>
        <div class="form-group">
            <label for="userPassword">비밀번호</label>
            <input type="password" id="userPassword" name="userPassword" required>
        </div>
        <div class="form-group">
            <label for="userName">이름</label>
            <input type="text" id="userName" name="userName" required>
        </div>
        <div class="form-group">
            <label for="userAddress">주소</label>
            <input type="text" id="userAddress" name="userAddress" required>
        </div>
        <div class="form-group">
            <label for="userPhonenumber">전화번호</label>
            <input type="tel" id="userPhonenumber" name="userPhonenumber" required>
        </div>
        <div class="form-group">
            <label for="userBirthday">생년월일</label>
            <input type="date" id="userBirthday" name="userBirthday" required>
        </div>
        <div class="form-group">
            <label for="freelancer">프리랜서 여부</label>
            <input type="checkbox" id="freelancer" name="freelancer">
            <label for="freelancer">프리랜서입니다.</label>
        </div>
        <div class="form-group">
            <label for="status">회원 상태</label>
            <select id="status" name="status" required>
                <option value="active">활성</option>
                <option value="inactive">비활성</option>
            </select>
        </div>
        <button type="submit" class="btn">회원가입</button>
    </form>
    <div class="footer">
        <p>이미 회원이신가요? <a href="#">로그인</a></p>
    </div>
</div>
<script src="/resources/script/member/userJoin.js"></script>
</body>
</html>
