document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    
    fetch('/com/advertisement/list', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        if (data && data.data) {
            console.log('광고 목록:', data.data);
            updateTable(data.data);
        }
    })
    .catch(error => {
        console.error('광고 목록 조회 중 오류 발생:', error);
    });
});

function updateTable(advertisements) {
    const tbody = document.querySelector('table tbody');
    tbody.innerHTML = '';
    
    advertisements.forEach((ad, index) => {
        let adTypeKorean;
        let detailPath;
        switch (ad.adType) {
            case 'Main':
                adTypeKorean = '메인';
                detailPath = `main`;
                break;
            case 'Premium':
                adTypeKorean = '프리미엄';
                detailPath = `premium`;
                break;
            case 'Banner':
                adTypeKorean = '배너';
                detailPath = `banner`;
                break;
            case 'Announcement':
                adTypeKorean = '공지';
                detailPath = `announce`;
                break;
            default:
                adTypeKorean = '-';
                detailPath = '';
        }
        
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${index + 1}</td>
            <td>${ad.recruitmentTitle != null ? ad.recruitmentTitle : '-'}</td>
            <td>${ad.startDate != null ? ad.startDate : '-'} ~ ${ad.endDate != null ? ad.endDate : '-'}</td>
            <td>${adTypeKorean}</td>
            <td>${ad.clickCount != null ? ad.clickCount : 0}</td>
        `;
        tr.style.cursor = 'pointer';
        tr.addEventListener('click', () => {
            if (detailPath) {
                window.location.href = `/company/product/detail?adIdx=${ad.adIdx}&adType=${detailPath}`;
            }
        });
        tbody.appendChild(tr);
    });
}
