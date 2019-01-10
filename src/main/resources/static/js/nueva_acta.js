// VARIABLES GLOBLAES
var calculado = false;
var hora_demanda,horas_config,tarifa;

function back(){
	location.href = "/home";
}

function enviar_acta(){
	//Validar que los datos calculados son los que se van a enviar
	if (calculado == true ){
		var objjson = {};
		arreglo = [];
		//Variables
		objjson.numero1 = document.getElementById("select_periodo").value; 	//PERIODO ID
		objjson.numero2 = document.getElementById("acta_tipo_id").value; 	//TIPO ID
		objjson.numero3 = document.getElementById("acta_cliente_id").value;	//EMPRESA ID
		objjson.numero4 = document.getElementById("acta_fabrica_id").value;	//FABRICA ID	
		objjson.numero5 = document.getElementById("inp_dem").value;			//GEST. DEMANDA (hs)
		objjson.numero6 = document.getElementById("inp_conf").value;		//GEST. CONFIG (hs)
		objjson.numero7 = document.getElementById("inp_tarifa").value;		//TARIFA (S/)
		
		//Lista de Jiras
		var tabla = document.getElementById("cuerpo_tabla");
		detalle_jiras = [];
		detalle_tarifas = [];
		var contador = 0;
		for (var i = 0; i < tabla.rows.length-1; i++) {			
			detalle_jiras[i] = tabla.rows[i].firstChild.innerHTML;
			detalle_tarifas[i] = tabla.rows[i].children[8].firstChild.value;
		}	
		objjson.arregloStr = detalle_jiras;	
		objjson.arregloDecimal = detalle_tarifas;	
		var data = JSON.stringify(objjson);
		console.log(data);
		$.ajax({
	        url : '/provrest/acta/nueva',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){	
	        	if (respuesta == null){
	        		alert('Los datos enviados no fueron consistentes. Vuelve a intentarlo.');
	        	}else{
	        		location.href = '/proveedor/gestion/listaactas';
	        	}
	        },
	    	error: function(error,sm1,sm2){
	        	 alert("Se produjo un error");
	        	console.log(sm2);
	            alert(sm1);
	        } 
	       });		
	}else{
		alert('Los parámentro de cálculo han cambiado. Recalcular.');
	}
}
//------------------ CALCULAR MONTOS
function calcularMontos(){
	if (!calculado){
		tarifa  = document.getElementById("inp_tarifa").value;		
		if (tarifa != '' &&  tarifa > 0){
			var tabla_detalle = document.getElementById("cuerpo_tabla");
			hora_demanda = document.getElementById("inp_dem").value;
			horas_config = document.getElementById("inp_conf").value;		
			var horas_des = [];	
			var horas_ges_dem = [];	
			var j = 0;//Numero de filas con check
			var nfilas = tabla_detalle.rows.length; //Numero de filas
			var total_horas_des = 0;
			var total_horas_ges_dem = 0;
			var super_total_horas = 0;
			var tarifa_total = 0;
			//Recorrer tabla
			for (var i = 0; i < nfilas; i++){
				var tr = tabla_detalle.rows[i];
				horas_des[i] = tr.children[4].innerHTML * 1;
				horas_ges_dem[i] = tr.children[5].innerHTML * 1;
				total_horas_des = total_horas_des + horas_des[i];
				total_horas_ges_dem = total_horas_ges_dem + horas_ges_dem[i];
			}
			//Repartir Gestión y Configuración
			var arr_hor_cof = [];
			var arr_horas_total = [];
			var arr_monto_total = [];
			for (var i = 0; i < nfilas; i++){
				horas_ges_dem[i] = horas_ges_dem[i] + (hora_demanda/total_horas_des)*horas_des[i];		
				arr_hor_cof[i] = (horas_config/total_horas_des)*horas_des[i];
				
				arr_horas_total[i] = horas_ges_dem[i] +  arr_hor_cof[i] + horas_des[i];
				arr_monto_total[i] = arr_horas_total[i] * tarifa;
				super_total_horas = arr_horas_total[i] + super_total_horas;
				tarifa_total = tarifa_total + arr_monto_total[i];
			}
			//Actualziar tabla
			for (var i = 0; i < nfilas; i++){
				var tr = tabla_detalle.rows[i];
				tr.children[5].innerHTML = horas_ges_dem[i].toFixed(2);
				tr.children[6].innerHTML = arr_hor_cof[i].toFixed(2);
				tr.children[7].innerHTML = arr_horas_total[i].toFixed(2);
				tr.children[8].children[0].value = tarifa;
				tr.children[8].children[0].readOnly = false;
				tr.children[9].innerHTML = 'S/ ' + arr_monto_total[i].toFixed(2);
			}				
			//ACTUALIZAR DETALLE
			document.getElementById("resumen_total_horas").innerHTML = super_total_horas.toFixed(2);
			document.getElementById("resumen_total_monto").innerHTML ='S/ '+ tarifa_total.toFixed(2);
			document.getElementById("resumen_tarifa").innerHTML = 'S/ '+(tarifa*1).toFixed(2);
			//AGREGAR LOS SUBTOTALES
			var tr = document.createElement('tr');
			tr.appendChild(crearTD(""));
	    	tr.appendChild(crearTD("TOTAL"));
	    	tr.appendChild(crearTD(""));
	    	tr.appendChild(crearTD(""));
	    	tr.appendChild(crearTD(total_horas_des.toFixed(2)));
	    	tr.appendChild(crearTD((total_horas_ges_dem*1 + 1*hora_demanda).toFixed(2)));
	    	tr.appendChild(crearTD((horas_config*1).toFixed(2)));
	    	tr.appendChild(crearTD(super_total_horas.toFixed(2)));
	    	tr.appendChild(crearTD(tarifa));
	    	tr.appendChild(crearTD('S/ ' +  tarifa_total.toFixed(2)));
	    	tr.setAttribute("class","tr_totales");
	    	tabla_detalle.appendChild(tr);		
	    	//MOSTRAR BOTONES
			document.getElementById("btns_enviar_acta").style.display = "block";
			calculado = true;
		}else{
			alert("La tarifa no es correcta");
		}
	}else{
		alert("Ya se realizó un cálculo. Refresca la página!");
	}	
}
function recalcular_fila(input_tarifa){
	var tr = input_tarifa.parentNode.parentNode;
	var tabla_body = tr.parentNode;
	var total_horas = tr.children[7].innerHTML*1;
	var total_monto = total_horas * input_tarifa.value;
	tr.children[9].innerHTML = 'S/ ' + total_monto.toFixed(2);
	var j = 0;
	var total_tarifas = 0;
	for (var i = 0; i < tabla_body.rows.length - 1; i++) {
		total_tarifas = total_tarifas + (tabla_body.rows[i].children[8].firstChild.value*1)*(tabla_body.rows[i].children[7].innerHTML*1);
		j = i;
	}
	
	var tarifa_final = total_tarifas/(tabla_body.rows[j+1].children[7].innerHTML*1);
	tabla_body.rows[j+1].children[8].innerHTML = tarifa_final.toFixed(2);
	var total_importe = (tarifa_final*(tabla_body.rows[j+1].children[7].innerHTML*1)).toFixed(2);
	tabla_body.rows[j+1].children[9].innerHTML = 'S/ ' + total_importe;
}
function cambiovalor(){
	document.getElementById("btns_enviar_acta").style.display = "none";
}
// ------------------ BUSCAR JIRAS PRE PARA EL DETALLE
function buscarJiras(){	
	//Reiniciando el lienzo
	calculado = false;
	document.getElementById("resumen_total_horas").innerHTML = '0';
	document.getElementById("resumen_total_monto").innerHTML ='S/ 0.00';
	document.getElementById("resumen_tarifa").innerHTML = 'S/ 0.00';
	
	document.getElementById("inp_dem").value = '';
	document.getElementById("inp_conf").value ='';
	document.getElementById("inp_tarifa").value = '';
	
	document.getElementById("btns_enviar_acta").style.display = "none";
	//Funcion calcular
	var mensaje =  document.getElementById("mensaje_resultado");
	var objjson = {};
	objjson.numero1 = document.getElementById("select_periodo").value*1; 	//PERIODO
	objjson.numero2 = document.getElementById("acta_tipo_id").value*1; 		//TIPO
	objjson.numero3 = document.getElementById("acta_cliente_id").value*1; 	//EMPRESA
	var data = JSON.stringify(objjson); 
	var cuerpo_tabla = document.getElementById("cuerpo_tabla");	
	var todos_cc = true; //Flag para validar que todos los jiras tegan centro de costo
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
		        		tr.appendChild(crearTD(respuesta[i].jira));
			        	tr.appendChild(crearTD(respuesta[i].resumen.substring(0,40)+'...'));
			        	tr.appendChild(crearTD(respuesta[i].tipoJira));
			        	var c_c;
			        	if (respuesta[i].centrocosto == '' || respuesta[i].centrocosto == null){
			        		c_c = 'No definido.';
			        		document.getElementById('id_mensaje_cc').classList.remove('dis_hiden');
			        		todos_cc = false;
			        	}else{
			        		c_c = respuesta[i].centrocosto;
			        	}
			        	tr.appendChild(crearTD(c_c));
			        	tr.appendChild(crearTD(respuesta[i].totalHoras));
			        	tr.appendChild(crearTD(respuesta[i].totalHorasGesDem));
			        	tr.appendChild(crearTD("0"));
			        	tr.appendChild(crearTD("0"));
			        	
			        	tr.appendChild(crearInput());
			        	tr.appendChild(crearTD("0"));	
			        	cuerpo_tabla.appendChild(tr);				        	
			        	//Mostrar calculador si es que todos los Jiras tiene Centro de Costo
			        	


			        	if (todos_cc){
				        	var calculador = document.getElementById('calculador');
				        	calculador.style.display = "flex";
			        	}
					}
	        	}else{
	        		limpiarTablaBusqueda();	
	        		mensaje.innerHTML = 'No hay actividades para ese periodo. Asegurate que no exista una acta con los mismos parámetros.';
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
		valor1 = '';
	}
	td = document.createElement('td');
	td.innerHTML = valor1;
	return td;
}
function crearInput(){
	td = document.createElement('td');
	input = document.createElement('input');
	input.setAttribute('class','form-control-muypeque');
	input.readOnly = true;
	input.setAttribute('onchange','recalcular_fila(this)');
	td.appendChild(input);
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