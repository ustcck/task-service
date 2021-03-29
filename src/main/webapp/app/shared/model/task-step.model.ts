import { ITask } from '@/shared/model/task.model';

export interface ITaskStep {
  id?: number;
  name?: string;
  order?: number;
  createTime?: Date;
  updateTime?: Date;
  delFlag?: number;
  task?: ITask | null;
}

export class TaskStep implements ITaskStep {
  constructor(
    public id?: number,
    public name?: string,
    public order?: number,
    public createTime?: Date,
    public updateTime?: Date,
    public delFlag?: number,
    public task?: ITask | null
  ) {}
}
