/*
 *    Copyright 2016 Lawrence Lai
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.oriole.document;

import org.springframework.data.annotation.Id;

public class MantisInfo {
	@Id	
	private long 		id;	
	private long 		groupId;
	
	private String      summany;
	private String		description;
	private String		targetVersion;
	private String     	targetBranch;

	public MantisInfo(long id, long groupId) {
		this.id = id;
		this.groupId = groupId;
	}

	public MantisInfo() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
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
