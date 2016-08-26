package org.oriole.document;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DeployRequest {
	@Id
	private long    id; //Per SQL CI Request
	private String  status; // SUCCESS , FAIL, SCHEDULE, HOLD

	private long   sqlCiId;	
	private String description;
	private String targetDatabase;		
	private String  requestBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date 	requestTs;	
	private Date 	executedTs;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date    completedTs;

	public DeployRequest(long id) {		
		this.id = id;	
	}

	public long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getSqlCiId() {
		return sqlCiId;
	}

	public void setSqlCiId(long sqlCiId) {
		this.sqlCiId = sqlCiId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTargetDatabase() {
		return targetDatabase;
	}

	public void setTargetDatabase(String targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public Date getRequestTs() {
		return requestTs;
	}

	public void setRequestTs(Date requestTs) {
		this.requestTs = requestTs;
	}
	
	public Date getExecutedTs() {
		return executedTs;
	}

	public void setExecutedTs(Date executedTs) {
		this.executedTs = executedTs;
	}

	public Date getCompletedTs() {
		return completedTs;
	}

	public void setCompletedTs(Date completedTs) {
		this.completedTs = completedTs;
	}

}
