package org.oriole.document.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.common.CommonUtils;
import org.oriole.common.JsonObject;
import org.oriole.document.SqlCategory;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.SqlCategoryRepository;
import org.oriole.exception.InputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlCategoryController {

	private static final Logger logger = Logger.getLogger(SqlCategoryController.class);

	@Autowired
	private SqlCategoryRepository sqlCategoryRepository;

	@Autowired
	private SequenceDao sequenceDao;

	// Exception

	@ExceptionHandler(InputDataException.class)
	public ErrorDetail sqlCIError(HttpServletRequest request, Exception exception) {
		ErrorDetail error = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage(),
				request.getRequestURL().append("/error").toString());
		return error;
	}

	@RequestMapping("/sqlCategory/searchAll")
	public @ResponseBody List<JsonObject> getSqlCategory() {
		List<SqlCategory> sqlCategories = sqlCategoryRepository.findAll();
		List<JsonObject> sqlCategoryJObject = new ArrayList<JsonObject>();

		for (SqlCategory sqlCategory : sqlCategories) {
			sqlCategoryJObject.add(new JsonObject(String.valueOf(sqlCategory.getId()), sqlCategory.getName()));
		}

		return sqlCategoryJObject;
	}

	@RequestMapping("/sqlCategory/searchById")
	public @ResponseBody SqlCategory getSqlCategoryById(@RequestParam Long id) {

		return sqlCategoryRepository.findById(id);
	}

	@RequestMapping("/sqlCategory/create")
	public @ResponseBody SqlCategory createSqlCategory(@RequestParam String name, @RequestParam String description,
			@RequestParam String createdBy) {

		logger.debug(String.format("[ws:createSqlCIGroup] [Parameter] %s %s %s ", name, createdBy, description));

		SqlCategory sqlCategory = new SqlCategory(
				sequenceDao.getNextSequenceId(DatabaseSequence.SQL_CATEGORY.getSequenceName()), name, description,
				createdBy);

		return sqlCategoryRepository.insert(sqlCategory);

	}

	@RequestMapping("/sqlCategory/change")
	public @ResponseBody void updateSqlCategory(Long id, String name, String description, String createdBy,
			String updatedBy) {

		logger.debug(String.format("[updateSqlCategory] [Parameter: %s %s %s %s %s %s]", id, description, updatedBy,
				description));

		SqlCategory sqlCategory = sqlCategoryRepository.findById(id);

		CommonUtils.validateNullObj(sqlCategory, "No SQL Category Exists");

		sqlCategory.setName(name);
		sqlCategory.setDescription(description);
		sqlCategory.setCreatedBy(createdBy);
		sqlCategory.setUpdatedBy(updatedBy);
		sqlCategory.setUpdatedTs(new Date());

		sqlCategoryRepository.save(sqlCategory);

	}

}