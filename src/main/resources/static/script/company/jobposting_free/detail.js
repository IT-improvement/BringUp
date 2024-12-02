document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    const urlParams = new URLSearchParams(window.location.search);
    const projectIndex = urlParams.get('project_index');

    if (!accessToken) {
        window.location.href = '/company/auth/login';
        return;
    }

    if (!projectIndex) {
        console.error('projectIndex가 없습니다.');
        return;
    }

    const url = `/com/freelancer/detail/` + projectIndex;

    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ` + accessToken,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const projectData = data.data;

            // 데이터를 화면에 반영
            document.getElementById('projectTitle').innerText = projectData.projectTitle;
            document.getElementById('projectDescription').innerText = projectData.projectDescription;
            document.getElementById('expectedDuration').innerText = projectData.expectedDuration;
            document.getElementById('requiredSkills').innerText = projectData.requiredSkills;
            document.getElementById('preferredSkills').innerText = projectData.preferredSkills;
            document.getElementById('workConditions').innerText = projectData.workConditions;
            document.getElementById('expectedCost').innerText = projectData.expectedCost + '원';
            document.getElementById('status').innerText = projectData.status;
            document.getElementById('createDay').innerText = projectData.create_day;
        })
        .catch(error => {
            console.error('프리랜서 프로젝트 상세 정보를 가져오는 중 오류 발생:', error);
            alert('프리랜서 프로젝트 정보를 불러올 수 없습니다.');
        });
});
