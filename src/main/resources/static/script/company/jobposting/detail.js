document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    // URL에서 recruitmentIndex 파라미터를 가져옵니다.
    const urlParams = new URLSearchParams(window.location.search);
    const recruitmentIndex = urlParams.get('recruitmentIndex');
    if (!accessToken) {
        window.location.href = '/company/auth/login';
        return;
    }

    const url = `/com/recruitment/detail/${recruitmentIndex}`;
    
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        window.recruitmentData = data.data;
        updateUI();
    })
    .catch(error => {
        console.error('채용 공고 상세 정보를 가져오는 중 오류 발생:', error);
        alert('채용 공고 상세 정보를 불러올 수 없습니다. 나중에 다시 시도해주세요.');
    });
});

function updateUI() {
    const recruitment = window.recruitmentData;
    document.getElementById('recruitmentTitle').textContent = recruitment.recruitmentTitle;
    document.getElementById('category').textContent = recruitment.category;
    document.getElementById('managerEmail').textContent = recruitment.managerEmail;
    document.getElementById('period').textContent = recruitment.period;
    document.getElementById('recruitmentClass').textContent = recruitment.recruitmentClass;
    document.getElementById('recruitmentType').textContent = recruitment.recruitmentType;
    document.getElementById('skill').textContent = recruitment.skill;
    document.getElementById('startDate').textContent = recruitment.startDate;
    document.getElementById('status').textContent = recruitment.status;
    
    if (recruitment.recruitment_img) {
        const img = document.createElement('img');
        img.src = recruitment.recruitment_img;
        img.alt = '채용 이미지';
        img.className = 'img-fluid';
        document.getElementById('recruitmentImage').appendChild(img);
    }

    document.getElementById('loadingIndicator').style.display = 'none';
    document.getElementById('recruitmentContent').style.display = 'block';
}