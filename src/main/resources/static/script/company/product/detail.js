document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const adIdx = urlParams.get('adIdx');
    const adType = urlParams.get('adType');
    const accessToken = localStorage.getItem('accessToken');

    if (!adType || !adIdx) {
        console.error('광고 타입 또는 ID가 지정되지 않았습니다.');
        return;
    }

    fetch(`/com/advertisement/${adType}/detail/${adIdx}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data && data.data) {
            const ad = data.data;
            document.getElementById('recruitmentTitle').innerText = ad.recruitmentTitle || '-';
            document.getElementById('adPeriod').innerText = `${ad.startDate || '-'} ~ ${ad.endDate || '-'}`;
            document.getElementById('adType').innerText = ad.adType || '-';
            document.getElementById('clickCount').innerText = ad.clickCount || 0;
        } else {
            console.error('데이터가 비어 있습니다.');
        }
    })
    .catch(error => console.error('Error fetching ad details:', error));
});
