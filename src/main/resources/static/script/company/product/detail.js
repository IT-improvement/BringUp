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
            if (adType === 'premium') {
                document.getElementById('recruitmentIndex').innerText = ad.recruitmentIndex || '-';
                document.getElementById('advertisementIndex').innerText = ad.advertisementIndex || '-';
                document.getElementById('status').innerText = ad.status || '-';
                document.getElementById('adType').innerText = ad.adType || '-';
                document.getElementById('timeSlot').innerText = ad.timeSlot || '-';
                document.getElementById('adPeriod').innerText = `${ad.startDate || '-'} ~ ${ad.endDate || '-'}`;
                document.getElementById('adImg').src = ad.ad_img || '';
                document.getElementById('viewCount').innerText = ad.view_count || 0;
                document.getElementById('clickCount').innerText = ad.click_count || 0;
            } else if (adType === 'main') {
                document.getElementById('recruitmentIndex').innerText = ad.recruitmentIndex || '-';
                document.getElementById('mainId').innerText = ad.mainId || '-';
                document.getElementById('exposureDays').innerText = ad.exposureDays || '-';
                document.getElementById('adPeriod').innerText = `${ad.startDate || '-'} ~ ${ad.endDate || '-'}`;
                document.getElementById('avaliableDate').innerText = ad.avaliableDate || '-';
                document.getElementById('discountRate').innerText = ad.discountRate || '-';
                document.getElementById('status').innerText = ad.status || '-';
                document.getElementById('imageUrl').src = ad.imageUrl || '';
                document.getElementById('viewCount').innerText = ad.viewCount || 0;
                document.getElementById('clickCount').innerText = ad.clickCount || 0;
            } else if (adType === 'banner') {
                document.getElementById('recruitmentIndex').innerText = ad.recruitmentIndex || '-';
                document.getElementById('bannerAdIndex').innerText = ad.bannerAdIndex || '-';
                document.getElementById('exposureDays').innerText = ad.exposureDays || '-';
                document.getElementById('adPeriod').innerText = `${ad.startDate || '-'} ~ ${ad.endDate || '-'}`;
                document.getElementById('status').innerText = ad.status || '-';
                document.getElementById('imageUrl').src = ad.imageUrl || '';
                document.getElementById('viewCount').innerText = ad.viewCount || 0;
                document.getElementById('clickCount').innerText = ad.clickCount || 0;
            } else if (adType === 'announcement') {
                document.getElementById('recruitmentIndex').innerText = ad.recruitmentIndex || '-';
                document.getElementById('announcementId').innerText = ad.announcementId || '-';
                document.getElementById('durationMonths').innerText = ad.durationMonths || '-';
                document.getElementById('status').innerText = ad.status || '-';
                document.getElementById('adPeriod').innerText = `${ad.startDate || '-'} ~ ${ad.endDate || '-'}`;
            }
        } else {
            console.error('데이터가 비어 있습니다.');
        }
    })
    .catch(error => console.error('Error fetching ad details:', error));
});
