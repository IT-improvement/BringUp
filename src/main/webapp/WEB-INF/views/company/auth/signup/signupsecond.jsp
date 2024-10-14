<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>기업 회원 가입 - 2단계</title>

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

    <link rel="stylesheet" type="text/css" href="/resources/style/company/signup/signupsecond.css">

    
<!-- Bootstrap JS -->
<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- 벤더 -->
<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

<!-- 테마 JS -->
<script src="/resources/script/common/function/functions.js"></script>

<!-- 회원가입 JS -->
<script src="/resources/script/company/signupsecond.js"></script>

<!-- 카카오 주소 API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp"/>
<div class="signupsecond-container bg-light d-flex align-items-center justify-content-center" style="min-height:83.5vh;">
    <div class="signupsecond-wrapper bg-white mx-auto my-5 border rounded-3 p-4 shadow" style="width: 700px;">
        <div class="signupsecond-box">
            <h2 class="text-center mb-4">기업 회원 가입 - 2단계</h2>
            <form id="companySignupsecondForm" name="companySignupsecondForm" enctype="multipart/form-data" autocomplete="off">
                <div class="mb-3">
                    <label for="id" class="form-label"><i class="fas fa-envelope"></i> 이메일 *</label>
                    <div class="input-group">
                        <input type="email" class="form-control" id="id" name="id" placeholder="이메일을 입력하세요" required autocomplete="new-email">
                        <button type="button" class="btn btn-primary" id="emailVerifyBtn">인증</button>
                    </div>
                </div>
                <div class="mb-3" id="verificationCodeContainer" style="display: none;">
                    <label for="verificationCode" class="form-label"><i class="fas fa-check-circle"></i> 인증번호</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="verificationCode" name="verificationCode" placeholder="인증번호를 입력하세요">
                        <button type="button" class="btn btn-primary" id="verifyCodeBtn">확인</button>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label"><i class="fas fa-lock"></i> 비밀번호 *</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력하세요" required autocomplete="new-password">
                </div>
                <div class="mb-3">
                    <label for="c_phone" class="form-label"><i class="fas fa-phone"></i> 회사 전화번호 *</label>
                    <input type="tel" class="form-control" id="c_phone" name="c_phone" placeholder="회사 전화번호를 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="c_name" class="form-label"><i class="fas fa-building"></i> 회사명 *</label>
                    <input type="text" class="form-control" id="c_name" name="c_name" placeholder="회사명을 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="c_logo" class="form-label"><i class="fas fa-image"></i> 회사 로고</label>
                    <input type="file" class="form-control" id="c_logo" name="c_logo" accept="image/*">
                </div>
                <div class="mb-3" id="ceoImageContainer">
                    <label for="ceo_image" class="form-label"><i class="fas fa-image"></i> 회사 대표 이미지 *</label>
                    <div class="d-flex flex-wrap">
                        <div class="input-group mb-2 mr-2">
                            <input type="file" class="form-control" id="ceo_image" name="c_img" accept="image/*" required>
                        </div>
                        <button type="button" class="btn btn-primary mb-2 w-100" id="addCeoImage">이미지 추가</button>
                    </div>
                </div>
                <script>
                    document.getElementById('addCeoImage').addEventListener('click', function() {
                        const ceoImageContainer = document.getElementById('ceoImageContainer').querySelector('.d-flex');
                        const existingInputs = ceoImageContainer.querySelectorAll('.input-group');
                        if (existingInputs.length < 5) { 
                            const inputGroup = document.createElement('div');
                            inputGroup.classList.add('input-group', 'mb-2', 'mr-2');
                            
                            const newInput = document.createElement('input');
                            newInput.type = 'file';
                            newInput.name = 'c_img';
                            newInput.accept = 'image/*';
                            newInput.required = true;
                            newInput.classList.add('form-control');
                            
                            const removeButton = document.createElement('button');
                            removeButton.type = 'button';
                            removeButton.textContent = 'X';
                            removeButton.classList.add('btn', 'btn-danger');
                            removeButton.addEventListener('click', function() {
                                ceoImageContainer.removeChild(inputGroup);
                            });
                            
                            inputGroup.appendChild(newInput);
                            inputGroup.appendChild(removeButton);
                            ceoImageContainer.insertBefore(inputGroup, this);
                        } else {
                            alert('이미지 입력칸은 최대 5개까지 생성할 수 있습니다.');
                        }
                    });
                </script>
                <div class="mb-3">
                    <label for="m_name" class="form-label"><i class="fas fa-user"></i> 담당자명 *</label>
                    <input type="text" class="form-control" id="m_name" name="m_name" placeholder="담당자명을 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="m_phone" class="form-label"><i class="fas fa-mobile-alt"></i> 담당자 번호 *</label>
                    <input type="tel" class="form-control" id="m_phone" name="m_phone" placeholder="담당자 번호를 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="c_address" class="form-label"><i class="fas fa-map-marker-alt"></i> 회사 주소 *</label>
                    <input type="text" class="form-control" id="c_address" name="c_address" placeholder="회사 주소를 입력하세요" required readonly>
                </div>
                <div class="mb-3" id="detailAddressContainer" style="display: none;">
                    <label for="c_address_detail" class="form-label"><i class="fas fa-map-pin"></i> 상세 주소</label>
                    <input type="text" class="form-control" id="c_address_detail" name="c_address_detail" placeholder="상세 주소를 입력하세요">
                </div>
                <div class="mb-3">
                    <label for="c_category" class="form-label"><i class="fas fa-industry"></i> 업종 *</label>
                    <input type="text" class="form-control" id="c_category" name="c_category" placeholder="업종을 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="c_content" class="form-label"><i class="fas fa-briefcase"></i> 사업내용 *</label>
                    <textarea class="form-control" id="c_content" name="c_content" placeholder="사업내용을 입력하세요" required></textarea>
                </div>
                <div class="mb-3">
                    <label for="welfare_benefits" class="form-label"><i class="fas fa-heart"></i> 복지</label>
                    <textarea class="form-control" id="welfare_benefits" name="welfare_benefits" placeholder="복지 내용을 입력하세요"></textarea>
                </div>
                <div class="mb-3">
                    <label for="c_history" class="form-label"><i class="fas fa-history"></i> 연혁</label>
                    <textarea class="form-control" id="c_history" name="c_history" placeholder="회사 연혁을 입력하세요"></textarea>
                </div>
                <div class="mb-3">
                    <label for="c_scale" class="form-label"><i class="fas fa-chart-bar"></i> 회사 규모 *</label>
                    <select class="form-control" id="c_scale" name="c_scale" required>
                        <option value="">선택하세요</option>
                        <option value="중소">중소기업</option>
                        <option value="중견">중견기업</option>
                        <option value="대">대기업</option>
                        <option value="공">공기업</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="c_vision" class="form-label"><i class="fas fa-eye"></i> 회사 비전</label>
                    <textarea class="form-control" id="c_vision" name="c_vision" placeholder="회사 비전을 입력하세요"></textarea>
                </div>
                <div class="mb-3">
                    <label for="c_size" class="form-label"><i class="fas fa-users"></i> 직원 수 *</label>
                    <input type="number" class="form-control" id="c_size" name="c_size" placeholder="직원 수를 입력하세요" required>
                </div>
                <div class="mb-3">
                    <label for="c_homePage" class="form-label"><i class="fas fa-globe"></i> 회사 홈페이지</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">http://</span>
                        </div>
                        <input type="text" class="form-control" id="c_homePage" name="c_homePage" placeholder="회사 홈페이지 URL을 입력하세요">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="subsidiary" class="form-label"><i class="fas fa-sitemap"></i> 계열사</label>
                    <input type="text" class="form-control" id="subsidiary" name="subsidiary" placeholder="계열사를 입력하세요">
                </div>
                <div class="mb-3">
                    <label for="financial_stat" class="form-label"><i class="fas fa-chart-line"></i> 재무재표</label>
                    <textarea class="form-control" id="financial_stat" name="financial_stat" placeholder="재무재표 정보를 입력하세요"></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label"><i class="fas fa-money-bill-wave"></i> 직급별 연봉 정보</label>
                    <div id="salaryList">
                    </div>
                    <button type="button" class="btn btn-secondary mt-2 w-100" id="addSalaryBtn">
                        <i class="fas fa-plus"></i> 연봉 정보 추가
                    </button>
                </div>
                <button type="submit" class="btn btn-primary w-100">회원가입 완료</button>
            </form>
            <div class="text-center mt-3 d-flex justify-content-between">
                <a href="/company/auth/findauth" class="findauth-link">아이디/비밀번호 찾기</a>
                <a href="/company/auth/login" class="login-link">로그인</a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" />
</body>
</html>