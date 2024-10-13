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
    
    <link rel="stylesheet" type="text/css" href="/resources/style/company/findauth/findauth.css">

    
<!-- Bootstrap JS -->
<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- 벤더 -->
<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

<!-- 테마 JS -->
<script src="/resources/script/common/function/functions.js"></script>

<!-- 아이디/비밀번호 찾기 JS -->
<script src="/resources/script/company/findauth.js"></script>


</head>

<body>
    <jsp:include page="/WEB-INF/views/member/header/member_header.jsp" />
<div class="find-container" style="height: 83.5vh;">
    <div class="find-wrapper">
        <div class="find-box">
            <h2 class="text-center mb-4" id="find-id-title">기업 회원 아이디 찾기</h2>
            <ul class="nav nav-tabs mb-4" id="findTab" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="find-id-tab" data-bs-toggle="tab" data-bs-target="#find-id" type="button" role="tab" aria-controls="find-id" aria-selected="true">아이디 찾기</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="find-pw-tab" data-bs-toggle="tab" data-bs-target="#find-pw" type="button" role="tab" aria-controls="find-pw" aria-selected="false">비밀번호 찾기</button>
                </li>
            </ul>
            <div class="tab-content mb-0 p-0" id="findTabContent">
                <div class="tab-pane fade show active" id="find-id" role="tabpanel" aria-labelledby="find-id-tab">
                    <form id="findIdForm">
                        <div class="mb-3">
                            <label for="businessNumber" class="form-label">사업자등록번호</label>
                            <input type="text" class="form-control" id="businessNumber" name="businessNumber" required placeholder="사업자등록번호를 입력하세요">
                        </div>

                        <div class="mb-3">
                            <label for="pwEmail" class="form-label">이메일</label>
                            <div class="input-group">
                                <input type="email" class="form-control me-2" id="idEmail" name="email" required placeholder="등록된 이메일 주소를 입력하세요">
                                <button type="button" id="idSendVerification" class="btn btn-primary rounded w-60">인증</button>
                            </div>
                        </div>
                        <div id="idVerificationSection" class="verification-section mb-3">
                            <div class="input-group">
                                <input type="text" class="form-control me-2" id="idVerificationCode" name="verificationCode" placeholder="인증번호를 입력하세요">
                                <button type="button" id="idVerifyCode" class="btn btn-secondary rounded w-60">확인</button>
                            </div>
                        </div>

                        <button type="submit" id="findIdButton" class="btn btn-primary w-100 mt-2">아이디 찾기</button>
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
                            <div class="input-group">
                                <input type="email" class="form-control me-2" id="pwEmail" name="email" required placeholder="등록된 이메일 주소를 입력하세요">
                                <button type="button" id="pwSendVerification" class="btn btn-primary rounded w-60">인증</button>
                            </div>
                        </div>
                        <div id="pwVerificationSection" class="verification-section mb-3">
                            <div class="input-group">
                                <input type="text" class="form-control me-2" id="pwVerificationCode" name="verificationCode" placeholder="인증번호를 입력하세요">
                                <button type="button" id="pwVerifyCode" class="btn btn-secondary rounded w-60">확인</button>
                            </div>
                        </div>

                        <button type="submit" id="findPwButton" class="btn btn-primary w-100 mt-2">비밀번호 찾기</button>
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

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" />

</body>
</html>
