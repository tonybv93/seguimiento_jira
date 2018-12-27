function enviar_acta(){
	var objjson = {};
	objjson.texto1 = document.getElementById("txa_descripcion").value; 	//DESCRIPCION
	objjson.numero1 = document.getElementById("select_periodo").value; 	//PERIODO ID
	objjson.numero2 = document.getElementById("acta_tipo_id").value; 	//TIPO ID
	objjson.numero3 = document.getElementById("acta_cliente_id").value;	//EMPRESA ID
	objjson.numero4 = document.getElementById("acta_fabrica_id").value;	//FABRICA ID
		
	var tabla = document.getElementById("cuerpo_tabla");
	detalle = [];
	var contador = 0;
	for (var i = 0; i < tabla.rows.length; i++) {		
		if (tabla.rows[i].getElementsByTagName("input")[0].checked == true){
			detalle[contador] = tabla.rows[i].firstChild.innerHTML;
			contador++;
		}		
	}
	objjson.arregloStr = detalle;
	var data = JSON.stringify(objjson);
	$.ajax({
        url : '/provrest/acta/nueva',  	        
        contentType:'application/json',
        method : 'post',
      	data : data,
        success : function(respuesta){	        	
        	console.log("Se registró en acta con código: ACT-" +respuesta);
        },
        error: function(error,sm1,sm2){
        	alert("Se produjo un error");
        	console.log(sm2);
            alert(sm1);
        }  	        
    });

}

function buscarJiras(){	
	var objjson = {};
	objjson.numero1 = document.getElementById("select_periodo").value*1; 	//PERIODO
	objjson.numero2 = document.getElementById("acta_tipo_id").value*1; 	//TIPO
	objjson.numero3 = document.getElementById("acta_cliente_id").value*1; 	//EMPRESA
	var data = JSON.stringify(objjson); 
	var cuerpo_tabla = document.getElementById("cuerpo_tabla");	
	$.ajax({
	        url : '/provrest/acta/jiraspre',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){	        	
	        	limpiarTablaBusqueda();	
	        	for (var i = 0; i < respuesta.length; i++) {
	        		var tr = document.createElement('tr');
		        	tr.appendChild(crearTD(respuesta[i].jira));
		        	tr.appendChild(crearTD(respuesta[i].tipoJira));
		        	tr.appendChild(crearTD(respuesta[i].resumen));
		        	tr.appendChild(crearTD(respuesta[i].centro_costo));
		        	tr.appendChild(crearTD(respuesta[i].totalHoras));
		        	tr.appendChild(crearTD("S/ 00.00"));
		        	tr.appendChild(crearSelector());
		        	cuerpo_tabla.appendChild(tr);	
				}	        	
	        },
	        error: function(error,sm1,sm2){
	        	 alert("Se produjo un error");
	        	console.log(sm2);
	            alert(sm1);
	        }  	        
	    });
}
function crearTD(valor1){
	if (valor1 == null || valor1 == ''){
		valor1 = '-';
	}
	td = document.createElement('td');
	td.innerHTML = valor1;
	return td;
}
function crearSelector(){
	td = document.createElement('td');
	ii = document.createElement('i');
	ii.setAttribute('class','input-helper');
	div = document.createElement('div');
	div.setAttribute('class','form-check form-check-flat form-group');
	label = document.createElement('label');
	label.innerHTML = 'Sí'
	label.setAttribute('class','form-check-label');
	sel = document.createElement('input');
	sel.setAttribute('type','checkbox');
	sel.setAttribute('id','check_detalle');
	sel.setAttribute('class','form-check-input');
	label.appendChild(sel);
	label.appendChild(ii);
	div.appendChild(label);
	td.appendChild(div);
	return td;
}
function limpiarTablaBusqueda(){
	a = document.getElementById("cuerpo_tabla");
	while(a.hasChildNodes())
		a.removeChild(a.firstChild);
}