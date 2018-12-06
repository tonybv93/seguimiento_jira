(function($) {

	var c3PieChart = c3.generate({
		bindto : '#c3-pie-chart1',
		data : {
			// iris data from R
			columns : [ [ 'LIQ', 11 ], [ 'PAR', 40 ], [ 'GA', 44 ],
					[ 'FT', 12 ], [ 'OTI', 20 ], [ 'Otros', 2 ], ],
			type : 'pie',
			onclick : function(d, i) {
				console.log("onclick", d, i);
			},
			onmouseover : function(d, i) {
				console.log("onmouseover", d, i);
			},
			onmouseout : function(d, i) {
				console.log("onmouseout", d, i);
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

})(jQuery);


(function($) {

	var c3PieChart = c3.generate({
		bindto : '#c3-pie-chart2',
		data : {
			// iris data from R
			columns : [ [ 'LIQ', 11 ], [ 'PAR', 40 ], [ 'GA', 44 ],
					[ 'FT', 12 ], [ 'OTI', 20 ], [ 'Otros', 2 ], ],
			type : 'pie',
			onclick : function(d, i) {
				console.log("onclick", d, i);
			},
			onmouseover : function(d, i) {
				console.log("onmouseover", d, i);
			},
			onmouseout : function(d, i) {
				console.log("onmouseout", d, i);
			}
		},

		size : {
			height : 280,
			width : 400
		},
		title : {
			text : "Distribución de Jiras sin atención por área"
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

})(jQuery);

