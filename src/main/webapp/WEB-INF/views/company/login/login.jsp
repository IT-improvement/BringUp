<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>기업 회원 로그인</title>
    <link rel="stylesheet" href="/style/common/vendor/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" href="/css/company/login/login.css">
    <style>
        
    </style>
</head>
<body>
    <div class="login-container">
        <form action="/v1/company/login" method="post" name="companyLoginForm">
            <h2 id="companyLoginTitle">기업 회원 로그인</h2>
            <div class="form-group">
                <label for="username"><i class="fas fa-user"></i> 아이디</label>
                <input id="username" type="text" name="username" class="form-control" placeholder="아이디를 입력하세요" required/>
            </div>
            <div class="form-group">
                <label for="password"><i class="fas fa-lock"></i> 비밀번호</label>
                <input id="password" type="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요" required/>
            </div>
            <button type="submit" class="btn btn-primary btn-block">로그인</button>
        </form>
    </div>

    <script src="/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>