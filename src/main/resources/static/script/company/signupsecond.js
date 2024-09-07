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
                    alert('이메일 인증을 완료해주세요.');
                    document.getElementById('id').focus();
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
    
                // 회사 홈페이지 URL에 "http://"를 추가
                if (joinDto.c_homePage && !joinDto.c_homePage.startsWith('http://')) {
                    joinDto.c_homePage = 'http://' + joinDto.c_homePage;
                }
    
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
    
                // CEO 이미지 파일 추가
                const ceoImageInputs = document.querySelectorAll('#ceoImageContainer input[type="file"]');
                ceoImageInputs.forEach((input, index) => {
                    if (input.files[0]) {
                        formData.append(`c_img`, input.files[0]);
                    }
                });
    
                // formData에 모든 데이터를 추가한 후
                for (let [key, value] of formData.entries()) {
                    console.log(key, value);
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
    
    document.addEventListener('DOMContentLoaded', function() {
        const emailVerifyBtn = document.getElementById('emailVerifyBtn');
        if (emailVerifyBtn) {
            emailVerifyBtn.addEventListener('click', function() {
                const email = document.getElementById('id').value;
                console.log('이메일 인증을 시도합니다. 이메일:', email);
                fetch('/com/email', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ email: email })
                })
                .then(response => response.json())
                .then(data => {
                    console.log('이메일 인증 응답:', data);
                    if (data.code == 200) {
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
        } else {
            console.error('emailVerifyBtn 요소를 찾을 수 없습니다.');
        }
    });
    
    document.addEventListener('DOMContentLoaded', function() {
        const verifyCodeBtn = document.getElementById('verifyCodeBtn');
        if (verifyCodeBtn) {
            verifyCodeBtn.addEventListener('click', function() {
                const verificationCode = document.getElementById('verificationCode').value;
                fetch('/com/email/verify', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        "certificationNumber" : verificationCode
                    })
                })
                .then(response => response.json())
                .then(data => {
                    if (data.code == 200) {
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
        } else {
            console.error('verifyCodeBtn 요소를 찾을 수 없습니다.');
        }
    });
