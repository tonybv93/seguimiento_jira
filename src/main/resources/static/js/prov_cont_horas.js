/*--------------------  MODAL ----------------------------*/
var modal;

function modal_buscar_jira(){
	modal = document.getElementById('mi_modal');
	span = document.getElementsByClassName("close")[0];
	modal.style.display = "block";
}

function modal_confirmar(btn,opcion){
	var boton_confirmar = document.getElementById('btn_confirmar_horas');
	tr = btn.parentNode.parentNode.parentNode;
	console.log(tr);
	id = tr.children[0].innerHTML;
	
	if (opcion == 1){
		document.getElementById('titulo_modal_confirmacion').innerHTML = 'Confirmar registro de actividad';
		boton_confirmar.setAttribute('class','btn btn-inverse-success');
		boton_confirmar.innerHTML = 'Aprobar';
	}else{
		document.getElementById('titulo_modal_confirmacion').innerHTML = 'Rechazar registro de actividad';
		boton_confirmar.setAttribute('class','btn btn-inverse-danger');
		boton_confirmar.innerHTML = 'Rechazar';
	}
	
	document.getElementById('mocon1').innerHTML = tr.children[6].innerHTML + ' - ' + tr.children[3].innerHTML;
	document.getElementById('mocon2').innerHTML = tr.children[5].innerHTML;
	document.getElementById('mocon3').innerHTML = tr.children[3].innerHTML;
	
	var desarr_select = document.getElementById('filtro_desarrollador');
	document.getElementById('mocon4').innerHTML = desarr_select.options[desarr_select.selectedIndex].text;		
	
	
	boton_confirmar.setAttribute('onclick','cambiar_estado('+id+','+opcion+')')
	
	
	modal = document.getElementById('modal_confirmacion');
	span =  document.getElementsByClassName("close")[1];
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

function seleccionar_tr(tr_seleccionado){
	inp_jira = document.getElementById('id_input');
	inp_jira.value = tr_seleccionado.children[0].innerHTML;
	limpiarTablaBusqueda();
	donut();
	modal.style.display = "none";	
}

//------------------------------ acciones -------------------------

//Cambiar estado
function cambiar_estado(id,id_nuevo_estado,index_fila){
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
	location.reload();	
	//fila.parentNode.removeChild(index_fila);
	//modal.style.display = "none";
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
        				onmouseover: function (d, i) { 
        					var datos = rep[13 - d.x].fecha;            					
        					var tabla = document.getElementById("detalle_registros");        					
        					for (var i = 0; i < tabla.rows.length; i++) {         					
        						if (datos.substring(0, 10) == tabla.rows[i].children[5].innerHTML) {
        							 tabla.children[i].classList.add('fondo_rojo');
								}							
        					}
        					},
        				onmouseout: function () { 
        					var tabla = document.getElementById("detalle_registros"); 
        					for (var i = 0; i < tabla.rows.length; i++) {         						
        						tabla.children[i].classList.remove('fondo_rojo');
        					}
											
        				},
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
        url : '/provrest/registros/semana/desarrollador',  	        
        contentType:'application/json',
        method : 'post',
      	data : data,	 //La misma información del gráfico de barras
        success : function(rep){  
        	var tabla = document.getElementById("detalle_registros");
        	limpiarTabla(tabla);
        	for (var i = 0; i < rep.length; i++) {        		
        		var tr = document.createElement('tr');
        		tr.appendChild(crearTDoculto(rep[i].id));        		
        		tr.appendChild(crearTD(rep[i].tipoActividad.descripcion));
        		tr.appendChild(crearTD(rep[i].flagfacturar));
        		tr.appendChild(crearTD(rep[i].nro_horas));
        		tr.appendChild(crearTD(rep[i].fecha_registro.substring(0, 10)));
        		tr.appendChild(crearTD(rep[i].fecha_real_trabajo.substring(0, 10)));
        		tr.appendChild(crearTD(rep[i].hjira.jira));  
        		tr.appendChild(crearTD(rep[i].hjira.empresa.nombre));   
        		tr.appendChild(crearTD(rep[i].hjira.indicador.indicador));
        		tr.appendChild(crearTD(rep[i].comentario));
        		tr.appendChild(crearBotonX());
        		tabla.appendChild(tr);        		
			}
        	
        }
	});
		
}
/*------------------------- AL CARGAR LA PÁGINA ------------------*/
cargarGraficoBarras();
/*---------------------------- AUXILIAR ------------------------*/
function crearTD(valor1){
	td = document.createElement('td');
	td.innerHTML = valor1;
	return td;
}
function limpiarTabla(a){
	while(a.hasChildNodes())
		a.removeChild(a.firstChild);
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
	btn1.setAttribute('onclick','modal_confirmar(this,1)');
	
	btn2.setAttribute('class','btn btn-icons btn-inverse-danger btn-ico');
	btn2.setAttribute('onclick','modal_confirmar(this,4)');	
	
	td = document.createElement('td');
	contenido = document.createElement('div');
	contenido.setAttribute('class','texto_derecha');
	contenido.appendChild(btn1);
	contenido.appendChild(btn2);
	td.appendChild(contenido);
	return td;
}

//------------------------------
function prueba(){
	var elemento = document.getElementsByClassName("c3-shapes c3-shapes-Trabajado c3-bars c3-bars-Trabajado");
	elemento[0].children[3].style.fill = "#12c03b";
}
function prueba2(){
	var elemento = document.getElementsByClassName("c3-shapes c3-shapes-Trabajado c3-bars c3-bars-Trabajado");
	elemento[0].children[3].style.fill = "rgb(33, 150, 243)";
}
function prueba3(){
	var elemento = document.getElementsByClassName("c3-xgrid-focus");
}
