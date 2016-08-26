function createDeployRequestbyGroup(){
	var data = {};
	    		
	$('[data-role="createGroupDeployRequest"]').each(function (i, el) {		
		data[$(this).attr('id')] = $(this).val();	
	});
	    
	$.ajax(
	{
		url: "http://localhost:8080/api/deployRequest/createBySqlCIGroup",
		data: data,
	    success: function (msg) {
	    	$('[data-popup="createGroupDeployRequest"]').fadeOut(350);	    
	        	
	    },
	    error:function(xhr, ajaxOptions, thrownError){ 
	    	alert(xhr.status); 
	        alert(thrownError); 
	    }
	});
}
function createDeployRequest(){
	var data = {};
	    		
	$('[data-role="createDeployRequest"]').each(function (i, el) {		
		data[$(this).attr('id')] = $(this).val();	
	});
	    
	$.ajax(
	{
		url: "http://localhost:8080/api/deployRequest/create",
		data: data,
	    success: function (msg) {
	    	$('[data-popup="createDeployRequest"]').fadeOut(350);	    
	        
	    },
	    error:function(xhr, ajaxOptions, thrownError){ 
	    	alert(xhr.status); 
	        alert(thrownError); 
	    }
	});
}
