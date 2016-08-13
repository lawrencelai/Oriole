package org.oriole.document;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SqlCategory {
	
	@Id	
	private long 		id;  //Per SQL CI Request

	private String		name;
	private String      description;
		
	private String      createdBy;
	private String      updatedBy;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		createdTs;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		updatedTs;

	public SqlCategory(long id, String name,String description, String createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
		this.createdTs = new Date();
		this.updatedBy = createdBy;
		this.updatedTs = new Date();
	}	

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
