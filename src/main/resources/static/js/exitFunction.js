$(document).ready(function () {
    $('#exitButton').on('click', function (event) {
        event.preventDefault();
        Swal.fire({
            title: 'Вы уверены, что хотите выйти?',
            text: "Ваши несохраненные данные могут быть потеряны.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Да, выйти',
            cancelButtonText: 'Нет, остаться'
        }).then((result) => {
            if (result.isConfirmed) {
                $.post('/logout', function () {
                    window.location.href = '/signup?logout';
                });
            }
        });
    });
});