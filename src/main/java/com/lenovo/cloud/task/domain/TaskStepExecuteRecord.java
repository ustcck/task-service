package com.lenovo.cloud.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaskStepExecuteRecord.
 */
@Entity
@Table(name = "task_step_execute_record")
public class TaskStepExecuteRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_step_id")
    private Long taskStepId;

    @Column(name = "task_step_execute_order")
    private Integer taskStepExecuteOrder;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "execute_time")
    private Instant executeTime;

    @Column(name = "finish_time")
    private Instant finishTime;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    @NotNull
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    @ManyToOne
    @JsonIgnoreProperties(value = { "taskStepExecuteRecords" }, allowSetters = true)
    private TaskExecuteRecord taskExecuteRecord;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskStepExecuteRecord id(Long id) {
        this.id = id;
        return this;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public TaskStepExecuteRecord taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskStepId() {
        return this.taskStepId;
    }

    public TaskStepExecuteRecord taskStepId(Long taskStepId) {
        this.taskStepId = taskStepId;
        return this;
    }

    public void setTaskStepId(Long taskStepId) {
        this.taskStepId = taskStepId;
    }

    public Integer getTaskStepExecuteOrder() {
        return this.taskStepExecuteOrder;
    }

    public TaskStepExecuteRecord taskStepExecuteOrder(Integer taskStepExecuteOrder) {
        this.taskStepExecuteOrder = taskStepExecuteOrder;
        return this;
    }

    public void setTaskStepExecuteOrder(Integer taskStepExecuteOrder) {
        this.taskStepExecuteOrder = taskStepExecuteOrder;
    }

    public Integer getStatus() {
        return this.status;
    }

    public TaskStepExecuteRecord status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getExecuteTime() {
        return this.executeTime;
    }

    public TaskStepExecuteRecord executeTime(Instant executeTime) {
        this.executeTime = executeTime;
        return this;
    }

    public void setExecuteTime(Instant executeTime) {
        this.executeTime = executeTime;
    }

    public Instant getFinishTime() {
        return this.finishTime;
    }

    public TaskStepExecuteRecord finishTime(Instant finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public TaskStepExecuteRecord createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public TaskStepExecuteRecord updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public TaskExecuteRecord getTaskExecuteRecord() {
        return this.taskExecuteRecord;
    }

    public TaskStepExecuteRecord taskExecuteRecord(TaskExecuteRecord taskExecuteRecord) {
        this.setTaskExecuteRecord(taskExecuteRecord);
        return this;
    }

    public void setTaskExecuteRecord(TaskExecuteRecord taskExecuteRecord) {
        this.taskExecuteRecord = taskExecuteRecord;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskStepExecuteRecord)) {
            return false;
        }
        return id != null && id.equals(((TaskStepExecuteRecord) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskStepExecuteRecord{" +
            "id=" + getId() +
            ", taskId=" + getTaskId() +
            ", taskStepId=" + getTaskStepId() +
            ", taskStepExecuteOrder=" + getTaskStepExecuteOrder() +
            ", status=" + getStatus() +
            ", executeTime='" + getExecuteTime() + "'" +
            ", finishTime='" + getFinishTime() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
