import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Task = () => import('@/entities/task/task.vue');
// prettier-ignore
const TaskUpdate = () => import('@/entities/task/task-update.vue');
// prettier-ignore
const TaskDetails = () => import('@/entities/task/task-details.vue');
// prettier-ignore
const TaskStep = () => import('@/entities/task-step/task-step.vue');
// prettier-ignore
const TaskStepUpdate = () => import('@/entities/task-step/task-step-update.vue');
// prettier-ignore
const TaskStepDetails = () => import('@/entities/task-step/task-step-details.vue');
// prettier-ignore
const TaskExecuteRecord = () => import('@/entities/task-execute-record/task-execute-record.vue');
// prettier-ignore
const TaskExecuteRecordUpdate = () => import('@/entities/task-execute-record/task-execute-record-update.vue');
// prettier-ignore
const TaskExecuteRecordDetails = () => import('@/entities/task-execute-record/task-execute-record-details.vue');
// prettier-ignore
const TaskStepExecuteRecord = () => import('@/entities/task-step-execute-record/task-step-execute-record.vue');
// prettier-ignore
const TaskStepExecuteRecordUpdate = () => import('@/entities/task-step-execute-record/task-step-execute-record-update.vue');
// prettier-ignore
const TaskStepExecuteRecordDetails = () => import('@/entities/task-step-execute-record/task-step-execute-record-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/task',
    name: 'Task',
    component: Task,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task/new',
    name: 'TaskCreate',
    component: TaskUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task/:taskId/edit',
    name: 'TaskEdit',
    component: TaskUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task/:taskId/view',
    name: 'TaskView',
    component: TaskDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-step',
    name: 'TaskStep',
    component: TaskStep,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-step/new',
    name: 'TaskStepCreate',
    component: TaskStepUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-step/:taskStepId/edit',
    name: 'TaskStepEdit',
    component: TaskStepUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-step/:taskStepId/view',
    name: 'TaskStepView',
    component: TaskStepDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-execute-record',
    name: 'TaskExecuteRecord',
    component: TaskExecuteRecord,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-execute-record/new',
    name: 'TaskExecuteRecordCreate',
    component: TaskExecuteRecordUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-execute-record/:taskExecuteRecordId/edit',
    name: 'TaskExecuteRecordEdit',
    component: TaskExecuteRecordUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-execute-record/:taskExecuteRecordId/view',
    name: 'TaskExecuteRecordView',
    component: TaskExecuteRecordDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-step-execute-record',
    name: 'TaskStepExecuteRecord',
    component: TaskStepExecuteRecord,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-step-execute-record/new',
    name: 'TaskStepExecuteRecordCreate',
    component: TaskStepExecuteRecordUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-step-execute-record/:taskStepExecuteRecordId/edit',
    name: 'TaskStepExecuteRecordEdit',
    component: TaskStepExecuteRecordUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/task-step-execute-record/:taskStepExecuteRecordId/view',
    name: 'TaskStepExecuteRecordView',
    component: TaskStepExecuteRecordDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
