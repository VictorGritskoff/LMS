$('document').ready(function (){
    $('table #editButton').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (course, status) {
            console.log('Course data:', course);
            $('#idUpdate').val(course.courseId);
            $('#courseNameEdit').val(course.name);
            $('#courseTeacherEdit').val(course.teacher.teacherId);
            $('#imagePathEdit').val(course.imagePath);
            $('#descriptionEdit').val(course.description);
            $('#priceEdit').val(course.price);

            // Устанавливаем выбранное значение в выпадающем списке преподавателей
            $('#courseTeacherEdit option').each(function() {
                console.log('Option value:', $(this).val()); // Логируем значение опции
                if($(this).val() == course.teacher.teacherId) {
                    $(this).prop('selected', true);
                } else {
                    $(this).prop('selected', false);
                }
            });
        });
        $('#editModal').modal();
    });

    $('table #viewButton').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (course, status){
            $('#idView').val(course.courseId);
            $('#courseNameView').val(course.name);
            $('#courseTeacherView').val(course.teacher.surname);
            $('#priceView').val(course.price);
            // можно добавить вывод имени, отчества и почты преподавателя
            // стоит добавить описание и можно изображение
        });
        $('#viewModal').modal();
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