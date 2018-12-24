/*--------------------  BUSCAR JIRAS ----------------------------*/
function cargar_menus(){
	ul_menu_total = document.getElementById("contenedor_menu");
	var str_busqueda = '../../rest/sidemenu';
	$.get(str_busqueda).done(function( menu ) {		
		for (var i = 0; i < menu.length; i++) {			
			
			var ul_sub = document.createElement('ul');
			ul_sub.setAttribute('class','nav flex-column sub-menu');			
			
			for (var j = 0; j < menu[i].submenus.length; j++) {				
				ul_sub.appendChild(construir_submenu(menu[i].submenus[j].texto,menu[i].submenus[j].url))
			}
			
			var div_sub = document.createElement('div');
			div_sub.appendChild(ul_sub);
			div_sub.setAttribute('class','collapse');
			div_sub.setAttribute('id',menu[i].url);
			
			
			var span_menu = document.createElement('span');
			span_menu.setAttribute('class','menu-title');
			span_menu.innerHTML = menu[i].texto;
			
			var i_menu1 = document.createElement('i');
			i_menu1.setAttribute('class','menu-icon mdi mdi-television');
			
			var i_menu2 = document.createElement('i');
			i_menu2.setAttribute('class','menu-arrow');
						
			var a_menu = document.createElement('a');
			a_menu.setAttribute('class','nav-link');
			a_menu.setAttribute('data-toggle','collapse');
			a_menu.setAttribute('href','#' + menu[i].url);		//DINAMICOOOO
			a_menu.setAttribute('aria-expanded','false');
			a_menu.setAttribute('aria-controls',menu[i].url);		
			a_menu.appendChild(i_menu1);
			a_menu.appendChild(span_menu);
			a_menu.appendChild(i_menu2);
			
			
			var li_menu = document.createElement('li');
			li_menu.setAttribute('class','nav-item');			
			li_menu.appendChild(a_menu);
			li_menu.appendChild(div_sub);			
			
			ul_menu_total.appendChild(li_menu);
		}
		
	});
}
function construir_submenu(texto,link){
	
	var a_sub = document.createElement('a');
	a_sub.innerHTML = texto;
	a_sub.setAttribute('class','nav-link');
	a_sub.setAttribute('href',link);
	
	var li_sub = document.createElement('li');
	li_sub.setAttribute('class','nav-item');
	li_sub.appendChild(a_sub);
	return li_sub;
}
function cerrar_sesion(){
	location.href ="/logout";
}
