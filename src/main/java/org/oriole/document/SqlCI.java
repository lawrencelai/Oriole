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

public class SqlCI {
    @Id
    private long id;      //SQL CI
    private long groupID; //SQL CI Group ID
    private int sequence;
    private String type;

    private String restrictedDatabase;

    private String description;
    private boolean active;
    private boolean archived;

    private String statement;
    private String fallbackStatement;

    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdTs;

    private String updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedTs;

    public SqlCI(long id, long groupID) {
        this.id = id;
        this.groupID = groupID;
    }

    public long getId() {
        return id;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRestrictedDatabase() {
        return restrictedDatabase;
    }

    public void setRestrictedDatabase(String restrictedDatabase) {
        this.restrictedDatabase = restrictedDatabase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getFallbackStatement() {
        return fallbackStatement;
    }

    public void setFallbackStatement(String fallbackStatement) {
        this.fallbackStatement = fallbackStatement;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }
}
