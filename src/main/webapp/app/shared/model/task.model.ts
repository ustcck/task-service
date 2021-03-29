import { ITaskStep } from '@/shared/model/task-step.model';

export interface ITask {
  id?: number;
  name?: string;
  type?: number;
  level?: number;
  createTime?: Date;
  updateTime?: Date;
  delFlag?: number;
  taskSteps?: ITaskStep[] | null;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public name?: string,
    public type?: number,
    public level?: number,
    public createTime?: Date,
    public updateTime?: Date,
    public delFlag?: number,
    public taskSteps?: ITaskStep[] | null
  ) {}
}
