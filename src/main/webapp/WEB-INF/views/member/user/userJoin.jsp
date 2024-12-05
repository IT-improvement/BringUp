<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>회원 가입</title>

    <!-- Meta Tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">
    <meta name="description" content="회원가입 페이지">

    <!-- Favicon -->
    <link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- Plugins CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/apexcharts/css/apexcharts.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">

    <!-- Theme CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

    <!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Custom JS -->
    <script src="/resources/script/common/function/functions.js"></script>

    <!-- User Join JS -->
    <script src="/resources/script/member/userJoin.js"></script>

    <style>
        body {
            font-family: 'Nunito Sans', sans-serif;
        }
        .user-signup-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 85vh;
            background-color: #f8f9fa;
            position: relative;
            overflow: hidden;
        }

        .user-signup-wrapper {
            background-color: #ffffff;
            width: 100%;
            max-width: 350px;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
            z-index: 1;
            position: relative;
            margin-top: 5%;
            margin-bottom: 5%;
        }
        .user-signup-box h2 {
            font-size: 1.4rem;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
        }
        .user-form-label {
            font-size: 0.85rem;
            color: #333;
            margin-bottom: 5px;
            display: block;
        }

        .user-form-control {
            border-radius: 5px;
            height: 40px;
            font-size: 0.85rem;
            border: 1px solid #ced4da;
            margin-bottom: 15px;
            padding: 8px 12px;
            width: 100%;
        }

        .user-form-control:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0px 0px 3px rgba(0, 123, 255, 0.3);
        }
        .user-btn-primary {
            background-color: #007bff;
            border: none;
            height: 40px;
            font-size: 0.9rem;
            font-weight: bold;
            color: #fff;
            width: 100%;
            border-radius: 5px;
            margin-top: 10px;
        }
        .user-btn-primary:hover {
            background-color: #0056b3;
        }
        .user-email-check-container {
            display: flex;}
        .user-form-control-email {
            flex: 1;
        }
        .user-email-check-button {
            background-color: #28a745;
            color: #fff;
            border: none;
            font-size: 0.85rem;
            font-weight: bold;
            padding: 0 12px;
            border-radius: 5px;
            height: 40px;
            margin-left: 10px;
            white-space: nowrap;
        }
        .user-email-check-button:hover {
            background-color: #218838;
        }
        .user-additional-links {
            display: flex;
            justify-content: space-between;
            font-size: 0.85rem;
            color: #007bff;
            margin-top: 15px;
        }
        .user-additional-links a {
            color: #007bff;
            text-decoration: none;
        }
        .user-additional-links a:hover {
            text-decoration: underline;
        }
        #emailCheckResult {
            font-size: 0.85rem;
        }
        .user-gender-selection {
            display: flex;
            margin-bottom: 15px;
        }

        .user-gender-button {
            flex: 1;
            padding: 10px 0;
            border: 1px solid #ced4da;
            background-color: #f8f9fa;
            font-weight: bold;
            color: #333;
            text-align: center;
            cursor: pointer;
        }

        .user-gender-button.active {
            background-color: #007bff;
            color: white;
            border: 1px solid #007bff;
        }


        .military-service-card {
            background-color: #ffffff;
            width: 100%;
            max-width: 350px;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
            position: absolute;
            top: 0;
            left: 100%;
            transition: transform 0.5s ease;
            z-index: 0;
        }

        .military-service-card.visible {
            transform: translateX(-100%);
        }


        .user-btn-primary, .user-btn-secondary {
            height: 40px;
            font-size: 0.9rem;
            font-weight: bold;
            width: 100%;
            border-radius: 5px;
        }

        .user-btn-primary {
            background-color: #007bff;
            color: #fff;
            border: none;
            margin-top: 10px;
        }

        .user-btn-primary:hover {
            background-color: #0056b3;
        }

        .user-btn-secondary {
            background-color: #dc3545;
            color: #fff;
            border: none;
            margin-top: 10px;
        }

        .user-btn-secondary:hover {
            background-color: #c82333;
        }


    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" />

