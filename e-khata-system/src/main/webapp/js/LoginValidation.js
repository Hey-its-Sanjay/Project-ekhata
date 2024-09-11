// For Show Password Icon
document.addEventListener('DOMContentLoaded', function() {
    const passwordInput = document.getElementById('password');
    const showPassIcon = document.querySelector('#pass .showPass');

    showPassIcon.addEventListener('click', function() {
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            showPassIcon.classList.add('fa-eye-slash');
        } else {
            passwordInput.type = 'password';
            showPassIcon.classList.remove('fa-eye-slash');
        }
    });

    passwordInput.addEventListener('input', function() {
        if (passwordInput.value.length > 0) {
            showPassIcon.style.display = 'block';
        } else {
            showPassIcon.style.display = 'none';
        }
    });

    if (passwordInput.value.length > 0) {
        showPassIcon.style.display = 'block';
    } else {
        showPassIcon.style.display = 'none';
    }
});