package org.oriole.document;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class DeploymentRequest {
	@Id
	private long id; //Per SQL CI Request
	private String status; // Success , Fail,  Scheduled, Pending

	private String  sqlCiId;	
	
	private String  targetDatabase;
		
	private String  requestBy;
	private Date 	requestTs;
	private Date    completedTs;

	public DeploymentRequest(long id) {		
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

	public String getSqlCiId() {
		return sqlCiId;
	}

	public void setSqlCiId(String sqlCiId) {
		this.sqlCiId = sqlCiId;
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

	public Date getCompletedTs() {
		return completedTs;
	}

	public void setCompletedTs(Date completedTs) {
		this.completedTs = completedTs;
	}

}
