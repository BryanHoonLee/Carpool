$('#loginform').submit(function(e){
    e.preventDefault();
    console.log("Action performed");
    $.ajax({
        url:'/login/submit',
        type:'post',
        data:$('#loginform').serialize(),
        success:function(){
            console.log("Successfully sent");
        }
    });
});