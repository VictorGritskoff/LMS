$('document').ready(function (){
    $('table #editButton').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (client, status){
            $('#idEdit').val(client.clientId);
            $('#nameEdit').val(client.firstName);
            $('#lastNameEdit').val(client.lastName);
            $('#surnameEdit').val(client.surname);
            $('#emailEdit').val(client.email);
        });
        $('#editModal').modal();
    });

    $('table #deleteButton').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmedDeleteButton').attr('href',href);
        $('#deleteModal').modal();
    });

    // Обработчик клика по кнопке подтверждения удаления
    $('#confirmedDeleteButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href'); // Получаем ссылку для удаления из атрибута href
        $.post(href, function () {
            window.location.reload(); // Перезагружаем страницу после успешного удаления
        });
    });
});