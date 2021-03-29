<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="taskStepExecuteRecord">
        <h2 class="jh-entity-heading" data-cy="taskStepExecuteRecordDetailsHeading">
          <span v-text="$t('taskServiceApp.taskStepExecuteRecord.detail.title')">TaskStepExecuteRecord</span> {{ taskStepExecuteRecord.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.taskId')">Task Id</span>
          </dt>
          <dd>
            <span>{{ taskStepExecuteRecord.taskId }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.taskStepId')">Task Step Id</span>
          </dt>
          <dd>
            <span>{{ taskStepExecuteRecord.taskStepId }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.taskStepExecuteOrder')">Task Step Execute Order</span>
          </dt>
          <dd>
            <span>{{ taskStepExecuteRecord.taskStepExecuteOrder }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.status')">Status</span>
          </dt>
          <dd>
            <span>{{ taskStepExecuteRecord.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.executeTime')">Execute Time</span>
          </dt>
          <dd>
            <span v-if="taskStepExecuteRecord.executeTime">{{ $d(Date.parse(taskStepExecuteRecord.executeTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.finishTime')">Finish Time</span>
          </dt>
          <dd>
            <span v-if="taskStepExecuteRecord.finishTime">{{ $d(Date.parse(taskStepExecuteRecord.finishTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.createTime')">Create Time</span>
          </dt>
          <dd>
            <span v-if="taskStepExecuteRecord.createTime">{{ $d(Date.parse(taskStepExecuteRecord.createTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.updateTime')">Update Time</span>
          </dt>
          <dd>
            <span v-if="taskStepExecuteRecord.updateTime">{{ $d(Date.parse(taskStepExecuteRecord.updateTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStepExecuteRecord.taskExecuteRecord')">Task Execute Record</span>
          </dt>
          <dd>
            <div v-if="taskStepExecuteRecord.taskExecuteRecord">
              <router-link
                :to="{ name: 'TaskExecuteRecordView', params: { taskExecuteRecordId: taskStepExecuteRecord.taskExecuteRecord.id } }"
                >{{ taskStepExecuteRecord.taskExecuteRecord.id }}</router-link
              >
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="taskStepExecuteRecord.id"
          :to="{ name: 'TaskStepExecuteRecordEdit', params: { taskStepExecuteRecordId: taskStepExecuteRecord.id } }"
          tag="button"
          class="btn btn-primary"
        >
          <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-step-execute-record-details.component.ts"></script>
