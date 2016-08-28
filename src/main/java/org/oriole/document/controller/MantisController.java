package org.oriole.document.controller;

import java.math.BigInteger;
import java.net.URL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;

@RestController
public class MantisController {
	
	private String user = "guest";  
	private String pwd = "";  
	private MantisConnectLocator mcl ;
	private MantisConnectPortType portType ;
	
	
	private void prepareMantisConnector() throws Exception{
		 URL url = new URL("https://www.mantisbt.org/bugs/api/soap/mantisconnect.php"); 
		 mcl = new MantisConnectLocator();  
	     portType = mcl.getMantisConnectPort(url);  	  
	}
	
	@RequestMapping("/api/mantis/getIssue")
	public @ResponseBody IssueData getMantisById(@RequestParam long bugid) throws Exception {
		 		
		prepareMantisConnector();
	
	    IssueData aIssueData = portType.mc_issue_get(user, pwd, new BigInteger("13547"));  
        System.out.println(aIssueData.getId());  
        System.out.println(aIssueData.getSummary()); 
        
        return aIssueData;    
	}
	
}
