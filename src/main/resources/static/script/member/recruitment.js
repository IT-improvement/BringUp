// recruitment.js

document.addEventListener('DOMContentLoaded', function () {
    // /auth 엔드포인트에서 데이터 가져오기
    fetch('/auth')
        .then(response => {
            if (!response.ok) {
                throw new Error('인증되지 않았습니다.');
            }
            return response.json();  // JSON 데이터를 추출합니다
        })
        .then(data => {
            // 템플릿 요소를 가져옵니다
            const template = document.getElementById('recruitmentTemplate');
            const recruitmentList = document.getElementById('recruitmentList');

            data.forEach(recruitment => {
                // 템플릿을 복제하여 새로운 요소를 만듭니다
                const item = template.cloneNode(true);
                item.classList.remove('d-none');

                // 텍스트 요소에 데이터를 삽입합니다
                item.querySelector('.recruitment-title').textContent = recruitment.title;
                item.querySelector('.recruitment-company').textContent = recruitment.companyName;
                item.querySelector('.recruitment-type').textContent = `채용 형태: ${recruitment.type}`;
                item.querySelector('.recruitment-period').textContent = `시작일: ${recruitment.startDate} 기간: ${recruitment.period}`;
                item.querySelector('.recruitment-skills').textContent = `기술 스택: ${recruitment.skills}`;
                item.querySelector('.recruitment-status').textContent = `상태: ${recruitment.status}`;
                item.querySelector('.recruitment-link').setAttribute('href', `/recruitment/view/${recruitment.id}`);

                // 리스트에 요소를 추가합니다
                recruitmentList.appendChild(item);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('데이터를 불러오는 중 오류가 발생했습니다.');
        });
});

