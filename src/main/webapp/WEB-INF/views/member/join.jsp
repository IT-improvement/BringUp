
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/joinProc" method="post" name="joinForm">
    <input type="email" name="user_email" placeholder="이메일" required/><br>
    <input type="password" name="user_password" placeholder="비밀번호" required/><br>
    <input type="text" name="user_name" placeholder="이름" required/><br>
    <input type="text" name="user_address" placeholder="주소" required/><br>
    <input type="text" name="user_phonenumber" placeholder="전화번호" required/><br>
    <input type="date" name="user_birthday" placeholder="생년월일" required/><br>
    <input type="checkbox" name="freelancer" value="yes"> 프리랜서<br>
    <input type="submit" value="회원가입"/>
</form>
</body>
</html>
