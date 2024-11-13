let currentPage = 1; // 전역 변수로 선언
const itemsPerPage = 10;
let advertisements = []; // 광고 데이터 배열

function updateTable(page) {
    const start = (page - 1) * itemsPerPage;
    const end = start + itemsPerPage;
    const paginatedItems = advertisements.slice(start, end);

    const tbody = document.getElementById('adTableBody');
    tbody.innerHTML = '';

    paginatedItems.forEach((ad, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${start + index + 1}</td>
            <td>${ad.recruitmentTitle || '-'}</td>
            <td>${ad.startDate || '-'} ~ ${ad.endDate || '-'}</td>
            <td>${ad.adTypeKorean || '-'}</td>
            <td>${ad.clickCount || 0}</td>
        `;
        tr.addEventListener('click', () => {
            const url = `/company/product/detail?adIdx=${ad.adIdx}&adType=${ad.adType}`;
            window.location.href = url;
        });
        tbody.appendChild(tr);
    });
}

function setupPagination(totalItems) {
    const pageCount = Math.ceil(totalItems / itemsPerPage);
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';

    for (let i = 1; i <= pageCount; i++) {
        const button = document.createElement('button');
        button.textContent = i;
        button.classList.add('btn', 'btn-outline-primary', 'mx-1');
        if (i === currentPage) button.classList.add('active');
        button.addEventListener('click', () => {
            currentPage = i;
            updateTable(currentPage);
            setupPagination(totalItems);
        });
        pagination.appendChild(button);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    // 광고 데이터를 가져오는 fetch 요청
    fetch('/com/advertisement/list', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data && data.data) {
            advertisements = data.data;
            updateTable(currentPage);
            setupPagination(advertisements.length);
        }
    })
    .catch(error => {
        console.error('광고 목록 조회 중 오류 발생:', error);
    });
});
