package com.lenovo.cloud.task.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lenovo.cloud.task.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskExecuteRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskExecuteRecord.class);
        TaskExecuteRecord taskExecuteRecord1 = new TaskExecuteRecord();
        taskExecuteRecord1.setId(1L);
        TaskExecuteRecord taskExecuteRecord2 = new TaskExecuteRecord();
        taskExecuteRecord2.setId(taskExecuteRecord1.getId());
        assertThat(taskExecuteRecord1).isEqualTo(taskExecuteRecord2);
        taskExecuteRecord2.setId(2L);
        assertThat(taskExecuteRecord1).isNotEqualTo(taskExecuteRecord2);
        taskExecuteRecord1.setId(null);
        assertThat(taskExecuteRecord1).isNotEqualTo(taskExecuteRecord2);
    }
}
