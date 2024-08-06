document.addEventListener('DOMContentLoaded', function() {
    const accessToken = sessionStorage.getItem('accessToken');
    if (!accessToken) {
        window.location.href = '/company/auth/login';
    }
});
