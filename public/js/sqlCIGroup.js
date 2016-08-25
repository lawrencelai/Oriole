
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
					{   "className": 'sqlCIGroup-edit',	
						"orderable": false,					
						"data":null,
						"defaultContent": '<a class="button-group" data-popup-open="editSQLCIGroup" href="#">Edit</a>'
					},
					{   "className": 'sqlCIGroup-edit',	
						"orderable": false,					
						"data":null,
						"defaultContent": '<a class="button-group" href="#">P4DEV</a><a class="button-group" href="#">P4SIT</a>'
					}
		           ],
		"order": [[1, 'desc']],
		"bProcessing": true,
		"bServerSide": true ,
		"bDestroy": true

	});
}

function formatSQLCI (groupid,data) {
    // `d` is the original data object for the row		
	var childrow ;//= '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';	
	if(data){
		for (val of data){
			if(val.active){
				activeFlag = '<a class="active-logo"  href="#"><span class="tooltiptext">Active</span></a>';
			}else{
				activeFlag = '<a class="inactive-logo"href="#"> <span class="tooltiptext">Inactive</span></a>';
			}
			
			childrow +=
				'<tr>'+					
					'<td><span sqlci-id="'+val.id+'">'+val.sequence+'<span></td>'+
					'<td>'+activeFlag+'</td>'+    	
	            	'<td>'+val.type+'</td>'+
		        	'<td>'+val.description+'</td>'+		     
		        	'<td>'+val.updatedBy+'</td>'+        
		        	'<td>'+val.updatedTs+'</td>'+
		        	'<td class="sqlCI-edit"><a class="button-group" data-popup-open="editSQLCI" href="#">Edit</a></td>'+	
					'<td><a class="button-group" deploy-db-group="P4DEV" href="#">P4DEV</a><a class="button-group" deploy-db-group="P4SIT" href="#">P4SIT</a></td>'
		        '</tr>'
	            ;
		}
		childrow += '<tr>'+
    		'<td class="sqlCI-add"><a class="button-group" data-popup-open="createSQLCI" sql-ci-group="'+groupid+'" href="#">New SQL CI</a></td>'+
    	'</tr>';
	}else{
		childrow += '<tr>'+
        	'<td class="sqlCI-add"><a class="button-group" data-popup-open="createSQLCI" sql-ci-group="'+groupid+'" href="#">New SQL CI</a></td>'+        	
        '</tr>';
	}
	
	childrow += '</table>';
    return childrow;
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