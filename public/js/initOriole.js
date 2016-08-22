$(document).ready(function () {
	var dt_sqlci_grp;
	var dt_dbpool;
	initSqlCategory();	
	initMenu();
	prepareCodeMirror('cm-create-sqlci');
	prepareCodeMirror('cm-edit-sqlci');
	$('[page]').fadeOut(0); 
	$("[bootstrap-switch]").bootstrapSwitch();	
	
	
});

function initMenu(){	
	$(".oriole-menuitem").mouseover(function() {
		 $(this).animate({ backgroundColor:'##aff1ff'},1000);
	}).mouseout(function() {
		$(this).animate({ backgroundColor:'#ffffff'},1000);
	});
	
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
}
