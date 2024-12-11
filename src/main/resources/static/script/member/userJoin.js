document.addEventListener('DOMContentLoaded', function () {
    const checkEmailBtn = document.getElementById('checkEmailBtn');
    const emailCheckResult = document.getElementById('emailCheckResult');
    let isEmailChecked = false; // 이메일 중복 체크 여부를 추적
    let isEmailAvailable = false; // 이메일이 사용 가능한지 여부를 추적

    // 이메일 입력 필드에 입력이 변경되면 중복 체크를 다시 해야 함을 표시
    document.getElementById('userEmail').addEventListener('input', function () {
        isEmailChecked = false; // 이메일이 변경되면 중복 체크 상태를 초기화
        emailCheckResult.textContent = ''; // 이전 결과 메시지를 초기화
    });

    checkEmailBtn.addEventListener('click', function () {
        const userEmail = document.getElementById('userEmail').value;

        // 이메일 형식 확인
        if (!userEmail.includes('@') || !userEmail.includes('.')) {
            emailCheckResult.textContent = '올바르지 않은 이메일 형식입니다. 다시 입력해주세요.';
            emailCheckResult.style.color = 'red';
            return;
        }

        fetch('/member/checkId', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userEmail: userEmail })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답이 올바르지 않습니다.');
                }
                return response.json();
            })
            .then(data => {
                isEmailChecked = true; // 이메일 중복 체크가 완료되었음을 표시
                isEmailAvailable = data.message.includes("true");

                if (isEmailAvailable) {
                    emailCheckResult.textContent = '사용 가능한 이메일입니다.';
                    emailCheckResult.style.color = 'green';
                } else {
                    emailCheckResult.textContent = '이미 사용 중인 이메일입니다.';
                    emailCheckResult.style.color = 'red';
                }
            })
            .catch(error => {
                console.error('에러:', error);
                emailCheckResult.textContent = '이메일 확인 중 오류가 발생했습니다.';
                emailCheckResult.style.color = 'red';
            });
    });

    const form = document.getElementById('userSignupForm');
    const militaryStatusSelect = document.getElementById('militaryStatus');
    const militaryStatusHidden = document.getElementById('militaryStatusHidden');
    const militaryTypeInput = document.getElementById('militaryType');
    const specialtyInput = document.getElementById('specialty');
    const rankNameInput = document.getElementById('rankName');
    const dischargeReasonInput = document.getElementById('dischargeReason');
    const exemptionReasonInput = document.getElementById('exemptionReason');

    // 페이지 로드 시 기본 값을 히든 필드에 설정
    militaryStatusHidden.value = militaryStatusSelect.value;

    militaryStatusSelect.addEventListener('change', function () {
        militaryStatusHidden.value = militaryStatusSelect.value;

        const selectedStatus = militaryStatusSelect.value;
        if (selectedStatus === "군필") {
            // 군필인 경우 필드를 활성화하고 required 추가
            militaryTypeInput.disabled = false;
            militaryTypeInput.required = true;

            specialtyInput.disabled = false;
            rankNameInput.disabled = false;
            dischargeReasonInput.disabled = false;

            exemptionReasonInput.disabled = true; // 면제사유는 비활성화
            exemptionReasonInput.required = false;
        } else if (selectedStatus === "미필" || selectedStatus === "면제") {
            // 미필 또는 면제인 경우 필드를 비활성화하고 required 제거
            militaryTypeInput.disabled = true;
            militaryTypeInput.required = false;

            specialtyInput.disabled = true;
            rankNameInput.disabled = true;
            dischargeReasonInput.disabled = true;

            if (selectedStatus === "면제") {
                exemptionReasonInput.disabled = false; // 면제사유 활성화
                exemptionReasonInput.required = true;
            } else {
                exemptionReasonInput.disabled = true;
                exemptionReasonInput.required = false;
            }
        }
    });

    // 페이지 로드 시 초기 상태 설정
    militaryStatusSelect.dispatchEvent(new Event('change'));

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        // 추가 유효성 검사
        if (!isEmailChecked || !isEmailAvailable) {
            alert('이메일 중복 확인을 완료해주세요.');
            return;
        }

        // Military 리스트 생성
        const militaryList = [{
            militaryStatus: document.getElementById('militaryStatusHidden').value,
            militaryType: document.getElementById('militaryType').value,
            specialty: document.getElementById('specialty').value,
            rankName: document.getElementById('rankName').value,
            dischargeReason: document.getElementById('dischargeReason').value,
            enlistmentDate: document.getElementById('enlistmentDate').value,
            dischargeDate: document.getElementById('dischargeDate').value,
            exemptionReason: document.getElementById('exemptionReason').value
        }];

        const formData = {
            userEmail: document.getElementById('userEmail').value,
            userPassword: document.getElementById('userPassword').value,
            userName: document.getElementById('userName').value,
            userAddress: document.getElementById('userAddress').value,
            userPhonenumber: document.getElementById('userPhoneNumber').value,
            userBirthday: document.getElementById('userBirthday').value,
            gender: document.getElementById('gender').value,
            militaryList: militaryList // Military 리스트 포함
        };

        // Console로 데이터 확인
        console.log("전송할 데이터:", formData);

        fetch('/member/joinProc', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) throw new Error('서버 응답 오류');
                return response.text();
            })
            .then(data => {
                console.log("서버 응답:", data);
                if (data.includes("회원가입이 성공적으로 완료되었습니다.")) {
                    alert('회원가입 성공!');
                    window.location.href = '/member/Login';
                } else {
                    alert('회원가입 실패: ' + data);
                }
            })
            .catch(error => {
                console.error('회원가입 중 오류:', error);
                alert('회원가입 중 오류가 발생했습니다.');
            });
    });

    const maleButton = document.getElementById('maleButton');
    const femaleButton = document.getElementById('femaleButton');
    const militaryServiceCard = document.getElementById('militaryServiceCard');
    const closeMilitaryCard = document.getElementById('closeMilitaryCard');
    const genderInput = document.getElementById('gender'); // hidden input

    // 초기화: hidden 필드 값 비우기
    genderInput.value = "";

    maleButton.addEventListener('click', function () {
        maleButton.classList.add('active');
        femaleButton.classList.remove('active');
        genderInput.value = "남";

        // Show military service card by sliding in from the right
        militaryServiceCard.style.display = 'block';
        setTimeout(() => {
            militaryServiceCard.classList.add('visible');
        }, 0);
    });

    femaleButton.addEventListener('click', function () {
        femaleButton.classList.add('active');
        maleButton.classList.remove('active');
        genderInput.value = "여";

        // Hide military service card by sliding out to the right
        militaryServiceCard.classList.remove('visible');
        setTimeout(() => {
            militaryServiceCard.style.display = 'none';
        }, 300);
    });

    closeMilitaryCard.addEventListener('click', function () {
        militaryServiceCard.classList.remove('visible');
        setTimeout(() => {
            militaryServiceCard.style.display = 'none';
        }, 300);
    });
});
