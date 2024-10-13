document.addEventListener('DOMContentLoaded', function() {
    const useridInput = document.getElementById('userid');
    const passwordInput = document.getElementById('password');

    // userid 툴팁 초기화
    const useridTooltip = new bootstrap.Tooltip(useridInput, {
        trigger: 'manual',
        placement: 'bottom',
        title: '아이디를 확인해주세요.',
        template: '<div class="tooltip" role="tooltip"><div class="arrow" style="color: #dc3545;"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
    });

    // password 툴팁 초기화
    const passwordTooltip = new bootstrap.Tooltip(passwordInput, {
        trigger: 'manual',
        placement: 'bottom',
        title: '비밀번호를 확인해주세요.',
        template: '<div class="tooltip" role="tooltip"><div class="arrow" style="color: #dc3545;"></div><div class="tooltip-inner bg-danger text-white"></div></div>'
    });

    useridInput.addEventListener('mouseover', function() {
        useridTooltip.hide();
    });

    useridInput.addEventListener('focusout', function() {
        this.classList.remove('is-invalid');
        useridTooltip.hide();
    });


    passwordInput.addEventListener('mouseover', function() {
        passwordTooltip.hide();
        document.getElementById('password').classList.remove('mb-4');
    });

    passwordInput.addEventListener('focusout', function() {
        this.classList.remove('is-invalid');
        passwordTooltip.hide();
    });

    document.getElementById('loginForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const userid = useridInput.value;
        const password = passwordInput.value;

        if (!userid) {
            useridInput.classList.add('is-invalid');
            useridTooltip.show();
            useridInput.focus();
            return;
        }

        if (!password) {
            passwordInput.classList.add('is-invalid');
            passwordTooltip.show();
            document.getElementById('password').classList.add('mb-4');
            passwordInput.focus();
            return;
        }

        fetch('/com/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userid: userid,
                password: password
            })
        })
        .then(response => {
            if (!response.ok) {
                console.log('서버 응답:', data);
                throw new Error('서버 응답이 올바르지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            console.log('서버 응답:', data);
            if (data.code === 200 && data.data && data.data.accessToken) {
                console.log('로그인 성공11:', data.code);
                // 액세스 토큰을 세션 스토리지에 저장
                localStorage.setItem('accessToken', data.data.accessToken);
                window.location.href = '/company';
            } else {
                console.error('로그인 실패:', data);
                alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
            }
        })
        .catch(error => {
            console.error('에러:', error);
            alert('로그인 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
        });
    });
});
