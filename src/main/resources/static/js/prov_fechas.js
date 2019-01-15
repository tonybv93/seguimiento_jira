var valor_actual="";
var valor_nuevo="";

	// GUARDAR
 function guardar_fecha(btn){
	 var jira = btn.parentNode.parentNode.parentNode.children[1].children[0].innerHTML;
	 var inp_fecha = btn.parentNode.parentNode.parentNode.children[9].children[0];
	 var brn_guardar = btn.parentNode.parentNode.parentNode.children[10].children[0].children[0];
	 var fecha = inp_fecha.value;
	 var objeto = {};
	 objeto.texto2 = jira;
	 objeto.texto1 = fecha;	 
	 data = JSON.stringify(objeto);
	 $.ajax({
	        url : '/provrest/jiras/fechas',  	        
	        contentType:'application/json',
	        method : 'post',
	      	data : data,
	        success : function(respuesta){
	        	inp_fecha.style="";
	        	if (fecha == '' || fecha == null){
	        		inp_fecha.setAttribute('class','input_sinfecha');
	        	}else{
	        		inp_fecha.setAttribute('class','input_fecha_azul');
	        	}	        	
	        	brn_guardar.classList.add('btn-outline-light');
        		brn_guardar.classList.remove('btn-outline-success');
	        },
	        error: function(error,sm1,sm2){
	        	alert("Se prodjo un error");
	        	console.log(sm2);
	            alert(sm1);
	        }  	        
	    });
 }
    // validación
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
  
    // on change
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
    		
    		var brn_guardar = inp.parentNode.children[1];
    		var div_guardar = inp.parentNode;
    		brn_guardar.classList.remove('boton_guardar_tabla_off');
    		brn_guardar.classList.add('boton_guardar_tabla');
    		brn_guardar.setAttribute('onclick','guardar_fecha(this)');
        	   		
    	}
    	valor_actual ="";
    }