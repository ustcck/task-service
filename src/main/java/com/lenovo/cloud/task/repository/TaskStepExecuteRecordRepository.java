package com.lenovo.cloud.task.repository;

import com.lenovo.cloud.task.domain.TaskStepExecuteRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaskStepExecuteRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskStepExecuteRecordRepository
    extends JpaRepository<TaskStepExecuteRecord, Long>, JpaSpecificationExecutor<TaskStepExecuteRecord> {}
