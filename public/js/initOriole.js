$(document).ready(function () {
	var dt_sqlci_grp;
	var dt_dbpool;
	var dt_mantispool;
	initMenu();
	prepareCodeMirror('cm-create-sqlci');
	prepareCodeMirror('cm-edit-sqlci');
	$('[page]').fadeOut(0); 
	$('[bootstrap-switch]').bootstrapSwitch();	
	commonFunction();
	
});
function commonFunction(){
    //----- OPEN
    $('[data-popup-open]').on('click', function(e)  {
        var targeted_popup_class = jQuery(this).attr('data-popup-open');
        $('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 
        e.preventDefault();
    });
 
    //----- CLOSE
    $('[data-popup-close]').on('click', function(e)  {
        var targeted_popup_class = jQuery(this).attr('data-popup-close');
        if(targeted_popup_class == "editSQLCIGroup" || targeted_popup_class == "createSQLCIGroup"){
             $('[data-role="'+targeted_popup_class+'"]#errorMsg').text("");
        }
        $('[data-popup="' + targeted_popup_class + '"]').fadeOut(350);
 
        e.preventDefault();
    });

}
function initMenu(){	
	$(".oriole-menuitem").mouseover(function() {
		 $(this).animate({ backgroundColor:'##aff1ff'},1000);
	}).mouseout(function() {
		$(this).animate({ backgroundColor:'#ffffff'},1000);
	});
	
	$('[menu-item]').on('click', function(e)  {  
		var menuitem = $(this).attr('menu-item');
		
		if(menuitem=="deployrequest-view"){
			initDeployRequestDataTable();
		}
		if(menuitem=="admin-view"){
			 initDatabasePoolDataTable();
			 initMantisPoolDataTable();
		}
		if(menuitem=="sql-ci-view"){
			initSqlCIGroupDataTable();
			initSqlCategory();
			initRestrictedDBPool();
		}
		$('[page]').fadeOut(0); 
	    $('[page="' + menuitem + '"]').fadeIn(150); 
	   
	    e.preventDefault();
	});
}
