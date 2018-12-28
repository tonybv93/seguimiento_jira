function enviar_acta(){
	var objjson = {};
	arreglo = [];

	objjson.numero1 = document.getElementById("select_periodo").value; 	//PERIODO ID
	objjson.numero2 = document.getElementById("acta_tipo_id").value; 	//TIPO ID
	objjson.numero3 = document.getElementById("acta_cliente_id").value;	//EMPRESA ID
	objjson.numero4 = document.getElementById("acta_fabrica_id").value;	//FABRICA ID
	
	objjson.numero5 = document.getElementById("inp_dem").value;			//GEST. DEMANDA (hs)
	objjson.numero6 = document.getElementById("inp_conf").value;		//GEST. CONFIG (hs)
	objjson.numero7 = document.getElementById("inp_tarifa").value;		//TARIFA (S/)
	
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
        	console.log(respuesta);
        },
    	error: function(error,sm1,sm2){
        	 alert("Se produjo un error");
        	console.log(sm2);
            alert(sm1);
        } 
       });
	
}
//------------------ CALCULAR MONTOS
function calcularMontos(){
	var tabla_detalle = document.getElementById("cuerpo_tabla");
	var hora_demanda = document.getElementById("inp_dem").value;
	var horas_config = document.getElementById("inp_conf").value;
	var tarifa  = document.getElementById("inp_tarifa").value;
	var horas_des = [];	
	var j = 0;//Numero de filas con check
	var nfilas = tabla_detalle.rows.length; //Numero de filas
	var total_horas = 0;
	var super_total_horas = 0;
	var tarifa_total = 0;
	//Recorrer tabla
	for (var i = 0; i < nfilas; i++){
		var tr = tabla_detalle.rows[i];
		if(tr.getElementsByTagName("input")[0].checked == true){
			horas_des[i] = tr.children[5].innerHTML * 1;
			j++;
		}else{
			horas_des[i] = 0;
		}	
		total_horas = total_horas + horas_des[i];
	}
	
	//Repartir Gestión
	var arr_hor_dem = [];
	var arr_hor_cof = [];
	var arr_horas_total = [];
	var arr_monto_total = [];
	for (var i = 0; i < nfilas; i++){
		arr_hor_dem[i] = (hora_demanda/total_horas)*horas_des[i];
		arr_hor_cof[i] = (horas_config/total_horas)*horas_des[i];
		arr_horas_total[i] = arr_hor_dem[i] +  arr_hor_cof[i] + horas_des[i];
		arr_monto_total[i] = arr_horas_total[i] * tarifa;
		super_total_horas = arr_horas_total[i] + super_total_horas;
		tarifa_total = tarifa_total + arr_monto_total[i];
	}
	//Actualziar tabla
	for (var i = 0; i < nfilas; i++){
		var tr = tabla_detalle.rows[i];
		tr.children[6].innerHTML = arr_hor_dem[i].toFixed(2);
		tr.children[7].innerHTML = arr_hor_cof[i].toFixed(2);
		tr.children[8].innerHTML = arr_horas_total[i].toFixed(2);
		tr.children[9].innerHTML = 'S/ ' + arr_monto_total[i].toFixed(2);
	}	
	
	//ACTUALIZAR DETALLE
	document.getElementById("resumen_total_horas").innerHTML = super_total_horas.toFixed(2);
	document.getElementById("resumen_total_monto").innerHTML ='S/ '+ tarifa_total.toFixed(2);
	document.getElementById("resumen_tarifa").innerHTML = 'S/ '+(tarifa*1).toFixed(2);
	
	document.getElementById("btns_enviar_acta").style.display = "block";
}

// ------------------ BUSCAR JIRAS PRE PARA EL DETALLE
function buscarJiras(){	
	var mensaje =  document.getElementById("mensaje_resultado");
	var objjson = {};
	objjson.numero1 = document.getElementById("select_periodo").value*1; 	//PERIODO
	objjson.numero2 = document.getElementById("acta_tipo_id").value*1; 		//TIPO
	objjson.numero3 = document.getElementById("acta_cliente_id").value*1; 	//EMPRESA
	var data = JSON.stringify(objjson); 
	var cuerpo_tabla = document.getElementById("cuerpo_tabla");	
	$.ajax({
	        url : '/provrest/acta/jiraspre',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){		        	
	        	if (respuesta.length != 0){	        		
	        		limpiarTablaBusqueda();	
	        		mensaje.innerHTML='';
		        	for (var i = 0; i < respuesta.length; i++) {
		        		var tr = document.createElement('tr');
		        		tr.appendChild(crearTDoculto(respuesta[i].jira));			        		
		        		tr.appendChild(crearSelector(respuesta[i].jira));			        	
			        	tr.appendChild(crearTD(respuesta[i].resumen.substring(0,40)+'...'));
			        	tr.appendChild(crearTD(respuesta[i].tipoJira));
			        	tr.appendChild(crearTD(respuesta[i].centro_costo));
			        	tr.appendChild(crearTD(respuesta[i].totalHoras));
			        	tr.appendChild(crearTD("0"));
			        	tr.appendChild(crearTD("0"));
			        	tr.appendChild(crearTD("0"));
			        	tr.appendChild(crearTD("0"));
			        	
			        	cuerpo_tabla.appendChild(tr);	
			        	
			        	//Mostrar calculador
			        	var calculador = document.getElementById('calculador');
			        	calculador.style.display = "flex";
					}
	        	}else{
	        		limpiarTablaBusqueda();	
	        		mensaje.innerHTML = 'No hay actividades para ese periodo.';
	        		var calculador = document.getElementById('calculador');
		        	calculador.style.display = "none";
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
function crearTDoculto(valor1){
	td = document.createElement('td');
	td.innerHTML = valor1;
	td.setAttribute('class','no_mostrar');
	return td;
}
function crearSelector(valor){
	td = document.createElement('td');
	ii = document.createElement('i');
	ii.setAttribute('class','input-helper');
	div = document.createElement('div');
	div.setAttribute('class','form-check form-check-flat form-group');
	label = document.createElement('label');
	label.innerHTML = valor;
	label.setAttribute('class','form-check-label');
	sel = document.createElement('input');
	sel.setAttribute('type','checkbox');	
	sel.setAttribute('id','check_detalle');
	sel.setAttribute('class','form-check-input');
	sel.checked = true;
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