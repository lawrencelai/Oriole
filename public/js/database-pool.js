function initRestrictedDBPool(){		
	    $.ajax(
	    {
	        url: "http://localhost:8080/api/database/restrictedList",
	        success: function (json) {
	        	var HTML = ""; 
	             $('select[id="restrictedDatabase"][data-role="createSQLCI"]').text(HTML); 
	             $('select[id="restrictedDatabase"][data-role="editSQLCI"]').text(HTML); 
	             $.each(json, function(i, value) { 	                
	                HTML = HTML+"<option value='"+value.name+"'>"+value.name+"</option>"; 
	             });	          
	             $('select[id="restrictedDatabase"][data-role="createSQLCI"]').append(HTML); 
	             $('select[id="restrictedDatabase"][data-role="editSQLCI"]').append(HTML); 
	        
	        },
	        error:function(xhr, ajaxOptions, thrownError){ 
	            alert(xhr.status); 
	            alert(thrownError); 
	        }	        
	    });
}