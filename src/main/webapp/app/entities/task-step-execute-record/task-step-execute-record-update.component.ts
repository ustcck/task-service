import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TaskExecuteRecordService from '@/entities/task-execute-record/task-execute-record.service';
import { ITaskExecuteRecord } from '@/shared/model/task-execute-record.model';

import { ITaskStepExecuteRecord, TaskStepExecuteRecord } from '@/shared/model/task-step-execute-record.model';
import TaskStepExecuteRecordService from './task-step-execute-record.service';

const validations: any = {
  taskStepExecuteRecord: {
    taskId: {},
    taskStepId: {},
    taskStepExecuteOrder: {},
    status: {
      required,
      numeric,
    },
    executeTime: {},
    finishTime: {},
    createTime: {
      required,
    },
    updateTime: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class TaskStepExecuteRecordUpdate extends Vue {
  @Inject('taskStepExecuteRecordService') private taskStepExecuteRecordService: () => TaskStepExecuteRecordService;
  public taskStepExecuteRecord: ITaskStepExecuteRecord = new TaskStepExecuteRecord();

  @Inject('taskExecuteRecordService') private taskExecuteRecordService: () => TaskExecuteRecordService;

  public taskExecuteRecords: ITaskExecuteRecord[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskStepExecuteRecordId) {
        vm.retrieveTaskStepExecuteRecord(to.params.taskStepExecuteRecordId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.taskStepExecuteRecord.id) {
      this.taskStepExecuteRecordService()
        .update(this.taskStepExecuteRecord)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('taskServiceApp.taskStepExecuteRecord.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.taskStepExecuteRecordService()
        .create(this.taskStepExecuteRecord)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('taskServiceApp.taskStepExecuteRecord.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.taskStepExecuteRecord[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.taskStepExecuteRecord[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.taskStepExecuteRecord[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.taskStepExecuteRecord[field] = null;
    }
  }

  public retrieveTaskStepExecuteRecord(taskStepExecuteRecordId): void {
    this.taskStepExecuteRecordService()
      .find(taskStepExecuteRecordId)
      .then(res => {
        res.executeTime = new Date(res.executeTime);
        res.finishTime = new Date(res.finishTime);
        res.createTime = new Date(res.createTime);
        res.updateTime = new Date(res.updateTime);
        this.taskStepExecuteRecord = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.taskExecuteRecordService()
      .retrieve()
      .then(res => {
        this.taskExecuteRecords = res.data;
      });
  }
}
