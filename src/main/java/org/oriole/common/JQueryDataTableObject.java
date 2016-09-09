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

import java.util.List;

public class JQueryDataTableObject<T> implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int iTotalRecords;
    private final int iTotalDisplayRecords;
    private final String sEcho;
    private final List<T> aaData;

    public JQueryDataTableObject(final List<T> pageContent,
            final int iTotalRecords,
            final int iTotalDisplayRecords,
            final String sEcho){

        this.aaData = pageContent;
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.sEcho = sEcho;
    }

    public int getiTotalRecords(){
        return this.iTotalRecords;
    }

    public int getiTotalDisplayRecords(){
        return this.iTotalDisplayRecords;
    }

    public String getsEcho(){
        return this.sEcho;
    }

    public List<T> getaaData(){
        return this.aaData;
    }
}
