package com.lenovo.cloud.task.service;

import com.lenovo.cloud.task.domain.*; // for static metamodels
import com.lenovo.cloud.task.domain.TaskStepExecuteRecord;
import com.lenovo.cloud.task.repository.TaskStepExecuteRecordRepository;
import com.lenovo.cloud.task.service.dto.TaskStepExecuteRecordCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TaskStepExecuteRecord} entities in the database.
 * The main input is a {@link TaskStepExecuteRecordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskStepExecuteRecord} or a {@link Page} of {@link TaskStepExecuteRecord} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskStepExecuteRecordQueryService extends QueryService<TaskStepExecuteRecord> {

    private final Logger log = LoggerFactory.getLogger(TaskStepExecuteRecordQueryService.class);

    private final TaskStepExecuteRecordRepository taskStepExecuteRecordRepository;

    public TaskStepExecuteRecordQueryService(TaskStepExecuteRecordRepository taskStepExecuteRecordRepository) {
        this.taskStepExecuteRecordRepository = taskStepExecuteRecordRepository;
    }

    /**
     * Return a {@link List} of {@link TaskStepExecuteRecord} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskStepExecuteRecord> findByCriteria(TaskStepExecuteRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskStepExecuteRecord> specification = createSpecification(criteria);
        return taskStepExecuteRecordRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskStepExecuteRecord} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStepExecuteRecord> findByCriteria(TaskStepExecuteRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskStepExecuteRecord> specification = createSpecification(criteria);
        return taskStepExecuteRecordRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskStepExecuteRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskStepExecuteRecord> specification = createSpecification(criteria);
        return taskStepExecuteRecordRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskStepExecuteRecordCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskStepExecuteRecord> createSpecification(TaskStepExecuteRecordCriteria criteria) {
        Specification<TaskStepExecuteRecord> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskStepExecuteRecord_.id));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), TaskStepExecuteRecord_.taskId));
            }
            if (criteria.getTaskStepId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskStepId(), TaskStepExecuteRecord_.taskStepId));
            }
            if (criteria.getTaskStepExecuteOrder() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getTaskStepExecuteOrder(), TaskStepExecuteRecord_.taskStepExecuteOrder)
                    );
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), TaskStepExecuteRecord_.status));
            }
            if (criteria.getExecuteTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecuteTime(), TaskStepExecuteRecord_.executeTime));
            }
            if (criteria.getFinishTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishTime(), TaskStepExecuteRecord_.finishTime));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), TaskStepExecuteRecord_.createTime));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), TaskStepExecuteRecord_.updateTime));
            }
            if (criteria.getTaskExecuteRecordId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTaskExecuteRecordId(),
                            root -> root.join(TaskStepExecuteRecord_.taskExecuteRecord, JoinType.LEFT).get(TaskExecuteRecord_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
