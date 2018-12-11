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
    var gbarras = c3.generate({
	bindto : '#c3_barras',
	data : {
		columns : 	[ 
			[ 'Trabajado', 2,10,6,2,10,0, 10,10,6,0,0,0 ], 				
			[ 'Faltante', 8,0,4, 8,0,10, 0,0,4,10,10,10 ], 
			[ 'Exceso',0,2,0,0,2,0,3,1,0,0,0,0], 
		],
		type : 'bar',
		groups: [
	         ['Trabajado','Faltante','Exceso']
	     ],
	     order: 'null'
	},	 
	size : {
		height : 200,
		width : 700
	},
	color : {
		pattern : [  '#2196f3', '#b1d5e2', '#c15456']
	},
	axis: {
	    x: {
	        type: 'category',
	        categories: ['lun', 'mar','mie', 'jue','vie', 'sab','lun', 'mar','mie', 'jue','vie', 'sab']
	    }
	},
	padding : {
		top : 0,
		right : 0,
		bottom : 0,
		left : 0,
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
	var fecha = document.getElementById("fecha_registro").value;	
	var nro_horas = document.getElementById("nro_horas").value;
	var tr = document.createElement('tr');
	var jira = document.getElementById("id_input").value;
	var str_busqueda = '../../rest/jira/' + jira;
	var alerta = document.getElementById("alerta_horas");
	
	if (nro_horas == 0 || nro_horas == null){
		alerta.innerHTML = "*Campo obligatorio";	
	}else{
		$.get(str_busqueda).done(function( data ) {	  
			tr.appendChild(crearTD(jira));
			tr.appendChild(crearTD(data.tipoRequerimiento.nombre));
			tr.appendChild(crearTD(data.resumen));
			tr.appendChild(crearTD(fecha));
			tr.appendChild(crearTD(data.horas_des));			
			tr.appendChild(crearTD(nro_horas));
			tr.appendChild(crearBotonX());		
			tr.appendChild(crearBotonCheck());		
			tabla.appendChild(tr);		
			
			var objjson = {};
			objjson.numero1 = nro_horas;
			objjson.texto1 = jira;
			objjson.texto2 = fecha;	
			objjson.texto3 = data.tipoRequerimiento.nombre;	
			objjson.texto4 = data.resumen;	
			data = JSON.stringify(objjson);
			$.ajax({
			        url : '/rest/registrhoras',  	        
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
		});	
	}
}

function crearTD(valor1){
		td = document.createElement('td');
		td.innerHTML = valor1;
		return td;
	}
function crearBotonX(){
	btn2 = document.createElement('button');
	i2 = document.createElement('i');
	i2.setAttribute('class','mdi mdi-close');
	btn2.appendChild(i2);
	btn2.setAttribute('class','btn btn-icons btn-inverse-warning');
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
	fila.parentNode.removeChild(fila);
}
function enviarElemento(btn){
	var fila = btn.parentNode.parentNode.parentNode;
	fila.parentNode.removeChild(fila);
}

//------------------------------ ACTUALIZAR GRÁFICOS DONUT -------------------------

$( function() {
    $("#id_input").change( function() {
    	var jira = document.getElementById("id_input").value;
    	var str_busqueda = '../../rest/jira/' + jira    	
    	$.get(str_busqueda).done(function( data ) {	  
    		cargarGrafico(data.horas_des,0);    		
    	});	
    });
});

function cargarGrafico(totales,consumidos){
	if (consumidos > totales ){
		restantes = 0;
		exceso = consumidos - totales;
	}else{
		restantes = totales - consumidos;
		exceso = 0;
	}
    var c3PieChart = c3.generate({
	bindto : '#c3-pie-chart1',
	data : {
		columns : 
			[ [ 'Consumidas', consumidos ], 				
			[ 'Restantes', restantes ], 
			[ 'Exceso',exceso], ],
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
		pattern : [  '#2196f3', '#5abcf1', '#ff6258'
				 ]
	},
	padding : {
		top : 0,
		right : 0,
		bottom : 0,
		left : 0,
	}
});		
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






