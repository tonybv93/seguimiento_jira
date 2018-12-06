(function($) {

	$.ajax({
	        url : '/rest/pie/cavali/atencion',  	        
	        method : 'post',
	        success : function(respuesta){
	        	var c3PieChart = c3.generate({
	        		bindto : '#c3-pie-chart1',
	        		data : {
	        			// iris data from R
	        			columns : 
	        				[ [ 'LIQ', respuesta[0] ], 
	        				[ 'PAR', respuesta[3] ], 
	        				[ 'GA', respuesta[2] ],
	        				[ 'FT', respuesta[4] ], 
	        				[ 'OTI', respuesta[1] ], 
	        				[ 'Otros',respuesta[5] ], ],
	        			type : 'pie',
	        			onclick : function(d, i) {
	        				var area_seleaccionada = document.getElementById('graf1_sele');
	        				area_seleaccionada.innerHTML = d.value;
	        			},
	        			onmouseover : function(d, i) {
	        				//console.log("onmouseover", d, i);
	        			},
	        			onmouseout : function(d, i) {
	        				//console.log("onmouseout", d, i);
	        			}
	        		},
	        		size : {
	        			height : 280,
	        			width : 400
	        		},
	        		title : {
	        			text : "Distribución de Jiras en atención por área"
	        		},
	        		color : {
	        			pattern : [ '#ffaf00', '#2196f3', '#19d895', '#ff6258', '#c32c92',
	        					'#5abcf1' ]
	        		},
	        		padding : {
	        			top : 0,
	        			right : 0,
	        			bottom : 30,
	        			left : 0,
	        		}
	        	});
	        },
	        error: function(error,sm1,sm2){
	        	alert("Se prodjo un error");
	        }  	        
	    });
})(jQuery);


(function($) {
	
	$.ajax({
        url : '/rest/pie/cavali/noatencion',  	        
        method : 'post',
        success : function(respuesta){
        	var c3PieChart = c3.generate({
        		bindto : '#c3-pie-chart2',
        		data : {
        			// iris data from R
        			columns : 
        				[ [ 'LIQ', respuesta[0] ], 
        				[ 'PAR', respuesta[3] ], 
        				[ 'GA', respuesta[2] ],
        				[ 'FT', respuesta[4] ], 
        				[ 'OTI', respuesta[1] ], 
        				[ 'Otros',respuesta[5] ], ],
        			type : 'pie',
        			onclick : function(d, i) {
        				var area_seleaccionada = document.getElementById('graf2_sele');
        				area_seleaccionada.innerHTML = d.value;
        				//console.log("onclick", d, i);
        			},
        			onmouseover : function(d, i) {
        				//console.log("onmouseover", d, i);
        			},
        			onmouseout : function(d, i) {
        				//console.log("onmouseout", d, i);
        			}
        		},

        		size : {
        			height : 280,
        			width : 400
        		},
        		title : {
        			text : "Distribución de Jiras NO atendidos por área"
        		},
        		color : {
        			pattern : [ '#ffaf00', '#2196f3', '#19d895', '#ff6258', '#c32c92',
        					'#5abcf1' ]
        		},
        		padding : {
        			top : 0,
        			right : 0,
        			bottom : 30,
        			left : 0,
        		}
        	});
        	
        },
        error: function(error,sm1,sm2){
        	alert("Se prodjo un error");
        }  	        
    });

})(jQuery);

