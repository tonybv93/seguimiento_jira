
/*--------------------  MODAL ----------------------------*/
var modal;

function modal_eliminar_acta(btn){
	var tr = btn.parentNode.parentNode;
	var id = tr.children[0].innerHTML*1;
	var periodo = tr.children[4].innerHTML;
	var tipo = tr.children[3].innerHTML;
	var cliente = tr.children[2].innerHTML;
	
	document.getElementById('spn_id').innerHTML = id;
	document.getElementById('spn_periodo').innerHTML = periodo;
	document.getElementById('spn_tipo').innerHTML = tipo;
	document.getElementById('spn_cliente').innerHTML = cliente;
	document.getElementById('input_id').value = id;
	
	console.log(tr);
	modal = document.getElementById('modal_eliminar');
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
