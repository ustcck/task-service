/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TaskExecuteRecordDetailComponent from '@/entities/task-execute-record/task-execute-record-details.vue';
import TaskExecuteRecordClass from '@/entities/task-execute-record/task-execute-record-details.component';
import TaskExecuteRecordService from '@/entities/task-execute-record/task-execute-record.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TaskExecuteRecord Management Detail Component', () => {
    let wrapper: Wrapper<TaskExecuteRecordClass>;
    let comp: TaskExecuteRecordClass;
    let taskExecuteRecordServiceStub: SinonStubbedInstance<TaskExecuteRecordService>;

    beforeEach(() => {
      taskExecuteRecordServiceStub = sinon.createStubInstance<TaskExecuteRecordService>(TaskExecuteRecordService);

      wrapper = shallowMount<TaskExecuteRecordClass>(TaskExecuteRecordDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { taskExecuteRecordService: () => taskExecuteRecordServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaskExecuteRecord = { id: 123 };
        taskExecuteRecordServiceStub.find.resolves(foundTaskExecuteRecord);

        // WHEN
        comp.retrieveTaskExecuteRecord(123);
        await comp.$nextTick();

        // THEN
        expect(comp.taskExecuteRecord).toBe(foundTaskExecuteRecord);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTaskExecuteRecord = { id: 123 };
        taskExecuteRecordServiceStub.find.resolves(foundTaskExecuteRecord);

        // WHEN
        comp.beforeRouteEnter({ params: { taskExecuteRecordId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.taskExecuteRecord).toBe(foundTaskExecuteRecord);
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
