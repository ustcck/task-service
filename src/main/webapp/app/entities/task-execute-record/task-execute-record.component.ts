import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITaskExecuteRecord } from '@/shared/model/task-execute-record.model';

import TaskExecuteRecordService from './task-execute-record.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TaskExecuteRecord extends Vue {
  @Inject('taskExecuteRecordService') private taskExecuteRecordService: () => TaskExecuteRecordService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public taskExecuteRecords: ITaskExecuteRecord[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTaskExecuteRecords();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllTaskExecuteRecords();
  }

  public retrieveAllTaskExecuteRecords(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.taskExecuteRecordService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.taskExecuteRecords = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ITaskExecuteRecord): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTaskExecuteRecord(): void {
    this.taskExecuteRecordService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('taskServiceApp.taskExecuteRecord.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTaskExecuteRecords();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'desc' : 'asc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllTaskExecuteRecords();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
