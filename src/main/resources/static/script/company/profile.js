document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem("accessToken");
    console.log("Access token: " + accessToken);
    const url = "/com/companyInfo/post"
    if (accessToken) {
        fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer `+ accessToken
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            // 데이터를 폼에 맞춰 입력
            document.getElementById('companyName').value = data.data.companyName;
            document.getElementById('representativeName').value = data.data.managerName;
            document.getElementById('address').value = data.data.companyAddress;
            document.getElementById('phoneNumber').value = data.data.companyPhonenumber;
            document.getElementById('homepage').value = data.data.companyHomepage;
            document.getElementById('industry').value = data.data.companyCategory;
            document.getElementById('establishDate').value = data.data.companyOpendate;
            document.getElementById('scale').value = data.data.companyScale;
            document.getElementById('employeeCount').value = data.data.companySize;
            document.getElementById('businessLicense').value = data.data.companyLicense;
            document.getElementById('representativeEmail').value = data.data.managerEmail;
            document.getElementById('representativePhoneNumber').value = data.data.managerPhonenumber;
            // 프로필 이미지 설정
            const images = data.data.companyLogo;
            console.log("회사 로고:", images);
            if (images) {
                sessionStorage.setItem('companyLogo', images);
                document.getElementById('profileImage').src = "/resources/logos/" + images;
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    } else {
        console.log("토큰을 찾을 수 없습니다.");
    }
});