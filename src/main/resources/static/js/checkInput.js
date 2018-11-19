/* $('#cityValidation').submit(function(e){
   e.preventDefault();
   $.ajax({
       url:'/location/city',
       type:'post',
       data:$('#cityName').serialize(),
       datatype: 'json',
       success:function(data){
       console.log("City is legit")
       }
       error: function (
       console.log("City does not exist");
       )
   });
});
*/

function cityValidate() {
   e.preventDefault();
   $.ajax({
       url:'/data/city/names',
       type:'get',
       data:$('#cityName').serialize(),
       datatype: 'json',
       success:function(data) {
           console.log("City is legit")
       },
       error: function() {
           console.log("City does not exist")
       }
   })
}
