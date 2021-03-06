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

import org.oriole.common.CommonEnum.ResourceType;
import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.oriole.document.MantisPool;
import org.oriole.document.repository.MantisPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.net.URL;
import java.util.List;

@RestController
public class MantisController {

    private String user;
    private String pwd;
    private MantisConnectLocator mcl;
    private MantisConnectPortType portType;

    @Autowired
    private MantisPoolRepository mantisPoolRepository;

    private void prepareMantisConnector() throws Exception {
        List<MantisPool> mantisPools = mantisPoolRepository.findByTypeAndActive(ResourceType.MANTIS.name(), true);
        if (!mantisPools.isEmpty() && mantisPools.size() == 1) {
            URL url = new URL(mantisPools.get(0).getUrl());
            user = mantisPools.get(0).getUsername();
            pwd = mantisPools.get(0).getPassword();
            mcl = new MantisConnectLocator();
            portType = mcl.getMantisConnectPort(url);
        }
    }

    @RequestMapping("/api/mantis/getIssue")
    public
    @ResponseBody
    IssueData getMantisById(@RequestParam BigInteger bugid) throws Exception {

        prepareMantisConnector();

        IssueData aIssueData = portType.mc_issue_get(user, pwd, bugid);
        System.out.println(aIssueData.getId());
        System.out.println(aIssueData.getSummary());

        return aIssueData;
    }

}
