$(document).ready(function() {
    $('.js-example-basic-single').select2();
});


$( function() {
    $("#id_categoria").change( function() {
        if ($(this).val() === "Jira") {
            $("#id_input").prop("disabled", false);
            $("#id_input2").prop("disabled", true);
            $("#id_input2").val("");
        } else {
            $("#id_input").prop("disabled", true);            
            $("#id_input2").prop("disabled", false);
            $("#id_input2").val("Otros");
        }
    });
});

function actualizarjiras(btn){
	var n = 0;
	var l = document.getElementById("contador_seg");
	btn.disabled=true;
	document.getElementById("modal_actualizando").style.display="block";	
	l.innerHTML = n;
	window.location.href = "http://172.16.17.101:8080/seguimiento/actualizar";
	window.setInterval(function(){
		  l.innerHTML = n;
		  n++;
		},1000);
}

