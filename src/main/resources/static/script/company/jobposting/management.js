document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    let recruitmentList = []; // 전체 목록을 저장할 변수

    if (accessToken) {
        fetch(`/com/recruitment/list`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            recruitmentList = data.data; // 전체 목록 저장
            displayRecruitments(recruitmentList); // 전체 목록 표시
        })
        .catch(error => {
            console.error('채용 목록을 가져오는 중 오류 발생:', error);
        });

        // 검색 폼 이벤트 리스너
        document.getElementById('search-form').addEventListener('submit', function(event) {
            event.preventDefault();
            const category = document.getElementById('search-category').value;
            const keyword = document.getElementById('search-keyword').value.toLowerCase();

            const filteredList = recruitmentList.filter(recruitment => {
                let value;
                switch(category) {
                    case 'jobPostingId':
                        value = String(recruitment.recruitmentIndex);
                        break;
                    case 'managerEmail':
                        value = recruitment.managerEmail;
                        break;
                    case 'employmentType':
                        value = recruitment.recruitmentType;
                        break;
                    case 'jobGroup':
                        value = recruitment.category;
                        break;
                    case 'techStack':
                        value = recruitment.skill;
                        break;
                    case 'duration':
                        value = recruitment.period;
                        break;
                    case 'status':
                        value = recruitment.status;
                        break;
                    case 'adGrade':
                        value = recruitment.recruitmentClass;
                        break;
                    case 'jobPostingTitle':
                        value = recruitment.jobPostingTitle; // 공고제목 추가
                        break;
                    default:
                        value = '';
                }
                return value.toLowerCase().includes(keyword);
            });

            displayRecruitments(filteredList);
        });
    } else {
        console.error('로컬 스토리지에 액세스 토큰이 없습니다.');
    }
});

// 채용 목록을 표시하는 함수
function displayRecruitments(recruitments) {
    const recruitmentListBody = document.getElementById('recruitment-list-body');
    recruitmentListBody.innerHTML = '';
    recruitments.forEach(recruitment => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${recruitment.recruitmentIndex}</td>
            <td>${recruitment.managerEmail}</td>
            <td>${recruitment.recruitmentType}</td>
            <td>${recruitment.category}</td>
            <td>${recruitment.skill}</td>
            <td>${recruitment.period}</td>
            <td>${recruitment.status}</td>
            <td>${recruitment.recruitmentClass}</td>
            <td>${recruitment.jobPostingTitle}</td>
        `;
        row.addEventListener('click', function() {
            window.location.href = `/company/jobpost/detail?recruitmentIndex=${recruitment.recruitmentIndex}`;
        });
        recruitmentListBody.appendChild(row);
    });
}