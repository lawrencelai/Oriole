$(function() {
    $('[menu-item]').on('click', function(e)  {  
    	var menuitem = $(this).attr('menu-item');
    	if(menuitem=="admin-view"){
    		 initDatabasePoolDataTable();
    	}
    	if(menuitem=="sql-ci-view"){
    		 initSqlCIGroupDataTable();
    	}
    	$('[page]').fadeOut(350); 
        $('[page="' + menuitem + '"]').fadeIn(350); 
       
        e.preventDefault();
    });
    
    $('[data-role="refresh-SqlCI-Group-dt"]').on('click', function(e)  {      	
    	$('#sqlCIGroup').DataTable().ajax.reload();
    });
    //----- OPEN
    $('[data-popup-open]').on('click', function(e)  {
        var targeted_popup_class = jQuery(this).attr('data-popup-open');
        $('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
        e.preventDefault();
    });
 
    //----- CLOSE
    $('[data-popup-close]').on('click', function(e)  {
        var targeted_popup_class = jQuery(this).attr('data-popup-close');
        $('[data-popup="' + targeted_popup_class + '"]').fadeOut(350);
 
        e.preventDefault();
    });
    
    //----- CLOSE
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
  
    $('.datepick').each(function(){
        $(this).datepicker();
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
	    var rowData = dttable_ci_grp.row(this).data() 
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
	        var row = dttable_ci_grp.row( tr );  
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
		searchSqlCIRequestById(childRowId);
//	    $.each(childRows, function(index, value) {
//    
//	    	$('[data-role="editSQLCIGroup"]').each(function () {
//	    		if($(this).attr('id') == index){$(this).val(value);}
//	    	});  
//	    });
//	    
//		var targeted_popup_class = $(this).find("a").attr('data-popup-open');
//		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
//	    e.preventDefault();		
	} );
    
});