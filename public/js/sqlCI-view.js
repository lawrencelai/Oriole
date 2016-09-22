$(function() {
    $('[data-role="refresh-SqlCI-Group-dt"]').on('click', function(e)  {     	
    	$('#sqlCIGroup').DataTable().ajax.reload();
    });

   $('[data-role="refresh-deploy-request-dt"]').on('click', function(e)  {
        $('#deployRequest').DataTable().ajax.reload();
   });

    $('[sync-mantis]').on('click', function(e)  {
        var targeted_class = $(this).attr("sync-mantis")
        $('[data-role="'+targeted_class+'"]#errorMsg').text("");
    	syncMantis($(this).attr('sync-mantis'));
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
        if(target_ws == "createGroupDeployRequest"){
        	createDeployRequestbyGroup();
        }
        if(target_ws == "createDeployRequest"){
        	createDeployRequest();
        }
        
    });
   

	$('#sqlCIGroup tbody').on( 'click', '.sqlCI-add', function (e) {	
		var id = $(this).find("a").attr('sql-ci-group');
		$('[data-role="createSQLCI"]:input[id="groupId"]').val(id);
		var targeted_popup_class = $(this).find("a").attr('data-popup-open');
		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
	    e.preventDefault();		
	} );
	
	$('#sqlCIGroup tbody').on( 'click', '.sqlCI-edit', function (e) {	   
		var targeted_popup_class = $(this).find("i").attr('data-popup-open');
		$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
	    e.preventDefault();		
	} );
	
	$('#sqlCIGroup tbody').on( 'click', '.sqlCIGroup-edit', function (e) {
	    var rowData = dt_sqlci_grp.row(this).data() ;
	    prepareSqlCIGroup(rowData.id);
	    
		var targeted_popup_class = $(this).find("i").attr('data-popup-open');
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
	});
	$('#sqlCIGroup tbody').on( 'click', '.sqlCI-edit', function (e) {
		var childRowId = $(this).closest('tr').find('[sqlci-id]').attr('sqlci-id');
		prepareEditSqlCIRequest(childRowId);
	} );

	
	$('#sqlCIGroup tbody').on( 'click', 'tr', function (e) {
	    $('[data-popup-open]').on('click', function() {
	        var targeted_deploy = $(this).attr('deploy-target');
            var targeted_popup_class = $(this).attr('data-popup-open');
	        if(targeted_popup_class == "createGroupDeployRequest"){
	            var rowData = dt_sqlci_grp.row($(this).closest('tr')).data();
                $('[data-popup="' + targeted_popup_class + '"]').find('#id').val(rowData.id);
                $('[data-popup="' + targeted_popup_class + '"]').find('#description').val(rowData.description);
                $('[data-popup="' + targeted_popup_class + '"]').find('#targetDatabase').val(targeted_deploy);
                $('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);
	  		}else if(targeted_popup_class == "createDeployRequest"){
	  			  var id = $(this).attr('ci-id');
	  		     $('[data-popup="' + targeted_popup_class + '"]').find('#id').val(id);
                // $('[data-popup="' + targeted_popup_class + '"]').find('#description').val(rowData.description);
                 $('[data-popup="' + targeted_popup_class + '"]').find('#targetDatabase').val(targeted_deploy);
                 $('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);
	  		}
	  	    e.preventDefault();
	    });
	});
});