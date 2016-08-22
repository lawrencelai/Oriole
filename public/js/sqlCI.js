function prepareCodeMirror(elementName){
	var setting =  {
			mode: 'text/x-sql',
			autoRefresh:true,
			indentWithTabs: true,
			smartIndent: true,
			lineNumbers: true,
			matchBrackets : true,
			autofocus: true,
			extraKeys: {"Ctrl-Space": "autocomplete",},
			hintOptions: {tables: {
							users: {name: null, score: null, birthDate: null},
							countries: {name: null, population: null, size: null}
							}
				    	}
		};
	CodeMirror.fromTextArea(document.getElementsByName(elementName)[0], setting).on('change',function(cMirror){
		  // get value right from instance
		document.getElementsByName(elementName)[0].value = cMirror.getValue();
	});
	
} 
 
function searchSqlCIRequest(row){	
	$.ajax({
		url: "http://localhost:8080/api/sqlCI/searchByGroupId?sqlCIGroupId="+row.data().id,
		success:function(response){ 
			var datachildrow = formatSQLCI (row.data().id,response);
			row.child(datachildrow).show();
			    
		},
	    error:function(xhr, ajaxOptions, thrownError){ 
	    	alert(xhr.status); 
	        alert(thrownError); 
	    }	 
	});
}

function prepareEditSqlCIRequest(id){	
	$.ajax({
		url: "http://localhost:8080/api/sqlCI/searchById?sqlCiId="+id,
		success:function(response){ 
			
		    $.each(response, function(index, value) {	    
		    	$('[data-role="editSQLCI"]').each(function () {
		    		if($(this).attr('id') == index){
		    			if($(this).attr('id')=='active'){
		    				$(this).bootstrapSwitch('state',value);
		    			}else{
		    				$(this).val(value);
		    			}
		    		}
		    	});  
		    });	
		    var targeted_popup_class = $(this).find("a").attr('data-popup-open');
			$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350); 			    
		},
	    error:function(xhr, ajaxOptions, thrownError){ 
	    	alert(xhr.status); 
	        alert(thrownError); 
	    }	 
	});
}
 
function createSqlCIRequest(){
	var data = {};
	    		
	$('[data-role="createSQLCI"]').each(function (i, el) {		
		data[$(this).attr('id')] = $(this).val();	
	});
	    
	$.ajax(
	{
		url: "http://localhost:8080/api/sqlCI/create",
		data: data,
	    success: function (msg) {
	    	$('[data-popup="createSQLCI"]').fadeOut(350);	    
	        	dttable.ajax.reload();
	    },
	    error:function(xhr, ajaxOptions, thrownError){ 
	    	alert(xhr.status); 
	        alert(thrownError); 
	    }
	});
}

function editSqlCIRequest(){
	    var data = {};
	    		
	    $('[data-role="editSQLCI"]').each(function () {
	    	data[$(this).attr('id')] = $(this).val();
	    });

	    $.ajax(
	    {
	        url: "http://localhost:8080/api/sqlCI/change",
	        data: data,
	        success: function (msg) {
	        	 $('[data-popup="editSQLCI"]').fadeOut(350);
	        	 dt_sqlci_grp.ajax.reload();
	        },
	        error:function(xhr, ajaxOptions, thrownError){ 
	            alert(xhr.status); 
	            alert(thrownError); 
	         }	    	
	    });
	}