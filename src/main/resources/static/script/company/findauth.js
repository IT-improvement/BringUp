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