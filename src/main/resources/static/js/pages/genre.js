$(document).ready(function() {
	var table = $('table#genre-data-table').DataTable({
		'ajax' : '/genre/data/genres',
		'serverSide' : true,
		"language" : {
			"url" : "//cdn.datatables.net/plug-ins/1.10.11/i18n/German.json"
		},
		columns : [ {
			data : 'id',
		}, {
			data : 'genre'
		}, {
			data : 'anothercolumn',
			orderable : false,
			searchable : false,
			render : function(data, type, row) {
				return '';
			}
		}
		// ,
		// {
		// add another column not
		// persisted on the
		// server-side
		// data : 'anothercolumn',
		// order is not available
		// orderable : false,
		// yet filter should be
		// available through the
		// method
		// findAll(DataTablesInput
		// input, Specification<T>
		// additionalSpecification)
		// searchable : false,
		// render : function(data,
		// type, row) {
		// if (row.userRole) {
		// return row.role;
		// }
		// return '';
		// }
		// }
		]
	});

	$('select#role_selector').change(function() {
		var filter = '';
		$('select#role_selector option:selected').each(function() {
			filter += $(this).text() + "+";
		});
		filter = filter.substring(0, filter.length - 1);
		table.columns(2).search(filter).draw();
	});

});