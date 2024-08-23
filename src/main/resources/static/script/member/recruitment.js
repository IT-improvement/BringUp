document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('accessToken'); // 로컬 스토리지에서 토큰을 가져옵니다.
    if (token) {
        fetch('/recruitment/view', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // 요청 헤더에 토큰을 포함합니다.
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답이 올바르지 않습니다.');
                }
                return response.text(); // JSON이 아니라 텍스트로 받아오는 경우
            })
            .then(data => {
                console.log('서버 응답:', data);
                // 서버로부터 받은 데이터를 화면에 렌더링합니다.
                document.querySelector('.recruitment-list').innerHTML = data;
            })
            .catch(error => {
                console.error('에러:', error);
                alert('페이지를 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.');
            });
    } else {
        alert('로그인이 필요합니다.');
        window.location.href = '/member/userLoginForm'; // 토큰이 없는 경우 로그인 페이지로 리디렉션합니다.
    }
});
