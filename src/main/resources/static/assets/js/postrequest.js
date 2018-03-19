/**
 * Created by louis on 2018/08/10.
 */
$( document ).ready(function() {

    var url = window.location;

    // // SUBMIT FORM
    // $("#qualificationPostForm").submit(function(event) {
    //     // Prevent the form from submitting via the browser.
    //     event.preventDefault();
    //     ajaxPost();
    // });






    function ajaxPost(formData){
        console.log("inside");
        // DO POST
        $.ajax({

            type : "POST",
            contentType : "application/json",
            url : url + "/postQualification",
            data : JSON.stringify(formData),
            dataType : 'json',
            // success : function(result) {
            //     if(result.status == "Done"){
            //         $("#postResultDiv").html("<strong>" + "Post Successfully! Customer's Info: FirstName = "
            //             + result.data.firstname + " ,LastName = " + result.data.lastname + "</strong>");
            //     }else{
            //         $("#postResultDiv").html("<strong>Error</strong>");
            //     }
            //     console.log(result);
            // },
            // error : function(e) {
            //     alert("Error!")
            //     console.log("ERROR: ", e);
            // }
        });

        // Reset FormData after Posting
        resetData();

    }

    function resetData(){
        $("#firstname").val("");
        $("#lastname").val("");
    }
})