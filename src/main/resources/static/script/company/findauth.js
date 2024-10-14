document.addEventListener('DOMContentLoaded', function() {
    const commonFieldsTemplate = document.getElementById('commonFieldsTemplate');
    const findIdForm = document.getElementById('findIdForm');
    const findPwForm = document.getElementById('findPwForm');
    const findTitle = document.getElementById('find-title');

    let codesend = false;
    let emailAccess = false;

    // 아이디 찾기 폼에 공통 필드 추가
    const idClone = document.importNode(commonFieldsTemplate.content, true);
    findIdForm.insertBefore(idClone, findIdForm.firstChild);

    // 비밀번호 찾기 폼에 공통 필드 추가
    const pwClone = document.importNode(commonFieldsTemplate.content, true);
    findPwForm.insertBefore(pwClone, findPwForm.querySelector('#findPwButton'));

    // 탭 전환 시 필드 초기화 및 제목 변경
    const tabs = document.querySelectorAll('[data-bs-toggle="tab"]');
    tabs.forEach(tab => {
        tab.addEventListener('shown.bs.tab', function (e) {
            const sourceForm = e.relatedTarget.getAttribute('data-bs-target') === '#find-id' ? findIdForm : findPwForm;
            const targetForm = e.target.getAttribute('data-bs-target') === '#find-id' ? findIdForm : findPwForm;
            
            // 사업자등록번호와 이메일 값 유지
            const sourceBusinessNumber = sourceForm.querySelector('.businessNumber').value;
            const sourceEmail = sourceForm.querySelector('.email').value;
            
            targetForm.querySelector('.businessNumber').value = sourceBusinessNumber;
            targetForm.querySelector('.email').value = sourceEmail;
            
            // 나머지 필드 초기화
            targetForm.querySelectorAll('input:not(.businessNumber):not(.email)').forEach(input => {
                input.value = '';
                input.classList.remove('is-invalid');
            });
            targetForm.querySelector('.verification-section').style.display = 'none';
            
            // codesend와 emailAccess 초기화
            codesend = false;
            emailAccess = false;
            
            // 제목 변경
            if (e.target.getAttribute('data-bs-target') === '#find-id') {
                findTitle.textContent = '기업 회원 아이디 찾기';
            } else {
                findTitle.textContent = '기업 회원 비밀번호 찾기';
            }
        });
    });

    const businessNumbers = document.querySelectorAll(".businessNumber");
    const userId = document.getElementById("pwUserId");
    const emails = document.querySelectorAll(".email");

    businessNumbers.forEach(businessNumber => {
        const tooltip1 = new bootstrap.Tooltip(businessNumber, {
            trigger: 'manual',
            placement: 'bottom',
            title: '사업자등록번호를 확인해주세요.',
            template: '<div class="tooltip tooltip-1" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
        });

        const tooltip2 = new bootstrap.Tooltip(businessNumber, {
            trigger: 'manual',
            placement: 'bottom',
            title: '사업자 등록 번호 형식으로 입력해주세요.',
            template: '<div class="tooltip tooltip-2" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
        });

        businessNumber.addEventListener("input", function(e) {
            // 숫자만 입력 가능하도록
            let value = this.value.replace(/[^0-9]/g, '');
            
            // 최대 10글자까지만 입력 가능하도록
            if (value.length > 10) {
                value = value.slice(0, 10);
            }
            
            // 3-2-5 형식으로 표시
            if (value.length > 5) {
                value = value.replace(/(\d{3})(\d{2})(\d{0,5})/, '$1-$2-$3');
            } else if (value.length > 3) {
                value = value.replace(/(\d{3})(\d{0,2})/, '$1-$2');
            }
            
            this.value = value;
        });

        businessNumber.addEventListener("focusout", function(){
            this.classList.remove("is-invalid");
            tooltip1.hide();
            tooltip2.hide();
        });

        // 툴팁 인스턴스를 요소에 저장
        businessNumber.tooltip1 = tooltip1;
        businessNumber.tooltip2 = tooltip2;
    });

    emails.forEach(email => {
        const emailTooltip1 = new bootstrap.Tooltip(email, {
            trigger: 'manual',
            placement: 'bottom',
            title: '이메일을 입력해주세요.',
            template: '<div class="tooltip tooltip-1" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
        });

        const emailTooltip2 = new bootstrap.Tooltip(email, {
            trigger: 'manual',
            placement: 'bottom',
            title: '올바른 이메일 형식으로 입력해주세요.',
            template: '<div class="tooltip tooltip-2" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
        });

        email.addEventListener("focusout", function(){
            this.classList.remove("is-invalid");
            emailTooltip1.hide();
            emailTooltip2.hide();
        });

        // 툴팁 인스턴스를 요소에 저장
        email.tooltip1 = emailTooltip1;
        email.tooltip2 = emailTooltip2;
    });

    const useridTooltip1 = new bootstrap.Tooltip(userId, {
        trigger: 'manual',
        placement: 'bottom',
        title: '아이디를 입력해주세요.',
        template: '<div class="tooltip tooltip-1" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
    });

    // userId 요소에 툴팁 인스턴스 저장
    userId.tooltip1 = useridTooltip1;

    document.getElementById("findIdButton").addEventListener("click", function(e){
        e.preventDefault();
        if (!validateBusinessNumber(findIdForm.querySelector('.businessNumber'))) return;
        if (!validateEmail(findIdForm.querySelector('.email'))) return;
        
        if (!codesend) {
            alert("이메일 인증을 진행해주세요.");
            return;
        }
        if (!emailAccess) {
            alert("인증번호 확인을 진행해주세요.");
            return;
        }
        // 여기에 아이디 찾기 로직 추가
        console.log("아이디 찾기 로직 실행");
    });

    document.getElementById("findPwButton").addEventListener("click", function(e){
        e.preventDefault();
        if (!validateUserId(userId)) return;
        if (!validateBusinessNumber(findPwForm.querySelector('.businessNumber'))) return;
        if (!validateEmail(findPwForm.querySelector('.email'))) return;
        
        if (!codesend) {
            alert("이메일 인증을 진행해주세요.");
            return;
        }
        if (!emailAccess) {
            alert("인증번호 확인을 진행해주세요.");
            return;
        }
        // 여기에 비밀번호 찾기 로직 추가
        console.log("비밀번호 찾기 로직 실행");
    });

    function validateUserId(userIdElement) {
        // 기존 툴팁 숨기기
        userIdElement.tooltip1.hide();

        if(userIdElement.value.trim() === "") {
            userIdElement.classList.add("is-invalid");
            userIdElement.tooltip1.show();
            userIdElement.focus();
            return false;
        }
        userIdElement.classList.remove("is-invalid");
        return true;
    }

    function validateBusinessNumber(businessNumberElement) {
        // 기존 툴팁 숨기기
        businessNumberElement.tooltip1.hide();
        businessNumberElement.tooltip2.hide();

        // 하이픈을 제거한 순수 숫자만으로 검사
        const pureNumber = businessNumberElement.value.replace(/-/g, '');

        if(pureNumber === ""){
            businessNumberElement.classList.add("is-invalid");
            businessNumberElement.tooltip1.show();
            businessNumberElement.focus();
            return false;
        }
        else if(pureNumber.length !== 10){
            businessNumberElement.classList.add("is-invalid");
            businessNumberElement.tooltip2.show();
            businessNumberElement.focus();
            return false;
        }
        businessNumberElement.classList.remove("is-invalid");
        return true;
    }

    function validateEmail(emailElement) {
        // 기존 툴팁 숨기기
        emailElement.tooltip1.hide();
        emailElement.tooltip2.hide();

        if(emailElement.value.trim() === "") {
            emailElement.classList.add("is-invalid");
            emailElement.tooltip1.show();
            emailElement.focus();
            return false;
        } else if(!/^\S+@\S+\.\S+$/.test(emailElement.value)){
            emailElement.classList.add("is-invalid");
            emailElement.tooltip2.show();
            emailElement.focus();
            return false;
        }
        emailElement.classList.remove("is-invalid");
        return true;
    }

    document.querySelectorAll('.verificationCode').forEach(input => {
        input.addEventListener("input", function(e) {
            // 숫자만 입력 가능하도록
            let value = this.value.replace(/[^0-9]/g, '');
            
            // 최대 6글자까지만 입력 가능하도록
            if (value.length > 6) {
                value = value.slice(0, 6);
            }
            
            this.value = value;
        });
    });

    // 이메일 인증 버튼 클릭 이벤트 핸들러 수정
    document.querySelectorAll('.sendVerification').forEach(button => {
        button.addEventListener('click', function() {
            const emailInput = this.closest('.input-group').querySelector('.email');
            if (validateEmail(emailInput)) {
                const email = emailInput.value;
                
                fetch('/com/email', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ email: email }),
                })
                .then(response => response.json())
                .then(data => {
                    if (data.code === 200) {
                        codesend = true;
                        this.closest('.mb-3').nextElementSibling.style.display = 'block';
                        alert("인증 메일이 발송되었습니다. 이메일을 확인해주세요.");
                    } else {
                        alert("인증 메일 발송에 실패했습니다. 다시 시도해주세요.");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("인증 메일 발송 중 오류가 발생했습니다.");
                });
            }
        });
    });

    // 인증번호 확인 버튼 클릭 이벤트 핸들러 수정
    document.querySelectorAll('.verifyCode').forEach(button => {
        button.addEventListener('click', function() {
            const code = this.closest('.input-group').querySelector('.verificationCode').value;
            console.log(code);
            fetch('/com/email/verify', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ certificationNumber: code }),
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data.code === 200) {
                    emailAccess = true;
                    alert("이메일 인증이 완료되었습니다.");
                } else {
                    alert("인증번호가 올바르지 않습니다. 다시 확인해주세요.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("인증번호 확인 중 오류가 발생했습니다.");
            });
        });
    });
});
