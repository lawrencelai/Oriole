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

package org.oriole.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class DeployRequest {
    @Id
    private long id; //Per SQL CI Request
    private String status; // SUCCESS , FAIL, SCHEDULE, HOLD

    private long sqlCiId;
    private String description;
    private String targetDatabase;

    private String requestBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestTs;
    private LocalDateTime executedTs;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedTs;

    public DeployRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getSqlCiId() {
        return sqlCiId;
    }

    public void setSqlCiId(long sqlCiId) {
        this.sqlCiId = sqlCiId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetDatabase() {
        return targetDatabase;
    }

    public void setTargetDatabase(String targetDatabase) {
        this.targetDatabase = targetDatabase;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public LocalDateTime getRequestTs() {
        return requestTs;
    }

    public void setRequestTs(LocalDateTime requestTs) {
        this.requestTs = requestTs;
    }

    public LocalDateTime getExecutedTs() {
        return executedTs;
    }

    public void setExecutedTs(LocalDateTime executedTs) {
        this.executedTs = executedTs;
    }

    public LocalDateTime getCompletedTs() {
        return completedTs;
    }

    public void setCompletedTs(LocalDateTime completedTs) {
        this.completedTs = completedTs;
    }
}
