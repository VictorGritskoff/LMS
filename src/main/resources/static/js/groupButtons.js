$('document').ready(function (){
    $('table #editButton').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (group, status) {
            $('#groupId').val(group.groupId);
            $('#groupClientUpdate').val(group.clients[0].clientId);
            $('#groupCourseUpdate').val(group.course.courseId);
            $('#groupDateUpdate').val(group.creationDate);
        });
        $('#editModal').modal();
    });

    $('table #deleteButton').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('#confirmedDeleteButton').attr('href',href);
        $('#deleteModal').modal();
    });

    $('#confirmedDeleteButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.post(href, function () {
            window.location.reload();
        });
    });
});