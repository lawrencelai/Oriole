$(function() {
    
    $('[data-role="refresh-database-pool-dt"]').on('click', function(e)  {      	
    	$('#databasePool').DataTable().ajax.reload();
    });

    $('[database-pool-ws]').on('click', function(e)  {
        var target_ws = $(this).attr('database-pool-ws');
        if(target_ws == "createDatabasePool"){
        	createDatabasePoolRequest();
        }
         if(target_ws == "editDatabasePool"){
        	editDatabasePoolRequest();
        }
    });

	$('#databasePool tbody').on( 'click', '.database-pool-edit', function (e) {
	    var rowData = dt_dbpool.row(this).data() 
	    $.each(rowData, function(index, value) {
	    	//alert(index +":"+ value);	    
	    	$('[data-role="editDatabasePool"]').each(function () {
	    		if($(this).attr('id') == index){$(this).val(value);}
	    	});  
	    });
	    
		var targeted_popup_class = $(this).find("a").attr('data-popup-open');
		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
	    e.preventDefault();		
	} );
	
});

function initDatabasePoolDataTable(){
	dt_dbpool = $('#databasePool').DataTable({
		"lengthMenu": [[10, 25, 50], [10, 25, 50]],
		"sAjaxSource": "http://localhost:8080/api/database/dt/search",
		
		"columns": [{ "data": "name"},		           
		            { "data": "active"} ,
		            {   "className": 'database-pool-edit',	
						"orderable": false,					
						"data":null,
						"defaultContent": '<a class="button-group" data-popup-open="editDatabasePool" href="#">Edit</a>'
					}
		          			
		           ],
		"oLanguage": {
		       "sEmptyTable" : "Your custom message for empty table"
		}          ,
		"order": [[0, 'desc']],
		"bProcessing": true,
		"bServerSide": true,
		"bDestroy": true

	});
	
}

function createDatabasePoolRequest(){
	var data = {};
	    		
	$('[data-role="createDatabasePool"]').each(function (i, el) {		
		data[$(this).attr('id')] = $(this).val();	
	});
	    
	$.ajax(
	{
		url: "http://localhost:8080/api/database/create",
		data: data,
	    success: function (msg) {
	    	$('[data-popup="createDatabasePool"]').fadeOut(350);	    
	    	dt_dbpool.ajax.reload();
	    },
	    error:function(xhr, ajaxOptions, thrownError){ 
	    	alert(xhr.status); 
	        alert(thrownError); 
	    }
	});
}

function editDatabasePoolRequest(){
	    var data = {};
	    		
	    $('[data-role="editDatabasePool"]').each(function () {
	    	data[$(this).attr('id')] = $(this).val();
	    });

	    $.ajax(
	    {
	        url: "http://localhost:8080/api/database/change",
	        data: data,
	        success: function (msg) {
	        	 $('[data-popup="editDatabasePool"]').fadeOut(350);
	        	 dt_dbpool.ajax.reload();
	        },
	        error:function(xhr, ajaxOptions, thrownError){ 
	            alert(xhr.status); 
	            alert(thrownError); 
	         }	    	
	    });
	}
