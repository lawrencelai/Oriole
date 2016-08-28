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
	var dataChildrow;
	$.ajax({
		url: "http://localhost:8080/api/sqlCI/searchByGroupId?sqlCIGroupId="+row.data().id,
		success:function(response){ 
			dataChildrow = formatSQLCI (row.data().id,response);
			row.child(dataChildrow).show();
		},
	    error:function(xhr, ajaxOptions, thrownError){ 
	    	alert(xhr.status); 
	        alert(thrownError); 
	    }
	}).done(activeDatabasePoolForCi());

}

function formatSQLCI (groupid,data,buttonHtml) {
    // `d` is the original data object for the row		
	var childrow ;
	if(data){
		for (val of data){
			if(val.active){
				activeFlag = "<i class='fa fa-circle-o-notch fa-spin' style='font-size:14px;color:green'>";
			}else{
				activeFlag = "<i class='fa fa-exclamation-circle' style='font-size:16px;color:orange'>";
			}
			
			childrow +=
				'<tr>'+					
					'<td><span sqlci-id="'+val.id+'">'+val.sequence+'<span></td>'+
					'<td>'+activeFlag+'</td>'+    	
	            	'<td>'+val.type+'</td>'+
		        	'<td>'+val.description+'</td>'+		     
		        	'<td>'+val.updatedBy+'</td>'+        
		        	'<td>'+val.updatedTs+'</td>'+
		        	'<td class="sqlCI-edit"><i class="fa fa-edit" style="font-size:24px"  data-popup-open="editSQLCI" href="#"></a></td>'+	
					'<td class="deploy-ci"></td>'+
		        '</tr>'
	            ;
		}
		childrow += '<tr>'+
    		'<td class="sqlCI-add"><a class="button-group" data-popup-open="createSQLCI" sql-ci-group="'+groupid+'" href="#">New SQL CI</a></td>'+
    	'</tr>';
	}else{
		childrow += '<tr>'+
        	'<td class="sqlCI-add"><a class="button-group" data-popup-open="createSQLCI" sql-ci-group="'+groupid+'" href="#">New SQL CI</a></td>'+        	
        '</tr>';
	}
	
	childrow += '</table>';
    return childrow;
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