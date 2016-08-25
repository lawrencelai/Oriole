$(function() {
	activeDatabasePool();
    $('[data-role="refresh-SqlCI-Group-dt"]').on('click', function(e)  {     	
    	$('#sqlCIGroup').DataTable().ajax.reload();
    	activeDatabasePool();
    });

    $('[sql-ci-ws]').on('click', function(e)  {
        var target_ws = $(this).attr('sql-ci-ws');
        if(target_ws == "createSQLCIGroup"){
        	createSqlCIGroupRequest();
        }
         if(target_ws == "editSQLCIGroup"){
        	editSqlCIGroupRequest();
        }
        if(target_ws == "createSQLCI"){
        	createSqlCIRequest();
        }
        if(target_ws == "editSQLCI"){
        	editSqlCIRequest();
        }
        
    });
  

	$('#sqlCIGroup tbody').on( 'click', '.sqlCI-add', function (e) {	
		var id = $(this).find("a").attr('sql-ci-group');
		$('[data-role="createSQLCI"]:input[id="sqlCIGroupId"]').val(id);
		var targeted_popup_class = $(this).find("a").attr('data-popup-open');
		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
	    e.preventDefault();		
	} );
	
	$('#sqlCIGroup tbody').on( 'click', '.sqlCI-edit', function (e) {	   
		var targeted_popup_class = $(this).find("a").attr('data-popup-open');
		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
	    e.preventDefault();		
	} );
	
	$('#sqlCIGroup tbody').on( 'click', '.sqlCIGroup-edit', function (e) {
	    var rowData = dt_sqlci_grp.row(this).data() 
	    $.each(rowData, function(index, value) {
	    	//alert(index +":"+ value);	    
	    	$('[data-role="editSQLCIGroup"]').each(function () {
	    		if($(this).attr('id') == index){$(this).val(value);}
	    	});  
	    });
	    
		var targeted_popup_class = $(this).find("a").attr('data-popup-open');
		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
	    e.preventDefault();		
	} );

	$('#sqlCIGroup tbody').on('click', 'td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = dt_sqlci_grp.row( tr );  
	        if ( row.child.isShown() ) {
	            // This row is already open - close it
	            row.child.hide();
	            tr.removeClass('shown');
	        }
	        else {
	            // Open this row
	        	searchSqlCIRequest(row);
	            tr.addClass('shown');
	        }
	 } );
	
	$('#sqlCIGroup tbody').on( 'click', '.sqlCI-edit', function (e) {
		var childRowId = $(this).closest('tr').find('[sqlci-id]').attr('sqlci-id');
		prepareEditSqlCIRequest(childRowId);
	} );
    
});