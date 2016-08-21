function initSqlCategory(){		
	    $.ajax(
	    {
	        url: "http://localhost:8080/api/sqlCategory/searchAll",
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