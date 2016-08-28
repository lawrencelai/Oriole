package org.oriole.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "ResourcePool")
public class ResourcePool {
	@Id
	private long id;
	
	private String name;
	private boolean active;
	
	private String type;
	
	private String username;
	private String password;

	private String createdBy;
	private String updatedBy;

	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		createdTs;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date   		updatedTs;
	
	public ResourcePool(long id,String type) {	
		this.id = id;
		this.type = type;
	}

	public void setId(long id) {
		this.id = id;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
}
