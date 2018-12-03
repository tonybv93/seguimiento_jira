var valor_actual="";
var valor_nuevo="";
    
    function validar_fecha(txt){
    	var error = "";    	
    	
    	if (txt == ""){
    		error = "";    		
    	}else if (txt.length < 8 || txt.length > 10){
    		error = "Longitud erronea";    		
    	}else if (!/^\d{1,2}\-\d{1,2}\-\d{4}$/.test(txt)){
    		error = "Formato inválido";    		
    	}else{
    		txt = txt.split("-");
    		dia= parseInt(txt[0]);
    		mes=parseInt(txt[1]);
    		anio=parseInt(txt[2]);
    		
    		// dias máximos por mes
    		var lstDias = [31,28,31,30,31,30,31,31,30,31,30,31];
    		//detectar si es año bisiesto
    		if (mes === 2){
    			var bisie = ( (!(anio % 4) && anio % 100) || !(anio % 400) );
    			if (bisie != false){
    				lstDias[1] = 29;  				
    			}
    		}
    		if ( dia > lstDias[mes-1] || lstDias[mes-1] === undefined )
    			error = "Fecha inválida";
    		if (anio < 2000)
    			error ="Fecha incoherente";
    	} 	    	  		
    	return error;
    }
    
    function val_act(inp){
    	valor_actual = inp.value;
    }
    
    function val_new(inp){
    	var txt_corregido="";
    	var valor_nuevo = inp.value; 
    	

    	//VALIDAR CONTENIDO
    	var error = validar_fecha(valor_nuevo);     	
    	if (error != ""){
    		alert(error);
    		inp.value = valor_actual;
    	}
    	else{
    		// CORREGIR EL FORMATO (agregar 0 adelante)
        	if (valor_nuevo !== "" && valor_nuevo != null ){
        		valor_nuevo = valor_nuevo.split("-");
        		if (valor_nuevo[0].length === 1)
        			valor_nuevo[0] = '0' + valor_nuevo[0];
        		if (valor_nuevo[1].length === 1)
        			valor_nuevo[1] = '0' + valor_nuevo[1];
        	
        	txt_corregido = valor_nuevo[0] + '-' + valor_nuevo[1] + '-' + valor_nuevo[2]; 
        	}    		
    		inp.value = txt_corregido;
    		console.log(txt_corregido, "=",valor_actual);
    		// CAMBIAR COLOR DE LETRA SI SE AGREGÓ NUEVO CONTENIDO    		
    		if ((valor_actual === txt_corregido)){
        	}else{
        		inp.style.backgroundColor = "transparent";
        		inp.style.border = "solid 1px #ff9900";
        		inp.style.color = "#ff9900";
        	}    		
    	}
    	valor_actual ="";
    }
    
    
    function guardar_fecha(boton){
    	var flag_azul = false;
    	var flag_verde = false;
    	var flag_blanco = false;
    	var fila = boton.parentNode.parentNode.parentNode;
    	var jira = fila.children[0].children[0];
    	var td_fecha1 = fila.children[6].children[0];
    	console.log(td_fecha1.value);
    	console.log('---');    	
    	var td_fecha2 = fila.children[7].children[0];
    	console.log(td_fecha2.value);
    	var objjson = {};
    	var data;
    	
    	// Construyendo JSON    	
    	objjson.jira = jira.innerHTML;
    	objjson.fecha1 = td_fecha1.value;
    	if (td_fecha1.value != "")
    		flag_azul = true;
    	if (td_fecha2.value != "")
    		flag_verde = true;
    	objjson.fecha2 = td_fecha2.value;
    	data = JSON.stringify(objjson); 		
    	
  		
  		
  		// Realizar petición
  		$.ajax({
  	        url : '/rest/jira/fechas',  	        
  	        contentType:'application/json',
  	        method : 'post',
  	      	data : data,
  	        success : function(respuesta){
  	        	console.log(respuesta);
  	        },
  	        error: function(error,sm1,sm2){
  	        	 alert("Se produjo un error");
  	        	console.log(sm2);
  	            alert(sm1);
  	        }  	        
  	    });
  		
  		if (flag_azul){
  			td_fecha1.style.backgroundColor = "";
  	  		td_fecha1.style.border = "none";
  			td_fecha1.style.color = "";
  			td_fecha1.className = "input_fecha_azul";
  		}else{
  			td_fecha1.className = "input_sinfecha";
  			td_fecha1.style.border = "solid 1px #ccc";
  		}
  		  		
  		if (flag_verde){
  			td_fecha2.style.backgroundColor = "";
  			td_fecha2.style.border = "none";
  			td_fecha2.style.color = "";
  			td_fecha2.className = "input_fecha_verde";
  		}else{
  			td_fecha2.className = "input_sinfecha";
  			td_fecha2.style.border = "solid 1px #ccc";
  		}
    }
    
    

        