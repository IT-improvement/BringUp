document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');

    let recruitmentData = [];

    if (!accessToken) {
        window.location.href = '/company/login';
    }

    // 데이터 가져오기 및 테이블 생성 함수
    function fetchAndDisplayData() {
        fetch("/com/recruitment/list", {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        })
        .then(response => response.json())
        .then(data => {
            recruitmentData = data.data;
            displayRecruitmentList(recruitmentData);
            console.log(data);
        })
        .catch(error => console.error('Error:', error));
    }

    // 테이블 생성 함수
    function displayRecruitmentList(data) {
        const tableBody = document.querySelector('#recruitment-list-body');
        tableBody.innerHTML = '';
        data.forEach((item, index) => {
            const row = createTableRow(item, index);
            tableBody.appendChild(row);
        });
    }

    // 테이블 행 생성 함수
    function createTableRow(item, index) {
        const row = document.createElement('tr');
        row.style.cursor = 'pointer';
        row.addEventListener('click', () => {
            window.location.href = `/company/jobpost/detail?index=${item.index}`;
        });
        
        const type = item.type == "RECRUITMENT" ? "정규채용" : "프리랜서";
        const recruitmentType = item.recruitmentType === "REGULAR_WORKER" ? "정규직" :
                                item.recruitmentType === "INREGULAR_WORKER" ? "계약직" : "아르바이트";
        
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${item.title}</td>
            <td>${type}</td>
            <td>${recruitmentType}</td>
            <td>${item.period}</td>
            <td>${item.viewCount}</td>
            <td>${item.applicantCount}</td>
        `;
        return row;
    }

    // 검색 함수
    function searchRecruitments() {
        const searchCategory = document.getElementById('search-category').value;
        const searchKeyword = document.getElementById('search-keyword').value.toLowerCase();

        const filteredData = recruitmentData.filter(item => {
            if (searchCategory === 'all') {
                return Object.values(item).some(value => 
                    String(value).toLowerCase().includes(searchKeyword)
                );
            } else if (searchCategory === 'type') {
                const displayType = item.type == "RECRUITMENT" ? "정규채용" : "프리랜서";
                return displayType.toLowerCase().includes(searchKeyword);
            } else if (searchCategory === 'recruitmentType') {
                const displayRecruitmentType = item.recruitmentType === "REGULAR_WORKER" ? "정규직" :
                                               item.recruitmentType === "INREGULAR_WORKER" ? "계약직" : "아르바이트";
                return displayRecruitmentType.toLowerCase().includes(searchKeyword);
            } else {
                const itemValue = String(item[searchCategory]).toLowerCase();
                return itemValue.includes(searchKeyword);
            }
        });

        displayRecruitmentList(filteredData);
    }

    // 검색 버튼 이벤트 리스너
    document.getElementById('search-button').addEventListener('click', searchRecruitments);

    const searchKeyword = document.getElementById('search-keyword');
    searchKeyword.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            searchRecruitments();
            console.log('검색 엔터');
        }
    });

    // 초기 데이터 로드
    fetchAndDisplayData();
});