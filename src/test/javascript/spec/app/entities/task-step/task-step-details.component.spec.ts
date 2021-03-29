/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TaskStepDetailComponent from '@/entities/task-step/task-step-details.vue';
import TaskStepClass from '@/entities/task-step/task-step-details.component';
import TaskStepService from '@/entities/task-step/task-step.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TaskStep Management Detail Component', () => {
    let wrapper: Wrapper<TaskStepClass>;
    let comp: TaskStepClass;
    let taskStepServiceStub: SinonStubbedInstance<TaskStepService>;

    beforeEach(() => {
      taskStepServiceStub = sinon.createStubInstance<TaskStepService>(TaskStepService);

      wrapper = shallowMount<TaskStepClass>(TaskStepDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { taskStepService: () => taskStepServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaskStep = { id: 123 };
        taskStepServiceStub.find.resolves(foundTaskStep);

        // WHEN
        comp.retrieveTaskStep(123);
        await comp.$nextTick();

        // THEN
        expect(comp.taskStep).toBe(foundTaskStep);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTaskStep = { id: 123 };
        taskStepServiceStub.find.resolves(foundTaskStep);

        // WHEN
        comp.beforeRouteEnter({ params: { taskStepId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.taskStep).toBe(foundTaskStep);
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
