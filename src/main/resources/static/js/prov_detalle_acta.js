function back(){
	location.href = "/proveedor/gestion/listaactas";
}
/*--------------------  MODAL ----------------------------*/
var modal;

function modal_buscar_jira(){
	modal = document.getElementById('mi_modal');
	span = document.getElementsByClassName("close")[0];
	modal.style.display = "block";
}


function cerrar_modal() {
	modal.style.display = "none";
}

window.onclick = function(event)	 {
if (event.target == modal) {
	modal.style.display = "none";
	}
}
function confirmar_envio(){
	var objjson = {};
	objjson.numero1 = document.getElementById('acta_id').innerHTML;	//Id de acta
	objjson.texto1 = document.getElementById('txtarea_descipcion').value ;
	console.log(objjson.texto1);
	data = JSON.stringify(objjson);
	$.ajax({
        url : '/provrest/acta/confirmarenvio',  	        
        contentType:'application/json',
        method : 'post',
      	data : data,
        success : function(respuesta){
        	console.log(respuesta);
        	location.reload();
        },
        error: function(error,sm1,sm2){
        	alert("Se prodjo un error");
        	console.log(sm2);
            alert(sm1);
        }  	        
    });

}