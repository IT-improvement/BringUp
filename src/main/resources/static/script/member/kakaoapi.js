
const REST_API_KEY =



function kakaoapiLogin(){
    fetch(`https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`)
}