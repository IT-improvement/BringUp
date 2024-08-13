document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    if (!accessToken) {
        window.location.href = '/company/auth/login';
    }
});
