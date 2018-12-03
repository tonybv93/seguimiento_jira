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


//=============== 
	function crearCampo(nombre,valor1){
  		td = document.createElement('td');
  		td.innerHTML = valor1;
  		return td;
  	}
	function crearIcono(valor1){
  		td = document.createElement('td');
  		i = document.createElement('i');
  		i.setAttribute('class',valor1 + ' icon_acu_col');
  		td.appendChild(i);  		
  		return td;
  	}
    function crearEstado(nombre,valor1){
  		td = document.createElement('td');
  		spn = document.createElement('span');
  		spn.setAttribute('class','est_aprob');
  		spn.innerHTML = valor1;  			
  		td.appendChild(spn);
  		return td;
  	}
    function crearFecha(clase,valor1){
  		td = document.createElement('td');
  		spn = document.createElement('span');
  		spn.setAttribute('class',clase);
  		spn.innerHTML = valor1;  			
  		td.appendChild(spn);
  		return td;
  	}
    function crearBotones(nombre){
    	btn1 = document.createElement('button');
    	i1 = document.createElement('i');
    	i1.setAttribute('class','mdi mdi-check');
    	btn1.appendChild(i1);
    	btn1.setAttribute('class','btn btn-icons btn-inverse-success');
    	btn1.setAttribute('onclick','confirm_terminar(this,1)');
    	
    	btn2 = document.createElement('button');
  		i2 = document.createElement('i');
  		i2.setAttribute('class','mdi mdi-close');
  		btn2.appendChild(i2);
  		btn2.setAttribute('class','btn btn-icons btn-inverse-warning');
  		btn2.setAttribute('onclick','confirm_terminar(this,2)');
  		
    	td = document.createElement('td');
		contenido = document.createElement('div');
		contenido.setAttribute('class','d-flex ico_centrado texto_derecha');
		contenido.appendChild(btn1);
		contenido.appendChild(btn2);
  		td.appendChild(contenido);
  		return td;
  	}	  

	  function confirm_terminar(boton,ind) {
		tipo_btn = ind;
		var fila = boton.parentNode.parentNode.parentNode;
		objjson.id = parseInt(fila.children[0].innerHTML);
		fila_accion = fila;
		modal.style.display = "block";
	  }
	  
	  function agregar_acuerdo(){
		  if (validar_nuevo_acuerdo() == 1){
			  
		objjson.numero1 = document.getElementById("id_categoria").value;	//Tipo
		objjson.numero2 = document.getElementById("id_input2").value;		//Area		
		objjson.numero3 = document.getElementById("responsable").value;		//Responsable
		objjson.numero4 = document.getElementById("id_input").value;		//Jira
		objjson.texto1 = document.getElementById("fecha_entrega").value;	//Fecha
		objjson.texto2 = document.getElementById("exampleTextarea1").value; 	//Acuerdo
		  
		  if (objjson.tipo == "Otros")
			  objjson.jira = null;
		  else if (objjson.tipo == "Jira")
			  objjson.area = null;
		  data = JSON.stringify(objjson);
		  
		  console.log(data);
		  objjson = {};
		//REALIZAR PETICION
		  	$.ajax({
	  	        url : '/rest/acuerdo/nuevo',  	        
	  	        contentType:'application/json',
	  	        method : 'post',
	  	      	data : data,
	  	        success : function(respuesta){
	  	        	console.log(respuesta);
	  	        	
	  	        	var tr = document.createElement('tr');
		  	      	var destino = document.getElementById('cuerpo');		  	      	
		  	      	
		  	      	tr.appendChild(crearCampo('id',respuesta.id));
		  	      	tr.appendChild(crearIcono(respuesta.tipo.icon_ind_contable));
		  	      	tr.appendChild(crearCampo('responsable',respuesta.responsable.username));
		  	      	tr.appendChild(crearCampo('acuerdo',respuesta.acuerdo));
		  	    	tr.appendChild(crearCampo('area',respuesta.areaSolicitante.nombrecorto));
			  	 	tr.appendChild(crearCampo('jira',respuesta.id_jira));
			  	 	
			  	 	var strfecha = respuesta.fecha_creacion;
			  	 	strfecha = strfecha.substring(8, 10) + '-' + strfecha.substring(5, 7) + '-' + strfecha.substring(0, 4);
				  	tr.appendChild(crearFecha('fechas_plomo',strfecha));
				  	strfecha = respuesta.fecha_limite;
				  	strfecha = strfecha.substring(8, 10) + '-' + strfecha.substring(5, 7) + '-' + strfecha.substring(0, 4);
				  	tr.appendChild(crearFecha('fechas_azul',strfecha));
				  	
				  	tr.appendChild(crearCampo('cierre',''));
				  	tr.appendChild(crearCampo('comentcierre',''));
				  	tr.appendChild(crearEstado('estado','En proceso'));
				  	tr.appendChild(crearBotones('accion'));	  	      	
		  	      	
		  	      	destino.insertBefore(tr,destino.getElementsByTagName('tr')[0]);
		  	      	
		  	      document.getElementById("exampleTextarea1").value = "";
		  	      document.getElementById("fecha_entrega").value = "";
	  	        	// AQUÍ AGREGAR UNA NUEVA COLUMNA
	  	        },
	  	        error: function(error,sm1,sm2){
	  	        	alert("Se prodjo un error");
	  	        	console.log(sm2);
	  	            alert(sm1);
	  	        }  	        
	  	    });
		  }
	  }
	  
	  function validar_nuevo_acuerdo(){		 
		 fecha_entrega = document.getElementById("fecha_entrega").value;
		 observaciones = document.getElementById("exampleTextarea1").value;
		 var resultado = 1;
		 
		 if (fecha_entrega == null || fecha_entrega.length== 0){
			 document.getElementById("alet-fecha").innerHTML = "Una fecha es obligatoria";
			 resultado = resultado*0;
		 }else{
			 var hoy = new Date();
			 var hoydia = hoy.getDate();
			 var hoymes = hoy.getMonth() + 1; // Enero es 0
			 var hoyyear = hoy.getFullYear();
			
			 arfecha = fecha_entrega.split("-");
			
			if (hoyyear*1 > arfecha[0]*1){				
				document.getElementById("alet-fecha").innerHTML = "NO se puede regresar años en el tiempo!";
				resultado = resultado*0;
			}
			else if(hoymes*1 > arfecha[1]*1){			
				document.getElementById("alet-fecha").innerHTML = "NO se puede regresar meses en el tiempo!";
				resultado = resultado*0;				
			}
			else if(hoydia*1 > arfecha[2]*1){
				document.getElementById("alet-fecha").innerHTML = "NO se puede regresar días en el tiempo!";
				resultado = resultado*0;				
			}
		 }
		 if (observaciones == null || observaciones.length== 0){
			 document.getElementById("alet-obser").innerHTML = "Es necesario detallar el acuerdo";
			 resultado = resultado*0;
		 }
		 return resultado;
	  }
	  
	  function clear_alert(id_span){
		  document.getElementById(id_span).innerHTML = "";
	  }
	  
	  
	  
	  function terminar_acuerdo(ind){		
		objjson.numero1 = document.getElementById("id_categoria").value;	//Tipo
		objjson.numero2 = document.getElementById("id_input2").value;		//Area		
		objjson.numero3 = document.getElementById("responsable").value;		//Responsable
		objjson.numero4 = document.getElementById("id_input").innerHTML;	//Jira
		objjson.texto1 = document.getElementById("fecha_entrega").value;	//Fecha
		objjson.texto2 = document.getElementById("exampleTextarea1").value; 	//Acuerdo
		data = JSON.stringify(objjson); 	
	  	console.log(data);
	  	objjson = {};
	  	
	  	// REALIZAR PETICION
	  	$.ajax({
  	        url : '/rest/acuerdo/terminado',  	        
  	        contentType:'application/json',
  	        method : 'post',
  	      	data : data,
  	        success : function(respuesta){
  	        	console.log(respuesta);
  	        	modal.style.display = "none";
  	        	var spn_estado_accion = fila_accion.children[10].children[0];
  	        	var td_comen_accion = fila_accion.children[9];
  	        	var td_botones =  fila_accion.children[11];
  	        	td_botones.removeChild(td_botones.children[0]);
  	        	if (respuesta.estado == "Cancelado")
  	        		spn_estado_accion.setAttribute('class','est_');
  	        	else
  	        		spn_estado_accion.setAttribute('class','est_pospro');
  	        	
  	        	spn_estado_accion.innerHTML = respuesta.estado;
  	        	td_comen_accion.innerHTML = respuesta.observacion;
  	        	// aqui agregar los cambios 
  	        },
  	        error: function(error,sm1,sm2){
  	        	alert("Se prodjo un error");
  	        	console.log(sm2);
  	            alert(sm1);
  	        }  	        
  	    });	  	
	  }
	 
	  
	  
	  
	  var span = document.getElementsByClassName("close")[0];
	  var modal = document.getElementById('mi_modal');
	  var btn_no = document.getElementById("boton_no");
	  var objjson = {};
	  var tipo_btn = 0;
	  var fila_accion;
	  
	  span.onclick = function() {
		  modal.style.display = "none";
		  tipo_btn = 0;
	  }
	  
	  btn_no.onclick = function() {
		  objjson = {};
		  modal.style.display = "none";
		  tipo_btn = 0;
	  }
	  
	  window.onclick = function(event) {
	  if (event.target == modal) {
		  modal.style.display = "none";
		  tipo_btn = 0;
		  }
	  }
	  