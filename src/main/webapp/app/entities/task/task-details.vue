<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="task">
        <h2 class="jh-entity-heading" data-cy="taskDetailsHeading">
          <span v-text="$t('taskServiceApp.task.detail.title')">Task</span> {{ task.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('taskServiceApp.task.name')">Name</span>
          </dt>
          <dd>
            <span>{{ task.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.task.type')">Type</span>
          </dt>
          <dd>
            <span>{{ task.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.task.level')">Level</span>
          </dt>
          <dd>
            <span>{{ task.level }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.task.createTime')">Create Time</span>
          </dt>
          <dd>
            <span v-if="task.createTime">{{ $d(Date.parse(task.createTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.task.updateTime')">Update Time</span>
          </dt>
          <dd>
            <span v-if="task.updateTime">{{ $d(Date.parse(task.updateTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('taskServiceApp.task.delFlag')">Del Flag</span>
          </dt>
          <dd>
            <span>{{ task.delFlag }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="task.id" :to="{ name: 'TaskEdit', params: { taskId: task.id } }" tag="button" class="btn btn-primary">
          <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-details.component.ts"></script>
