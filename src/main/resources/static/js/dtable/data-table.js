(function($) {
  'use strict';
  $(function() {
    $('#order-listing').DataTable({
    	"language": {
    	    "sProcessing":     "Procesando...",
    	    "sLengthMenu":     "Mostrar _MENU_ registros",
    	    "sZeroRecords":    "No se encontraron resultados",
    	    "sEmptyTable":     "Ningún dato disponible en esta tabla",
    	    "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
    	    "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
    	    "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
    	    "sInfoPostFix":    "",
    	    "sSearch":         "Buscar:",
    	    "sUrl":            "",
    	    "sInfoThousands":  ",",
    	    "sLoadingRecords": "Cargando...",
    	    "oPaginate": {
    	        "sFirst":    "Primero",
    	        "sLast":     "Último",
    	        "sNext":     "Siguiente",
    	        "sPrevious": "Anterior"
    	    },
    	    "oAria": {
    	        "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
    	        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
    	    }
        },    	
      "aLengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
      "iDisplayLength": 10
    });
    $('#order-listing').each(function(){
      var datatable = $(this);
      // SEARCH - Add the placeholder for Search and Turn this into in-line form control
      var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
      search_input.attr('placeholder', 'Buscar');
      search_input.removeClass('form-control-sm');
      // LENGTH - Inline-Form control
      var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
      length_sel.removeClass('form-control-sm');
    });
  });
})(jQuery);
