package com.lenovo.cloud.task.repository;

import com.lenovo.cloud.task.domain.TaskExecuteRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaskExecuteRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskExecuteRecordRepository extends JpaRepository<TaskExecuteRecord, Long>, JpaSpecificationExecutor<TaskExecuteRecord> {}
