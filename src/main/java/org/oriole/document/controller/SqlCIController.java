package org.oriole.document.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.oriole.common.CommonEnum.DatabaseSequence;
import org.oriole.common.CommonUtils;
import org.oriole.document.SqlCI;
import org.oriole.document.SqlCIGroup;
import org.oriole.document.dao.SequenceDao;
import org.oriole.document.exception.ErrorDetail;
import org.oriole.document.repository.SqlCIGroupRepository;
import org.oriole.document.repository.SqlCIRepository;
import org.oriole.exception.InputDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlCIController {

	@Autowired
	private SqlCIGroupRepository sqlCIGroupRepository;

	@Autowired
	private SqlCIRepository sqlCIRepository;

	@Autowired
	private SequenceDao sequenceDao;

	// Exception

	@ExceptionHandler(InputDataException.class)
	public ErrorDetail sqlCIError(HttpServletRequest request, Exception exception) {
		ErrorDetail error = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage(),
				request.getRequestURL().append("/error").toString());
		return error;
	}

	@RequestMapping("/api/sqlCI/searchByGroupId")
	public @ResponseBody List<SqlCI> getSqlCIListByGroupId(@RequestParam long sqlCIGroupId) {
		return sqlCIRepository.findByGroupID(sqlCIGroupId);
	}
	
	@RequestMapping("/api/sqlCI/searchById")
	public @ResponseBody SqlCI getSqlCIListById(@RequestParam long sqlCiId) {
		return sqlCIRepository.findById(sqlCiId);
	}

	@RequestMapping("/api/sqlCI/create")
	public @ResponseBody SqlCI createSqlCI(
			@RequestParam Long sqlCIGroupId, 
			@RequestParam String createdBy,
			@RequestParam String sqlCategory,
			@RequestParam String statement,
			@RequestParam Boolean active ,
			String description) {

		SqlCIGroup sqlCIGroup = sqlCIGroupRepository.findById(sqlCIGroupId);

		CommonUtils.validateNullObj(sqlCIGroup, "SQL CI Group ID not found");

		List<SqlCI> sqlCIList = sqlCIRepository.findByGroupID(sqlCIGroup.getId());
		SqlCI sqlCI = new SqlCI(sequenceDao.getNextSequenceId(DatabaseSequence.SQL_CI.getSequenceName()),
				sqlCIGroup.getId());
		if (sqlCIList == null) {
			sqlCI.setSequence(1);
		} else {
			sqlCI.setSequence(sqlCIList.size() + 1);
		}

		sqlCI.setType(sqlCategory);
		sqlCI.setStatement(statement);
		sqlCI.setDescription(description);
		sqlCI.setCreatedBy(createdBy);
		sqlCI.setCreatedTs(new Date());
		sqlCI.setUpdatedBy(createdBy);
		sqlCI.setUpdatedTs(new Date());

		return sqlCIRepository.insert(sqlCI);

	}

	@RequestMapping("/api/sqlCI/change")
	public @ResponseBody void updateSqlCI(@RequestParam long id, @RequestParam int sequence,
			@RequestParam String createdBy, @RequestParam String updatedBy, @RequestParam String sqlCategory,
			@RequestParam String statement, @RequestParam Boolean active, String description) {

		SqlCI sqlCI = sqlCIRepository.findById(id);
		sqlCI.setActive(active);
		sqlCI.setSequence(sequence);
		sqlCI.setCreatedBy(createdBy);
		sqlCI.setUpdatedBy(updatedBy);
		sqlCI.setType(sqlCategory);
		sqlCI.setStatement(createdBy);
		sqlCI.setDescription(description);
		sqlCI.setUpdatedTs(new Date());

		sqlCIRepository.save(sqlCI);

	}

	@RequestMapping("/api/sqlCI/delete")
	public @ResponseBody void deleteById(@RequestParam Long sqlCIId) {

		SqlCI sqlCI = sqlCIRepository.findById(sqlCIId);

		CommonUtils.validateNullObj(sqlCI, "SQL CI not found");

		sqlCIRepository.delete(sqlCI);
	}
}