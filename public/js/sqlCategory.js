function initSqlCategory(){		
	    $.ajax(
	    {
	        url: "http://localhost:8080/api/sqlCategory/searchAll",
	        success: function (json) {
	        	var HTML = ""; 
	             $('select[id="type"][data-role="createSQLCI"]').text(HTML); 
	             $('select[id="type"][data-role="editSQLCI"]').append(HTML); 
	             $.each(json, function(i, value) { 	                
	                HTML = HTML+"<option value" +
	                		"='"+value.name+"'>"+value.name+"</option>"; 
	             });
	             $('select[id="type"][data-role="createSQLCI"]').append(HTML); 
	             $('select[id="type"][data-role="editSQLCI"]').append(HTML); 
	        
	        },
	        error:function(xhr, ajaxOptions, thrownError){ 
	            alert(xhr.status); 
	            alert(thrownError); 
	        }	        
	    });
}