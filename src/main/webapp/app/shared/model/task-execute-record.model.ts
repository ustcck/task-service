import { ITaskStepExecuteRecord } from '@/shared/model/task-step-execute-record.model';

export interface ITaskExecuteRecord {
  id?: number;
  taskId?: number;
  taskType?: number;
  taskName?: string | null;
  stepRecordNum?: number;
  executeTime?: Date | null;
  finishTime?: Date | null;
  status?: number;
  createTime?: Date;
  updateTime?: Date;
  taskStepExecuteRecords?: ITaskStepExecuteRecord[] | null;
}

export class TaskExecuteRecord implements ITaskExecuteRecord {
  constructor(
    public id?: number,
    public taskId?: number,
    public taskType?: number,
    public taskName?: string | null,
    public stepRecordNum?: number,
    public executeTime?: Date | null,
    public finishTime?: Date | null,
    public status?: number,
    public createTime?: Date,
    public updateTime?: Date,
    public taskStepExecuteRecords?: ITaskStepExecuteRecord[] | null
  ) {}
}
