
function initSqlCIGroupDataTable(){
	//sqlCIGroup
	dt_sqlci_grp = $('#sqlCIGroup').DataTable({
		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
		"sAjaxSource": "http://localhost:8080/api/sqlCIGroup/dt/search",		
		"columns": [{
		             "className":      'details-control',
		             "orderable":      false,
		             "data":           null,
		             "defaultContent": ''
		            },
		            { "data": "id"},
		            { "data": "mantisInfo.id"},
		            { "data": "description" },
		            { "data": "mantisInfo.targetVersion"},
		            { "data": "dependentGroupId" },
		            { "data": "createdBy" },
		            { "data": "createdTs" },
					{   "className": "sqlCIGroup-edit",	
						"orderable": false,					
						"data":null,
						"defaultContent": '<a class="button-group" data-popup-open="editSQLCIGroup" href="#">Edit</a>'
					},
					{   "className":"sqlCIGroup-edit",	
						"orderable": false,					
						"data":null,
						"defaultContent": '<div class="deploy-ci-group"></div>'
					}
		           ],
		"order": [[1, 'desc']],
		"bProcessing": true,
		"bServerSide": true ,
		"bDestroy": true

	});	
	 activeDatabasePoolForCiGroup();
}



function createSqlCIGroupRequest(){
    var data = {};
    		
    $('[data-role="createSQLCIGroup"]').each(function () {
    	data[$(this).attr('id')] = $(this).val();
    });
    
    $.ajax(
    {
        url: "http://localhost:8080/api/sqlCIGroup/create",
        data: data,
        success: function (msg) {
        	 $('[data-popup="createSQLCIGroup"]').fadeOut(350);
        	 dt_sqlci_grp.ajax.reload();
        },
        error:function(xhr, ajaxOptions, thrownError){ 
            alert(xhr.status); 
            alert(thrownError); 
         },
    	complete : function (msg) {
    		$("#lblForAjax").text(msg);
    	}
    });
}

function editSqlCIGroupRequest(){
    var data = {};
    		
    $('[data-role="editSQLCIGroup"]').each(function () {
    	data[$(this).attr('id')] = $(this).val();
    });

    $.ajax(
    {
        url: "http://localhost:8080/api/sqlCIGroup/change",
        data: data,
        success: function (msg) {
        	 $('[data-popup="editSQLCIGroup"]').fadeOut(350);
        	 dt_sqlci_grp.ajax.reload();
        },
        error:function(xhr, ajaxOptions, thrownError){ 
            alert(xhr.status); 
            alert(thrownError); 
         },
    	complete : function (msg) {
    		$("#lblForAjax").text(msg);
    	}
    });
}