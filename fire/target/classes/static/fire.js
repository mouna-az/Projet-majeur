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
    var search = {}
    search["long"] = $("#long").val();
	search["lat"] = $("#lat").val();
	search["type"] = $("#type").val();
    search["efficiency"] = $("#efficiency").val();
    search["liquidType"] = $("#liquidType").val();
    search["liquidQuantity"] = $("#liquidQuantity").val();
    search["lliquidConsumption"] = $("#liquidConsumption").val();
    $("#sign").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/vehicle/add",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
                + JSON.stringify(data, null, 4) + "&lt;/pre&gt;";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#sign").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4>&lt;pre&gt;"
                + e.responseText + "&lt;/pre&gt;";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#login").prop("disabled", false);

        }
    });

}
