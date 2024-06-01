function registerUser(event) {
    event.preventDefault();

    const usernameInput = document.querySelector('input[name="new_username"]');
    const firstNameInput = document.querySelector('input[name="firstName"]');
    const surnameInput = document.querySelector('input[name="surname"]');
    const lastNameInput = document.querySelector('input[name="lastName"]');
    const emailInput = document.querySelector('input[name="email"]');
    const passwordInput = document.querySelector('input[name="new_password"]');




    function createToast(type, icon, title, text) {
        let notifications = document.querySelector('.notifications');
        let newToast = document.createElement('div');
        newToast.innerHTML = `
            <div class="toast ${type}">
                <i class="${icon}"></i>
                <div class="content">
                    <div class="title">${title}</div>
                    <span>${text}</span>
                </div>
                <i class="fa-solid fa-xmark" onclick="(this.parentElement).remove()"></i>
            </div>`;
        notifications.appendChild(newToast);
        newToast.timeOut = setTimeout(
            () => newToast.remove(), 5000
        )
    }

    // Проверка наличия данных в полях
    if (!usernameInput.value.trim() || !firstNameInput.value.trim() || !surnameInput.value.trim() || !lastNameInput.value.trim() || !emailInput.value.trim() || !passwordInput.value.trim()) {
        createToast('error', 'fa-solid fa-circle-exclamation', 'Error', 'All fields are required!');
        return;
    }

    // Проверка формата email
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(emailInput.value)) {
        createToast('warning', 'fa-solid fa-circle-exclamation', 'Warning', 'Invalid email format!');
        return;
    }

    // Собираем данные из формы
    const formData = {
        username: usernameInput.value,
        firstName: firstNameInput.value,
        surname: surnameInput.value,
        lastName: lastNameInput.value,
        email: emailInput.value,
        password: passwordInput.value,
    };

    // Отправляем данные на сервер в формате JSON
    fetch('/register/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                // Если сервер вернул ошибку, отобразить соответствующее уведомление
                response.text().then(errorMessage => {
                    if (errorMessage.includes("Username is already taken")) {
                        createToast('warning', 'fa-solid fa-circle-exclamation', 'Warning', 'Registration failed. Username already exists.');
                    } else if (errorMessage.includes("Email is already taken")) {
                        createToast('warning', 'fa-solid fa-circle-exclamation', 'Warning', 'Registration failed. Email already exists.');
                    } else {
                        createToast('error', 'fa-solid fa-circle-exclamation', 'Error', errorMessage);
                    }
                });
                throw new Error('Registration failed.');
            }
            return response.json();
        })
        .then(data => {
            // Обработка успешного ответа от сервера
            console.log('Success:', data);
        })
        .catch((error) => {
            // Обработка других ошибок
            createToast('success', 'fa-solid fa-circle-check', 'Success', 'Registration successful. Redirecting...');
            setTimeout(() => {
                window.location.reload();
            }, 5000);
            console.error('Error:', error);
        });
}
