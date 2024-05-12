function loginUser(event) {
    const usernameInput = document.querySelector('input[name="username"]');
    const passwordInput = document.querySelector('input[name="password"]');

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
    if (!usernameInput.value.trim() || !passwordInput.value.trim()) {
        event.preventDefault(); // Предотвращаем отправку формы
        createToast('error', 'fa-solid fa-circle-exclamation', 'Error', 'Username and password are required!');
    }
}

// Добавляем обработчик для формы авторизации
const authForm = document.getElementById('authForm');
authForm.addEventListener('submit', loginUser);
