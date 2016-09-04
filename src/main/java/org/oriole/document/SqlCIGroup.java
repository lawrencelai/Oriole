package org.oriole.document;


import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SqlCIGroup {
	
	@Id	
	private long 		id;  //Per SQL CI Request
	private String      owner;	

	private String      description;

	private boolean 	active;	
	private boolean 	archived;
	
	private boolean     approved;
	
	private String      approvedBy;	
	private String      createdBy;
	private String      updatedBy;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		createdTs;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		updatedTs;	
	
	private MantisInfo  mantisInfo;
	
	public SqlCIGroup(long id) {	
		this.id = id;	
	}
	public long getId() {
		return id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
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
	public boolean isArchived() {
		return archived;
	}
	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}
	public Date getUpdatedTs() {
		return updatedTs;
	}
	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}
	public MantisInfo getMantisInfo() {
		return mantisInfo;
	}
	public void setMantisInfo(MantisInfo mantisInfo) {
		this.mantisInfo = mantisInfo;
	}	
}
