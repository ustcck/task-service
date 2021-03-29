import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TaskService from '@/entities/task/task.service';
import { ITask } from '@/shared/model/task.model';

import { ITaskStep, TaskStep } from '@/shared/model/task-step.model';
import TaskStepService from './task-step.service';

const validations: any = {
  taskStep: {
    name: {
      required,
    },
    order: {
      required,
      numeric,
    },
    createTime: {
      required,
    },
    updateTime: {
      required,
    },
    delFlag: {
      required,
      numeric,
    },
  },
};

@Component({
  validations,
})
export default class TaskStepUpdate extends Vue {
  @Inject('taskStepService') private taskStepService: () => TaskStepService;
  public taskStep: ITaskStep = new TaskStep();

  @Inject('taskService') private taskService: () => TaskService;

  public tasks: ITask[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskStepId) {
        vm.retrieveTaskStep(to.params.taskStepId);
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
    if (this.taskStep.id) {
      this.taskStepService()
        .update(this.taskStep)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('taskServiceApp.taskStep.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.taskStepService()
        .create(this.taskStep)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('taskServiceApp.taskStep.created', { param: param.id });
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
      this.taskStep[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.taskStep[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.taskStep[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.taskStep[field] = null;
    }
  }

  public retrieveTaskStep(taskStepId): void {
    this.taskStepService()
      .find(taskStepId)
      .then(res => {
        res.createTime = new Date(res.createTime);
        res.updateTime = new Date(res.updateTime);
        this.taskStep = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.taskService()
      .retrieve()
      .then(res => {
        this.tasks = res.data;
      });
  }
}
