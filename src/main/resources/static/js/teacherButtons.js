$('document').ready(function (){
    $('table #editButton').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (teacher, status){
            $('#idUpdate').val(teacher.teacherId);
            $('#firstNameUpdate').val(teacher.firstName);
            $('#lastNameUpdate').val(teacher.lastName);
            $('#surnameUpdate').val(teacher.surname);
            $('#emailUpdate').val(teacher.email);
            $('#salaryUpdate').val(teacher.salary);
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
    var currentMROT = MROT;
    document.getElementById("salaryAdd").setAttribute("min", currentMROT);
});