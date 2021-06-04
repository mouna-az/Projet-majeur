//ajax
$(document).ready(function () {

    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        vehicadd_ajax_submit() ;
    });

});
//ajouter
function vehicadd_ajax_submit() {	
    console.log("Je suis dans la fonction")
    var search = {}
    search["long"] = $("#long").val();
	search["lat"] = $("#lat").val();
	search["type"] = $("#type").val();
    search["efficiency"] = $("#efficiency").val();
    search["liquidType"] = $("#liquidType").val();
    search["liquidQuantity"] = $("#liquidQuantity").val();
    search["lliquidConsumption"] = $("#liquidConsumption").val();
    $("#sign").prop("disabled", true);
    
    console.log(JSON.stringify(search))
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/vehicle/add",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        success: function (data) {
            console.log("SUCCESS : ", data);
            $("#sign").prop("disabled", false);
        },
    });

}
