package org.oriole.document;

import org.oriole.common.CommonEnum.ResourceType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ResourcePool")
public class DatabasePool extends ResourcePool{
			
	private String host;
	private String port;
	private String serviceName;
	private String sid;
	
	
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
}
