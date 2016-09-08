$(function() {

	$('[data-role="refresh-database-pool-dt"]').on('click', function(e) {
		$('#databasePool').DataTable().ajax.reload();
	});

	$('[database-pool-ws]').on('click', function(e) {
		var target_ws = $(this).attr('database-pool-ws');
		if (target_ws == "createDatabasePool") {
			createDatabasePoolRequest();
		}
		if (target_ws == "editDatabasePool") {
			editDatabasePoolRequest();
		}
	});

	$('#databasePool tbody').on('click', '.database-pool-edit', function(e) {
		var rowData = dt_dbpool.row(this).data()
		$.each(rowData, function(index, value) {
			var name = $('[data-role="editDatabasePool"]').find(index);
    			if(index=='active'  || index=='restricted' ){
    				$('[data-role="editDatabasePool"]').find(name).bootstrapSwitch('state',value);
    			}else{
    				$('[data-role="editDatabasePool"]').find(name).val(value);
    			}
			
		});

		var targeted_popup_class = $(this).find("a").attr('data-popup-open');
		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);
		e.preventDefault();
	});

});

function initDatabasePoolDataTable() {
	dt_dbpool = $('#databasePool')
			.DataTable(
					{
						"lengthMenu" : [ [ 10, 25, 50 ], [ 10, 25, 50 ] ],
						"sAjaxSource" : "http://localhost:8080/api/database/dt/search",
						 "columnDefs": [
						                {						                   
						                    "render": function ( data, type, row ) {
						                    	if(data)
						                    		return "<i class='fa fa-circle-o-notch fa-spin' style='font-size:14px;color:green'>";
						                    	else
						                    		return "<i class='fa fa-exclamation-circle' style='font-size:16px;color:red'>";
						                    },
						                    "targets": 1
						                },
						                {						                   
						                    "render": function ( data, type, row ) {
						                    	if(data)
						                    		return "<i class='fa fa-ban' style='font-size:14px;color:red'>";
						                    	else
						                    		return "<i class='fa fa-plug' style='font-size:16px;color:green'>";
						                    },
						                    "targets": 2
						                },
						                { "visible": false, "targets": 3 },
						                { "visible": false, "targets": 4 },
						                { "visible": false, "targets": 5 },
						                { "visible": false, "targets": 6 },
						                { "visible": false, "targets": 7 },
						                { "visible": false, "targets": 8 },
						                { "visible": false, "targets": 9 },
						                { "visible": false, "targets": 10 },
						                { "visible": false, "targets": 11 },
						                { "visible": false, "targets": 12 },
						                { "visible": false, "targets": 13 }					                
						            ],
						"columns" : [						        
								{"data" : "name"},
								{"data" : "active"},
								{"data" : "restricted"},
								{"data" : "type"},
								{"data" : "username"},
								{"data" : "password"},
								{"data" : "createdBy"},
								{"data" : "createdTs"},
								{"data" : "updatedBy"},
								{"data" : "updatedTs"},
								{"data" : "host"},
								{"data" : "port"},
								{"data" : "serviceName"},
								{"data" : "sid"},
								{
									"className" : 'database-pool-edit',
									"orderable" : false,
									"data" : null,
									"defaultContent" : '<a class="button-group" data-popup-open="editDatabasePool" href="#">Edit</a>'
								}

						],
						"oLanguage" : {
							"sEmptyTable" : "Your custom message for empty table"
						},
						"order" : [ [ 0, 'desc' ] ],
						"bProcessing" : true,
						"bServerSide" : true,
						"bDestroy" : true

					});

}

function createDatabasePoolRequest() {
	var data = {};

	$('[data-role="createDatabasePool"]').each(function() {
		data[$(this).attr('id')] = $(this).val();
	});

	$.ajax({
		url : "http://localhost:8080/api/database/create",
		data : data,
		success : function(msg) {
			$('[data-popup="createDatabasePool"]').fadeOut(350);
			dt_dbpool.ajax.reload();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
}

function editDatabasePoolRequest() {
	var data = {};

	$('[data-role="editDatabasePool"]').each(function() {
		data[$(this).attr('id')] = $(this).val();
	});

	$.ajax({
		url : "http://localhost:8080/api/database/change",
		data : data,
		success : function(msg) {
			$('[data-popup="editDatabasePool"]').fadeOut(350);
			dt_dbpool.ajax.reload();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
}
function deployableDBPoolForCiGroup() {
	var data = {};
	$.ajax({
		url : "http://localhost:8080/api/database/deployableList",
		data : data,
		success : function(json) {
			var HTML = "";
			$('.deploy-ci-group').text(HTML);
			$.each(json, function(i, value) {
				HTML = HTML + "<a class='button-group' data-popup-open='createGroupDeployRequest' deploy-target='"+value.name+"' href='#'>" + value.name
						+ "</a>";
			});
			$('.deploy-ci-group').append(HTML);		
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
}

function deployableDBPoolForCi() {
	var data = {};
	$.ajax({
		url : "http://localhost:8080/api/database/deployableList",
		data : data,	
		success : function(json) {
			var HTML = "";
			$('.deploy-ci').text(HTML);
			$.each(json, function(i, value) {
				HTML = HTML + "<a class='button-group' data-popup-open='createDeployRequest'  href='#'>" + value.name
						+ "</a>";
			});
			$('.deploy-ci').append(HTML);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
		
	});
}
