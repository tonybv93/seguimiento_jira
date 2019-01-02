/*--------------------  BUSCAR JIRAS ----------------------------*/
function buscar_jiras(){
	str = document.getElementById("jira_buscador").value;
	tabla = document.getElementById("tabla_buscar");
	var str_busqueda = '../../provrest/buscarxfab/jira/' + str;
	$.get(str_busqueda)
	.done(function( data ) {
		limpiarTablaBusqueda();
		if (data != null && data != ''){
			for (var i = 0; i < data.length; i++) {			
				var tr = document.createElement('tr');
				tr.setAttribute('class','tr_seleccionable');
				tr.setAttribute('onclick','seleccionar_tr(this)');				
				tr.appendChild(crearTD(data[i].jira));
				tr.appendChild(crearTD(data[i].descripcion));		
				tr.appendChild(crearTD(data[i].tipo));		
				tabla.appendChild(tr);
			}
		}else{
			var div_notfound = document.createElement('p');
			div_notfound.innerHTML = 'No se encontraron resultados';
			tabla.appendChild(div_notfound);
		}
	})
	.fail(function(){
		alert("Se produjo un error");
	})
	;
}
function limpiarTablaBusqueda(){
	document.getElementById("jira_buscador").value = "";
	a = document.getElementById("tabla_buscar");
	while(a.hasChildNodes())
		a.removeChild(a.firstChild);
}

/*--------------------  MODAL ----------------------------*/
var modal;

function modal_buscar_jira(){
	modal = document.getElementById('mi_modal');
	span = document.getElementsByClassName("close")[0];
	modal.style.display = "block";
}

function modal_confirmar(btn){
	tr = btn.parentNode.parentNode.parentNode;
	document.getElementById('mocon1').innerHTML = tr.children[2].innerHTML + ' - ' + tr.children[4].innerHTML;
	document.getElementById('mocon2').innerHTML = tr.children[7].innerHTML;
	document.getElementById('mocon3').innerHTML = tr.children[6].innerHTML;
	document.getElementById('mocon4').innerHTML = tr.children[5].innerHTML;
	document.getElementById('input_pk').value = tr.children[0].innerHTML;
	
	modal = document.getElementById('modal_confirmacion');
	span =  document.getElementsByClassName("close")[1];
	modal.style.display = "block";
}

function cerrar_modal() {
	limpiarTablaBusqueda();
	modal.style.display = "none";
}

window.onclick = function(event)	 {
if (event.target == modal) {
	limpiarTablaBusqueda();
	modal.style.display = "none";
	}
}
function seleccionar_tr(tr_seleccionado){
	inp_jira = document.getElementById('id_input');
	inp_jira.value = tr_seleccionado.children[0].innerHTML;
	limpiarTablaBusqueda();
	donut();
	modal.style.display = "none";	
}
/*------------------------- AL CARGAR LA PÁGINA ------------------*/
fecha_hoy();
cargarGraficoBarras();

//--------------------- FECHA ACTUAL POR DEFECTO
function fecha_hoy () {
	var myDate = document.getElementById("fecha_registro");
	var today = new Date();
	myDate.value = today.toISOString().substr(0, 10);
}

