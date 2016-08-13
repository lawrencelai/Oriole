package org.oriole.document;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SqlCI {
	@Id	
	private long 		id;      //SQL CI 	
	private long 		groupID; //SQL CI Group ID	
	private int 		sequence;	
	private String		type;
	
	
	private String      description;
	private boolean 	active;

	private String		statement;
	private String		fallbackStatement;	
	
	private String      createdBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		createdTs;
	
	private String      updatedBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		updatedTs;

	public SqlCI(long id, long groupID) {
		this.id = id;
		this.groupID = groupID;
	}
	public long getId() {
		return id;
	}
	public long getGroupID() {
		return groupID;
	}
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public String getFallbackStatement() {
		return fallbackStatement;
	}
	public void setFallbackStatement(String fallbackStatement) {
		this.fallbackStatement = fallbackStatement;
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
