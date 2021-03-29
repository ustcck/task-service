<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="taskExecuteRecord">
        <h2 class="jh-entity-heading" data-cy="taskExecuteRecordDetailsHeading">
          <span v-text="$t('taskServiceApp.taskExecuteRecord.detail.title')">TaskExecuteRecord</span> {{ taskExecuteRecord.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.taskId')">Task Id</span>
          </dt>
          <dd>
            <span>{{ taskExecuteRecord.taskId }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.taskType')">Task Type</span>
          </dt>
          <dd>
            <span>{{ taskExecuteRecord.taskType }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.taskName')">Task Name</span>
          </dt>
          <dd>
            <span>{{ taskExecuteRecord.taskName }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.stepRecordNum')">Step Record Num</span>
          </dt>
          <dd>
            <span>{{ taskExecuteRecord.stepRecordNum }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.executeTime')">Execute Time</span>
          </dt>
          <dd>
            <span v-if="taskExecuteRecord.executeTime">{{ $d(Date.parse(taskExecuteRecord.executeTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.finishTime')">Finish Time</span>
          </dt>
          <dd>
            <span v-if="taskExecuteRecord.finishTime">{{ $d(Date.parse(taskExecuteRecord.finishTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.status')">Status</span>
          </dt>
          <dd>
            <span>{{ taskExecuteRecord.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.createTime')">Create Time</span>
          </dt>
          <dd>
            <span v-if="taskExecuteRecord.createTime">{{ $d(Date.parse(taskExecuteRecord.createTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskExecuteRecord.updateTime')">Update Time</span>
          </dt>
          <dd>
            <span v-if="taskExecuteRecord.updateTime">{{ $d(Date.parse(taskExecuteRecord.updateTime), 'long') }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="taskExecuteRecord.id"
          :to="{ name: 'TaskExecuteRecordEdit', params: { taskExecuteRecordId: taskExecuteRecord.id } }"
          tag="button"
          class="btn btn-primary"
        >
          <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-execute-record-details.component.ts"></script>
