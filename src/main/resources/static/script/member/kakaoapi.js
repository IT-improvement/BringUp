function kakaoapiLogin(){
    console.log("성공");
    fetch('/api/kakao/login', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답이 올바르지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            JSON.stringify(data);
            console.log("data: " + JSON.stringify(data));
            window.open(data.location, "kakao","width=600, height=400, top=150, left=150")
        })
        .catch(error => {
            console.error('에러:', error);
        });
}
