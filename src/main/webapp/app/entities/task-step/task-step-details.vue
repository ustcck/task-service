<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="taskStep">
        <h2 class="jh-entity-heading" data-cy="taskStepDetailsHeading">
          <span v-text="$t('taskServiceApp.taskStep.detail.title')">TaskStep</span> {{ taskStep.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('taskServiceApp.taskStep.name')">Name</span>
          </dt>
          <dd>
            <span>{{ taskStep.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStep.order')">Order</span>
          </dt>
          <dd>
            <span>{{ taskStep.order }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStep.createTime')">Create Time</span>
          </dt>
          <dd>
            <span v-if="taskStep.createTime">{{ $d(Date.parse(taskStep.createTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStep.updateTime')">Update Time</span>
          </dt>
          <dd>
            <span v-if="taskStep.updateTime">{{ $d(Date.parse(taskStep.updateTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStep.delFlag')">Del Flag</span>
          </dt>
          <dd>
            <span>{{ taskStep.delFlag }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.taskStep.task')">Task</span>
          </dt>
          <dd>
            <div v-if="taskStep.task">
              <router-link :to="{ name: 'TaskView', params: { taskId: taskStep.task.id } }">{{ taskStep.task.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="taskStep.id"
          :to="{ name: 'TaskStepEdit', params: { taskStepId: taskStep.id } }"
          tag="button"
          class="btn btn-primary"
        >
          <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-step-details.component.ts"></script>
