<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="taskServiceApp.taskExecuteRecord.home.createOrEditLabel"
          data-cy="TaskExecuteRecordCreateUpdateHeading"
          v-text="$t('taskServiceApp.taskExecuteRecord.home.createOrEditLabel')"
        >
          Create or edit a TaskExecuteRecord
        </h2>
        <div>
          <div class="form-group" v-if="taskExecuteRecord.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="taskExecuteRecord.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskExecuteRecord.taskId')" for="task-execute-record-taskId"
              >Task Id</label
            >
            <input
              type="number"
              class="form-control"
              name="taskId"
              id="task-execute-record-taskId"
              data-cy="taskId"
              :class="{ valid: !$v.taskExecuteRecord.taskId.$invalid, invalid: $v.taskExecuteRecord.taskId.$invalid }"
              v-model.number="$v.taskExecuteRecord.taskId.$model"
              required
            />
            <div v-if="$v.taskExecuteRecord.taskId.$anyDirty && $v.taskExecuteRecord.taskId.$invalid">
              <small class="form-text text-danger" v-if="!$v.taskExecuteRecord.taskId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.taskExecuteRecord.taskId.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskExecuteRecord.taskType')" for="task-execute-record-taskType"
              >Task Type</label
            >
            <input
              type="number"
              class="form-control"
              name="taskType"
              id="task-execute-record-taskType"
              data-cy="taskType"
              :class="{ valid: !$v.taskExecuteRecord.taskType.$invalid, invalid: $v.taskExecuteRecord.taskType.$invalid }"
              v-model.number="$v.taskExecuteRecord.taskType.$model"
              required
            />
            <div v-if="$v.taskExecuteRecord.taskType.$anyDirty && $v.taskExecuteRecord.taskType.$invalid">
              <small class="form-text text-danger" v-if="!$v.taskExecuteRecord.taskType.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.taskExecuteRecord.taskType.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskExecuteRecord.taskName')" for="task-execute-record-taskName"
              >Task Name</label
            >
            <input
              type="text"
              class="form-control"
              name="taskName"
              id="task-execute-record-taskName"
              data-cy="taskName"
              :class="{ valid: !$v.taskExecuteRecord.taskName.$invalid, invalid: $v.taskExecuteRecord.taskName.$invalid }"
              v-model="$v.taskExecuteRecord.taskName.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskExecuteRecord.stepRecordNum')"
              for="task-execute-record-stepRecordNum"
              >Step Record Num</label
            >
            <input
              type="number"
              class="form-control"
              name="stepRecordNum"
              id="task-execute-record-stepRecordNum"
              data-cy="stepRecordNum"
              :class="{ valid: !$v.taskExecuteRecord.stepRecordNum.$invalid, invalid: $v.taskExecuteRecord.stepRecordNum.$invalid }"
              v-model.number="$v.taskExecuteRecord.stepRecordNum.$model"
              required
            />
            <div v-if="$v.taskExecuteRecord.stepRecordNum.$anyDirty && $v.taskExecuteRecord.stepRecordNum.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.taskExecuteRecord.stepRecordNum.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.taskExecuteRecord.stepRecordNum.numeric"
                v-text="$t('entity.validation.number')"
              >
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskExecuteRecord.executeTime')"
              for="task-execute-record-executeTime"
              >Execute Time</label
            >
            <div class="d-flex">
              <input
                id="task-execute-record-executeTime"
                data-cy="executeTime"
                type="datetime-local"
                class="form-control"
                name="executeTime"
                :class="{ valid: !$v.taskExecuteRecord.executeTime.$invalid, invalid: $v.taskExecuteRecord.executeTime.$invalid }"
                :value="convertDateTimeFromServer($v.taskExecuteRecord.executeTime.$model)"
                @change="updateInstantField('executeTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskExecuteRecord.finishTime')"
              for="task-execute-record-finishTime"
              >Finish Time</label
            >
            <div class="d-flex">
              <input
                id="task-execute-record-finishTime"
                data-cy="finishTime"
                type="datetime-local"
                class="form-control"
                name="finishTime"
                :class="{ valid: !$v.taskExecuteRecord.finishTime.$invalid, invalid: $v.taskExecuteRecord.finishTime.$invalid }"
                :value="convertDateTimeFromServer($v.taskExecuteRecord.finishTime.$model)"
                @change="updateInstantField('finishTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskExecuteRecord.status')" for="task-execute-record-status"
              >Status</label
            >
            <input
              type="number"
              class="form-control"
              name="status"
              id="task-execute-record-status"
              data-cy="status"
              :class="{ valid: !$v.taskExecuteRecord.status.$invalid, invalid: $v.taskExecuteRecord.status.$invalid }"
              v-model.number="$v.taskExecuteRecord.status.$model"
              required
            />
            <div v-if="$v.taskExecuteRecord.status.$anyDirty && $v.taskExecuteRecord.status.$invalid">
              <small class="form-text text-danger" v-if="!$v.taskExecuteRecord.status.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.taskExecuteRecord.status.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskExecuteRecord.createTime')"
              for="task-execute-record-createTime"
              >Create Time</label
            >
            <div class="d-flex">
              <input
                id="task-execute-record-createTime"
                data-cy="createTime"
                type="datetime-local"
                class="form-control"
                name="createTime"
                :class="{ valid: !$v.taskExecuteRecord.createTime.$invalid, invalid: $v.taskExecuteRecord.createTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.taskExecuteRecord.createTime.$model)"
                @change="updateInstantField('createTime', $event)"
              />
            </div>
            <div v-if="$v.taskExecuteRecord.createTime.$anyDirty && $v.taskExecuteRecord.createTime.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.taskExecuteRecord.createTime.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.taskExecuteRecord.createTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('taskServiceApp.taskExecuteRecord.updateTime')"
              for="task-execute-record-updateTime"
              >Update Time</label
            >
            <div class="d-flex">
              <input
                id="task-execute-record-updateTime"
                data-cy="updateTime"
                type="datetime-local"
                class="form-control"
                name="updateTime"
                :class="{ valid: !$v.taskExecuteRecord.updateTime.$invalid, invalid: $v.taskExecuteRecord.updateTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.taskExecuteRecord.updateTime.$model)"
                @change="updateInstantField('updateTime', $event)"
              />
            </div>
            <div v-if="$v.taskExecuteRecord.updateTime.$anyDirty && $v.taskExecuteRecord.updateTime.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.taskExecuteRecord.updateTime.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.taskExecuteRecord.updateTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
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
            :disabled="$v.taskExecuteRecord.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./task-execute-record-update.component.ts"></script>
