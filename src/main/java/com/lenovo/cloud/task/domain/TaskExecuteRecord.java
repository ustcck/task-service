package com.lenovo.cloud.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaskExecuteRecord.
 */
@Entity
@Table(name = "task_execute_record")
public class TaskExecuteRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @NotNull
    @Column(name = "task_type", nullable = false)
    private Integer taskType;

    @Column(name = "task_name")
    private String taskName;

    @NotNull
    @Column(name = "step_record_num", nullable = false)
    private Integer stepRecordNum;

    @Column(name = "execute_time")
    private Instant executeTime;

    @Column(name = "finish_time")
    private Instant finishTime;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    @NotNull
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    @OneToMany(mappedBy = "taskExecuteRecord")
    @JsonIgnoreProperties(value = { "taskExecuteRecord" }, allowSetters = true)
    private Set<TaskStepExecuteRecord> taskStepExecuteRecords = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskExecuteRecord id(Long id) {
        this.id = id;
        return this;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public TaskExecuteRecord taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskType() {
        return this.taskType;
    }

    public TaskExecuteRecord taskType(Integer taskType) {
        this.taskType = taskType;
        return this;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public TaskExecuteRecord taskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getStepRecordNum() {
        return this.stepRecordNum;
    }

    public TaskExecuteRecord stepRecordNum(Integer stepRecordNum) {
        this.stepRecordNum = stepRecordNum;
        return this;
    }

    public void setStepRecordNum(Integer stepRecordNum) {
        this.stepRecordNum = stepRecordNum;
    }

    public Instant getExecuteTime() {
        return this.executeTime;
    }

    public TaskExecuteRecord executeTime(Instant executeTime) {
        this.executeTime = executeTime;
        return this;
    }

    public void setExecuteTime(Instant executeTime) {
        this.executeTime = executeTime;
    }

    public Instant getFinishTime() {
        return this.finishTime;
    }

    public TaskExecuteRecord finishTime(Instant finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public TaskExecuteRecord status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public TaskExecuteRecord createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public TaskExecuteRecord updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Set<TaskStepExecuteRecord> getTaskStepExecuteRecords() {
        return this.taskStepExecuteRecords;
    }

    public TaskExecuteRecord taskStepExecuteRecords(Set<TaskStepExecuteRecord> taskStepExecuteRecords) {
        this.setTaskStepExecuteRecords(taskStepExecuteRecords);
        return this;
    }

    public TaskExecuteRecord addTaskStepExecuteRecord(TaskStepExecuteRecord taskStepExecuteRecord) {
        this.taskStepExecuteRecords.add(taskStepExecuteRecord);
        taskStepExecuteRecord.setTaskExecuteRecord(this);
        return this;
    }

    public TaskExecuteRecord removeTaskStepExecuteRecord(TaskStepExecuteRecord taskStepExecuteRecord) {
        this.taskStepExecuteRecords.remove(taskStepExecuteRecord);
        taskStepExecuteRecord.setTaskExecuteRecord(null);
        return this;
    }

    public void setTaskStepExecuteRecords(Set<TaskStepExecuteRecord> taskStepExecuteRecords) {
        if (this.taskStepExecuteRecords != null) {
            this.taskStepExecuteRecords.forEach(i -> i.setTaskExecuteRecord(null));
        }
        if (taskStepExecuteRecords != null) {
            taskStepExecuteRecords.forEach(i -> i.setTaskExecuteRecord(this));
        }
        this.taskStepExecuteRecords = taskStepExecuteRecords;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskExecuteRecord)) {
            return false;
        }
        return id != null && id.equals(((TaskExecuteRecord) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskExecuteRecord{" +
            "id=" + getId() +
            ", taskId=" + getTaskId() +
            ", taskType=" + getTaskType() +
            ", taskName='" + getTaskName() + "'" +
            ", stepRecordNum=" + getStepRecordNum() +
            ", executeTime='" + getExecuteTime() + "'" +
            ", finishTime='" + getFinishTime() + "'" +
            ", status=" + getStatus() +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
