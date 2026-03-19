package com.fleetmgmt.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * AuditLog JPA Entity — maps to the Audit_Log table in MySQL.
 * Records every system action with timestamp for accountability.
 */
@Entity
@Table(name = "Audit_Log")
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Log_ID")
    private Integer logId;

    @Column(name = "Action_Type", nullable = false, length = 50)
    private String actionType;

    @Column(name = "Table_Name", nullable = false, length = 50)
    private String tableName;

    @Column(name = "Record_ID")
    private Integer recordId;

    @Column(name = "Description", length = 255)
    private String description;

    @Column(name = "Action_Date")
    private LocalDateTime actionDate;

    // Constructors
    public AuditLogEntity() {}

    public AuditLogEntity(String actionType, String tableName, Integer recordId, String description) {
        this.actionType = actionType;
        this.tableName = tableName;
        this.recordId = recordId;
        this.description = description;
        this.actionDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getLogId() { return logId; }
    public void setLogId(Integer logId) { this.logId = logId; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public Integer getRecordId() { return recordId; }
    public void setRecordId(Integer recordId) { this.recordId = recordId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getActionDate() { return actionDate; }
    public void setActionDate(LocalDateTime actionDate) { this.actionDate = actionDate; }
}
