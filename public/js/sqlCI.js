 $(document).ready(function () {
			  var mime = 'text/x-sql';		 
			  window.editor = CodeMirror.fromTextArea(document.getElementById('statement'), {
			    mode: mime,
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
			  })
});

function searchSqlCIRequest(row){	
	$.ajax({
		url: "http://localhost:8080/sqlCI/searchByGroupId?sqlCIGroupId="+row.data().id,
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
 
function createSqlCIRequest(){
	var data = {};
	    		
	$('[data-role="createSQLCI"]').each(function () {
		data[$(this).attr('id')] = $(this).val();
	});
	    
	$.ajax(
	{
		url: "http://localhost:8080/sqlCI/create",
		data: data,
	    success: function (msg) {
	    	$('[data-popup="createSQLCIGroup"]').fadeOut(350);	    
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
	        url: "http://localhost:8080/sqlCIGroup/change",
	        data: data,
	        success: function (msg) {
	        	 $('[data-popup="editSQLCIGroup"]').fadeOut(350);
	        	 dttable.ajax.reload();
	        },
	        error:function(xhr, ajaxOptions, thrownError){ 
	            alert(xhr.status); 
	            alert(thrownError); 
	         },
	    	complete : function (msg) {
	    		$("#lblForAjax").text(msg);
	    	}
	    });
	}