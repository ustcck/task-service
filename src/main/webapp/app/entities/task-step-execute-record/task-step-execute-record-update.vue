<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="taskServiceApp.taskStepExecuteRecord.home.createOrEditLabel"
          data-cy="TaskStepExecuteRecordCreateUpdateHeading"
          v-text="$t('taskServiceApp.taskStepExecuteRecord.home.createOrEditLabel')"
        >
          Create or edit a TaskStepExecuteRecord
        </h2>
        <div>
          <div class="form-group" v-if="taskStepExecuteRecord.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="taskStepExecuteRecord.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.taskId')"
              for="task-step-execute-record-taskId"
              >Task Id</label
            >
            <input
              type="number"
              class="form-control"
              name="taskId"
              id="task-step-execute-record-taskId"
              data-cy="taskId"
              :class="{ valid: !$v.taskStepExecuteRecord.taskId.$invalid, invalid: $v.taskStepExecuteRecord.taskId.$invalid }"
              v-model.number="$v.taskStepExecuteRecord.taskId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.taskStepId')"
              for="task-step-execute-record-taskStepId"
              >Task Step Id</label
            >
            <input
              type="number"
              class="form-control"
              name="taskStepId"
              id="task-step-execute-record-taskStepId"
              data-cy="taskStepId"
              :class="{ valid: !$v.taskStepExecuteRecord.taskStepId.$invalid, invalid: $v.taskStepExecuteRecord.taskStepId.$invalid }"
              v-model.number="$v.taskStepExecuteRecord.taskStepId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.taskStepExecuteOrder')"
              for="task-step-execute-record-taskStepExecuteOrder"
              >Task Step Execute Order</label
            >
            <input
              type="number"
              class="form-control"
              name="taskStepExecuteOrder"
              id="task-step-execute-record-taskStepExecuteOrder"
              data-cy="taskStepExecuteOrder"
              :class="{
                valid: !$v.taskStepExecuteRecord.taskStepExecuteOrder.$invalid,
                invalid: $v.taskStepExecuteRecord.taskStepExecuteOrder.$invalid,
              }"
              v-model.number="$v.taskStepExecuteRecord.taskStepExecuteOrder.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.status')"
              for="task-step-execute-record-status"
              >Status</label
            >
            <input
              type="number"
              class="form-control"
              name="status"
              id="task-step-execute-record-status"
              data-cy="status"
              :class="{ valid: !$v.taskStepExecuteRecord.status.$invalid, invalid: $v.taskStepExecuteRecord.status.$invalid }"
              v-model.number="$v.taskStepExecuteRecord.status.$model"
              required
            />
            <div v-if="$v.taskStepExecuteRecord.status.$anyDirty && $v.taskStepExecuteRecord.status.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.taskStepExecuteRecord.status.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.taskStepExecuteRecord.status.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.executeTime')"
              for="task-step-execute-record-executeTime"
              >Execute Time</label
            >
            <div class="d-flex">
              <input
                id="task-step-execute-record-executeTime"
                data-cy="executeTime"
                type="datetime-local"
                class="form-control"
                name="executeTime"
                :class="{ valid: !$v.taskStepExecuteRecord.executeTime.$invalid, invalid: $v.taskStepExecuteRecord.executeTime.$invalid }"
                :value="convertDateTimeFromServer($v.taskStepExecuteRecord.executeTime.$model)"
                @change="updateInstantField('executeTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.finishTime')"
              for="task-step-execute-record-finishTime"
              >Finish Time</label
            >
            <div class="d-flex">
              <input
                id="task-step-execute-record-finishTime"
                data-cy="finishTime"
                type="datetime-local"
                class="form-control"
                name="finishTime"
                :class="{ valid: !$v.taskStepExecuteRecord.finishTime.$invalid, invalid: $v.taskStepExecuteRecord.finishTime.$invalid }"
                :value="convertDateTimeFromServer($v.taskStepExecuteRecord.finishTime.$model)"
                @change="updateInstantField('finishTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.createTime')"
              for="task-step-execute-record-createTime"
              >Create Time</label
            >
            <div class="d-flex">
              <input
                id="task-step-execute-record-createTime"
                data-cy="createTime"
                type="datetime-local"
                class="form-control"
                name="createTime"
                :class="{ valid: !$v.taskStepExecuteRecord.createTime.$invalid, invalid: $v.taskStepExecuteRecord.createTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.taskStepExecuteRecord.createTime.$model)"
                @change="updateInstantField('createTime', $event)"
              />
            </div>
            <div v-if="$v.taskStepExecuteRecord.createTime.$anyDirty && $v.taskStepExecuteRecord.createTime.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.taskStepExecuteRecord.createTime.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.taskStepExecuteRecord.createTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.updateTime')"
              for="task-step-execute-record-updateTime"
              >Update Time</label
            >
            <div class="d-flex">
              <input
                id="task-step-execute-record-updateTime"
                data-cy="updateTime"
                type="datetime-local"
                class="form-control"
                name="updateTime"
                :class="{ valid: !$v.taskStepExecuteRecord.updateTime.$invalid, invalid: $v.taskStepExecuteRecord.updateTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.taskStepExecuteRecord.updateTime.$model)"
                @change="updateInstantField('updateTime', $event)"
              />
            </div>
            <div v-if="$v.taskStepExecuteRecord.updateTime.$anyDirty && $v.taskStepExecuteRecord.updateTime.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.taskStepExecuteRecord.updateTime.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.taskStepExecuteRecord.updateTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskStepExecuteRecord.taskExecuteRecord')"
              for="task-step-execute-record-taskExecuteRecord"
              >Task Execute Record</label
            >
            <select
              class="form-control"
              id="task-step-execute-record-taskExecuteRecord"
              data-cy="taskExecuteRecord"
              name="taskExecuteRecord"
              v-model="taskStepExecuteRecord.taskExecuteRecord"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  taskStepExecuteRecord.taskExecuteRecord && taskExecuteRecordOption.id === taskStepExecuteRecord.taskExecuteRecord.id
                    ? taskStepExecuteRecord.taskExecuteRecord
                    : taskExecuteRecordOption
                "
                v-for="taskExecuteRecordOption in taskExecuteRecords"
                :key="taskExecuteRecordOption.id"
              >
                {{ taskExecuteRecordOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.taskStepExecuteRecord.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./task-step-execute-record-update.component.ts"></script>
