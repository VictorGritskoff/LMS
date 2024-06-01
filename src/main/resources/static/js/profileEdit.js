document.querySelector('.edit-button').addEventListener('click', function() {
    Swal.fire({
        title: 'Редактировать профиль',
        html:
            '<input id="editFirstName" class="swal2-input" placeholder="Имя" value="' + document.getElementById('firstName').value + '">' +
            '<input id="editLastName" class="swal2-input" placeholder="Фамилия" value="' + document.getElementById('lastName').value + '">' +
            '<input id="editSurname" class="swal2-input" placeholder="Отчество" value="' + document.getElementById('surname').value + '">' +
            '<input id="editUsername" class="swal2-input" placeholder="Имя пользователя" value="' + document.getElementById('username').value + '">' +
            '<input id="editMail" class="swal2-input" placeholder="Почта" value="' + document.getElementById('email').value + '">' +
            '<input id="editPassword" class="swal2-input" placeholder="Пароль" value="' + document.getElementById('password').value + '">' +
            '<input id="editImage" class="swal2-input" placeholder="Ссылка на изображение">',
        focusConfirm: false,
        showCancelButton: true,
        cancelButtonText: 'Отмена',
        preConfirm: () => {
            // Получение значений из полей ввода
            const firstName = Swal.getPopup().querySelector('#editFirstName').value;
            const lastName = Swal.getPopup().querySelector('#editLastName').value;
            const surname = Swal.getPopup().querySelector('#editSurname').value;
            const username = Swal.getPopup().querySelector('#editUsername').value;
            const email = Swal.getPopup().querySelector('#editMail').value;
            const password = Swal.getPopup().querySelector('#editPassword').value;
            const image = Swal.getPopup().querySelector('#editImage').value;
            // Отправка данных на сервер для обновления (реализация зависит от вашего бэкэнда)
            console.log('Отправка данных:', { firstName, lastName, surname, username, email, password, image });
        }
    });
});
