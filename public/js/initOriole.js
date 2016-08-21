$(document).ready(function () {
	var dttable_ci_grp;
	var dt_dbpool;
	initSqlCategory();	
	initMenu();
	prepareCodeMirror();
	$('[page]').fadeOut(0); 
	$("[bootstrap-switch]").bootstrapSwitch();	
});