package org.oriole.common;

public class JsonObject {	
	private String id;
	private String name;

	public JsonObject() {

	}
	
	public JsonObject(String i, String n) {
		id = i;
	    name = n;
	}

	public String getId(){
		return id;
	}

	public String getName(){
		return name;
	}
	
}
