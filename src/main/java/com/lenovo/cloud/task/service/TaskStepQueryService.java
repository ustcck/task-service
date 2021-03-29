package com.lenovo.cloud.task.service;

import com.lenovo.cloud.task.domain.*; // for static metamodels
import com.lenovo.cloud.task.domain.TaskStep;
import com.lenovo.cloud.task.repository.TaskStepRepository;
import com.lenovo.cloud.task.service.dto.TaskStepCriteria;
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
 * Service for executing complex queries for {@link TaskStep} entities in the database.
 * The main input is a {@link TaskStepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaskStep} or a {@link Page} of {@link TaskStep} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskStepQueryService extends QueryService<TaskStep> {

    private final Logger log = LoggerFactory.getLogger(TaskStepQueryService.class);

    private final TaskStepRepository taskStepRepository;

    public TaskStepQueryService(TaskStepRepository taskStepRepository) {
        this.taskStepRepository = taskStepRepository;
    }

    /**
     * Return a {@link List} of {@link TaskStep} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskStep> findByCriteria(TaskStepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskStep> specification = createSpecification(criteria);
        return taskStepRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskStep} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStep> findByCriteria(TaskStepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskStep> specification = createSpecification(criteria);
        return taskStepRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskStepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskStep> specification = createSpecification(criteria);
        return taskStepRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskStepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskStep> createSpecification(TaskStepCriteria criteria) {
        Specification<TaskStep> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskStep_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TaskStep_.name));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), TaskStep_.order));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), TaskStep_.createTime));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), TaskStep_.updateTime));
            }
            if (criteria.getDelFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelFlag(), TaskStep_.delFlag));
            }
            if (criteria.getTaskId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTaskId(), root -> root.join(TaskStep_.task, JoinType.LEFT).get(Task_.id))
                    );
            }
        }
        return specification;
    }
}
