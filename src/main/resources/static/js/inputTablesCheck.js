// Функция для создания уведомлений
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
    );
}

// Получение ссылок на поля ввода
const firstNameInput = document.getElementById('firstNameAdd');
const lastNameInput = document.getElementById('lastNameAdd');
const surnameInput = document.getElementById('surnameAdd');
const emailInput = document.getElementById('emailAdd');

// Получение ссылки на форму
const addClientForm = document.getElementById('addClientForm');

// Обработчик события отправки формы
addClientForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Отменяет стандартное действие отправки формы

    // Проверка наличия данных в полях
    if (!firstNameInput.value.trim() || !lastNameInput.value.trim() || !surnameInput.value.trim() || !emailInput.value.trim()) {
        createToast('error', 'fa-solid fa-circle-exclamation', 'Error', 'All fields are required!');
        return;
    }

    // Проверка валидности email
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(emailInput.value.trim())) {
        createToast('error', 'fa-solid fa-circle-exclamation', 'Error', 'Invalid email format!');
        return;
    }

    // Остальные проверки...

    this.submit();
});
