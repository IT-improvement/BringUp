document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
        fetch('/com/recruitment/list', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            const recruitmentList = data.data;
            const recruitmentListBody = document.getElementById('recruitment-list-body');
            recruitmentList.forEach(recruitment => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${recruitment.recruitmentIndex}</td>
                    <td>${recruitment.managerEmail}</td>
                    <td>${recruitment.recruitmentType}</td>
                    <td>${recruitment.category}</td>
                    <td>${recruitment.skill}</td>
                    <td>${recruitment.startDate}</td>
                    <td>${recruitment.period}</td>
                    <td>${recruitment.status}</td>
                    <td>${recruitment.recruitmentClass}</td>
                `;
                row.addEventListener('click', function() {
                    window.location.href = `/jobpost/management/detail`;
                });
                recruitmentListBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching recruitment list:', error);
        });
    } else {
        console.error('Access token not found in local storage.');
    }
});
