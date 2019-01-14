
function exportarTabla(){
	var fechani = document.getElementById('fechaini').value;
	var fechafin = document.getElementById('fechafin').value;
	objjson = {};
	objjson.texto1 = fechani;
	objjson.texto2 = fechafin;
	data = JSON.stringify(objjson);
	$.ajax({
	        url : '/provrest/horasporfabrica/entrefechas',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){
	        	lst = [];	        	
	        	for (var i = 0; i < respuesta.length; i++) {
	        		json = {};
	        		json.usuario = respuesta[i].usuario.descripcion;
	        		json.fecha_registro = respuesta[i].fecha_real_trabajo.substring(0,10);
	        		json.fecha_trabajo = respuesta[i].fecha_real_trabajo.substring(0,10);
	        		json.tipo_tarea = respuesta[i].tipoActividad.descripcion;
	        		json.descripcion = respuesta[i].comentario;
	        		if (respuesta[i].flagfacturar){
	        			json.facturable = 'Sí';
	        		}else{
	        			json.facturable = 'No';
	        		}
	        		
	        		json.horas_trabajo = respuesta[i].nro_horas;
	        		json.horas_gest_dem = respuesta[i].nro_horas;
	        		json.jira = respuesta[i].hjira.jira;	
	        		json.jira_detalle = respuesta[i].hjira.descripcion;
	        		json.cliente = respuesta[i].hjira.empresa.nombre;	        		
	        		lst[i] = json;
				}	        	
	        	$("#dvjson").excelexportjs({
	        		  containerid: "dvjson", 
	        		  datatype: 'json', 
	        		  dataset: lst, 
	        		  columns: getColumns(lst)     
	        		});
	        },
  	        error: function(error,sm1,sm2){
  	        	alert("Se prodjo un error");
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

function filtrarTabla(){
	var fechani = document.getElementById('fechaini').value;
	var fechafin = document.getElementById('fechafin').value;
	objjson = {};
	objjson.texto1 = fechani;
	objjson.texto2 = fechafin;
	data = JSON.stringify(objjson);
	$.ajax({
	        url : '/provrest/horasporfabrica/entrefechas',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){    
	        	tabla_detalle = document.getElementById('detalle_registros');
	        	for (var i = 0; i < respuesta.length; i++) {
	        		var tr = document.createElement('tr');
	    			tr.appendChild(crearTD(respuesta[i].usuario.descripcion));
	    			tr.appendChild(crearTD(respuesta[i].fecha_real_trabajo.substring(0,10)));
	    			tr.appendChild(crearTD(respuesta[i].nro_horas));
	    			tr.appendChild(crearTD(respuesta[i].hjira.jira));
	    			tr.appendChild(crearTD(respuesta[i].hjira.descripcion));
	    			tr.appendChild(crearTD(respuesta[i].tipoActividad.descripcion));
	    			tr.appendChild(crearTD(respuesta[i].hjira.empresa.nombre));
	    			tabla_detalle.appendChild(tr);	
	        	}
	        },
  	        error: function(error,sm1,sm2){
  	        	alert("Se prodjo un error");
  	        	console.log(sm2);
  	            alert(sm1);
  	        }  	        
  	    });
}