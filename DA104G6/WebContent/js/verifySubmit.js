$('.successVerify').click(function(){
    $.ajax({
        url: 'Sign_up/sign_up.do',
        type: 'post',
        data: {
            action: 'verify',
            verify: 'success',
            gro_no: $(this).val()
        },
        error: function(){
            window.alert('fail');
        }
    });
});