//------------------------------ ACTUALIZAR GRÁFICOS BARRAS -------------------------
function cargarGraficoBarras(){
	$.ajax({
        url : '/provrest/horas/semana',  	        
        contentType:'application/json',
        method : 'post',
      	data : 1,
        success : function(rep){  			
        	var t = [];
        	var f = [];
        	var e = [];
        	for (var i = 0; i < 14; i++) {
				if (rep[i].total < 9 ){
					t[i] = rep[i].total;
					f[i] = 9 - t[i];
					e[i] = 0;
				}else{
					t[i] = 9;
					f[i] = 0;
					e[i] = rep[i].total - 9;
				}
			}
        	var gbarras = c3.generate({
        		bindto : '#c3_barras',
        		data : {
        			columns : 	[ 
        					[ 'Trabajado', t[13], t[12], t[11], t[10], t[9], t[8], t[7], t[6], t[5], t[4], t[3], t[2], t[1], t[0]	], 				
        					[ 'Faltante', f[13], f[12], f[11], f[10], f[9], f[8], f[7], f[6], f[5], f[4], f[3], f[2], f[1], f[0]	], 
        					[ 'Exceso', e[13], e[12], e[11], e[10], e[9], e[8], e[7], e[6], e[5], e[4], e[3], e[2], e[1], e[0]	] 
        				],
        				type : 'bar',
        				groups: [
        			         ['Trabajado','Faltante','Exceso']
        			     ],
        			     order: 'null'
        			},
        			legend: {
        		        position: 'right'
        		    },
        			size : {
        				height : 200,
        				width : 800
        			},
        			color : {
        				pattern : [  '#2196f3', '#b1d5e2', '#c15456']
        			},
        			axis: {
        				x: {
        			    	label: {
        			    		text: 'Diciembre',
        		                position: 'outer-center',        		                
        			    	},
        			    	type: 'category',
    		                categories: [	'Lun ' + rep[13].leyenda,
		        				'Mar ' + rep[12].leyenda,
		        				'Mie ' + rep[11].leyenda,
		        				'Jue ' + rep[10].leyenda,
		        				'Vie ' + rep[9].leyenda,
		        				'Sab ' + rep[8].leyenda,
		        				'Dom ' + rep[7].leyenda,
		        				'Lun ' + rep[6].leyenda,
		        				'Mar ' + rep[5].leyenda,
		        				'Mie ' + rep[4].leyenda,
		        				'Jue ' + rep[3].leyenda,
		        				'Vie ' + rep[2].leyenda,
		        				'Sab ' + rep[1].leyenda,
		        				'Dom ' + rep[0].leyenda
		        			]
        			    },	
        			    y: {
        			    	label: {
        			    		text: 'Horas',
        			    		position: 'outer-middle'        
        			    	}
        			    }, 
        			}
        	    });
        },
        error: function(error,sm1,sm2){
        	alert("Se prodjo un error");
        	console.log(sm2);
            alert(sm1);
        }  	        
    });
		
}
//------------------------------ AGREGAR a TABLA -------------------------
function clear_alert(idalert,input){	
	if (input.value != ''){
		var alerta = document.getElementById(idalert);
		alerta.innerHTML = '';
	}
}

