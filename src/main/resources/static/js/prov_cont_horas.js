/*--------------------  MODAL ----------------------------*/
var modal;

function modal_buscar_jira(){
	modal = document.getElementById('mi_modal');
	span = document.getElementsByClassName("close")[0];
	modal.style.display = "block";
}

function modal_confirmar(btn){
	tr = btn.parentNode.parentNode.parentNode;
	document.getElementById('mocon1').innerHTML = tr.children[1].innerHTML + ' - ' + tr.children[3].innerHTML;
	document.getElementById('mocon2').innerHTML = tr.children[4].innerHTML;
	document.getElementById('mocon3').innerHTML = tr.children[5].innerHTML;
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

//------------------------------ acciones -------------------------

//Cambiar estado
function cambiar_estado(btn,id_nuevo_estado){
	var fila = btn.parentNode.parentNode.parentNode;	
	id = fila.children[0].innerHTML;
	objjson = {};
	//Cambiar estado
	objjson.numero1 = id;	//Id de registro
	objjson.numero2 = id_nuevo_estado; 	//ID de nuevo estado
	data = JSON.stringify(objjson);
	$.ajax({
	        url : '/provrest/registro/cambiarestado',  	        
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

//----------------------------- FILTRAR POR DESARROLLADOR
function filtrar(){
	cargarGraficoBarras();
}

//------------------------------ ACTUALIZAR GRÁFICOS BARRAS -------------------------
function cargarGraficoBarras(){
	var id_des = document.getElementById('filtro_desarrollador').value;
	objjson = {};
	objjson.numero1 = id_des;	//Id de desarrollador
	data = JSON.stringify(objjson);
	$.ajax({
        url : '/provrest/horas/semana/desarrollador',  	        
        contentType:'application/json',
        method : 'post',
      	data : data,
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
//------------------------------ ACTUALIZAR LISTA
	$.ajax({
        url : '/provrest/horas/semana/desarrollador',  	        
        contentType:'application/json',
        method : 'post',
      	data : data,	 //La misma información del gráfico de barras
        success : function(rep){  
        	
        }
	});
		
}
/*------------------------- AL CARGAR LA PÁGINA ------------------*/
cargarGraficoBarras();

