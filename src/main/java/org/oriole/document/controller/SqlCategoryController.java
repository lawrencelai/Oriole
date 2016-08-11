package org.oriole.document.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.oriole.common.JsonObject;
import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.document.SqlCategory;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.SqlCategoryRepository;
import org.oriole.exception.InputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlCategoryController {
	
	private static final Logger logger = Logger.getLogger(SqlCategoryController.class);
		
	@Autowired
	private SqlCategoryRepository sqlCategoryRepository;
	
	@Autowired
	private SequenceDao sequenceDao;
	
	//Exception
	
	@ExceptionHandler(InputDataException.class)
	public ErrorDetail sqlCIError(HttpServletRequest request, Exception exception) {
	    ErrorDetail error = new ErrorDetail(HttpStatus.BAD_REQUEST.value(),
	    		exception.getLocalizedMessage(), 
	    		request.getRequestURL().append("/error").toString());
	    return error;
	}
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/sqlCategory/searchAll")
	public @ResponseBody List<JsonObject> getSqlCategory() {
	   List<SqlCategory> sqlCategories = sqlCategoryRepository.findAll();
	   List<JsonObject> sqlCategoryJObject = new ArrayList<JsonObject>();
	   
	   for(SqlCategory sqlCategory:sqlCategories){
		   sqlCategoryJObject.add(new JsonObject(String.valueOf(sqlCategory.getId()),sqlCategory.getName()));
	   }
	   
	   return sqlCategoryJObject;
	}
	 
    @CrossOrigin(origins = "http://localhost")
    @RequestMapping("/sqlCategory/searchById")
    public @ResponseBody SqlCategory getSqlCategoryById(Long id) { 
    	logger.debug("getSQLCIGroup - Parameter :" + id);
    	
    	if(id == null){
    		throw new InputDataException("No SqL Category ID");
    	}
    	return sqlCategoryRepository.findById(id);
    }
    
    @CrossOrigin(origins = "http://localhost")
    @RequestMapping("/sqlCategory/create")
    public @ResponseBody SqlCategory createSqlCategory(String name,String description, String createdBy) {
   
    	logger.debug(String.format("[ws:createSqlCIGroup] [Parameter] %s %s %s ", name, createdBy, description));
    	if(name == null){
    		throw new InputDataException("No SQL Category Name");
    	}
    	if(createdBy == null){
    		throw new InputDataException("No created By");
    	}
    	if(description == null){
    		throw new InputDataException("No Description");
    	}    	
    	
    	SqlCategory sqlCategory = new SqlCategory(sequenceDao.getNextSequenceId(DatabaseSequence.SQL_CATEGORY.name()),name,description,createdBy);

    	return sqlCategoryRepository.insert(sqlCategory);
    	
    }
    @CrossOrigin(origins = "http://localhost")    
    @RequestMapping("/sqlCategory/change")
    public @ResponseBody void updateSqlCategory(Long id, String name,String description, String createdBy , String updatedBy) {
   
    	logger.debug(String.format("[updateSqlCategory] [Parameter: %s %s %s %s %s %s]", id, description, updatedBy, description));
    	
    	if(id == null){
    		throw new InputDataException("No SQL Category ID");
    	}
    	
    	SqlCategory sqlCategory = sqlCategoryRepository.findById(id);
    	
    	if(sqlCategory == null){
    		throw new InputDataException("No SQL Category Exists");
    	}
    
    	sqlCategory.setName(name);
    	sqlCategory.setDescription(description);
    	sqlCategory.setCreatedBy(createdBy);
    	sqlCategory.setUpdatedBy(updatedBy);
    	sqlCategory.setUpdatedTs(new Date());
    	
    	sqlCategoryRepository.save(sqlCategory);
    	
    }
   
    
}