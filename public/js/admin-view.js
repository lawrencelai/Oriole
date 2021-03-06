$(function() {

	$('[data-role="refresh-database-pool-dt"]').on('click', function(e) {
		$('#databasePool').DataTable().ajax.reload();
	});

	$('[database-pool-ws]').on('click', function(e) {
		var target_ws = $(this).attr('database-pool-ws');
		if (target_ws == "createDatabasePool") {
			createDatabasePoolRequest();
		}
		if (target_ws == "createDatabasePool") {
        	createMantisPoolRequest();
        }
		if (target_ws == "editDatabasePool") {
			editDatabasePoolRequest();
		}
		if (target_ws == "editMantisPool") {
            editMantisPoolRequest();
        }
	});

	$('#databasePool tbody').on('click', '.database-pool-edit', function(e) {
		var rowData = dt_dbpool.row(this).data();
        prepareEditDatabasePoolRequest(rowData.name);

		var targeted_popup_class = $(this).find("i").attr('data-popup-open');
		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);hh
		e.preventDefault();
	});

	$('#mantisPool tbody').on('click', '.mantis-pool-edit', function(e) {
    		var rowData = dt_mantispool.row(this).data();
            prepareEditMantisPoolRequest(rowData.name);

    		var targeted_popup_class = $(this).find("i").attr('data-popup-open');
    		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);
    		e.preventDefault();
    	});

});
function prepareEditDatabasePoolRequest(name){
	$.ajax({
		url: "http://localhost:8080/api/resource/database/searchByName?name="+name,
		success:function(response){

		    $.each(response, function(index, value) {
		    	$('[data-role="editDatabasePool"]').each(function () {
		    		if($(this).attr('id') == index){
		    			if($(this).attr('id')=='active'){
		    				$(this).bootstrapSwitch('state',value);
		    			}else if($(this).attr('id')=='type' || $(this).attr('id')=='restrictedDatabase'){
		    				$(this).find('option[value="' +value + '"]').prop('selected', true);
		    			}else{
		    				$(this).val(value);
		    			}
		    		}
		    	});
		    });
		},
	    error:function(xhr, ajaxOptions, thrownError){
	    	alert(xhr.status);
	        alert(thrownError);
	    }
	});
}

function prepareEditMantisPoolRequest(name){
	$.ajax({
		url: "http://localhost:8080/api/resource/mantis/searchByName?name="+name,
		success:function(response){

		    $.each(response, function(index, value) {
		    	$('[data-role="editMantisPool"]').each(function () {
		    		if($(this).attr('id') == index){
		    			if($(this).attr('id')=='active'){
		    				$(this).bootstrapSwitch('state',value);
		    			}else if($(this).attr('id')=='type'){
		    				$(this).find('option[value="' +value + '"]').prop('selected', true);
		    			}else{
		    				$(this).val(value);
		    			}
		    		}
		    	});
		    });
		},
	    error:function(xhr, ajaxOptions, thrownError){
	    	alert(xhr.status);
	        alert(thrownError);
	    }
	});
}

function initDatabasePoolDataTable() {
	dt_dbpool = $('#databasePool')
			.DataTable(
					{
						"lengthMenu" : [ [ 10, 25, 50 ], [ 10, 25, 50 ] ],
						"sAjaxSource" : "http://localhost:8080/api/resource/database/dt/search",
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
						                { "visible": false, "targets": 13 },
						                { "visible": true, "targets": 14 }
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
									"className":"database-pool-edit",
									"orderable" : false,
									"data" : null,
									"defaultContent":'<i class="fa fa-edit" style="font-size:24px" data-popup-open="editDatabasePool"></i>'
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

function initMantisPoolDataTable() {
	dt_mantispool = $('#mantisPool').DataTable(
					{
						"lengthMenu" : [ [ 10, 25, 50 ], [ 10, 25, 50 ] ],
						"sAjaxSource" : "http://localhost:8080/api/resource/mantis/dt/search",
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
						                { "visible": false, "targets": 2 },
						                { "visible": false, "targets": 3 },
						                { "visible": false, "targets": 4 },
						                { "visible": false, "targets": 5 },
						                { "visible": false, "targets": 6 },
						                { "visible": false, "targets": 7 },
						                { "visible": false, "targets": 8 },
						                { "visible": false, "targets": 9 },
						                { "visible": true, "targets": 10 }
						            ],
						"columns" : [
								{"data" : "name"},
								{"data" : "active"},
								{"data" : "url"},
								{"data" : "type"},
								{"data" : "username"},
								{"data" : "password"},
								{"data" : "createdBy"},
								{"data" : "createdTs"},
								{"data" : "updatedBy"},
								{"data" : "updatedTs"},
								{
									"className":"mantis-pool-edit",
									"orderable" : false,
									"data" : null,
									"defaultContent":'<i class="fa fa-edit" style="font-size:24px" data-popup-open="editMantisPool"></i>'
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
		url : "http://localhost:8080/api/resource/database/create",
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
		url : "http://localhost:8080/api/resource/database/change",
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
function createMantisPoolRequest() {
	var data = {};

	$('[data-role="createMantisPool"]').each(function() {
		data[$(this).attr('id')] = $(this).val();
	});

	$.ajax({
		url : "http://localhost:8080/api/resource/mantis/create",
		data : data,
		success : function(msg) {
			$('[data-popup="createMantisPool"]').fadeOut(350);
			dt_dbpool.ajax.reload();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
}

function editMantisPoolRequest() {
	var data = {};

	$('[data-role="editMantisPool"]').each(function() {
		data[$(this).attr('id')] = $(this).val();
	});

	$.ajax({
		url : "http://localhost:8080/api/resource/mantis/change",
		data : data,
		success : function(msg) {
			$('[data-popup="editMantisPool"]').fadeOut(350);
			dt_dbpool.ajax.reload();
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
}
function deployableDBPoolForCiGroup(groupid) {
	var data = {};
	$.ajax({
		url : "http://localhost:8080/api/resource/database/deployableList",
		data : data,
		success : function(json) {
			var HTML = "";
			$('.deploy-ci-group-'+groupid).text(HTML);
			$.each(json, function(i, value) {
				HTML = HTML + "<a class='button-group'"+
				              "data-popup-open='createGroupDeployRequest'" +
				              "ci-group-id='"+groupid+"'"+
				              "deploy-target='"+value.name+"' href='#'>" +
				              value.name + "</a>";
			});
			$('.deploy-ci-group-'+groupid).append(HTML);
			return HTML;
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
}

function deployableDBPoolForCi(id) {
	var data = {};
	$.ajax({
		url : "http://localhost:8080/api/resource/database/deployableList",
		data : data,	
		success : function(json) {
			var HTML = "";
			$('.deploy-ci').text(HTML);
			$.each(json, function(i, value) {
				HTML = HTML + "<a class='button-group' data-popup-open='createDeployRequest'"+
				              "ci-id='"+id+"'"+
				              "deploy-target='"+value.name+"' href='#'>" + value.name + "</a>";
			});
			$('.deploy-ci').append(HTML);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
		
	});
}
