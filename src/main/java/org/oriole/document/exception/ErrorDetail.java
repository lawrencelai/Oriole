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

package org.oriole.document.exception;

public class ErrorDetail {
	
	private int status;
	private String message;
	private String url;
		
	public ErrorDetail(int status, String message, String url) {
		super();
		this.status = status;
		this.message = message;
		this.url = url;
	}

	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}

	public String getUrl() {
		return url;
	}

} 