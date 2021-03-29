import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TaskStepExecuteRecordService from '@/entities/task-step-execute-record/task-step-execute-record.service';
import { ITaskStepExecuteRecord } from '@/shared/model/task-step-execute-record.model';

import { ITaskExecuteRecord, TaskExecuteRecord } from '@/shared/model/task-execute-record.model';
import TaskExecuteRecordService from './task-execute-record.service';

const validations: any = {
  taskExecuteRecord: {
    taskId: {
      required,
      numeric,
    },
    taskType: {
      required,
      numeric,
    },
    taskName: {},
    stepRecordNum: {
      required,
      numeric,
    },
    executeTime: {},
    finishTime: {},
    status: {
      required,
      numeric,
    },
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
export default class TaskExecuteRecordUpdate extends Vue {
  @Inject('taskExecuteRecordService') private taskExecuteRecordService: () => TaskExecuteRecordService;
  public taskExecuteRecord: ITaskExecuteRecord = new TaskExecuteRecord();

  @Inject('taskStepExecuteRecordService') private taskStepExecuteRecordService: () => TaskStepExecuteRecordService;

  public taskStepExecuteRecords: ITaskStepExecuteRecord[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskExecuteRecordId) {
        vm.retrieveTaskExecuteRecord(to.params.taskExecuteRecordId);
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
    if (this.taskExecuteRecord.id) {
      this.taskExecuteRecordService()
        .update(this.taskExecuteRecord)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('taskServiceApp.taskExecuteRecord.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.taskExecuteRecordService()
        .create(this.taskExecuteRecord)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('taskServiceApp.taskExecuteRecord.created', { param: param.id });
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
      this.taskExecuteRecord[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.taskExecuteRecord[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.taskExecuteRecord[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.taskExecuteRecord[field] = null;
    }
  }

  public retrieveTaskExecuteRecord(taskExecuteRecordId): void {
    this.taskExecuteRecordService()
      .find(taskExecuteRecordId)
      .then(res => {
        res.executeTime = new Date(res.executeTime);
        res.finishTime = new Date(res.finishTime);
        res.createTime = new Date(res.createTime);
        res.updateTime = new Date(res.updateTime);
        this.taskExecuteRecord = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.taskStepExecuteRecordService()
      .retrieve()
      .then(res => {
        this.taskStepExecuteRecords = res.data;
      });
  }
}
