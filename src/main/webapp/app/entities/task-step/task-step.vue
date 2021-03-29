<template>
  <div>
    <h2 id="page-heading" data-cy="TaskStepHeading">
      <span v-text="$t('taskServiceApp.taskStep.home.title')" id="task-step-heading">Task Steps</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('taskServiceApp.taskStep.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link
          :to="{ name: 'TaskStepCreate' }"
          tag="button"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-task-step"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('taskServiceApp.taskStep.home.createLabel')"> Create a new Task Step </span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && taskSteps && taskSteps.length === 0">
      <span v-text="$t('taskServiceApp.taskStep.home.notFound')">No taskSteps found</span>
    </div>
    <div class="table-responsive" v-if="taskSteps && taskSteps.length > 0">
      <table class="table table-striped" aria-describedby="taskSteps">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('taskServiceApp.taskStep.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('order')">
              <span v-text="$t('taskServiceApp.taskStep.order')">Order</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'order'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createTime')">
              <span v-text="$t('taskServiceApp.taskStep.createTime')">Create Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updateTime')">
              <span v-text="$t('taskServiceApp.taskStep.updateTime')">Update Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updateTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('delFlag')">
              <span v-text="$t('taskServiceApp.taskStep.delFlag')">Del Flag</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'delFlag'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('task.id')">
              <span v-text="$t('taskServiceApp.taskStep.task')">Task</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'task.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="taskStep in taskSteps" :key="taskStep.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TaskStepView', params: { taskStepId: taskStep.id } }">{{ taskStep.id }}</router-link>
            </td>
            <td>{{ taskStep.name }}</td>
            <td>{{ taskStep.order }}</td>
            <td>{{ taskStep.createTime ? $d(Date.parse(taskStep.createTime), 'short') : '' }}</td>
            <td>{{ taskStep.updateTime ? $d(Date.parse(taskStep.updateTime), 'short') : '' }}</td>
            <td>{{ taskStep.delFlag }}</td>
            <td>
              <div v-if="taskStep.task">
                <router-link :to="{ name: 'TaskView', params: { taskId: taskStep.task.id } }">{{ taskStep.task.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TaskStepView', params: { taskStepId: taskStep.id } }"
                  tag="button"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                </router-link>
                <router-link
                  :to="{ name: 'TaskStepEdit', params: { taskStepId: taskStep.id } }"
                  tag="button"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(taskStep)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="taskServiceApp.taskStep.delete.question" data-cy="taskStepDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-taskStep-heading" v-text="$t('taskServiceApp.taskStep.delete.question', { id: removeId })">
          Are you sure you want to delete this Task Step?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-taskStep"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTaskStep()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="taskSteps && taskSteps.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-step.component.ts"></script>
