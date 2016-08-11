package org.oriole.common;

public class CommonEnum{

	
	public static enum MongoDbSqlCIGroup {
		id(1),
		referenceNumber(2),
		targetVersion(4),
		dependent(5),
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
	
	public static enum DatabaseSequence {
		SQL_CI_GROUP("SqlCIGroup"),
		SQL_CI("SqlCI"),
		SQL_CATEGORY("SqlCategory"),
		DATABASE_POOL("DatabasePool"),
		DEPOLYMENT_REQUEST("DeploymentRequest"),
		MANTIS_INFO("MantisInfo");		
		
		private String sequenceName;
		
		private DatabaseSequence(final String seqname) {
	        this.sequenceName = seqname;
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
		SUCCESS,FAILED,	SCHEDULED,HOLD;
	}

}




