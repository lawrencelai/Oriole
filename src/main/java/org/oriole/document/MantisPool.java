package org.oriole.document;

import org.oriole.common.CommonEnum.ResourceType;

public class MantisPool extends ResourcePool{
			
	private String url;
	
	public MantisPool(long id) {
		super(id,ResourceType.MANTIS.name());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
