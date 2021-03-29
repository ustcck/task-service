<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="taskServiceApp.task.home.createOrEditLabel"
          data-cy="TaskCreateUpdateHeading"
          v-text="$t('taskServiceApp.task.home.createOrEditLabel')"
        >
          Create or edit a Task
        </h2>
        <div>
          <div class="form-group" v-if="task.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="task.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.task.name')" for="task-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="task-name"
              data-cy="name"
              :class="{ valid: !$v.task.name.$invalid, invalid: $v.task.name.$invalid }"
              v-model="$v.task.name.$model"
              required
            />
            <div v-if="$v.task.name.$anyDirty && $v.task.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.task.type')" for="task-type">Type</label>
            <input
              type="number"
              class="form-control"
              name="type"
              id="task-type"
              data-cy="type"
              :class="{ valid: !$v.task.type.$invalid, invalid: $v.task.type.$invalid }"
              v-model.number="$v.task.type.$model"
              required
            />
            <div v-if="$v.task.type.$anyDirty && $v.task.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.task.type.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.task.level')" for="task-level">Level</label>
            <input
              type="number"
              class="form-control"
              name="level"
              id="task-level"
              data-cy="level"
              :class="{ valid: !$v.task.level.$invalid, invalid: $v.task.level.$invalid }"
              v-model.number="$v.task.level.$model"
              required
            />
            <div v-if="$v.task.level.$anyDirty && $v.task.level.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.level.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.task.level.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.task.createTime')" for="task-createTime">Create Time</label>
            <div class="d-flex">
              <input
                id="task-createTime"
                data-cy="createTime"
                type="datetime-local"
                class="form-control"
                name="createTime"
                :class="{ valid: !$v.task.createTime.$invalid, invalid: $v.task.createTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.task.createTime.$model)"
                @change="updateInstantField('createTime', $event)"
              />
            </div>
            <div v-if="$v.task.createTime.$anyDirty && $v.task.createTime.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.createTime.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.task.createTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.task.updateTime')" for="task-updateTime">Update Time</label>
            <div class="d-flex">
              <input
                id="task-updateTime"
                data-cy="updateTime"
                type="datetime-local"
                class="form-control"
                name="updateTime"
                :class="{ valid: !$v.task.updateTime.$invalid, invalid: $v.task.updateTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.task.updateTime.$model)"
                @change="updateInstantField('updateTime', $event)"
              />
            </div>
            <div v-if="$v.task.updateTime.$anyDirty && $v.task.updateTime.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.updateTime.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.task.updateTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.task.delFlag')" for="task-delFlag">Del Flag</label>
            <input
              type="number"
              class="form-control"
              name="delFlag"
              id="task-delFlag"
              data-cy="delFlag"
              :class="{ valid: !$v.task.delFlag.$invalid, invalid: $v.task.delFlag.$invalid }"
              v-model.number="$v.task.delFlag.$model"
              required
            />
            <div v-if="$v.task.delFlag.$anyDirty && $v.task.delFlag.$invalid">
              <small class="form-text text-danger" v-if="!$v.task.delFlag.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.task.delFlag.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
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
            :disabled="$v.task.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./task-update.component.ts"></script>
