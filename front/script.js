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
            window.location.href = 'seller_main1.html';
        }
    } else {
        alert('Please fill in all fields.');
    }
});

// 햄버거 메뉴 클릭 시 이동
function goToMenuPage() {
    window.location.href = 'menu.html';  // 메뉴 페이지로 이동
}

// 하단 네비게이션 아이템 클릭 시 해당 페이지로 이동
function goToPage(page) {
    window.location.href = page;
}
