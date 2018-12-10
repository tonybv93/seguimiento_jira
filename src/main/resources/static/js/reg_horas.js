var contador = 0;
function agregar(){
	var tabla = document.getElementById("detalle_registros");
	var fecha = new Date().toLocaleDateString("es-ES");	
	var periodo = document.getElementById("periodo").value
	var nro_horas = document.getElementById("nro_horas").value
	var tr = document.createElement('tr');
	var jira = document.getElementById("id_input").value;
	var str_busqueda = '../../rest/jira/' + jira
	console.log(str_busqueda);
	$.get(str_busqueda).done(function( data ) {	  
		contador++;
		tr.appendChild(crearTD(contador));
		tr.appendChild(crearTD(jira));
		tr.appendChild(crearTD(data.resumen));
		tr.appendChild(crearTD(data.horas_des));
		tr.appendChild(crearTD(10));
		tr.appendChild(crearTD(periodo));
		tr.appendChild(crearTD(fecha));
		tr.appendChild(crearTD(nro_horas));
		tr.appendChild(crearTD(data.fabrica.tarifa));
		tr.appendChild(crearTD(data.fabrica.tarifa*nro_horas));
		  
		tabla.appendChild(tr);	  
	});	
}
function crearTD(valor1){
		td = document.createElement('td');
		td.innerHTML = valor1;
		return td;
	}
