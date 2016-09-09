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

package org.oriole.document.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.MTOMFeature;

import org.oriole.document.SqlCI;
import org.oriole.document.repository.SqlCIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class SqlExportController {
	@Autowired
	private SqlCIRepository sqlCIRepository;
	
    @RequestMapping(value="/api/sql/export", method=RequestMethod.GET)
    public void export(String key,HttpServletResponse response) throws Exception {
    	downloadFile(response);
    }

    private void downloadFile(HttpServletResponse response) {
        ServletOutputStream servletStream;
        try {            
            String fileName = "abc.sql";   
            File file = new File(fileName);
            if(file.exists()){
            	file.delete();
            }
            OutputStream outStream = new FileOutputStream(file, true);
            BufferedOutputStream bufferStream = new BufferedOutputStream(outStream);
            List<SqlCI> sqlCIList = sqlCIRepository.findByGroupID(1);
        	for(SqlCI sqlCI : sqlCIList){		   		
        		bufferStream.write(sqlCI.getStatement().getBytes("UTF-8"));
        		bufferStream.write(System.getProperty("line.separator").getBytes());        	
        	} 
            bufferStream.close();

            response.setContentType("application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            
            InputStream inputStream = new FileInputStream(file);
            servletStream = response.getOutputStream();
            byte[] bufferData = new byte[1024*1024];
            int read = 0;
            while ((read = inputStream.read(bufferData)) != -1) {
                servletStream.write(bufferData, 0, read);       
            }
            servletStream.flush();
            servletStream.close();
            inputStream.close();
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}