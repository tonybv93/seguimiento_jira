/*--------------------  BUSCAR JIRAS ----------------------------*/
function buscar_jiras(){
	str = document.getElementById("jira_buscador").value;
	tabla = document.getElementById("tabla_buscar");
	var str_busqueda = '../../rest/buscar/jira/' + str;
	$.get(str_busqueda).done(function( data ) {
		limpiarTablaBusqueda();
		for (var i = 0; i < data.length; i++) {			
			var tr = document.createElement('tr');
			tr.setAttribute('class','tr_seleccionable');
			tr.setAttribute('onclick','seleccionar_tr(this)');
			
			tr.appendChild(crearTD(data[i].jira));
			tr.appendChild(crearTD(data[i].descripcion));		
			tr.appendChild(crearTD(data[i].tipo));		
			tabla.appendChild(tr);
		}		
	});
}
function limpiarTablaBusqueda(){
	document.getElementById("jira_buscador").value = "";
	a = document.getElementById("tabla_buscar");
	while(a.hasChildNodes())
		a.removeChild(a.firstChild);
}

/*--------------------  MODAL ----------------------------*/
function modal_buscar_jira(){
	modal.style.display = "block";
}
var span = document.getElementsByClassName("close")[0];
var modal = document.getElementById('mi_modal');

span.onclick = function() {
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
        url : '/rest/horas/semana',  	        
        contentType:'application/json',
        method : 'post',
      	data : 1,
        success : function(rep){  			
        	var t = [];
        	var f = [];
        	var e = [];
        	for (var i = 0; i < 14; i++) {
				if (rep[i].total < 8 ){
					t[i] = rep[i].total;
					f[i] = 8 - t[i];
					e[i] = 0;
				}else{
					t[i] = 8;
					f[i] = 0;
					e[i] = rep[i].total - 8;
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
//------------------------------ AGREGAR TABLA -------------------------
function clear_alert(idalert,input){	
	var alerta = document.getElementById(idalert);
	alerta.innerHTML = '';
}

function agregar(){
	var tabla = document.getElementById("detalle_registros");
	var fechajs = document.getElementById("fecha_registro").value;
	
	var dd = fechajs.substring(8,10);
	var mm = fechajs.substring(5,7);
	var yyyy = fechajs.substring(0,4);
	var fechastr = dd+'-'+mm+'-'+yyyy
	var nro_horas = document.getElementById("nro_horas").value;
	var tr = document.createElement('tr');
	var jira = document.getElementById("id_input").value;
	var str_busqueda = '../../rest/hxjira/' + jira;
	var alerta = document.getElementById("alerta_horas");
	
	if (nro_horas == 0 || nro_horas == null){
		alerta.innerHTML = "*Campo obligatorio";	
	}else{
		$.get(str_busqueda).done(function( data ) {	  
			if (data.horas_desarrollo < nro_horas){
				alert("No hay horas disponibles");
			}else{
				var objjson = {};
				objjson.numero1 = nro_horas;
				objjson.texto1 = jira;
				objjson.texto2 = fechajs;	
				objjson.texto3 = data.tipo;	
				objjson.texto4 = data.descripcion;	
				
				datajs = JSON.stringify(objjson);
				$.ajax({
				        url : '/rest/registrhoras',  	        
				        contentType:'application/json',
				        method : 'post',
				      	data : datajs,
				        success : function(respuesta){
				        	console.log(respuesta);
				        	tr.appendChild(crearTDoculto(respuesta));
							tr.appendChild(crearTD(jira));
							tr.appendChild(crearTD(data.tipo));
							tr.appendChild(crearTD(data.descripcion));
							tr.appendChild(crearTD(fechastr));			
							tr.appendChild(crearTD(nro_horas));
							tr.appendChild(crearTD("Creado"));
							tr.appendChild(crearBotonX());		
							tr.appendChild(crearBotonCheck());		
							tabla.appendChild(tr);	
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
	btn2 = document.createElement('button');
	i2 = document.createElement('i');
	i2.setAttribute('class','mdi mdi-close');
	btn2.appendChild(i2);
	btn2.setAttribute('class','btn btn-icons btn-inverse-danger');
	btn2.setAttribute('onclick','quitarElemento(this)');		
	td = document.createElement('td');
	contenido = document.createElement('div');
	contenido.setAttribute('class','d-flex ico_centrado texto_derecha');
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
function quitarElemento(btn){
	var fila = btn.parentNode.parentNode.parentNode;	
	id = fila.children[0].innerHTML;
	console.log(id);
	objjson = {};
	objjson.numero1 = id;
	data = JSON.stringify(objjson);
	$.ajax({
	        url : '/rest/registro/eliminar',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){
	        	console.log(respuesta);
	        },
	        error: function(error,sm1,sm2){
	        	alert("Se prodjo un error");
	        	console.log(sm2);
	            alert(sm1);
	        }  	        
	    });
	
	fila.parentNode.removeChild(fila);
}


function enviarElemento(btn){
	var fila = btn.parentNode.parentNode.parentNode;	
	id = fila.children[0].innerHTML;
	console.log(id);
	objjson = {};
	objjson.numero1 = id;
	data = JSON.stringify(objjson);
	$.ajax({
	        url : '/rest/registro/confirmar',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){
	        	console.log(respuesta);
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

$( function() {
    $("#id_input").change( function() {
    	var jira = document.getElementById("id_input").value;
    	var str_busqueda = '../../rest/hxjira/' + jira    	
    	$.get(str_busqueda).done(function( data ) {	  
    		if (data != null){
    			cargarGraficoDonut(data.horas_desarrollo,data.consumido_desarrollo); 
    			document.getElementById("resultado").innerHTML = data.descripcion;
    		}	   		
    	})
    	.fail(function () {
    		document.getElementById("resultado").innerHTML = "No encontrado.";
        });	
    });
});

function donut(){
	var jira = document.getElementById("id_input").value;
	var str_busqueda = '../../rest/hxjira/' + jira    	
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
		div_cont.innerHTML = "No existe data para mostrar en este requerimiento. No se han aprobado horas de dsarrollo.";			
	}			
}


/*--------------------  INPUT2 ----------------------------*/

$(document).ready(function() {
    $('.js-example-basic-single').select2();
});

$( function() {
    $("#id_categoria").change( function() {
        if ($(this).val() == 1) {        	
        	 $("#jira_selector").show();
        	 $("#area_selector").hide();
        	 
            $("#id_input").prop("disabled", false);
            $("#id_input2").prop("disabled", true);
            $("#id_input2").val("");
        } else {              
            $("#jira_selector").hide();
            $("#area_selector").show();
            
            $("#id_input").prop("disabled", true);  
            $("#id_input2").prop("disabled", false);
            $("#id_input2").val("Otros");
        }
    });
});






