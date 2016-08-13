$(document).ready(function () {
	var dttable;
	initSqlCIGroupDataTable();
	initSqlCategory();	
	initMenu();
	$('[page]').fadeOut(0); 
	$("[name='sqlci-group-active-checkbox']").bootstrapSwitch();	
});

function initMenu(){	
	$(".oriole-menuitem").mouseover(function() {
		 $(this).animate({ backgroundColor:'##aff1ff'},1000);
	}).mouseout(function() {
		$(this).animate({ backgroundColor:'#ffffff'},1000);
	}); 
}

function initSqlCategory(){		
	    $.ajax(
	    {
	        url: "http://localhost:8080/sqlCategory/searchAll",
	        success: function (json) {
	        	var HTML = ""; 
	             $.each(json, function(i, value) { 	                
	                HTML = HTML+"<option id='"+value.id+"'>"+value.name+"</option>"; 
	             });
	             $('#sqlCategory').append(HTML); 
	        
	        },
	        error:function(xhr, ajaxOptions, thrownError){ 
	            alert(xhr.status); 
	            alert(thrownError); 
	        }	        
	    });
}

function initSqlCIGroupDataTable(){
	//sqlCIGroup
	dttable = $('#sqlCIGroup').DataTable({
		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
		"sAjaxSource": "http://localhost:8080/sqlCIGroup/dt/search",
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
						"defaultContent": '<a class="edit-SQLCI-Group" data-popup-open="editSQLCIGroup" href="#">Edit</a>'
					},
					{   "className": 'sqlCIGroup-edit',	
						"orderable": false,					
						"data":null,
						"defaultContent": '<a class="deploy-SQLCI-Group-1" href="#">P4DEV</a><a class="deploy-SQLCI-Group-2" href="#">P4SIT</a>'
					}
		           ],
		"order": [[1, 'desc']],
		"bProcessing": true,
		"bServerSide": true,                            

	});
}

function formatSQLCI (groupid,data) {
    // `d` is the original data object for the row		
	var childrow ;//= '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';	
	if(data){
		for (val of data){
			childrow +=
				'<tr>'+					
					'<td>'+val.sequence+'</td>'+    	
	            	'<td>'+val.sqlCIType+'</td>'+	          
	            	'<td>'+val.statement+'</td>'+	           
		        	'<td>'+val.description+'</td>'+		     
		        	'<td>'+val.createdBy+'</td>'+		        
		        	'<td>'+val.createdTs+'</td>'+
		        	'<td>'+val.updatedBy+'</td>'+		        
		        	'<td>'+val.updateddTs+'</td>'+
		        	'<td class="sqlCI-edit"><a class="edit-SQLCI" data-popup-open="editSQLCI" href="#">Edit</a></td>'+	
					'<td><a class="deploy-SQLCI-1" href="#">P4DEV</a><a class="deploy-SQLCI-2" href="#">P4SIT</a></td>'
		        '</tr>'
	            ;
		}
		childrow += '<tr>'+
    		'<td class="sqlCI-add"><a class="edit-SQLCI-Group" data-popup-open="createSQLCI" sql-ci-group="'+groupid+'" href="#">New SQL CI</a></td>'+        	
    	'</tr>';
	}else{
		childrow += '<tr>'+
        	'<td class="sqlCI-add"><a class="edit-SQLCI-Group" data-popup-open="createSQLCI" sql-ci-group="'+groupid+'" href="#">New SQL CI</a></td>'+        	
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
        url: "http://localhost:8080/sqlCIGroup/create",
        data: data,
        success: function (msg) {
        	 $('[data-popup="createSQLCIGroup"]').fadeOut(350);
        	 dttable.ajax.reload();
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
        url: "http://localhost:8080/sqlCIGroup/change",
        data: data,
        success: function (msg) {
        	 $('[data-popup="editSQLCIGroup"]').fadeOut(350);
        	 dttable.ajax.reload();
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