<template>
  <div>
    <h2 id="page-heading" data-cy="TaskExecuteRecordHeading">
      <span v-text="$t('taskServiceApp.taskExecuteRecord.home.title')" id="task-execute-record-heading">Task Execute Records</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('taskServiceApp.taskExecuteRecord.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link
          :to="{ name: 'TaskExecuteRecordCreate' }"
          tag="button"
          id="jh-create-entity"
          data-cy="entityCreateButton"
          class="btn btn-primary jh-create-entity create-task-execute-record"
        >
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('taskServiceApp.taskExecuteRecord.home.createLabel')"> Create a new Task Execute Record </span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && taskExecuteRecords && taskExecuteRecords.length === 0">
      <span v-text="$t('taskServiceApp.taskExecuteRecord.home.notFound')">No taskExecuteRecords found</span>
    </div>
    <div class="table-responsive" v-if="taskExecuteRecords && taskExecuteRecords.length > 0">
      <table class="table table-striped" aria-describedby="taskExecuteRecords">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taskId')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.taskId')">Task Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taskType')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.taskType')">Task Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taskName')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.taskName')">Task Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('stepRecordNum')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.stepRecordNum')">Step Record Num</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'stepRecordNum'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('executeTime')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.executeTime')">Execute Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'executeTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('finishTime')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.finishTime')">Finish Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'finishTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createTime')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.createTime')">Create Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('updateTime')">
              <span v-text="$t('taskServiceApp.taskExecuteRecord.updateTime')">Update Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updateTime'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="taskExecuteRecord in taskExecuteRecords" :key="taskExecuteRecord.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TaskExecuteRecordView', params: { taskExecuteRecordId: taskExecuteRecord.id } }">{{
                taskExecuteRecord.id
              }}</router-link>
            </td>
            <td>{{ taskExecuteRecord.taskId }}</td>
            <td>{{ taskExecuteRecord.taskType }}</td>
            <td>{{ taskExecuteRecord.taskName }}</td>
            <td>{{ taskExecuteRecord.stepRecordNum }}</td>
            <td>{{ taskExecuteRecord.executeTime ? $d(Date.parse(taskExecuteRecord.executeTime), 'short') : '' }}</td>
            <td>{{ taskExecuteRecord.finishTime ? $d(Date.parse(taskExecuteRecord.finishTime), 'short') : '' }}</td>
            <td>{{ taskExecuteRecord.status }}</td>
            <td>{{ taskExecuteRecord.createTime ? $d(Date.parse(taskExecuteRecord.createTime), 'short') : '' }}</td>
            <td>{{ taskExecuteRecord.updateTime ? $d(Date.parse(taskExecuteRecord.updateTime), 'short') : '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TaskExecuteRecordView', params: { taskExecuteRecordId: taskExecuteRecord.id } }"
                  tag="button"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                </router-link>
                <router-link
                  :to="{ name: 'TaskExecuteRecordEdit', params: { taskExecuteRecordId: taskExecuteRecord.id } }"
                  tag="button"
                  class="btn btn-primary btn-sm edit"
                  data-cy="entityEditButton"
                >
                  <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(taskExecuteRecord)"
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
          id="taskServiceApp.taskExecuteRecord.delete.question"
          data-cy="taskExecuteRecordDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-taskExecuteRecord-heading" v-text="$t('taskServiceApp.taskExecuteRecord.delete.question', { id: removeId })">
          Are you sure you want to delete this Task Execute Record?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-taskExecuteRecord"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTaskExecuteRecord()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="taskExecuteRecords && taskExecuteRecords.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-execute-record.component.ts"></script>
