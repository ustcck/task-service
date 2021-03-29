import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaskExecuteRecord } from '@/shared/model/task-execute-record.model';
import TaskExecuteRecordService from './task-execute-record.service';

@Component
export default class TaskExecuteRecordDetails extends Vue {
  @Inject('taskExecuteRecordService') private taskExecuteRecordService: () => TaskExecuteRecordService;
  public taskExecuteRecord: ITaskExecuteRecord = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskExecuteRecordId) {
        vm.retrieveTaskExecuteRecord(to.params.taskExecuteRecordId);
      }
    });
  }

  public retrieveTaskExecuteRecord(taskExecuteRecordId) {
    this.taskExecuteRecordService()
      .find(taskExecuteRecordId)
      .then(res => {
        this.taskExecuteRecord = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
