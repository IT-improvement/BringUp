document.addEventListener('DOMContentLoaded', function () {
    // /member/name 엔드포인트에서 데이터 가져오기
    fetch('/member/name')
        .then(response => {
            if (!response.ok) {
                throw new Error('인증되지 않았습니다.');
            }
            return response.json();  // JSON 데이터를 추출합니다
        })
        .then(data => {
            // 가져온 데이터에서 이름과 이메일을 추출합니다
            const userName = data.data.name;
            const userEmail = data.data.email;

            // HTML 요소에 데이터를 삽입합니다
            document.querySelector('.user-info strong').textContent = userName;  // 이름 삽입
            document.querySelector('.user-info small').textContent = userEmail;  // 이메일 삽입
        })
        .catch(error => {
            console.error('Error:', error);
            alert('사용자 정보를 불러오는 중 오류가 발생했습니다.');
        });
});
