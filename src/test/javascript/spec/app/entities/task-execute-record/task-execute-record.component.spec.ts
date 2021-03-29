/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TaskExecuteRecordComponent from '@/entities/task-execute-record/task-execute-record.vue';
import TaskExecuteRecordClass from '@/entities/task-execute-record/task-execute-record.component';
import TaskExecuteRecordService from '@/entities/task-execute-record/task-execute-record.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('TaskExecuteRecord Management Component', () => {
    let wrapper: Wrapper<TaskExecuteRecordClass>;
    let comp: TaskExecuteRecordClass;
    let taskExecuteRecordServiceStub: SinonStubbedInstance<TaskExecuteRecordService>;

    beforeEach(() => {
      taskExecuteRecordServiceStub = sinon.createStubInstance<TaskExecuteRecordService>(TaskExecuteRecordService);
      taskExecuteRecordServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TaskExecuteRecordClass>(TaskExecuteRecordComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          taskExecuteRecordService: () => taskExecuteRecordServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      taskExecuteRecordServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTaskExecuteRecords();
      await comp.$nextTick();

      // THEN
      expect(taskExecuteRecordServiceStub.retrieve.called).toBeTruthy();
      expect(comp.taskExecuteRecords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      taskExecuteRecordServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(taskExecuteRecordServiceStub.retrieve.called).toBeTruthy();
      expect(comp.taskExecuteRecords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      taskExecuteRecordServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(taskExecuteRecordServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      taskExecuteRecordServiceStub.retrieve.reset();
      taskExecuteRecordServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(taskExecuteRecordServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.taskExecuteRecords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      taskExecuteRecordServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTaskExecuteRecord();
      await comp.$nextTick();

      // THEN
      expect(taskExecuteRecordServiceStub.delete.called).toBeTruthy();
      expect(taskExecuteRecordServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
