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

<div class="signupsecond-container">
    <div class="signupsecond-wrapper">
        <a href="/" class="main-link">메인으로</a>
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
                    <input type="url" class="form-control" id="c_homePage" name="c_homePage" placeholder="회사 홈페이지 URL을 입력하세요">
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
                    <button type="button" class="btn btn-secondary mt-2" id="addSalaryBtn">
                        <i class="fas fa-plus"></i> 연봉 정보 추가
                    </button>
                </div>
                <button type="submit" class="btn btn-primary w-100">회원가입 완료</button>
            </form>
        </div>
    </div>
</div>

<script>
    // 페이지 로드 시 실행되는 함수
document.addEventListener('DOMContentLoaded', function() {
    // 현재 URL이 '/company/auth/signup/second'인지 확인
    if (window.location.pathname === '/company/auth/signup/second') {
        // 세션 스토리지에서isValid 값 확인
        const isValid = sessionStorage.getItem('isValid');
        const company_opendate = sessionStorage.getItem('company_opendate');
        const company_licence = sessionStorage.getItem('company_licence');
        const master_name = sessionStorage.getItem('master_name');
        
        // isValid가 'true'가 아니거나 존재하지 않는 경우
        if (isValid !== 'true') {
            alert('사업자번호 진위여부를 먼저 인증해주세요.');
            window.location.href = '/company/auth/signup';
        }
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const salaryList = document.getElementById('salaryList');
    const addSalaryBtn = document.getElementById('addSalaryBtn');

    function addSalaryField() {
        const newField = document.createElement('div');
        newField.className = 'salary-field mb-2';
        newField.innerHTML = `
            <div class="input-group">
                <input type="text" class="form-control" name="salaries[].position" placeholder="직급" required>
                <input type="number" class="form-control" name="salaries[].minSalary" placeholder="최소연봉" step="10" min="0" required>
                <span class="input-group-text">만 원</span>
                <input type="number" class="form-control" name="salaries[].maxSalary" placeholder="최대연봉" step="10" min="0" required>
                <span class="input-group-text">만 원</span>
                <button type="button" class="btn btn-danger remove-salary">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        `;
        salaryList.appendChild(newField);

        newField.querySelector('.remove-salary').addEventListener('click', function() {
            salaryList.removeChild(newField);
        });
    }

    addSalaryBtn.addEventListener('click', addSalaryField);
});

document.addEventListener('DOMContentLoaded', function() {
    const companySignupsecondForm = document.getElementById('companySignupsecondForm');
    if (companySignupsecondForm) {
        companySignupsecondForm.addEventListener('submit', function(e) {
            e.preventDefault();

            // 필수 필드 검증
            const requiredFields = [
                {id: 'id', name: '이메일'},
                {id: 'password', name: '비밀번호'},
                {id: 'c_phone', name: '회사 전화번호'},
                {id: 'c_name', name: '회사명'},
                {id: 'm_name', name: '담당자명'},
                {id: 'm_phone', name: '담당자 번호'},
                {id: 'c_address', name: '회사 주소'},
                {id: 'c_category', name: '업종'},
                {id: 'c_content', name: '사업내용'},
                {id: 'c_scale', name: '회사 규모'},
                {id: 'c_size', name: '직원 수'}
            ];

            for (const field of requiredFields) {
                const element = document.getElementById(field.id);
                if (!element || !element.value.trim()) {
                    alert(`${field.name} 필드는 필수입니다.`);
                    element.focus();
                    return;
                }
            }

            // 이메일 인증 확인
            if (!sessionStorage.getItem('emailVerified')) {
                // alert('이메일 인증을 완료해주세요.');
                sessionStorage.setItem('emailVerified', 'true');
                return;
            }

            const joinDto = {
                company_opendate: sessionStorage.getItem('company_opendate'),
                company_licence: sessionStorage.getItem('company_licence'),
                master_name: sessionStorage.getItem('master_name'),
                id: document.getElementById('id').value,
                password: document.getElementById('password').value,
                c_phone: document.getElementById('c_phone').value,
                c_name: document.getElementById('c_name').value,
                m_name: document.getElementById('m_name').value,
                m_phone: document.getElementById('m_phone').value,
                c_address: document.getElementById('c_address').value + ' ' + (document.getElementById('c_address_detail').value || ''),
                c_category: document.getElementById('c_category').value,
                c_content: document.getElementById('c_content').value,
                welfare_benefits: document.getElementById('welfare_benefits').value || null,
                c_history: document.getElementById('c_history').value || null,
                c_scale: document.getElementById('c_scale').value,
                c_vision: document.getElementById('c_vision').value || null,
                c_size: parseInt(document.getElementById('c_size').value),
                c_homePage: document.getElementById('c_homePage').value || null,
                subsidiary: document.getElementById('subsidiary').value || null,
                financial_stat: document.getElementById('financial_stat').value || null,
                salaries: []
            };

            // 급여 정보 수집
            const salaryFields = document.querySelectorAll('.salary-field');
            salaryFields.forEach(field => {
                const position = field.querySelector('input[name="salaries[].position"]').value;
                const minSalary = field.querySelector('input[name="salaries[].minSalary"]').value;
                const maxSalary = field.querySelector('input[name="salaries[].maxSalary"]').value;
                
                if (position || minSalary || maxSalary) {
                    joinDto.salaries.push({
                        position: position || null,
                        minSalary: minSalary ? parseInt(minSalary) : null,
                        maxSalary: maxSalary ? parseInt(maxSalary) : null
                    });
                }
            });

            const formData = new FormData();

            // joinDto 객체를 JSON 문자열로 변환하여 Blob으로 추가
            const joinDtoBlob = new Blob([JSON.stringify(joinDto)], {type: 'application/json'});
            formData.append('joinDto', joinDtoBlob, 'joinDto.json');

            // 로고 파일 추가
            const logoFile = document.getElementById('c_logo').files[0];
            if (logoFile) {
                formData.append('c_logo', logoFile);
            }

            fetch('/com/join/second', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(result => {
                if (result.code === 201) {         
                    alert('회원가입이 완료되었습니다.');
                    window.location.href = '/company/auth/login';
                } else {
                    alert('회원가입에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(error => {
                console.error('오류:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            });
        });
    } else {
        console.error('companySignupsecondForm 요소를 찾을 수 없습니다.');
    }
});

// 주소 입력 필드 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const addressInput = document.getElementById('c_address');
    if (addressInput) {
        addressInput.addEventListener('click', function() {
            openAddressSearch();
        });
    } else {
        console.error('주소 입력 필드를 찾을 수 없습니다.');
    }
});

function openAddressSearch() {
    new daum.Postcode({
        oncomplete: function(data) {
            let addr = '';
            let extraAddr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            document.getElementById('c_address').value = addr + extraAddr;
            document.getElementById('c_address').dispatchEvent(new Event('input', { bubbles: true }));
            document.getElementById('detailAddressContainer').style.display = 'block';
            document.getElementById('c_address_detail').focus();
        }
    }).open();
}

document.getElementById('emailVerifyBtn').addEventListener('click', function() {
    const email = document.getElementById('id').value;
    fetch('/com/email', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email: email })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('인증메일을 보냈습니다.');
            document.getElementById('verificationCodeContainer').style.display = 'block';
        } else {
            alert('인증메일 발송에 실패했습니다. 다시 시도해주세요.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('인증메일 발송 중 오류가 발생했습니다.');
    });
});

document.getElementById('verifyCodeBtn').addEventListener('click', function() {
    const email = document.getElementById('id').value;
    const verificationCode = document.getElementById('verificationCode').value;
    fetch('/com/email/verify', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 
            email: email,
            verificationCode: verificationCode
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('인증이 완료되었습니다.');
            sessionStorage.setItem('emailVerified', 'true');
        } else {
            alert('인증에 실패했습니다. 인증번호를 확인해주세요.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('인증 확인 중 오류가 발생했습니다.');
    });
});
</script>

</body>
</html>