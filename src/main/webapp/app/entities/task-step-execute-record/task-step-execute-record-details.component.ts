import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaskStepExecuteRecord } from '@/shared/model/task-step-execute-record.model';
import TaskStepExecuteRecordService from './task-step-execute-record.service';

@Component
export default class TaskStepExecuteRecordDetails extends Vue {
  @Inject('taskStepExecuteRecordService') private taskStepExecuteRecordService: () => TaskStepExecuteRecordService;
  public taskStepExecuteRecord: ITaskStepExecuteRecord = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskStepExecuteRecordId) {
        vm.retrieveTaskStepExecuteRecord(to.params.taskStepExecuteRecordId);
      }
    });
  }

  public retrieveTaskStepExecuteRecord(taskStepExecuteRecordId) {
    this.taskStepExecuteRecordService()
      .find(taskStepExecuteRecordId)
      .then(res => {
        this.taskStepExecuteRecord = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
