$('document').ready(function (){
    $('table #editButton').on('click',function (event){
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (order, status) {
            $('#idUpdate').val(order.orderId);
            $('#orderClientUpdate').val(order.client.clientId);
            $('#orderCourseUpdate').val(order.course.courseId);
            $('#orderPriceUpdate').val(order.orderPrice);

            $('#orderClientUpdate option').each(function() {
                console.log('Option value:', $(this).val());
                if($(this).val() == order.client.clientId) {
                    $(this).prop('selected', true);
                } else {
                    $(this).prop('selected', false);
                }
            });
            $('#orderCourseUpdate option').each(function() {
                console.log('Option value:', $(this).val());
                if($(this).val() == order.course.courseId) {
                    $(this).prop('selected', true);
                } else {
                    $(this).prop('selected', false);
                }
            });
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
    const orderCourseSelect = document.getElementById('orderCourse');
    const orderPriceInput = document.getElementById('orderPrice');

    orderCourseSelect.addEventListener('change', function () {
        const selectedOption = this.options[this.selectedIndex];
        const price = selectedOption.getAttribute('data-price');
        orderPriceInput.value = price ? price : '';
    });

});