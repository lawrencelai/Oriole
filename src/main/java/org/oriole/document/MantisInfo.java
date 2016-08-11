package org.oriole.document;

import org.springframework.data.annotation.Id;

public class MantisInfo {
	@Id	
	private long 		id;	
	private long 		sqlCIGroupID;
	
	private String      summany;	
	private String		description;
	private String		targetVersion;
	private String     	targetBranch;

	public MantisInfo(long id, long sqlCIGroupID) {
		this.id = id;
		this.sqlCIGroupID = sqlCIGroupID;
	}

	public MantisInfo() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public long getSqlCIGroupID() {
		return sqlCIGroupID;
	}

	public String getSummany() {
		return summany;
	}

	public void setSummany(String summany) {
		this.summany = summany;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTargetVersion() {
		return targetVersion;
	}

	public void setTargetVersion(String targetVersion) {
		this.targetVersion = targetVersion;
	}

	public String getTargetBranch() {
		return targetBranch;
	}

	public void setTargetBranch(String targetBranch) {
		this.targetBranch = targetBranch;
	}
	
	
}
