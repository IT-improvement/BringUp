document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const adIdx = urlParams.get('adIdx');
    let adType = urlParams.get('adType');
    const accessToken = localStorage.getItem('accessToken');

    let recruitmentId = null;

    if (!adType || !adIdx) {
        console.error('광고 타입 또는 ID가 지정되지 않았습니다.');
        return;
    }

    adType = adType.toLowerCase();

    document.getElementById('deleteButton').addEventListener('click', () => {
        if (confirm('정말로 광고를 삭제하시겠습니까?')) {
            deleteAd();
        }
    });

    function deleteAd() {
        fetch(`/com/advertisement/${adType}/${adIdx}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        })
        .then(response => {
            if (response.ok) {
                alert('광고가 삭제되었습니다.');
                window.location.href = '/company/product/management';
            } else {
                alert('광고 삭제에 실패하였습니다.');
            }
        });
    }

    // 광고 상세 정보 조회

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
            const recruitmentIndex= ad.recruitmentIndex;
            const advertisementIndex = ad.advertisementIndex;
            const status = ad.status;
            const adType = window.location.search.split('=')[2];
            const timeSlot = ad.timeSlot;
            const startDate = ad.startDate;
            const endDate = ad.endDate;
            const ad_img = ad.ad_img || ad.imageUrl;
            const view_count = ad.view_count;
            const click_count = ad.click_count;

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
                console.log(startDate, endDate);
                console.log(adType);
                const recruitmentTitleElement = document.getElementById('recruitmentTitle');
                if (recruitmentTitleElement) recruitmentTitleElement.innerText = recruitmentTitle;

            if (adType === 'premium') {
                console.log(status);
                const statusElement = document.getElementById('status');
                if (statusElement) statusElement.innerText = status;

                const timeSlotElement = document.getElementById('timeSlot');
                if (timeSlotElement) timeSlotElement.innerText = timeSlot;

                const adPeriodElement = document.getElementById('adPeriod');
                if (adPeriodElement) adPeriodElement.innerText = startDate + ' ~ ' + endDate;

                const adImageElement = document.getElementById('adImage');
                if (adImageElement) adImageElement.src = ad_img;

                const viewCountElement = document.getElementById('viewCount');
                if (viewCountElement) viewCountElement.innerText = view_count;

                const clickCountElement = document.getElementById('clickCount');
                if (clickCountElement) clickCountElement.innerText = click_count;

                const adTypeElement = document.getElementById('adType');
                if (adTypeElement) adTypeElement.innerText = adType;

            } else if (adType === 'main') {
                const mainIdElement = document.getElementById('mainId');
                if (mainIdElement) mainIdElement.innerText = ad.mainId;

                const adImageElement = document.getElementById('adImage');
                if (adImageElement && ad_img) adImageElement.src = ad_img;

                const recruitmentIndexElement = document.getElementById('recruitmentIndex');
                if (recruitmentIndexElement) recruitmentIndexElement.innerText = ad.recruitmentIndex;

                const exposureDaysElement = document.getElementById('exposureDays');
                if (exposureDaysElement) exposureDaysElement.innerText = ad.exposureDays;

                const adPeriodElement = document.getElementById('adPeriod');
                if (adPeriodElement) adPeriodElement.innerText = `${ad.startDate} ~ ${ad.endDate}`;

                const avaliableDateElement = document.getElementById('avaliableDate');
                if (avaliableDateElement) avaliableDateElement.innerText = ad.avaliableDate;

                const discountRateElement = document.getElementById('discountRate');
                if (discountRateElement) discountRateElement.innerText = ad.discountRate;

                const statusElement = document.getElementById('status');
                if (statusElement) statusElement.innerText = ad.status;

                const imageUrlElement = document.getElementById('imageUrl');
                if (imageUrlElement) imageUrlElement.src = ad.imageUrl;

                const viewCountElement = document.getElementById('viewCount');
                if (viewCountElement) viewCountElement.innerText = ad.viewCount;

                const clickCountElement = document.getElementById('clickCount');
                if (clickCountElement) clickCountElement.innerText = ad.clickCount;

                const adTypeElement = document.getElementById('adType');
                if (adTypeElement) adTypeElement.innerText = adType;
                
            } else if (adType === 'banner') {
                const bannerAdIndexElement = document.getElementById('bannerAdIndex');
                if (bannerAdIndexElement) bannerAdIndexElement.innerText = ad.bannerAdIndex;

                const exposureDaysElement = document.getElementById('exposureDays');
                if (exposureDaysElement) exposureDaysElement.innerText = ad.exposureDays;

                const adPeriodElement = document.getElementById('adPeriod');
                if (adPeriodElement) adPeriodElement.innerText = `${ad.startDate} ~ ${ad.endDate}`;

                const statusElement = document.getElementById('status');
                if (statusElement) statusElement.innerText = ad.status;

                const imageUrlElement = document.getElementById('imageUrl');
                if (imageUrlElement) imageUrlElement.src = ad.imageUrl;

                const viewCountElement = document.getElementById('viewCount');
                if (viewCountElement) viewCountElement.innerText = ad.viewCount;

                const clickCountElement = document.getElementById('clickCount');
                if (clickCountElement) clickCountElement.innerText = ad.clickCount;

                const adTypeElement = document.getElementById('adType');
                if (adTypeElement) adTypeElement.innerText = adType;
            } else if (adType === 'announcement') {
                const announcementIdElement = document.getElementById('announcementId');
                if (announcementIdElement) announcementIdElement.innerText = ad.announcementId;

                const durationMonthsElement = document.getElementById('durationMonths');
                if (durationMonthsElement) durationMonthsElement.innerText = ad.durationMonths;

                const statusElement = document.getElementById('status');
                if (statusElement) statusElement.innerText = ad.status;

                const adPeriodElement = document.getElementById('adPeriod');
                if (adPeriodElement) adPeriodElement.innerText = `${ad.startDate} ~ ${ad.endDate}`;

                const adTypeElement = document.getElementById('adType');
                if (adTypeElement) adTypeElement.innerText = adType;
            }
        })
        .catch(error => console.error('Error fetching recruitment details:', error));
            } else {
                console.error('데이터가 비어 있습니다.');
            }
        })
        .catch(error => console.error('Error fetching ad details:', error));
});
