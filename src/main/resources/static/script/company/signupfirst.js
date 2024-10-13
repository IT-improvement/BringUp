document.addEventListener('DOMContentLoaded', function() {
    const businessNumber = document.getElementById('businessNumber');
    const name = document.getElementById('name');
    const startDate = document.getElementById('startDate');
    const signupButton = document.getElementById('signupButton');

    // 툴팁 생성
    const businessNumberTooltip1 = new bootstrap.Tooltip(businessNumber, {
        trigger: 'manual',
        placement: 'bottom',
        title: '사업자등록번호를 확인해주세요.',
        template: '<div class="tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
    });

    const businessNumberTooltip2 = new bootstrap.Tooltip(businessNumber, {
        trigger: 'manual',
        placement: 'bottom',
        title: '사업자등록번호를 입력해주세요.',
        template: '<div class="tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
    });

    const nameTooltip = new bootstrap.Tooltip(name, {
        trigger: 'manual',
        placement: 'bottom',
        title: '회사 대표 이름을 입력해주세요.',
        template: '<div class="tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
    });

    const startDateTooltip = new bootstrap.Tooltip(startDate, {
        trigger: 'manual',
        placement: 'bottom',
        title: '설립일을 선택해주세요.',
        template: '<div class="tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
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

    // 모든 입력 필드에 대한 이벤트 리스너 추가
    [businessNumber, name, startDate].forEach(element => {
        element.addEventListener("focus", function() {
            this.classList.remove("is-invalid");
            this.classList.remove("mb-4");
            if (element === businessNumber) {
                businessNumberTooltip1.hide();
                businessNumberTooltip2.hide();
            } else if (element === name) {
                nameTooltip.hide();
            } else if (element === startDate) {
                startDateTooltip.hide();
            }
        });
    });

    // 유효성 검사 함수들
    function validateBusinessNumber() {
        const pureNumber = businessNumber.value.replace(/-/g, '');
        if(pureNumber === "") {
            businessNumber.classList.add("is-invalid");
            businessNumber.classList.add("mb-4");
            businessNumberTooltip2.show();
            businessNumberTooltip1.hide();
            return false;
        } else if(pureNumber.length !== 10) {
            businessNumber.classList.add("is-invalid");
            businessNumber.classList.add("mb-4");
            businessNumberTooltip1.show();
            businessNumberTooltip2.hide();
            return false;
        }
        businessNumber.classList.remove("is-invalid");
        businessNumber.classList.remove("mb-4");
        businessNumberTooltip1.hide();
        businessNumberTooltip2.hide();
        return true;
    }

    function validateName() {
        if(name.value.trim() === "") {
            name.classList.add("is-invalid");
            name.classList.add("mb-4");
            nameTooltip.show();
            return false;
        }
        name.classList.remove("is-invalid");
        name.classList.remove("mb-4");
        nameTooltip.hide();
        return true;
    }

    function validateStartDate() {
        if(startDate.value === "") {
            startDate.classList.add("is-invalid");
            startDate.classList.add("mb-4");
            startDateTooltip.show();
            return false;
        }
        startDate.classList.remove("is-invalid");
        startDate.classList.remove("mb-4");
        startDateTooltip.hide();
        return true;
    }

    // 사업자 인증 버튼 클릭 이벤트
    signupButton.addEventListener("click", function(e) {
        // 사업자등록번호 검증
        if (!validateBusinessNumber()) {
            console.log("사업자등록번호 유효성 검사 실패: " + businessNumber.value);
            return;
        }
        
        // 회사 대표 이름 검증
        if (!validateName()) {
            console.log("회사 대표 이름 유효성 검사 실패: " + name.value);
            return;
        }
        
        // 설립일 검증
        if (!validateStartDate()) {
            console.log("설립일 유효성 검사 실패: " + startDate.value);
            return;
        }

        // 모든 유효성 검사를 통과한 경우
        console.log("사업자 인증 로직 실행");
        fetch("/com/join/first", {
            method: "POST",
            body: JSON.stringify({
                company_licence: businessNumber.value,
                master_name: name.value,
                company_opendate: startDate.value
            }),
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200){
                sessionStorage.setItem("isValid", true);
                sessionStorage.setItem("company_opendate", startDate.value);
                sessionStorage.setItem("company_licence", businessNumber.value);
                sessionStorage.setItem("master_name", name.value);
                alert("인증이 완료되었습니다. \n 다음 단계를 진행해주세요.");
                window.location.href = "/company/auth/signup/second";
            }else{
                console.log("사업자 인증 실패");
            }   
        });
    });
});
