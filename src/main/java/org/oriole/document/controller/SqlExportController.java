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

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.oriole.document.SqlCI;
import org.oriole.document.SqlCIGroup;
import org.oriole.document.repository.SqlCIGroupRepository;
import org.oriole.document.repository.SqlCIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
public class SqlExportController {
    @Autowired
    private SqlCIRepository sqlCIRepository;

    @Autowired
    private SqlCIGroupRepository sqlCIGroupRepository;

    @RequestMapping(value = "/api/sql/export", method = RequestMethod.GET, produces = "application/zip")
    public byte[] exportZip(
            @RequestParam long groupid, String key, HttpServletResponse response) throws Exception {
        //setting headers
        response.setContentType("application/zip");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        //simple file list, just for tests
        ArrayList<File> files = prepareSqlFileByGroupId(groupid);

        //packing files
        for (File file : files) {
            //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private ArrayList<File> prepareSqlFileByGroupId(long groupid) {
        SqlCIGroup sqlCIGroup = sqlCIGroupRepository.findById(groupid);
        ArrayList<File> files = prepareSqlFile(sqlCIGroup);
        return files;
    }

    private ArrayList<File> prepareSqlFile(SqlCIGroup sqlCIGroup) {

        List<SqlCI> sqlCIList = sqlCIRepository.findByGroupID(sqlCIGroup.getId());
        ArrayList<File> fileList = new ArrayList<>(sqlCIList.size());
        StringBuilder fileName = null;
        int counter = 0;
        try {
            for (SqlCI sqlCI : sqlCIList) {
                //prepare file and file
                fileName = new StringBuilder(Long.toString(sqlCIGroup.getMantisInfo().getId())).append("_")
                        .append(sqlCI.getSequence()).append("_")
                        .append(sqlCI.getDescription()).append("_")
                        .append(sqlCI.getType()).append(".sql");

                File file = new File(fileName.toString());
                if (file.exists()) {
                    file.delete();
                }

                OutputStream outStream = new FileOutputStream(file, true);
                BufferedOutputStream bufferStream = new BufferedOutputStream(outStream);
                bufferStream.write(sqlCI.getStatement().getBytes("UTF-8"));
                bufferStream.write(System.getProperty("line.separator").getBytes());
                bufferStream.close();
                fileList.add(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

}