<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>기업 회원 가입</title>
    <link rel="stylesheet" href="/style/common/vendor/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" href="/css/company/signup/signup.css">
    <style>
        
    </style>
</head>
<body>
    <div class="signup-container">
        <form id="companySignupForm" name="companySignupForm">
            <h2 id="companySignupTitle">기업 회원 가입</h2>
            <div class="form-group">
                <label for="businessNumber"><i class="fas fa-building"></i> 사업자 번호</label>
                <input id="businessNumber" type="text" name="businessNumber" class="form-control" placeholder="사업자 번호를 입력하세요" required/>
            </div>
            <div class="form-group">
                <label for="name"><i class="fas fa-user"></i> 회사명</label>
                <input id="name" type="text" name="name" class="form-control" placeholder="회사명을 입력하세요" required/>
            </div>
            <div class="form-group">
                <label for="startDate"><i class="fas fa-calendar-alt"></i> 설립일</label>
                <input id="startDate" type="date" name="startDate" class="form-control" required/>
            </div>
            <button type="submit" class="btn btn-primary btn-block">사업자 인증</button>
        </form>
    </div>

    <script src="/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/resources/script/company/signupfirst.js"></script>
</body>
</html>