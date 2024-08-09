<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>아이디/비밀번호 찾기</title>

    <!-- 메타 태그 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">
    <meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">

    <!-- 파비콘 -->
    <link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

    <!-- 구글 폰트 -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- 플러그인 CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/apexcharts/css/apexcharts.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">

    <!-- 테마 CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

    <style>
        .find-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f8f9fa;
        }
        .find-wrapper {
            position: relative;
            width: 100%;
            max-width: 450px;
            padding-top: 2em;
        }
        .find-box {
            width: 100%;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
        }
        .additional-links {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .additional-links a {
            text-decoration: none;
            color: #007bff;
            font-weight: 500;
            transition: color 0.3s ease;
        }
        .additional-links a:hover {
            color: #0056b3;
        }
        .nav-tabs {
            border-bottom: 2px solid #dee2e6;
            display: flex;
            justify-content: center;
        }
        .nav-tabs .nav-item {
            width: 50%;
        }
        .nav-tabs .nav-link {
            color: #495057;
            border: none;
            border-bottom: 2px solid transparent;
            padding: 10px 0;
            font-weight: 500;
            transition: all 0.3s ease;
            width: 100%;
            text-align: center;
        }
        .nav-tabs .nav-link:hover {
            border-color: transparent;
            color: #007bff;
        }
        .nav-tabs .nav-link.active {
            color: #007bff;
            background-color: transparent;
            border-bottom: 2px solid #007bff;
        }
        .tab-content {
            padding-top: 20px;
        }
        .form-label {
            font-weight: 500;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            padding: 10px 0;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .verification-section {
            display: none;
        }
        .main-link {
            position: absolute;
            top: 0;
            left: 0;
            text-decoration: none;
            color: #007bff;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<div class="find-container">
    <div class="find-wrapper">
        <a href="/" class="main-link">메인으로</a>
        <div class="find-box">
            <h2 class="text-center mb-4">아이디/비밀번호 찾기</h2>
            <ul class="nav nav-tabs mb-4" id="findTab" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="find-id-tab" data-bs-toggle="tab" data-bs-target="#find-id" type="button" role="tab" aria-controls="find-id" aria-selected="true">아이디 찾기</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="find-pw-tab" data-bs-toggle="tab" data-bs-target="#find-pw" type="button" role="tab" aria-controls="find-pw" aria-selected="false">비밀번호 찾기</button>
                </li>
            </ul>
            <div class="tab-content" id="findTabContent">
                <div class="tab-pane fade show active" id="find-id" role="tabpanel" aria-labelledby="find-id-tab">
                    <form id="findIdForm">
                        <div class="mb-3">
                            <label for="businessNumber" class="form-label">사업자등록번호</label>
                            <input type="text" class="form-control" id="businessNumber" name="businessNumber" required placeholder="사업자등록번호를 입력하세요">
                        </div>
                        <div class="mb-3">
                            <label for="idEmail" class="form-label">이메일</label>
                            <input type="email" class="form-control" id="idEmail" name="email" required placeholder="등록된 이메일 주소를 입력하세요">
                        </div>
                        <button type="button" id="sendVerification" class="btn btn-primary w-100 mb-3">인증번호 전송</button>
                        <div id="verificationSection" class="verification-section mb-3">
                            <input type="text" class="form-control mb-2" id="verificationCode" name="verificationCode" placeholder="인증번호를 입력하세요">
                            <button type="button" id="verifyCode" class="btn btn-secondary w-100">인증번호 확인</button>
                        </div>
                        <button type="submit" id="findIdButton" class="btn btn-primary w-100">아이디 찾기</button>
                    </form>
                </div>
                <div class="tab-pane fade" id="find-pw" role="tabpanel" aria-labelledby="find-pw-tab">
                    <form id="findPwForm">
                        <div class="mb-3">
                            <label for="pwBusinessNumber" class="form-label">사업자등록번호</label>
                            <input type="text" class="form-control" id="pwBusinessNumber" name="businessNumber" required placeholder="사업자등록번호를 입력하세요">
                        </div>
                        <div class="mb-3">
                            <label for="pwUserId" class="form-label">아이디</label>
                            <input type="text" class="form-control" id="pwUserId" name="userId" required placeholder="아이디를 입력하세요">
                        </div>
                        <div class="mb-3">
                            <label for="pwEmail" class="form-label">이메일</label>
                            <input type="email" class="form-control" id="pwEmail" name="email" required placeholder="등록된 이메일 주소를 입력하세요">
                        </div>
                        <button type="button" id="pwSendVerification" class="btn btn-primary w-100 mb-3">인증번호 전송</button>
                        <div id="pwVerificationSection" class="verification-section mb-3">
                            <input type="text" class="form-control mb-2" id="pwVerificationCode" name="verificationCode" placeholder="인증번호를 입력하세요">
                            <button type="button" id="pwVerifyCode" class="btn btn-secondary w-100">인증번호 확인</button>
                        </div>
                        <button type="submit" id="findPwButton" class="btn btn-primary w-100">비밀번호 찾기</button>
                    </form>
                </div>
            </div>
            <div class="additional-links">
                <a href="/company/auth/login">로그인</a>
                <a href="/company/auth/signup">회원가입</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- 벤더 -->
<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

<!-- 테마 JS -->
<script src="/resources/script/common/function/functions.js"></script>

<!-- 아이디/비밀번호 찾기 JS -->
<script src="/resources/script/company/findauth.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const businessNumberInput = document.getElementById('businessNumber');
        const idEmailInput = document.getElementById('idEmail');
        const sendVerificationButton = document.getElementById('sendVerification');
        const verificationSection = document.getElementById('verificationSection');
        const verifyCodeButton = document.getElementById('verifyCode');
        const findIdButton = document.getElementById('findIdButton');

        const pwBusinessNumberInput = document.getElementById('pwBusinessNumber');
        const pwUserIdInput = document.getElementById('pwUserId');
        const pwEmailInput = document.getElementById('pwEmail');
        const pwSendVerificationButton = document.getElementById('pwSendVerification');
        const pwVerificationSection = document.getElementById('pwVerificationSection');
        const pwVerifyCodeButton = document.getElementById('pwVerifyCode');
        const findPwButton = document.getElementById('findPwButton');

        sendVerificationButton.addEventListener('click', function() {
            if (businessNumberInput.value.trim() === '' || idEmailInput.value.trim() === '') {
                alert('사업자등록번호와 이메일을 모두 입력해주세요.');
            } else {
                // 여기에 인증번호 전송 로직 추가
                verificationSection.style.display = 'block';
                alert('인증번호를 전송했습니다.');
            }
        });

        verifyCodeButton.addEventListener('click', function() {
            // 여기에 인증번호 확인 로직 추가
            alert('인증번호가 확인되었습니다.');
        });

        findIdButton.addEventListener('click', function(e) {
            e.preventDefault();
            if (businessNumberInput.value.trim() === '' || idEmailInput.value.trim() === '') {
                alert('사업자등록번호와 이메일을 모두 입력해주세요.');
            } else if (verificationSection.style.display !== 'block') {
                alert('인증번호 전송 버튼을 눌러 인증을 진행해주세요.');
            } else {
                // 여기에 아이디 찾기 로직 추가
                alert('아이디 찾기를 진행합니다.');
            }
        });

        pwSendVerificationButton.addEventListener('click', function() {
            if (pwBusinessNumberInput.value.trim() === '' || pwUserIdInput.value.trim() === '' || pwEmailInput.value.trim() === '') {
                alert('사업자등록번호, 아이디, 이메일을 모두 입력해주세요.');
            } else {
                // 여기에 인증번호 전송 로직 추가
                pwVerificationSection.style.display = 'block';
                alert('인증번호를 전송했습니다.');
            }
        });

        pwVerifyCodeButton.addEventListener('click', function() {
            // 여기에 인증번호 확인 로직 추가
            alert('인증번호가 확인되었습니다.');
        });

        findPwButton.addEventListener('click', function(e) {
            e.preventDefault();
            if (pwBusinessNumberInput.value.trim() === '' || pwUserIdInput.value.trim() === '' || pwEmailInput.value.trim() === '') {
                alert('사업자등록번호, 아이디, 이메일을 모두 입력해주세요.');
            } else if (pwVerificationSection.style.display !== 'block') {
                alert('인증번호 전송 버튼을 눌러 인증을 진행해주세요.');
            } else {
                // 여기에 비밀번호 찾기 로직 추가
                alert('비밀번호 찾기를 진행합니다.');
            }
        });
    });
</script>

</body>
</html>
