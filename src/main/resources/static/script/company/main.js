document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
        window.location.href = '/company/auth/login';
        return;
    }

    fetch('/com/recruitment/list', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log('채용 목록 응답:', data); // 이 부분이 추가되었습니다.
        const recruitmentList = data.data;
        const recruitmentListBody = document.getElementById('recruitment-list-body');
        if (recruitmentListBody) {
            recruitmentListBody.innerHTML = ''; // 기존 내용을 지웁니다
            recruitmentList.forEach(recruitment => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${recruitment.recruitmentTitle}</td>
                    <td>${recruitment.recruitmentType}</td>
                    <td>${recruitment.startDate}</td>
                    <td>${recruitment.category}</td>
                    <td>${recruitment.status}</td>
                    <td><a href="/company/jobpost/detail?recruitmentIndex=${recruitment.recruitmentIndex}">상세보기</a></td>
                `;
                recruitmentListBody.appendChild(row);
            });
        } else {
            console.error('recruitment-list-body 요소를 찾을 수 없습니다.');
            alert('채용 목록을 표시할 수 없습니다. 페이지를 새로고침하거나 나중에 다시 시도해주세요.');
        }
    })
    .catch(error => {
        console.error('채용 목록을 가져오는 중 오류 발생:', error);
    });
});