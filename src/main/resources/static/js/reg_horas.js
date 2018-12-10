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
var contador = 0;
function agregar(){
	var tabla = document.getElementById("detalle_registros");
	var fecha = new Date().toLocaleDateString("es-ES");	
	var periodo = document.getElementById("periodo").value
	var nro_horas = document.getElementById("nro_horas").value
	var tr = document.createElement('tr');
	var jira = document.getElementById("id_input").value;
	var str_busqueda = '../../rest/jira/' + jira
	$.get(str_busqueda).done(function( data ) {	  
		contador++;
		tr.appendChild(crearTD(contador));
		tr.appendChild(crearTD(jira));
		tr.appendChild(crearTD(data.resumen));
		tr.appendChild(crearTD(data.horas_des));
		tr.appendChild(crearTD(periodo));
		tr.appendChild(crearTD(fecha));
		tr.appendChild(crearTD(nro_horas));
		tr.appendChild(crearTD(data.fabrica.tarifa));
		tr.appendChild(crearTD(data.fabrica.tarifa*nro_horas));
		tr.appendChild(crearBotonX());
		
		  
		tabla.appendChild(tr);	  
	});	
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
function quitarElemento(btn){
	var fila = btn.parentNode.parentNode.parentNode;
	fila.parentNode.removeChild(fila);
}

//------------------------------ ACTUALIZAR GRÁFICOS DONUT -------------------------

$( function() {
    $("#id_input").change( function() {
    	var jira = document.getElementById("id_input").value;
    	var str_busqueda = '../../rest/jira/' + jira    	
    	$.get(str_busqueda).done(function( data ) {	  
    		cargarGrafico(data.horas_des,4);    		
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
cargarGraficoBarras();

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