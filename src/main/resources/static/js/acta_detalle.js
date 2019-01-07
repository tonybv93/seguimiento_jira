
/*--------------------  MODAL ----------------------------*/
var modal;

function modal_confirmacion(id_estado_nuevo){
	modal = document.getElementById('mi_modal');
	span = document.getElementsByClassName("close")[0];
	titulo = document.getElementById('titulo_modal');
	icono = document.getElementById('icono_modal');
	
	boton_confirmar = document.getElementById('btn_confirmar_modal');
	boton_confirmar.setAttribute('onclick','confirmar_envio('+ id_estado_nuevo +')');
	
	if (id_estado_nuevo == 3){
		boton_confirmar.innerHTML = "Aprobar";
		titulo.innerHTML = 'Aprobar Acta';
		modal.style.display = "block";
		icono.setAttribute('class','mdi mdi-check-circle-outline icono_modal_bien');
	}else if(id_estado_nuevo == 4){
		boton_confirmar.innerHTML = "Rechazar";
		titulo.innerHTML = 'Rechazar Acta';
		modal.style.display = "block";
		icono.setAttribute('class','mdi mdi-alert-outline icono_modal_alerta');
	}else{
		alert("No existe esa acción!");
	}
}


function cerrar_modal() {
	modal.style.display = "none";
}

window.onclick = function(event)	 {
if (event.target == modal) {
	modal.style.display = "none";
	}
}
function confirmar_envio(id_estado_nuevo){
	var objjson = {};
	objjson.numero1 = document.getElementById('acta_id').innerHTML;	//Id de acta
	objjson.numero2 = id_estado_nuevo;	//Id de estado nuevo
	console.log(objjson.texto1);
	data = JSON.stringify(objjson);
	$.ajax({
        url : '/rest/acta/confirmarenvio',  	        
        contentType:'application/json',
        method : 'post',
      	data : data,
        success : function(respuesta){
        	console.log(respuesta);
        	location.href ="/acta/actasporabrobar";
        },
        error: function(error,sm1,sm2){
        	alert("Se prodjo un error");
        	console.log(sm2);
            alert(sm1);
        }  	        
    });

}