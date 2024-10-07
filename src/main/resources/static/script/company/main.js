document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
        window.location.href = '/company/auth/login';
        return;
    }

    let currentPage = 1;
    const itemsPerPage = 5;
    let totalItems = 0;
    let allData = [];

    function fetchData() {
        fetch('/com/recruitment/list', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log("받은 데이터:", data);
            allData = data.data;
            totalItems = allData.length;
            renderPage(currentPage);
            updatePagination();
        })
        .catch(error => {
            console.error('채용 목록을 가져오는 중 오류 발생:', error);
            const recruitmentListBody = document.getElementById('recruitment-list-body');
            if (recruitmentListBody) {
                recruitmentListBody.innerHTML = '<tr><td colspan="6" class="text-center">데이터를 불러오는 중 오류가 발생했습니다.</td></tr>';
            }
        });
    }

    function renderPage(page) {
        const recruitmentListBody = document.getElementById('recruitment-list-body');
        recruitmentListBody.innerHTML = '';

        const start = (page - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const pageData = allData.slice(start, end);

        pageData.forEach((recruitment, index) => {
            const row = document.createElement('tr');
            const number = recruitment.index;
            const recruitmentType = recruitment.recruitmentType === 'IRREGULAR_WORKER' ? '비정규직' :
                        recruitment.recruitmentType === 'REGULAR_WORKER' ? '정규직' :
                        recruitment.recruitmentType === 'PART_TIME_WORKER' ? '파트타임' : '기타';

            const type = recruitment.type === "RECRUITMENT" ? '정규 채용' : '프리랜서';

            row.innerHTML = `
                <td>${start + index + 1}</td>
                <td>${recruitment.title || ''}</td>
                <td>${type || ''}</td>
                <td>${recruitmentType || ''}</td>
                <td>${recruitment.period || ''}</td>
                <td>${recruitment.viewCount || '0'}</td>
                <td>${recruitment.applicantCount || '0'}</td>
            `;
            row.style.cursor = 'pointer';
            row.addEventListener('click', () => {
                window.location.href = `/company/jobpost/detail?index=`+number;
            });
            recruitmentListBody.appendChild(row);
        });

        updateJobCountAndTotal(totalItems);
    }

    function updatePagination() {
        const totalPages = Math.ceil(totalItems / itemsPerPage);
        const paginationContainer = document.getElementById('paginationContainer');
        paginationContainer.innerHTML = '';

        // 이전 페이지 버튼
        const prevLi = document.createElement('li');
        prevLi.className = `page-item ${currentPage === 1 ? 'disabled' : ''}`;
        prevLi.innerHTML = '<a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>';
        paginationContainer.appendChild(prevLi);

        // 현재 페이지 / 전체 페이지 입력 필드
        const pageInputLi = document.createElement('li');
        pageInputLi.className = 'page-item';
        pageInputLi.innerHTML = `
            <span class="page-link page-input-container">
                <input id="currentPageInput" type="text" inputmode="numeric" min="1" max="${totalPages}" value="${currentPage}" style="width: 10px; text-align: center; border: none; background: transparent; ">
                / ${totalPages}
            </span>
        `;
        paginationContainer.appendChild(pageInputLi);

        // 다음 페이지 버튼
        const nextLi = document.createElement('li');
        nextLi.className = `page-item ${currentPage === totalPages ? 'disabled' : ''}`;
        nextLi.innerHTML = '<a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>';
        paginationContainer.appendChild(nextLi);

        // 이벤트 리스너 제거 및 다시 추가
        paginationContainer.removeEventListener('click', paginationClickHandler);
        paginationContainer.addEventListener('click', paginationClickHandler);

        // 페이지 입력 필드에 이벤트 리스너 추가
        const currentPageInput = document.getElementById('currentPageInput');
        currentPageInput.addEventListener('input', function(e) {
            let value = this.value.replace(/[^0-9]/g, '');
            value = value === '' ? '' : Math.min(parseInt(value), totalPages).toString();
            this.value = value;
        });
        currentPageInput.addEventListener('change', function() {
            let newPage = parseInt(this.value) || 1;
            if (newPage < 1) newPage = 1;
            if (newPage > totalPages) newPage = totalPages;
            this.value = newPage;
            if (newPage !== currentPage) {
                currentPage = newPage;
                renderPage(currentPage);
                updatePagination();
            }
        });
    }

    function paginationClickHandler(e) {
        e.preventDefault();
        if (e.target.tagName === 'A' || e.target.parentElement.tagName === 'A') {
            const pageItem = e.target.closest('.page-item');
            if (pageItem.classList.contains('disabled')) return;

            if (pageItem.querySelector('[aria-label="Previous"]')) {
                currentPage--;
            } else if (pageItem.querySelector('[aria-label="Next"]')) {
                currentPage++;
            }

            renderPage(currentPage);
            updatePagination();
        }
    }

    function updateJobCountAndTotal(count) {
        const jobCountElement = document.getElementById('jobCount');
        const totalEntriesElement = document.getElementById('totalEntries');
        
        if (jobCountElement) {
            jobCountElement.textContent = count;
        }
        
        if (totalEntriesElement) {
            totalEntriesElement.textContent = `총 ${count} 개`;
        }
    }

    fetchData();
});