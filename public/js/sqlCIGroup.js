function syncMantis(sync_mantis){
    var data = {};
    data['bugid'] =  $('[data-role="'+sync_mantis+'"]:input[id="mantisInfoId"]').val();

    $.ajax(
    {
        url: "http://localhost:8080/api/mantis/getIssue",
        data: data,
        success: function (data) {
            $('[data-role="'+sync_mantis+'"]#mantisInfoTargetVersion').val(data.os_build);
            $('[data-role="'+sync_mantis+'"]#mantisInfoSummary').val(data.summary);
        },
        error:function(xhr, ajaxOptions, thrownError){
            var err = eval("(" + xhr.responseText + ")");
            $('[data-role="'+sync_mantis+'"]#errorMsg').text(err.message);
        }
    });

}
function initSqlCIGroupDataTable(){
	//sqlCIGroup
	dt_sqlci_grp = $('#sqlCIGroup').DataTable({		
		"scrollY":  "600px",
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

function prepareSqlCIGroup(groupid){
	$.ajax({
		url: "http://localhost:8080/api/sqlCIGroup/searchById?groupId="+groupid,
		success:function(response){
		    $.each(response, function(index, value) {
		    	$('[data-role="editSQLCIGroup"]').each(function (index1,value1) {
		    		if($(this).attr('id') == index){
		    			if($(this).attr('id')=='active'){
		    				$(this).bootstrapSwitch('state',value);

		    			}else if($(this).attr('id')=='type' || $(this).attr('id')=='restrictedDatabase'){
		    				$(this).find('option[value="' +value + '"]').prop('selected', true);

		    			}else{
		    				$(this).val(value);

		    			}
		    		}else if (index == "mantisInfo"){
		    		    $('#mantisInfoId[data-role="editSQLCIGroup"]').val(value.id);
		    		    $('#mantisInfoTargetVersion[data-role="editSQLCIGroup"]').val(value.targetVersion);
		    		}
		    	});
		    });
		    var targeted_popup_class = $(this).find("a").attr('data-popup-open');
			$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);
		},
	    error:function(xhr, ajaxOptions, thrownError){
	    	alert(xhr.status);
	        alert(thrownError);
	    }
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