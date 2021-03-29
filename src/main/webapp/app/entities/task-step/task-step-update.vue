<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="taskServiceApp.taskStep.home.createOrEditLabel"
          data-cy="TaskStepCreateUpdateHeading"
          v-text="$t('taskServiceApp.taskStep.home.createOrEditLabel')"
        >
          Create or edit a TaskStep
        </h2>
        <div>
          <div class="form-group" v-if="taskStep.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="taskStep.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskStep.name')" for="task-step-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="task-step-name"
              data-cy="name"
              :class="{ valid: !$v.taskStep.name.$invalid, invalid: $v.taskStep.name.$invalid }"
              v-model="$v.taskStep.name.$model"
              required
            />
            <div v-if="$v.taskStep.name.$anyDirty && $v.taskStep.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.taskStep.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskStep.order')" for="task-step-order">Order</label>
            <input
              type="number"
              class="form-control"
              name="order"
              id="task-step-order"
              data-cy="order"
              :class="{ valid: !$v.taskStep.order.$invalid, invalid: $v.taskStep.order.$invalid }"
              v-model.number="$v.taskStep.order.$model"
              required
            />
            <div v-if="$v.taskStep.order.$anyDirty && $v.taskStep.order.$invalid">
              <small class="form-text text-danger" v-if="!$v.taskStep.order.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.taskStep.order.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskStep.createTime')" for="task-step-createTime"
              >Create Time</label
            >
            <div class="d-flex">
              <input
                id="task-step-createTime"
                data-cy="createTime"
                type="datetime-local"
                class="form-control"
                name="createTime"
                :class="{ valid: !$v.taskStep.createTime.$invalid, invalid: $v.taskStep.createTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.taskStep.createTime.$model)"
                @change="updateInstantField('createTime', $event)"
              />
            </div>
            <div v-if="$v.taskStep.createTime.$anyDirty && $v.taskStep.createTime.$invalid">
              <small class="form-text text-danger" v-if="!$v.taskStep.createTime.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.taskStep.createTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskStep.updateTime')" for="task-step-updateTime"
              >Update Time</label
            >
            <div class="d-flex">
              <input
                id="task-step-updateTime"
                data-cy="updateTime"
                type="datetime-local"
                class="form-control"
                name="updateTime"
                :class="{ valid: !$v.taskStep.updateTime.$invalid, invalid: $v.taskStep.updateTime.$invalid }"
                required
                :value="convertDateTimeFromServer($v.taskStep.updateTime.$model)"
                @change="updateInstantField('updateTime', $event)"
              />
            </div>
            <div v-if="$v.taskStep.updateTime.$anyDirty && $v.taskStep.updateTime.$invalid">
              <small class="form-text text-danger" v-if="!$v.taskStep.updateTime.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.taskStep.updateTime.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskStep.delFlag')" for="task-step-delFlag">Del Flag</label>
            <input
              type="number"
              class="form-control"
              name="delFlag"
              id="task-step-delFlag"
              data-cy="delFlag"
              :class="{ valid: !$v.taskStep.delFlag.$invalid, invalid: $v.taskStep.delFlag.$invalid }"
              v-model.number="$v.taskStep.delFlag.$model"
              required
            />
            <div v-if="$v.taskStep.delFlag.$anyDirty && $v.taskStep.delFlag.$invalid">
              <small class="form-text text-danger" v-if="!$v.taskStep.delFlag.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.taskStep.delFlag.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('taskServiceApp.taskStep.task')" for="task-step-task">Task</label>
            <select class="form-control" id="task-step-task" data-cy="task" name="task" v-model="taskStep.task">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="taskStep.task && taskOption.id === taskStep.task.id ? taskStep.task : taskOption"
                v-for="taskOption in tasks"
                :key="taskOption.id"
              >
                {{ taskOption.id }}
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
            :disabled="$v.taskStep.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./task-step-update.component.ts"></script>
