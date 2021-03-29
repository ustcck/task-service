package com.lenovo.cloud.task.service;

import com.lenovo.cloud.task.domain.*; // for static metamodels
import com.lenovo.cloud.task.domain.TaskExecuteRecord;
import com.lenovo.cloud.task.repository.TaskExecuteRecordRepository;
import com.lenovo.cloud.task.service.dto.TaskExecuteRecordCriteria;
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
 * Service for executing complex queries for {@link TaskExecuteRecord} entities in the database.
 * The main input is a {@link TaskExecuteRecordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskExecuteRecord} or a {@link Page} of {@link TaskExecuteRecord} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskExecuteRecordQueryService extends QueryService<TaskExecuteRecord> {

    private final Logger log = LoggerFactory.getLogger(TaskExecuteRecordQueryService.class);

    private final TaskExecuteRecordRepository taskExecuteRecordRepository;

    public TaskExecuteRecordQueryService(TaskExecuteRecordRepository taskExecuteRecordRepository) {
        this.taskExecuteRecordRepository = taskExecuteRecordRepository;
    }

    /**
     * Return a {@link List} of {@link TaskExecuteRecord} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskExecuteRecord> findByCriteria(TaskExecuteRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskExecuteRecord> specification = createSpecification(criteria);
        return taskExecuteRecordRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskExecuteRecord} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskExecuteRecord> findByCriteria(TaskExecuteRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskExecuteRecord> specification = createSpecification(criteria);
        return taskExecuteRecordRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskExecuteRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskExecuteRecord> specification = createSpecification(criteria);
        return taskExecuteRecordRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskExecuteRecordCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskExecuteRecord> createSpecification(TaskExecuteRecordCriteria criteria) {
        Specification<TaskExecuteRecord> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskExecuteRecord_.id));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), TaskExecuteRecord_.taskId));
            }
            if (criteria.getTaskType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskType(), TaskExecuteRecord_.taskType));
            }
            if (criteria.getTaskName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaskName(), TaskExecuteRecord_.taskName));
            }
            if (criteria.getStepRecordNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStepRecordNum(), TaskExecuteRecord_.stepRecordNum));
            }
            if (criteria.getExecuteTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExecuteTime(), TaskExecuteRecord_.executeTime));
            }
            if (criteria.getFinishTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishTime(), TaskExecuteRecord_.finishTime));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), TaskExecuteRecord_.status));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), TaskExecuteRecord_.createTime));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), TaskExecuteRecord_.updateTime));
            }
            if (criteria.getTaskStepExecuteRecordId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTaskStepExecuteRecordId(),
                            root -> root.join(TaskExecuteRecord_.taskStepExecuteRecords, JoinType.LEFT).get(TaskStepExecuteRecord_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
