function initDatabasePoolDataTable(){
	$('#dbPool').DataTable({
		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
		"sAjaxSource": "http://localhost:8080/api/database/dt/search",
		 "columnDefs": [
		                {  "render": function ( data, type, row ) {
		                        return '<a class="button-group" data-popup-open="edit-DB-Pool" href="#">Edit</a>';
		                    },
		                    "targets": 3
		                },
		                { "visible": false,  "targets": [ 3 ] }
		            ],
		"columns": [{ "data": "name"},
		            { "data": "description" },
		            { "data": "active"} ,
		            {   "className": 'sqlCIGroup-edit',	
						"orderable": false,					
						"data":null,
						"defaultContent": '<a class="button-group" data-popup-open="editSQLCIGroup" href="#">Edit</a>'
					}
		          			
		           ],
		"oLanguage": {
		       "sEmptyTable" : "Your custom message for empty table"
		}          ,
		"order": [[0, 'desc']],
		"bProcessing": true,
		"bServerSide": true                         

	});
	
}