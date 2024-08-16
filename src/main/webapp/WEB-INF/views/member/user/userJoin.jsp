<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 400px; /* max-width를 조정하여 전체 컨테이너의 너비를 설정 */
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        .input-group {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
        }
        .input-group input[type="email"] {
            flex: 1;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        .input-group button {
            margin-left: 10px;
            padding: 10px 15px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        .input-group button:hover {
            background-color: #218838;
        }
        .form-group {
            margin-bottom: 20px;
        }
        input[type="text"], input[type="password"], input[type="date"], input[type="tel"], select {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 0 auto 15px auto;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            display: block;
        }
        input[type="checkbox"] {
            margin-right: 10px;
        }
        .btn {
            width: calc(100% - 20px);
            padding: 15px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
            margin: 0 auto;
            display: block;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .footer {
            text-align: center;
            margin-top: 20px;
            color: #777;
        }
        .footer a {
            color: #007BFF;
            text-decoration: none;
        }
        .footer a:hover {
            text-decoration: underline;
        }
        #emailCheckResult {
            margin-top: 5px;
            font-size: 14px;
            color: red; /* 기본 색상: 빨간색 (에러 시) */
        }
    </style>
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
<script src="/resources/script/member/userSingup.js"></script>
<script src="/resources/script/member/userIdCheck.js"></script>
<script>

</script>

</body>
</html>
