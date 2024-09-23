document.getElementById('companySignupForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const businessNumber = document.getElementById('businessNumber').value.replace(/-/g, '');
    const companyName = document.getElementById('name').value;
    const startDate = document.getElementById('startDate').value;

    const data = {
        company_licence: businessNumber,
        company_opendate: startDate,
        master_name: companyName
    };

    fetch('/com/join/first', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {

            if (result.code === 200 && result.data.isValid) {
                // 세션 스토리지에 데이터 저장
                sessionStorage.setItem('company_licence', data.company_licence);
                sessionStorage.setItem('company_opendate', data.company_opendate);
                sessionStorage.setItem('master_name', data.master_name);
                sessionStorage.setItem('isValid', result.data.isValid);
                
                // 모달 생성
                const modal = document.createElement('div');
                modal.style.cssText = `
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background-color: rgba(0,0,0,0.5);
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    z-index: 1000;
                `;
                const modalContent = document.createElement('div');
                modalContent.style.cssText = `
                    background-color: white;
                    padding: 20px;
                    border-radius: 5px;
                    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                `;
                modalContent.innerHTML = `
                    <h3>알림</h3>
                    <p>사업자 인증이 완료되었습니다. 다음 단계로 진행해 주세요.</p>
                    <button id="modalConfirmButton">확인</button>
                `;
                modal.appendChild(modalContent);
                document.body.appendChild(modal);

                // URL 변경 방지
                const preventUrlChange = function(e) {
                    e.preventDefault();
                    return false;
                };

                // 모달이 열려있는 동안 URL 변경 방지
                window.addEventListener('popstate', preventUrlChange);
                window.addEventListener('hashchange', preventUrlChange);

                // 모달의 확인 버튼에 이벤트 리스너 추가
                document.getElementById('modalConfirmButton').addEventListener('click', function() {
                    // URL 변경 방지 이벤트 리스너 제거
                    window.removeEventListener('popstate', preventUrlChange);
                    window.removeEventListener('hashchange', preventUrlChange);

                    // 모달 닫기
                    modal.remove();
                    
                    // 다음 페이지로 이동
                    window.location.href = '/company/auth/signup/second';
                });
            } else {
                alert('사업자 인증에 실패했습니다. 다시 시도해주세요.');
            }
        })
        .catch(error => {
            console.error('오류:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
});
