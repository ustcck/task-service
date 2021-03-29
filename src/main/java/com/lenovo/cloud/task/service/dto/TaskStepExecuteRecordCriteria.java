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
 * Criteria class for the {@link com.lenovo.cloud.task.domain.TaskStepExecuteRecord} entity. This class is used
 * in {@link com.lenovo.cloud.task.web.rest.TaskStepExecuteRecordResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /task-step-execute-records?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskStepExecuteRecordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter taskId;

    private LongFilter taskStepId;

    private IntegerFilter taskStepExecuteOrder;

    private IntegerFilter status;

    private InstantFilter executeTime;

    private InstantFilter finishTime;

    private InstantFilter createTime;

    private InstantFilter updateTime;

    private LongFilter taskExecuteRecordId;

    public TaskStepExecuteRecordCriteria() {}

    public TaskStepExecuteRecordCriteria(TaskStepExecuteRecordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.taskStepId = other.taskStepId == null ? null : other.taskStepId.copy();
        this.taskStepExecuteOrder = other.taskStepExecuteOrder == null ? null : other.taskStepExecuteOrder.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.executeTime = other.executeTime == null ? null : other.executeTime.copy();
        this.finishTime = other.finishTime == null ? null : other.finishTime.copy();
        this.createTime = other.createTime == null ? null : other.createTime.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.taskExecuteRecordId = other.taskExecuteRecordId == null ? null : other.taskExecuteRecordId.copy();
    }

    @Override
    public TaskStepExecuteRecordCriteria copy() {
        return new TaskStepExecuteRecordCriteria(this);
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

    public LongFilter getTaskStepId() {
        return taskStepId;
    }

    public void setTaskStepId(LongFilter taskStepId) {
        this.taskStepId = taskStepId;
    }

    public IntegerFilter getTaskStepExecuteOrder() {
        return taskStepExecuteOrder;
    }

    public void setTaskStepExecuteOrder(IntegerFilter taskStepExecuteOrder) {
        this.taskStepExecuteOrder = taskStepExecuteOrder;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
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

    public LongFilter getTaskExecuteRecordId() {
        return taskExecuteRecordId;
    }

    public void setTaskExecuteRecordId(LongFilter taskExecuteRecordId) {
        this.taskExecuteRecordId = taskExecuteRecordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskStepExecuteRecordCriteria that = (TaskStepExecuteRecordCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(taskStepId, that.taskStepId) &&
            Objects.equals(taskStepExecuteOrder, that.taskStepExecuteOrder) &&
            Objects.equals(status, that.status) &&
            Objects.equals(executeTime, that.executeTime) &&
            Objects.equals(finishTime, that.finishTime) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(taskExecuteRecordId, that.taskExecuteRecordId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            taskId,
            taskStepId,
            taskStepExecuteOrder,
            status,
            executeTime,
            finishTime,
            createTime,
            updateTime,
            taskExecuteRecordId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskStepExecuteRecordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (taskStepId != null ? "taskStepId=" + taskStepId + ", " : "") +
                (taskStepExecuteOrder != null ? "taskStepExecuteOrder=" + taskStepExecuteOrder + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (executeTime != null ? "executeTime=" + executeTime + ", " : "") +
                (finishTime != null ? "finishTime=" + finishTime + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (taskExecuteRecordId != null ? "taskExecuteRecordId=" + taskExecuteRecordId + ", " : "") +
            "}";
    }
}