function agregar(){
	var tabla = document.getElementById("detalle_registros");
	var fechajs = document.getElementById("fecha_registro").value;
	var fechahoy = new Date();
	console.log(fechahoy + ' vs ' + fechajs);
	var dd = fechajs.substring(8,10);
	var mm = fechajs.substring(5,7);
	var yyyy = fechajs.substring(0,4);
	
	var fechastr = dd+'-'+mm+'-'+yyyy
	var nro_horas = document.getElementById("nro_horas").value;
	var tr = document.createElement('tr');
	var jira = document.getElementById("id_input").value.toUpperCase();
	var str_busqueda = '../../provrest/hxjiraxfab/' + jira;
	var alerta = document.getElementById("alerta_horas");
	var alertajira = document.getElementById("alerta_jira");
	var tipo_reg = document.getElementById("cbx_tipo_registro");
	var comentario = document.getElementById("txa_comentario").value;
	var resultado = document.getElementById("resultado");
	
	//Validar que el input de JIRA tenga un JIRA : [SALIR]
	if (jira == '' || jira == null || resultado.innerHTML == 'No encontrado.'){
		alertajira.innerHTML = "*Obligatorio";
		resultado.innerHTML ="";
	}else{
		//Validar que hayan horas y que estén entre 0 y 24 : [SALIR]
		if (nro_horas == 0 || nro_horas == null){
			alerta.innerHTML = "*Obligatorio";	
		}else if(nro_horas < 0.5 || nro_horas > 24){
			alerta.innerHTML = "*Las horas deben estar entre 1 y 24.";
		}else{
			//Buscar jira en la tabla HXJ (Data = Un hjira) 
			$.get(str_busqueda).done(function( data ) {	  
				//Comprobar que hayan Horas disponibles : [SALIR]
				if (data.horas_desarrollo <= nro_horas*1.1){
					alert("Las horas disponibles son menores a: " + nro_horas*1.1 +'h (Incluyendo gestión de demanda).');
				}else{
					//Si hay horas disponibles, construir el REGISTRO
					var objjson = {};
					objjson.numero1 = nro_horas;
					objjson.numero2 = tipo_reg.value;
					objjson.texto1 = jira;
					objjson.texto2 = fechajs;	
					objjson.texto3 = data.tipo;	
					objjson.texto4 = data.descripcion;	
					objjson.texto5 = comentario;					
					datajs = JSON.stringify(objjson);
					
					//Enviar el registro, se espera como respuesta el ID del registro creado en BD
					$.ajax({
					        url : '/provrest/registro/nuevo',  	        
					        contentType:'application/json',
					        method : 'post',
					      	data : datajs,
					        success : function(respuesta){
					        	//Comrpobar que la respuesta sea númérica
					        	if (!isNaN(respuesta)){
					        		//Si la respuesta es correcta, crear la fila y agregarla a la tabla
						        	tr.appendChild(crearTDoculto(respuesta));
						        	tr.appendChild(crearTD('<i class="mdi mdi-creation ico_creado"></i>'));
									tr.appendChild(crearTD(jira));
									tr.appendChild(crearTD(data.tipo));
									tr.appendChild(crearTD(data.descripcion));
									tr.appendChild(crearTD(tipo_reg.options[tipo_reg.selectedIndex].text));							
									tr.appendChild(crearTD(nro_horas));	
									tr.appendChild(crearTD(fechastr));
									tr.appendChild(crearTD(comentario));
									tr.appendChild(crearBotonX());	
									tabla.appendChild(tr);
									
									//Limpiar los campos (Reiniciar buscador)
									document.getElementById("id_input").value = '';
									document.getElementById("resultado").innerHTML = "";
						    		var div_cont = document.getElementById("c3-pie-chart1");
						    		div_cont.innerHTML = "";	
						    		document.getElementById("nro_horas").value = '';
						    		document.getElementById("cbx_tipo_registro").value = 1;
						    		document.getElementById("txa_comentario").value = '';						    		
						    			
					        	}else{
					        		alert(respuesta);
					        	}
					        },
					        error: function(error,sm1,sm2){
					        	alert("Se prodjo un error");
					        	console.log(sm2);
					            alert(sm1);
					        }  	        
					    });				
				}			
			});	
		}
	}
}

//-----------auxiliares de creacion
function crearTD(valor1){
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
function crearBotonX(){
	btn1 = document.createElement('button');
	btn2 = document.createElement('button');
	i1 = document.createElement('i');
	i1.setAttribute('class','mdi mdi-checkbox-marked-circle-outline');
	i2 = document.createElement('i');
	i2.setAttribute('class','mdi mdi-close');
	btn1.appendChild(i1);
	btn2.appendChild(i2);
	
	btn1.setAttribute('class','btn btn-icons btn-inverse-success btn-ico');
	btn1.setAttribute('onclick','modal_confirmar(this)');
	
	btn2.setAttribute('class','btn btn-icons btn-inverse-danger btn-ico');
	btn2.setAttribute('onclick','quitarElemento(this)');	
	
	td = document.createElement('td');
	contenido = document.createElement('div');
	contenido.setAttribute('class','texto_derecha');
	contenido.appendChild(btn1);
	contenido.appendChild(btn2);
	td.appendChild(contenido);
	return td;
}
function crearBotonCheck(){
	btn2 = document.createElement('button');
	i2 = document.createElement('i');
	i2.setAttribute('class','mdi mdi-checkbox-marked-circle-outline');
	btn2.appendChild(i2);
	btn2.setAttribute('class','btn btn-icons btn-inverse-success');
	btn2.setAttribute('onclick','enviarElemento(this)');		
	td = document.createElement('td');
	contenido = document.createElement('div');
	contenido.setAttribute('class','d-flex ico_centrado texto_derecha');
	contenido.appendChild(btn2);
	td.appendChild(contenido);
	return td;
}

//------------ FUNCIONES DE REGISTRO
function quitarElemento(btn){
	var fila = btn.parentNode.parentNode.parentNode;	
	id = fila.children[0].innerHTML;
	objjson = {};
	objjson.numero1 = id;	//id del registro
	data = JSON.stringify(objjson);
	$.ajax({
	        url : '/provrest/registro/eliminar',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){
	        	if (document.getElementById("id_input").value.toUpperCase() != ''){
	        		donut();
	        	}
	        	fila.parentNode.removeChild(fila);
	        },
	        error: function(error,sm1,sm2){
	        	alert("Se prodjo un error");
	        	console.log(sm2);
	            alert(sm1);
	        }  	        
	    });
	
	
}

