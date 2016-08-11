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
