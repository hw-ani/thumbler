document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const userType = document.querySelector('input[name="userType"]:checked').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Check if username and password fields are not empty
    if (username && password) {
        if (userType === 'consumer') {
            window.location.href = 'customer_home.html';
        } else if (userType === 'seller') {
            window.location.href = 'seller_home.html';
        }
    } else {
        alert('Please fill in all fields.');
    }
});
