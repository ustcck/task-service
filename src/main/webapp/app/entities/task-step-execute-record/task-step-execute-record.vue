<template>
  <div>
    <h2 id="page-heading" data-cy="TaskStepExecuteRecordHeading">
      <span v-text="$t('taskServiceApp.taskStepExecuteRecord.home.title')" id="task-step-execute-record-heading"
        >Task Step Execute Records</span
      >
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('taskServiceApp.taskStepExecuteRecord.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link
          :to="{ name: 'TaskStepExecuteRecordCreate' }"
          tag="button"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-task-step-execute-record"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('taskServiceApp.taskStepExecuteRecord.home.createLabel')"> Create a new Task Step Execute Record </span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && taskStepExecuteRecords && taskStepExecuteRecords.length === 0">
      <span v-text="$t('taskServiceApp.taskStepExecuteRecord.home.notFound')">No taskStepExecuteRecords found</span>
    </div>
    <div class="table-responsive" v-if="taskStepExecuteRecords && taskStepExecuteRecords.length > 0">
      <table class="table table-striped" aria-describedby="taskStepExecuteRecords">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taskId')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.taskId')">Task Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taskStepId')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.taskStepId')">Task Step Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskStepId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taskStepExecuteOrder')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.taskStepExecuteOrder')">Task Step Execute Order</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskStepExecuteOrder'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('executeTime')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.executeTime')">Execute Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'executeTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('finishTime')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.finishTime')">Finish Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'finishTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createTime')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.createTime')">Create Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updateTime')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.updateTime')">Update Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updateTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taskExecuteRecord.id')">
              <span v-text="$t('taskServiceApp.taskStepExecuteRecord.taskExecuteRecord')">Task Execute Record</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskExecuteRecord.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="taskStepExecuteRecord in taskStepExecuteRecords" :key="taskStepExecuteRecord.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TaskStepExecuteRecordView', params: { taskStepExecuteRecordId: taskStepExecuteRecord.id } }">{{
                taskStepExecuteRecord.id
              }}</router-link>
            </td>
            <td>{{ taskStepExecuteRecord.taskId }}</td>
            <td>{{ taskStepExecuteRecord.taskStepId }}</td>
            <td>{{ taskStepExecuteRecord.taskStepExecuteOrder }}</td>
            <td>{{ taskStepExecuteRecord.status }}</td>
            <td>{{ taskStepExecuteRecord.executeTime ? $d(Date.parse(taskStepExecuteRecord.executeTime), 'short') : '' }}</td>
            <td>{{ taskStepExecuteRecord.finishTime ? $d(Date.parse(taskStepExecuteRecord.finishTime), 'short') : '' }}</td>
            <td>{{ taskStepExecuteRecord.createTime ? $d(Date.parse(taskStepExecuteRecord.createTime), 'short') : '' }}</td>
            <td>{{ taskStepExecuteRecord.updateTime ? $d(Date.parse(taskStepExecuteRecord.updateTime), 'short') : '' }}</td>
            <td>
              <div v-if="taskStepExecuteRecord.taskExecuteRecord">
                <router-link
                  :to="{ name: 'TaskExecuteRecordView', params: { taskExecuteRecordId: taskStepExecuteRecord.taskExecuteRecord.id } }"
                  >{{ taskStepExecuteRecord.taskExecuteRecord.id }}</router-link
                >
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TaskStepExecuteRecordView', params: { taskStepExecuteRecordId: taskStepExecuteRecord.id } }"
                  tag="button"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                </router-link>
                <router-link
                  :to="{ name: 'TaskStepExecuteRecordEdit', params: { taskStepExecuteRecordId: taskStepExecuteRecord.id } }"
                  tag="button"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(taskStepExecuteRecord)"
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
        ><span
          id="taskServiceApp.taskStepExecuteRecord.delete.question"
          data-cy="taskStepExecuteRecordDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p
          id="jhi-delete-taskStepExecuteRecord-heading"
          v-text="$t('taskServiceApp.taskStepExecuteRecord.delete.question', { id: removeId })"
        >
          Are you sure you want to delete this Task Step Execute Record?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-taskStepExecuteRecord"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTaskStepExecuteRecord()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="taskStepExecuteRecords && taskStepExecuteRecords.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-step-execute-record.component.ts"></script>
