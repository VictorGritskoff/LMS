document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/settings')
        .then(response => response.json())
        .then(data => {
            document.getElementById('firstName').value = data.firstName;
            document.getElementById('lastName').value = data.lastName;
            document.getElementById('surname').value = data.surname;
            document.getElementById('username').value = data.username;
            document.getElementById('email').value = data.email;
            document.getElementById('password').value = data.password;
            console.log("Путь к изображению:", data.imagePath);
            if (data.imagePath) {
                document.getElementById('profileImage').src = "/uploads" + data.imagePath;
            }
        });
    document.querySelector('.edit-button').addEventListener('click', function() {
        Swal.fire({
            title: 'Редактировать профиль',
            html: `
                <input id="editFirstName" class="swal2-input" placeholder="Имя" value="${document.getElementById('firstName').value}">
                <input id="editLastName" class="swal2-input" placeholder="Фамилия" value="${document.getElementById('lastName').value}">
                <input id="editSurname" class="swal2-input" placeholder="Отчество" value="${document.getElementById('surname').value}">
                <input id="editUsername" class="swal2-input" placeholder="Имя пользователя" value="${document.getElementById('username').value}">
                <input id="editEmail" class="swal2-input" placeholder="Почта" value="${document.getElementById('email').value}">
                <input id="editPassword" class="swal2-input" placeholder="Пароль" value="${document.getElementById('password').value}">
                <input type="file" id="editImage" class="swal2-input" placeholder="Изображение">
            `,
            focusConfirm: false,
            preConfirm: () => {
                return {
                    firstName: document.getElementById('editFirstName').value,
                    lastName: document.getElementById('editLastName').value,
                    surname: document.getElementById('editSurname').value,
                    username: document.getElementById('editUsername').value,
                    email: document.getElementById('editEmail').value,
                    password: document.getElementById('editPassword').value,
                    imageFile: document.getElementById('editImage').files[0]
                }
            }
        }).then((result) => {
            if (result.isConfirmed) {
                let formData = new FormData();
                formData.append('employee', new Blob([JSON.stringify(result.value)], { type: 'application/json' }));
                if (result.value.imageFile) {
                    formData.append('image', result.value.imageFile);
                }
                fetch('/api/settings', {
                    method: 'PUT',
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data) {
                            Swal.fire('Сохранено!', '', 'success')
                                .then(() => {
                                    document.getElementById('firstName').value = result.value.firstName;
                                    document.getElementById('lastName').value = result.value.lastName;
                                    document.getElementById('surname').value = result.value.surname;
                                    document.getElementById('username').value = result.value.username;
                                    document.getElementById('email').value = result.value.email;
                                    document.getElementById('password').value = result.value.password;
                                    if (result.value.imageFile) {
                                        console.log("Путь к изображению при загрузке:", result.value.imageFile);
                                        document.getElementById('profileImage').src = URL.createObjectURL(result.value.imageFile);
                                    }
                                });
                        } else {
                            Swal.fire('Ошибка!', 'Не удалось сохранить изменения', 'error');
                        }
                    });
            }
        });

        document.getElementById('editImage').addEventListener('change', function(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('editImagePreview').src = e.target.result;
                }
                reader.readAsDataURL(file);
            }
        });
    });
});