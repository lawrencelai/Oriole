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

package org.oriole.common;

public class CommonEnum{

	
	public static enum MongoDbSqlCIGroup {
		id(1),
		referenceNumber(2),
		targetVersion(4),
		dependentGroupId(5),
		description (3),
		owner(0),
		approved(0),
		approvedBy(0),
		createdBy(6),
		createdTs(7),
		updatedBy(0),
		updatedTs(0);		
		
		private int dtColNumber;
		
		private MongoDbSqlCIGroup(final int dtColumnNum) {
	        this.dtColNumber = dtColumnNum;
	    }

	    
	    public static String findMongoFieldNameByColumnNum(final int dtColumnNumber) {
	    	for(MongoDbSqlCIGroup e: MongoDbSqlCIGroup.values()) {
	    		if(e.dtColNumber == dtColumnNumber) {
	    		    return e.name();
	    		}
	    	}
	    	return null;// not found
	    	
	    }
	}
	
	public static enum MongoDeployRequest {
		sqlCiId(1),
		description(2),
		targetDatabase(3),
		status(4),	
		requestBy(5),
		requestTs(6),
		executedTs(7),
		completedTs(8);		
		
		private int dtColNumber;
		
		private MongoDeployRequest(final int dtColumnNum) {
	        this.dtColNumber = dtColumnNum;
	    }

	    
	    public static String findMongoFieldNameByColumnNum(final int dtColumnNumber) {
	    	for(MongoDbSqlCIGroup e: MongoDbSqlCIGroup.values()) {
	    		if(e.dtColNumber == dtColumnNumber) {
	    		    return e.name();
	    		}
	    	}
	    	return null;// not found
	    	
	    }
	}
	
	public static enum DatabaseSequence {
		SQL_CI_GROUP("SqlCIGroup"),
		SQL_CI("SqlCI"),
		SQL_CATEGORY("SqlCategory"),
		RESOURCE_POOL("ResourcePool"),
		DEPOLYMENT_REQUEST("DeploymentRequest"),
		MANTIS_INFO("MantisInfo");		
		
		private String sequenceName;
		
		private DatabaseSequence(final String seqname) {
	        this.sequenceName = seqname;
	    }
		
	    public String getSequenceName() {
			return sequenceName;
		}

		public void setSequenceName(String sequenceName) {
			this.sequenceName = sequenceName;
		}



		public static String find(final String seqname) {
	    	for(DatabaseSequence e: DatabaseSequence.values()) {
	    		if(e.sequenceName == seqname) {
	    		    return e.name();
	    		}
	    	}
	    	return null;// not found
	    	
	    }
	}
	
	public static enum DeployRequestState {
		SUCCESS,FAIL,SCHEDULE,HOLD;
		
		public static DeployRequestState find(String str) {
		    for (DeployRequestState state : DeployRequestState.values()) {
		        if (state.name().equalsIgnoreCase(str))
		            return state;
		    }
		    return null;
		}
	}
	public static enum ResourceType {
		DATABASE,MANTIS;
		
		public static ResourceType find(String str) {
		    for (ResourceType resource : ResourceType.values()) {
		        if (resource.name().equalsIgnoreCase(str))
		            return resource;
		    }
		    return null;
		}
	}
	
}




