/*--------------------  BLOCK ----------------------------*/
var barrita_xc = document.getElementById('barrita_porcentaje').innerHTML;
var barrita_graf = document.getElementById('barrita_graf'); 
console.log(barrita_xc);
barrita_graf.style.width=barrita_xc;

/*--------------------  MODAL ----------------------------*/
var modal;
var dias_por_mes = [31,28,31,30,31,30,31,31,30,31,30,31];
var nombres_mes = ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Setiembre','Octubre','Noviembre','Diciembre'];


function modal_buscar_jira(){
	modal = document.getElementById('mi_modal');
	span = document.getElementsByClassName("close")[0];
	modal.style.display = "block";
}

function modal_confirmar(btn,opcion){
	var boton_confirmar = document.getElementById('btn_confirmar_horas');
	tr = btn.parentNode.parentNode.parentNode;
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
	
	document.getElementById('mocon1').innerHTML = tr.children[7].innerHTML;
	document.getElementById('mocon2').innerHTML = tr.children[6].innerHTML;
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
	        	location.reload();
	        },
	        error: function(error,sm1,sm2){
	        	alert("Se prodjo un error");
	        	console.log(sm2);
	            alert(sm1);
	        }  	        
	    });
		
	//fila.parentNode.removeChild(index_fila);
	//modal.style.display = "none";
}

//----------------------------- FILTRAR POR DESARROLLADOR
function filtrar(){
	cargarGraficoBarras();
}
//----------------------------- FILTRAR POR PERIODO
function filtrar_P(){
	cargarGraficoBarras();
	cargarGestionDemanda();
}

//------------------------------ ACTUALIZAR GRÁFICOS BARRAS -------------------------
function cargarGraficoBarras(){
	var id_des = document.getElementById('filtro_desarrollador').value;
	var periodo = document.getElementById('filtro_periodo').value;
	objjson = {};
	objjson.numero1 = id_des;	//Id de desarrollador
	objjson.texto1 = periodo;	//Id de PERIODO
	data = JSON.stringify(objjson);
	dia = periodo.substring(4,6) * 1;
	
	// Arreglos datos
	arreglo_1 = [];
	arreglo_1[0] = 'Trabajado';
	
	arreglo_2 = [];
	arreglo_2[0] = 'Faltante';
	
	arreglo_3 = [];
	arreglo_3[0] = 'Exceso';
	
	categorias = [];
	
	$.ajax({
        url : '/provrest/horas/mes/desarrollador',  	        
        contentType:'application/json',
        method : 'post',
      	data : data,
        success : function(rep){  	
        	var t = [];
        	var f = [];
        	var e = [];
        	for (var i = 0; i < dias_por_mes[dia - 1]; i++) {
				if (rep[i].total < 9 ){
					t[i] = rep[i].total;
					f[i] = 9 - t[i];
					e[i] = 0;
				}else{
					t[i] = 9;
					f[i] = 0;
					e[i] = rep[i].total - 9;
				}
				
				arreglo_1[i+1] = t[i];
				arreglo_2[i+1] = f[i];
				arreglo_3[i+1] = e[i];
				categorias[i] = rep[i].leyenda;
			}
        	var gbarras = c3.generate({
        		bindto : '#c3_barras',        		
        		data : {
        			columns : 	[ 
        				arreglo_1,arreglo_2,arreglo_3
        				],
        				type : 'bar',
        				onmouseover: function (d, i) { 
        					var datos = rep[d.x].fecha;            					
        					var tabla = document.getElementById("detalle_registros");   
        					for (var i = 0; i < tabla.rows.length; i++) {   
        						if (datos.substring(0, 10) == tabla.rows[i].children[6].innerHTML) {        							
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
        		        position: 'bottom'
        		    },
        			size : {
        				height : 200,
        				width : 850
        			},
        			color : {
        				pattern : [  '#2196f3', '#b1d5e2', '#c15456']
        			},
        			axis: { 
        				x: {
        			    	label: {
        			    		text: nombres_mes[dia - 1],
        		                position: 'outer-center',        		                
        			    	},
        			    	type: 'category',
    		                categories: 
    		                	categorias
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
//------------------------------ ACTUALIZAR LISTA 1
	$.ajax({
        url : '/provrest/registros/semana/desarrollador',  	        
        contentType:'application/json',
        method : 'post',
      	data : data,	 //La misma información del gráfico de barras
        success : function(rep){  
        	console.log(rep);
        	var strfact;
        	var tabla = document.getElementById("detalle_registros");
        	limpiarTabla(tabla);
        	for (var i = 0; i < rep.length; i++) {        		
        		var tr = document.createElement('tr');
        		tr.appendChild(crearTDoculto(rep[i].id));        		
        		tr.appendChild(crearTD(rep[i].tipoActividad.descripcion));
        		if (rep[i].flagfacturar == true){
        			strfact='Sí';
        		}else{
        			strfact='No';
        		}        		
        		tr.appendChild(crearTD(strfact));
        		tr.appendChild(crearTD(rep[i].nro_horas));
        		tr.appendChild(crearTD(rep[i].nro_horas_gestion));
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
	
	//------------------------------ ACTUALIZAR LISTA 2

}
/*-----------------------------------------------------------*/
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
