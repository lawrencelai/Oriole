package org.oriole.common;

import org.oriole.exception.InputDataException;

public class CommonUtils {	
	public static void validateNullObj(Object obj, String message){
		if(obj == null){
			throw new InputDataException(message);   
		}
	}
}