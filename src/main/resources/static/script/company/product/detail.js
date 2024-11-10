document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const adIdx = urlParams.get('adIdx');
    const adType = urlParams.get('adType');
    const accessToken = localStorage.getItem('accessToken');

    let recruitmentId = null;

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
            console.log(data);
            const ad = data.data;
            recruitmentId = ad.recruitmentIndex;

            fetch(`/com/recruitment/detail/${recruitmentId}`, {
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
                console.log(data);
                const recruitmentTitle = data.data.r_title;
                document.getElementById('recruitmentTitle').innerText = recruitmentTitle;
            })
            .catch(error => console.error('Error fetching recruitment details:', error));

            if (adType === 'premium') {
                document.getElementById('status').innerText = ad.status || '-';
                document.getElementById('adType').innerText = ad.adType || '-';
                document.getElementById('timeSlot').innerText = ad.timeSlot || '-';
                document.getElementById('adPeriod').innerText = `${ad.startDate || '-'} ~ ${ad.endDate || '-'}`;
                document.getElementById('adImg').src = ad.ad_img || '';
                document.getElementById('viewCount').innerText = ad.view_count || 0;
                document.getElementById('clickCount').innerText = ad.click_count || 0;
            } else if (adType === 'main') {
                const mainIdElem = document.getElementById('mainId');
                if (mainIdElem) {
                    mainIdElem.innerText = ad.mainId || '-';
                }

                const exposureDaysElem = document.getElementById('exposureDays');
                if (exposureDaysElem) {
                    exposureDaysElem.innerText = ad.exposureDays || '-';
                }

                const adPeriodElem = document.getElementById('adPeriod');
                if (adPeriodElem) {
                    adPeriodElem.innerText = `${ad.startDate || '-'} ~ ${ad.endDate || '-'}`;
                }

                const avaliableDateElem = document.getElementById('avaliableDate');
                if (avaliableDateElem) {
                    avaliableDateElem.innerText = ad.avaliableDate || '-';
                }

                const discountRateElem = document.getElementById('discountRate');
                if (discountRateElem) {
                    discountRateElem.innerText = ad.discountRate || '-';
                }

                const statusElem = document.getElementById('status');
                if (statusElem) {
                    statusElem.innerText = ad.status || '-';
                }

                const imageUrlElem = document.getElementById('imageUrl');
                if (imageUrlElem) {
                    imageUrlElem.src = ad.imageUrl || '';
                }

                document.getElementById('viewCount').innerText = ad.viewCount || 0;
                document.getElementById('clickCount').innerText = ad.clickCount || 0;
            } else if (adType === 'banner') {
                document.getElementById('bannerAdIndex').innerText = ad.bannerAdIndex || '-';
                document.getElementById('exposureDays').innerText = ad.exposureDays || '-';
                document.getElementById('adPeriod').innerText = `${ad.startDate || '-'} ~ ${ad.endDate || '-'}`;
                document.getElementById('status').innerText = ad.status || '-';
                document.getElementById('imageUrl').src = ad.imageUrl || '';
                document.getElementById('viewCount').innerText = ad.viewCount || 0;
                document.getElementById('clickCount').innerText = ad.clickCount || 0;
            } else if (adType === 'announcement') {
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
