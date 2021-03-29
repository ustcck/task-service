package com.lenovo.cloud.task.service;

import com.lenovo.cloud.task.domain.TaskStep;
import com.lenovo.cloud.task.repository.TaskStepRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskStep}.
 */
@Service
@Transactional
public class TaskStepService {

    private final Logger log = LoggerFactory.getLogger(TaskStepService.class);

    private final TaskStepRepository taskStepRepository;

    public TaskStepService(TaskStepRepository taskStepRepository) {
        this.taskStepRepository = taskStepRepository;
    }

    /**
     * Save a taskStep.
     *
     * @param taskStep the entity to save.
     * @return the persisted entity.
     */
    public TaskStep save(TaskStep taskStep) {
        log.debug("Request to save TaskStep : {}", taskStep);
        return taskStepRepository.save(taskStep);
    }

    /**
     * Partially update a taskStep.
     *
     * @param taskStep the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskStep> partialUpdate(TaskStep taskStep) {
        log.debug("Request to partially update TaskStep : {}", taskStep);

        return taskStepRepository
            .findById(taskStep.getId())
            .map(
                existingTaskStep -> {
                    if (taskStep.getName() != null) {
                        existingTaskStep.setName(taskStep.getName());
                    }

                    if (taskStep.getOrder() != null) {
                        existingTaskStep.setOrder(taskStep.getOrder());
                    }

                    if (taskStep.getCreateTime() != null) {
                        existingTaskStep.setCreateTime(taskStep.getCreateTime());
                    }

                    if (taskStep.getUpdateTime() != null) {
                        existingTaskStep.setUpdateTime(taskStep.getUpdateTime());
                    }

                    if (taskStep.getDelFlag() != null) {
                        existingTaskStep.setDelFlag(taskStep.getDelFlag());
                    }

                    return existingTaskStep;
                }
            )
            .map(taskStepRepository::save);
    }

    /**
     * Get all the taskSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStep> findAll(Pageable pageable) {
        log.debug("Request to get all TaskSteps");
        return taskStepRepository.findAll(pageable);
    }

    /**
     * Get one taskStep by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskStep> findOne(Long id) {
        log.debug("Request to get TaskStep : {}", id);
        return taskStepRepository.findById(id);
    }

    /**
     * Delete the taskStep by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskStep : {}", id);
        taskStepRepository.deleteById(id);
    }
}
