/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TaskStepExecuteRecordDetailComponent from '@/entities/task-step-execute-record/task-step-execute-record-details.vue';
import TaskStepExecuteRecordClass from '@/entities/task-step-execute-record/task-step-execute-record-details.component';
import TaskStepExecuteRecordService from '@/entities/task-step-execute-record/task-step-execute-record.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TaskStepExecuteRecord Management Detail Component', () => {
    let wrapper: Wrapper<TaskStepExecuteRecordClass>;
    let comp: TaskStepExecuteRecordClass;
    let taskStepExecuteRecordServiceStub: SinonStubbedInstance<TaskStepExecuteRecordService>;

    beforeEach(() => {
      taskStepExecuteRecordServiceStub = sinon.createStubInstance<TaskStepExecuteRecordService>(TaskStepExecuteRecordService);

      wrapper = shallowMount<TaskStepExecuteRecordClass>(TaskStepExecuteRecordDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { taskStepExecuteRecordService: () => taskStepExecuteRecordServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaskStepExecuteRecord = { id: 123 };
        taskStepExecuteRecordServiceStub.find.resolves(foundTaskStepExecuteRecord);

        // WHEN
        comp.retrieveTaskStepExecuteRecord(123);
        await comp.$nextTick();

        // THEN
        expect(comp.taskStepExecuteRecord).toBe(foundTaskStepExecuteRecord);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTaskStepExecuteRecord = { id: 123 };
        taskStepExecuteRecordServiceStub.find.resolves(foundTaskStepExecuteRecord);

        // WHEN
        comp.beforeRouteEnter({ params: { taskStepExecuteRecordId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.taskStepExecuteRecord).toBe(foundTaskStepExecuteRecord);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
