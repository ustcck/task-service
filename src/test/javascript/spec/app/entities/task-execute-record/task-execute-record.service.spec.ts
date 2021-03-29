/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import TaskExecuteRecordService from '@/entities/task-execute-record/task-execute-record.service';
import { TaskExecuteRecord } from '@/shared/model/task-execute-record.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('TaskExecuteRecord Service', () => {
    let service: TaskExecuteRecordService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new TaskExecuteRecordService();
      currentDate = new Date();
      elemDefault = new TaskExecuteRecord(0, 0, 0, 'AAAAAAA', 0, currentDate, currentDate, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            executeTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            finishTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a TaskExecuteRecord', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            executeTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            finishTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            createTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            executeTime: currentDate,
            finishTime: currentDate,
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a TaskExecuteRecord', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a TaskExecuteRecord', async () => {
        const returnedFromService = Object.assign(
          {
            taskId: 1,
            taskType: 1,
            taskName: 'BBBBBB',
            stepRecordNum: 1,
            executeTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            finishTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 1,
            createTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executeTime: currentDate,
            finishTime: currentDate,
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a TaskExecuteRecord', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of TaskExecuteRecord', async () => {
        const returnedFromService = Object.assign(
          {
            taskId: 1,
            taskType: 1,
            taskName: 'BBBBBB',
            stepRecordNum: 1,
            executeTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            finishTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 1,
            createTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
            updateTime: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            executeTime: currentDate,
            finishTime: currentDate,
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of TaskExecuteRecord', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a TaskExecuteRecord', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a TaskExecuteRecord', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
