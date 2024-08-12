
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            text-align: center;
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
        input[type="text"], input[type="password"] {
            width: calc(100% - 20px); /* 양쪽 간격을 동일하게 설정 */
            padding: 10px;
            margin: 0 auto 15px auto; /* 하단 여백을 주고 상단은 고정 */
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            display: block;
        }
        .btn {
            width: calc(100% - 20px); /* 양쪽 간격을 동일하게 설정 */
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
        .links {
            margin-top: 20px;
        }
        .links a {
            color: #007BFF;
            text-decoration: none;
            display: block;
            margin-bottom: 10px;
        }
        .links a:hover {
            text-decoration: underline;
        }
        .footer {
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
    </style>
</head>
<body>
<div class="container">
    <h2>로그인</h2>
    <form>
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

</body>
</html>