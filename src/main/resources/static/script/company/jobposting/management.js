document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
        fetch(`/com/recruitment/detail/list`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 올바르지 않습니다');
            }
            return response.json();
        })
        .then(data => {
            if (data.data && Array.isArray(data.data)) {
                console.log(data.data);
                updateRecruitmentList(data.data);
            } else {
                console.error('데이터 형식이 올바르지 않습니다:', data);
                updateRecruitmentList([]);
            }
        })
        .catch(error => {
            console.error('채용 목록을 가져오는 중 오류 발생:', error);
            updateRecruitmentList([]);
        });
    } else {
        console.error('액세스 토큰이 없습니다');
        updateRecruitmentList([]);
    }
});

function updateRecruitmentList(recruitmentList) {
    const recruitmentListBody = document.getElementById('recruitment-list-body');
    recruitmentListBody.innerHTML = '';
    if (recruitmentList.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = '<td colspan="8" class="text-center">등록된 공고가 없습니다.</td>';
        recruitmentListBody.appendChild(row);
    } else {
        recruitmentList.forEach((recruitment, index) => {
            const row = document.createElement('tr');
            row.addEventListener('click', function() {
                window.location.href = `/company/jobpost/detail?index=${recruitment.index}`;
            });
            const recruitmentType = recruitment.recruitmentType === 'IRREGULAR_WORKER' ? '비정규직' :
                          recruitment.recruitmentType === 'REGULAR_WORKER' ? '정규직' :
                          recruitment.recruitmentType === 'PART_TIME_WORKER' ? '파트타임' : '기타';

            const type = recruitment.type === "RECRUITMENT" ? '정규 채용' : '프리랜서';

            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${recruitment.recruitmentTitle || '-'}</td>
                <td>${recruitmentType || '-'}</td>
                <td>${type || '-'}</td>
                <td>${recruitment.period || '-'}</td>
                <td>${recruitment.viewCount || '0'}</td>
                <td>${recruitment.applicantCount || '0'}</td>
            `;
            recruitmentListBody.appendChild(row);
        });
    }
}