<div class="user-signup-container">
    <div class="user-signup-wrapper">
        <div class="user-signup-box">
            <img class="navbar-brand-item light-mode-item" src="/resources/style/common/images/Logo.png" alt="로고" style="width: 50%; margin-left: 25%; margin-bottom: 5%;">
            <form id="userSignupForm">
                <label for="userEmail" class="user-form-label"><i class="fas fa-envelope"></i> 이메일</label>
                <div class="user-email-check-container">
                    <input type="email" class="user-form-control user-form-control-email" id="userEmail" name="user_email" placeholder="이메일을 입력하세요" required>
                    <button type="button" class="user-email-check-button" id="checkEmailBtn">중복 체크</button>
                </div>
                <div id="emailCheckResult"></div>

                <label for="userPassword" class="user-form-label"><i class="fas fa-lock"></i> 비밀번호</label>
                <input type="password" class="user-form-control" id="userPassword" name="user_password" placeholder="비밀번호를 입력하세요" required>

                <label for="userName" class="user-form-label"><i class="fas fa-user"></i> 이름</label>
                <input type="text" class="user-form-control" id="userName" name="user_name" placeholder="이름을 입력하세요" required>

                <label class="user-form-label"><i class="fas fa-mercury"></i>성별</label>
                <div class="user-gender-selection">
                    <button type="button" class="user-gender-button active" data-gender="남" id="maleButton">남</button>
                    <button type="button" class="user-gender-button" data-gender="여" id="femaleButton">여</button>
                    <input type="hidden" id="gender" name="gender" value="">

                </div>

                <!-- 병역 영역 -->
                <div class="military-service-card" id="militaryServiceCard" style="display: none;">
                    <h3>병역 정보</h3>
                    <div>
                        <label class="user-form-label">군필 여부</label>
                        <input type="hidden" id="militaryStatusHidden" name="military_status_hidden" value="">
                        <select class="user-form-control" id="militaryStatus" name="military.status" required>
                            <option value="군필">군필</option>
                            <option value="미필">미필</option>
                            <option value="면제">면제</option>
                        </select>

                        <label class="user-form-label">군별</label>
                        <input type="text" class="user-form-control" id="militaryType" name="military_type" placeholder="육군, 해군 등" required>

                        <label class="user-form-label">병과</label>
                        <input type="text" class="user-form-control" id="specialty" name="specialty" placeholder="병과를 입력하세요">

                        <label class="user-form-label">계급</label>
                        <input type="text" class="user-form-control" id="rankName" name="rank_name" placeholder="계급을 입력하세요">

                        <label class="user-form-label">전역사유</label>
                        <input type="text" class="user-form-control" id="dischargeReason" name="discharge_reason" placeholder="전역사유를 입력하세요">

                        <label class="user-form-label">입대일자</label>
                        <input type="date" class="user-form-control" id="enlistmentDate" name="enlistment_date">

                        <label class="user-form-label">제대일자</label>
                        <input type="date" class="user-form-control" id="dischargeDate" name="discharge_date">

                        <label class="user-form-label">면제사유</label>
                        <input type="text" class="user-form-control" id="exemptionReason" name="exemption_reason" placeholder="면제사유를 입력하세요">

                        <button type="button" class="user-btn-secondary" id="closeMilitaryCard">닫기</button>
                    </div>
                </div>

                <label for="userAddress" class="user-form-label"><i class="fas fa-map-marker-alt"></i> 주소</label>
                <input type="text" class="user-form-control" id="userAddress" name="user_address" placeholder="주소를 입력하세요" required>

                <label for="userPhoneNumber" class="user-form-label"><i class="fas fa-phone"></i> 전화번호</label>
                <input type="text" class="user-form-control" id="userPhoneNumber" name="user_phonenumber" placeholder="전화번호를 입력하세요" required>

                <label for="userBirthday" class="user-form-label"><i class="fas fa-calendar-alt"></i> 생년월일</label>
                <input type="date" class="user-form-control" id="userBirthday" name="user_birthday" required>

                <button type="submit" class="user-btn-primary">회원 가입</button>

            </form>
            <div class="user-additional-links">
                <a href="/member/auth/findauth">아이디/비밀번호 찾기</a>
                <a href="/member/auth/login">로그인</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" />
</body>
</html>
