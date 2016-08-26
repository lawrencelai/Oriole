function initDeployRequestDataTable() {
	dt_deployRequest = $('#deployRequest').DataTable({
		"lengthMenu" : [ [ 10, 25, 50 ], [ 10, 25, 50 ] ],
		"sAjaxSource" : "http://localhost:8080/api/deployRequest/dt/search",
		"columns" : [{"data" : "sqlCiId"},
					 {"data" : "description"},
					 {"data" : "targetDatabase"},
					 {"data" : "status"},
					 {"data" : "requestBy"},
					 {"data" : "requestTs"},
					 {"data" : "executedTs"},
					 {"data" : "completedTs"}
					 ],
		"oLanguage" : {
			"sEmptyTable" : "Your deploy Request for empty table"
		},
		"order" : [ [ 5, 'asc' ] ],
		"bProcessing" : true,
		"bServerSide" : true,
		"bDestroy" : true
	});

}
