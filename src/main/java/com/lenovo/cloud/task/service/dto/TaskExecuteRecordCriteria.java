package com.lenovo.cloud.task.service.dto;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lenovo.cloud.task.domain.TaskExecuteRecord} entity. This class is used
 * in {@link com.lenovo.cloud.task.web.rest.TaskExecuteRecordResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /task-execute-records?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskExecuteRecordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter taskId;

    private IntegerFilter taskType;

    private StringFilter taskName;

    private IntegerFilter stepRecordNum;

    private InstantFilter executeTime;

    private InstantFilter finishTime;

    private IntegerFilter status;

    private InstantFilter createTime;

    private InstantFilter updateTime;

    private LongFilter taskStepExecuteRecordId;

    public TaskExecuteRecordCriteria() {}

    public TaskExecuteRecordCriteria(TaskExecuteRecordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.taskType = other.taskType == null ? null : other.taskType.copy();
        this.taskName = other.taskName == null ? null : other.taskName.copy();
        this.stepRecordNum = other.stepRecordNum == null ? null : other.stepRecordNum.copy();
        this.executeTime = other.executeTime == null ? null : other.executeTime.copy();
        this.finishTime = other.finishTime == null ? null : other.finishTime.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createTime = other.createTime == null ? null : other.createTime.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.taskStepExecuteRecordId = other.taskStepExecuteRecordId == null ? null : other.taskStepExecuteRecordId.copy();
    }

    @Override
    public TaskExecuteRecordCriteria copy() {
        return new TaskExecuteRecordCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public IntegerFilter getTaskType() {
        return taskType;
    }

    public void setTaskType(IntegerFilter taskType) {
        this.taskType = taskType;
    }

    public StringFilter getTaskName() {
        return taskName;
    }

    public void setTaskName(StringFilter taskName) {
        this.taskName = taskName;
    }

    public IntegerFilter getStepRecordNum() {
        return stepRecordNum;
    }

    public void setStepRecordNum(IntegerFilter stepRecordNum) {
        this.stepRecordNum = stepRecordNum;
    }

    public InstantFilter getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(InstantFilter executeTime) {
        this.executeTime = executeTime;
    }

    public InstantFilter getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(InstantFilter finishTime) {
        this.finishTime = finishTime;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public InstantFilter getCreateTime() {
        return createTime;
    }

    public void setCreateTime(InstantFilter createTime) {
        this.createTime = createTime;
    }

    public InstantFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(InstantFilter updateTime) {
        this.updateTime = updateTime;
    }

    public LongFilter getTaskStepExecuteRecordId() {
        return taskStepExecuteRecordId;
    }

    public void setTaskStepExecuteRecordId(LongFilter taskStepExecuteRecordId) {
        this.taskStepExecuteRecordId = taskStepExecuteRecordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskExecuteRecordCriteria that = (TaskExecuteRecordCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(taskType, that.taskType) &&
            Objects.equals(taskName, that.taskName) &&
            Objects.equals(stepRecordNum, that.stepRecordNum) &&
            Objects.equals(executeTime, that.executeTime) &&
            Objects.equals(finishTime, that.finishTime) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(taskStepExecuteRecordId, that.taskStepExecuteRecordId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            taskId,
            taskType,
            taskName,
            stepRecordNum,
            executeTime,
            finishTime,
            status,
            createTime,
            updateTime,
            taskStepExecuteRecordId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskExecuteRecordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (taskType != null ? "taskType=" + taskType + ", " : "") +
                (taskName != null ? "taskName=" + taskName + ", " : "") +
                (stepRecordNum != null ? "stepRecordNum=" + stepRecordNum + ", " : "") +
                (executeTime != null ? "executeTime=" + executeTime + ", " : "") +
                (finishTime != null ? "finishTime=" + finishTime + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (taskStepExecuteRecordId != null ? "taskStepExecuteRecordId=" + taskStepExecuteRecordId + ", " : "") +
            "}";
    }
}
