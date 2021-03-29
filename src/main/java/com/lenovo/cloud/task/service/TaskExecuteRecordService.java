package com.lenovo.cloud.task.service;

import com.lenovo.cloud.task.domain.TaskExecuteRecord;
import com.lenovo.cloud.task.repository.TaskExecuteRecordRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskExecuteRecord}.
 */
@Service
@Transactional
public class TaskExecuteRecordService {

    private final Logger log = LoggerFactory.getLogger(TaskExecuteRecordService.class);

    private final TaskExecuteRecordRepository taskExecuteRecordRepository;

    public TaskExecuteRecordService(TaskExecuteRecordRepository taskExecuteRecordRepository) {
        this.taskExecuteRecordRepository = taskExecuteRecordRepository;
    }

    /**
     * Save a taskExecuteRecord.
     *
     * @param taskExecuteRecord the entity to save.
     * @return the persisted entity.
     */
    public TaskExecuteRecord save(TaskExecuteRecord taskExecuteRecord) {
        log.debug("Request to save TaskExecuteRecord : {}", taskExecuteRecord);
        return taskExecuteRecordRepository.save(taskExecuteRecord);
    }

    /**
     * Partially update a taskExecuteRecord.
     *
     * @param taskExecuteRecord the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskExecuteRecord> partialUpdate(TaskExecuteRecord taskExecuteRecord) {
        log.debug("Request to partially update TaskExecuteRecord : {}", taskExecuteRecord);

        return taskExecuteRecordRepository
            .findById(taskExecuteRecord.getId())
            .map(
                existingTaskExecuteRecord -> {
                    if (taskExecuteRecord.getTaskId() != null) {
                        existingTaskExecuteRecord.setTaskId(taskExecuteRecord.getTaskId());
                    }

                    if (taskExecuteRecord.getTaskType() != null) {
                        existingTaskExecuteRecord.setTaskType(taskExecuteRecord.getTaskType());
                    }

                    if (taskExecuteRecord.getTaskName() != null) {
                        existingTaskExecuteRecord.setTaskName(taskExecuteRecord.getTaskName());
                    }

                    if (taskExecuteRecord.getStepRecordNum() != null) {
                        existingTaskExecuteRecord.setStepRecordNum(taskExecuteRecord.getStepRecordNum());
                    }

                    if (taskExecuteRecord.getExecuteTime() != null) {
                        existingTaskExecuteRecord.setExecuteTime(taskExecuteRecord.getExecuteTime());
                    }

                    if (taskExecuteRecord.getFinishTime() != null) {
                        existingTaskExecuteRecord.setFinishTime(taskExecuteRecord.getFinishTime());
                    }

                    if (taskExecuteRecord.getStatus() != null) {
                        existingTaskExecuteRecord.setStatus(taskExecuteRecord.getStatus());
                    }

                    if (taskExecuteRecord.getCreateTime() != null) {
                        existingTaskExecuteRecord.setCreateTime(taskExecuteRecord.getCreateTime());
                    }

                    if (taskExecuteRecord.getUpdateTime() != null) {
                        existingTaskExecuteRecord.setUpdateTime(taskExecuteRecord.getUpdateTime());
                    }

                    return existingTaskExecuteRecord;
                }
            )
            .map(taskExecuteRecordRepository::save);
    }

    /**
     * Get all the taskExecuteRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskExecuteRecord> findAll(Pageable pageable) {
        log.debug("Request to get all TaskExecuteRecords");
        return taskExecuteRecordRepository.findAll(pageable);
    }

    /**
     * Get one taskExecuteRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskExecuteRecord> findOne(Long id) {
        log.debug("Request to get TaskExecuteRecord : {}", id);
        return taskExecuteRecordRepository.findById(id);
    }

    /**
     * Delete the taskExecuteRecord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskExecuteRecord : {}", id);
        taskExecuteRecordRepository.deleteById(id);
    }
}
