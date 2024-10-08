document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const loginData = {
        username: username,
        password: password
    };

    try {
        const response = await fetch('http://localhost:8080/api/auth/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        });

        let result;
        const contentType = response.headers.get("content-type");

        if (contentType && contentType.includes("application/json")) {
            result = await response.json();
        } else {
            result = await response.text();
        }

        if (response.ok) {
            // Lưu token vào localStorage sau khi đăng nhập thành công
            localStorage.setItem('token', result.data.token);

            window.location.href = '../views/home.html';
        } else {
            if (response.status === 404) {
                document.getElementById('error-message').innerText = 'Thông tin đăng nhập không hợp lệ!';
                document.getElementById('error-message').style.display = 'block';
            } else if (response.status === 401) {
                document.getElementById('error-message').innerText = 'Thông tin không hợp lệ.';
                document.getElementById('error-message').style.display = 'block';
            } else {
                document.getElementById('error-message').innerText = 'Đã có lỗi xảy ra.';
                document.getElementById('error-message').style.display = 'block';
            }
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('error-message').innerText = 'Không thể kết nối đến server!';
        document.getElementById('error-message').style.display = 'block';
    }
});
