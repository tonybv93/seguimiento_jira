function actualizarjiras(btn){
	var n = 0;
	var l = document.getElementById("contador_seg");
	btn.disabled=true;
	document.getElementById("modal_actualizando").style.display="block";	
	l.innerHTML = n;
	window.location.href = "/seguimiento/actualizar";
	window.setInterval(function(){
		  l.innerHTML = n;
		  n++;
		},1000);
}

function buscar(inp){
	var buscar = inp.value.toLowerCase();
	var table = document.getElementById("tabla");
	var tr = table.getElementsByTagName("tr"); 
	var nro_filas = 0;
	
	for (i = 1; i < tr.length; i++) {
	  	td = tr[i].getElementsByTagName("td");  
	  	
		if (td) {    				
	         if (td[0].innerHTML.toLowerCase().search(buscar) != -1  ||
	        		 td[3].innerHTML.toLowerCase().search(buscar) != -1 ||
	        		 td[4].innerHTML.toLowerCase().search(buscar) != -1 ||
	        		 td[5].innerHTML.toLowerCase().search(buscar) != -1 ||
	        		 td[6].innerHTML.toLowerCase().search(buscar) != -1 ||
	        		 buscar ==='') {
	           tr[i].style.display = "";
	         } else {
	           tr[i].style.display = "none";
	      	 }
	    }
		
	  }
}

function filtrar() {
	
	  // Declare variables 
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("filtro");
	  filter = input.options[input.selectedIndex].innerHTML;
	  table = document.getElementById("tabla");
	  tr = table.getElementsByTagName("tr");
	  var titulo = document.getElementById("titulo_lista");
	  var txt_titu = filter;
	  
	  if (filter === "Liquidaciones")
		  filter = "LIQ";
	  else if (filter === "Operaciones TI")
		  filter = "OTI";
	  else if (filter === "Gestión de activos")
		  filter = "GA";
	  else if (filter === "Participantes")
		  filter = "PAR";
	  else if (filter === "Factura Negociable")
		  filter = "FT";
	  else if (filter === "Otros")
		  filter = "Otros";
	  else 
		  filter = "Todos";

	  nro_filas = 0;
	  
	  for (i = 1; i < tr.length; i++) {
	  	td = tr[i].getElementsByTagName("td")[1];   	  	
		if (td) {
				
	         if (td.innerHTML === filter || filter ==='Todos') {
	           tr[i].style.display = "";
	           nro_filas++;
	         } else {
	           tr[i].style.display = "none";
	      	 }
	    }
		
	  }
	  titulo.innerHTML = txt_titu + ' (' + nro_filas +')';
	}

function filtrar_etiqueta(etiqueta){
	
	input = document.getElementById("filtro");
	filtroArea = input.options[input.selectedIndex].innerHTML;	
	filtro = etiqueta.children[0].innerHTML;
	var titulo = document.getElementById("titulo_lista");
	var txt_titu = filtroArea + ' - ' + filtro;
	
	table = document.getElementById("tabla");
	tr = table.getElementsByTagName("tr");
	
	if (filtroArea === "Liquidaciones")
		filtroArea = "LIQ";
	  else if (filtroArea === "Operaciones TI")
		  filtroArea = "OTI";
	  else if (filtroArea === "Gestión de activos")
		  filtroArea = "GA";
	  else if (filtroArea === "Participantes")
		  filtroArea = "PAR";
	  else if (filtroArea === "Factura Negociable")
		  filtroArea = "FT";
	  else if (filtroArea === "Otros")
		  filtroArea = "Otros";
	  else 
		  filtroArea = "Todos";	
	console.log("filtro = ",filtro, " - FiltroArea = ",filtroArea)
	
	nro_filas = 0;
	for (i = 1; i < tr.length; i++) {
		tdarea = tr[i].getElementsByTagName("td")[1]; 
	  	td = tr[i].getElementsByTagName("td")[10];  
	  	
		if (td) {				
	         if ((td.innerHTML === filtro && filtroArea === tdarea.innerHTML) || (td.innerHTML === filtro && filtroArea === 'Todos')) {
	           tr[i].style.display = "";
	           nro_filas++;
	         } else {
	           tr[i].style.display = "none";
	      	 }
	    }
		titulo.innerHTML = txt_titu + ' (' + nro_filas +')';
	  }
}
