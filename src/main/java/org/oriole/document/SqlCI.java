package org.oriole.document;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SqlCI {
	@Id	
	private long 		id;  //SQL CI 	
	private long 		sqlCIGroupID; //SQL CI Group ID	
	private int 		sequence;
	
	private String      description;
	
	private boolean 	active;
	
	private String		sqlCIType;
	private String		statement;
	private Boolean     approved;
	private String      approvedBy;
	
	private String      createdBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		createdTs;
	
	private String      updatedBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		updatedTs;

	public SqlCI(long id, long sqlCIGroupID) {
		this.id = id;
		this.sqlCIGroupID = sqlCIGroupID;
	}

	public SqlCI() {
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}

	public long getSqlCIGroupID() {
		return sqlCIGroupID;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getSqlCIType() {
		return sqlCIType;
	}

	public void setSqlCIType(String sqlCIType) {
		this.sqlCIType = sqlCIType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return sqlCIType;
	}

	public void setType(String type) {
		this.sqlCIType = type;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}
}
