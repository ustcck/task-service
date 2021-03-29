import { ITaskExecuteRecord } from '@/shared/model/task-execute-record.model';

export interface ITaskStepExecuteRecord {
  id?: number;
  taskId?: number | null;
  taskStepId?: number | null;
  taskStepExecuteOrder?: number | null;
  status?: number;
  executeTime?: Date | null;
  finishTime?: Date | null;
  createTime?: Date;
  updateTime?: Date;
  taskExecuteRecord?: ITaskExecuteRecord | null;
}

export class TaskStepExecuteRecord implements ITaskStepExecuteRecord {
  constructor(
    public id?: number,
    public taskId?: number | null,
    public taskStepId?: number | null,
    public taskStepExecuteOrder?: number | null,
    public status?: number,
    public executeTime?: Date | null,
    public finishTime?: Date | null,
    public createTime?: Date,
    public updateTime?: Date,
    public taskExecuteRecord?: ITaskExecuteRecord | null
  ) {}
}
