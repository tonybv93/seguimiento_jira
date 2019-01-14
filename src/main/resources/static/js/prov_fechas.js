var valor_actual="";
var valor_nuevo="";
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
    // on click
    function val_act(inp){
    	valor_actual = inp.value;
    }    
    // on blur
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