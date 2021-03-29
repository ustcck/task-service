package com.lenovo.cloud.task.repository;

import com.lenovo.cloud.task.domain.TaskStep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaskStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskStepRepository extends JpaRepository<TaskStep, Long>, JpaSpecificationExecutor<TaskStep> {}