function enviarElemento(btn){
	var fila = btn.parentNode.parentNode.parentNode;	
	id = fila.children[0].innerHTML;
	objjson = {};
	objjson.numero1 = id;	//Id del registro
	objjson.numero2 = 2;	//Estado nuevo (2 = confirmado)
	data = JSON.stringify(objjson);
	$.ajax({
	        url : '/provrest/registro/cambiarestador',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){
	        },
	        error: function(error,sm1,sm2){
	        	alert("Se prodjo un error");
	        	console.log(sm2);
	            alert(sm1);
	        }  	        
	    });	
	fila.parentNode.removeChild(fila);
}

//------------------------------ ACTUALIZAR GRÁFICOS DONUT -------------------------
//---- INPUT JIRA
//Cambiar Donut cuando cambie el valor de input jira
$( function() {
    $("#id_input").change( function() {
    	var jira = document.getElementById("id_input").value.toUpperCase();
    	if (jira!=''){
    		var str_busqueda = '../../provrest/hxjiraxfab/' + jira    	
        	$.get(str_busqueda).done(function( data ) {	 
        		if (data != null && data !=''){
        			cargarGraficoDonut(data.horas_desarrollo,data.consumido_desarrollo); 
        			document.getElementById("resultado").innerHTML = data.jira + ': ' +  data.descripcion;
        		}else{
        			document.getElementById("resultado").innerHTML = "No encontrado.";
            		var div_cont = document.getElementById("c3-pie-chart1");
        			div_cont.innerHTML = "No encontrado.";
        		}	
        	})
        	.fail(function () {
        		document.getElementById("resultado").innerHTML = "No encontrado.";
        		var div_cont = document.getElementById("c3-pie-chart1");
        		div_cont.innerHTML = "No encontrado.";	
            });    		
    	}else{
    		document.getElementById("resultado").innerHTML = "";
    		var div_cont = document.getElementById("c3-pie-chart1");
    		div_cont.innerHTML = "";
    	}
    	
    		
    });
});
//Invocar manualmente el cambio de donut
function donut(){
	var jira = document.getElementById("id_input").value.toUpperCase();
	var str_busqueda = '../../provrest/hxjiraxfab/' + jira    	
	$.get(str_busqueda).done(function( data ) {	  		
		if (data != null){
			cargarGraficoDonut(data.horas_desarrollo,data.consumido_desarrollo); 
			document.getElementById("resultado").innerHTML = data.descripcion;
		}	   		
	})
	.fail(function () {
		document.getElementById("resultado").innerHTML = "No existe";
    });
}
//Armar el donut
function cargarGraficoDonut(totales,consumidos){
	if (totales != 0 && totales != null){
		var restantes = totales - consumidos;
	    var c3PieChart = c3.generate({
		bindto : '#c3-pie-chart1',
		data : {
			columns : 
				[ [ 'Consumidas', consumidos ], 				
				[ 'Restantes', restantes ] ],
			type : 'donut',
			onclick : function(d, i) {				
			},		
			onmouseover : function(d, i) {
			},
			onmouseout : function(d, i) {
			}
		},
		donut: { 
			width: 50,
			label: {
				format: function(val) { return val; }
			}
		},
		size : {
			height : 200,
			width : 300
		},
		color : {
			pattern : [  '#2196f3', '#b1d5e2'
					 ]
		},
		padding : {
			top : 0,
			right : 0,
			bottom : 0,
			left : 0,
		}
	    });
	}else{
		var div_cont = document.getElementById("c3-pie-chart1");
		div_cont.innerHTML = "No existe data para mostrar en este requerimiento. No se han aprobado horas de desarrollo.";			
	}			
}
