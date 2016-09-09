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

import org.oriole.common.CommonEnum.ResourceType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ResourcePool")
public class DatabasePool extends ResourcePool{
			
	private String host;
	private String port;
	private String serviceName;
	private String sid;
	
	private boolean restricted;
	
	
	public DatabasePool(long id) {
		super(id,ResourceType.DATABASE.name());	
	}
	

	public long getId() {
		return super.getId();
	}	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public boolean isRestricted() {
		return restricted;
	}
	
	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}
}
