package com.lenovo.cloud.task.service;

import com.lenovo.cloud.task.domain.TaskStepExecuteRecord;
import com.lenovo.cloud.task.repository.TaskStepExecuteRecordRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskStepExecuteRecord}.
 */
@Service
@Transactional
public class TaskStepExecuteRecordService {

    private final Logger log = LoggerFactory.getLogger(TaskStepExecuteRecordService.class);

    private final TaskStepExecuteRecordRepository taskStepExecuteRecordRepository;

    public TaskStepExecuteRecordService(TaskStepExecuteRecordRepository taskStepExecuteRecordRepository) {
        this.taskStepExecuteRecordRepository = taskStepExecuteRecordRepository;
    }

    /**
     * Save a taskStepExecuteRecord.
     *
     * @param taskStepExecuteRecord the entity to save.
     * @return the persisted entity.
     */
    public TaskStepExecuteRecord save(TaskStepExecuteRecord taskStepExecuteRecord) {
        log.debug("Request to save TaskStepExecuteRecord : {}", taskStepExecuteRecord);
        return taskStepExecuteRecordRepository.save(taskStepExecuteRecord);
    }

    /**
     * Partially update a taskStepExecuteRecord.
     *
     * @param taskStepExecuteRecord the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskStepExecuteRecord> partialUpdate(TaskStepExecuteRecord taskStepExecuteRecord) {
        log.debug("Request to partially update TaskStepExecuteRecord : {}", taskStepExecuteRecord);

        return taskStepExecuteRecordRepository
            .findById(taskStepExecuteRecord.getId())
            .map(
                existingTaskStepExecuteRecord -> {
                    if (taskStepExecuteRecord.getTaskId() != null) {
                        existingTaskStepExecuteRecord.setTaskId(taskStepExecuteRecord.getTaskId());
                    }

                    if (taskStepExecuteRecord.getTaskStepId() != null) {
                        existingTaskStepExecuteRecord.setTaskStepId(taskStepExecuteRecord.getTaskStepId());
                    }

                    if (taskStepExecuteRecord.getTaskStepExecuteOrder() != null) {
                        existingTaskStepExecuteRecord.setTaskStepExecuteOrder(taskStepExecuteRecord.getTaskStepExecuteOrder());
                    }

                    if (taskStepExecuteRecord.getStatus() != null) {
                        existingTaskStepExecuteRecord.setStatus(taskStepExecuteRecord.getStatus());
                    }

                    if (taskStepExecuteRecord.getExecuteTime() != null) {
                        existingTaskStepExecuteRecord.setExecuteTime(taskStepExecuteRecord.getExecuteTime());
                    }

                    if (taskStepExecuteRecord.getFinishTime() != null) {
                        existingTaskStepExecuteRecord.setFinishTime(taskStepExecuteRecord.getFinishTime());
                    }

                    if (taskStepExecuteRecord.getCreateTime() != null) {
                        existingTaskStepExecuteRecord.setCreateTime(taskStepExecuteRecord.getCreateTime());
                    }

                    if (taskStepExecuteRecord.getUpdateTime() != null) {
                        existingTaskStepExecuteRecord.setUpdateTime(taskStepExecuteRecord.getUpdateTime());
                    }

                    return existingTaskStepExecuteRecord;
                }
            )
            .map(taskStepExecuteRecordRepository::save);
    }

    /**
     * Get all the taskStepExecuteRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStepExecuteRecord> findAll(Pageable pageable) {
        log.debug("Request to get all TaskStepExecuteRecords");
        return taskStepExecuteRecordRepository.findAll(pageable);
    }

    /**
     * Get one taskStepExecuteRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskStepExecuteRecord> findOne(Long id) {
        log.debug("Request to get TaskStepExecuteRecord : {}", id);
        return taskStepExecuteRecordRepository.findById(id);
    }

    /**
     * Delete the taskStepExecuteRecord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskStepExecuteRecord : {}", id);
        taskStepExecuteRecordRepository.deleteById(id);
    }
}
