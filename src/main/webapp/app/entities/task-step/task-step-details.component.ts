import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaskStep } from '@/shared/model/task-step.model';
import TaskStepService from './task-step.service';

@Component
export default class TaskStepDetails extends Vue {
  @Inject('taskStepService') private taskStepService: () => TaskStepService;
  public taskStep: ITaskStep = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskStepId) {
        vm.retrieveTaskStep(to.params.taskStepId);
      }
    });
  }

  public retrieveTaskStep(taskStepId) {
    this.taskStepService()
      .find(taskStepId)
      .then(res => {
        this.taskStep = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
