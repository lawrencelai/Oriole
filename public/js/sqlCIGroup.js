
function initSqlCIGroupDataTable(){
	//sqlCIGroup
	dt_sqlci_grp = $('#sqlCIGroup').DataTable({		
		"scrollY":        "600px",
        "scrollCollapse": true,
		"lengthMenu": [[10, 25, 50], [10, 25, 50]],
		"sAjaxSource": "http://localhost:8080/api/sqlCIGroup/dt/search",
		"columnDefs": [{
        				 "render": function ( data, type, row ) {
        				    deployableDBPoolForCiGroup(data.id);
        				    return '<div class="deploy-ci-group-'+ data.id +'"></div>'
        				 },"targets": 8
        				}
        			],
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
		            { "data": "createdBy" },
		            { "data": "createdTs" },
					{   "className": "sqlCIGroup-edit",	
						"orderable": false,					
						"data":null,
						"defaultContent": '<i class="fa fa-edit" style="font-size:24px" data-popup-open="editSQLCIGroup" href="#"></a>'
					},
					{   "className":"sqlCIGroup-deploy",	
						"orderable": false,					
						"data":null
					}
		           ],
		"order": [[1, 'desc']],
		"bProcessing": true,
		"bServerSide": true ,
		"bDestroy": true

	});
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