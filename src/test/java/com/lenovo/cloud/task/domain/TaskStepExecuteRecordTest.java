package com.lenovo.cloud.task.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lenovo.cloud.task.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskStepExecuteRecordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskStepExecuteRecord.class);
        TaskStepExecuteRecord taskStepExecuteRecord1 = new TaskStepExecuteRecord();
        taskStepExecuteRecord1.setId(1L);
        TaskStepExecuteRecord taskStepExecuteRecord2 = new TaskStepExecuteRecord();
        taskStepExecuteRecord2.setId(taskStepExecuteRecord1.getId());
        assertThat(taskStepExecuteRecord1).isEqualTo(taskStepExecuteRecord2);
        taskStepExecuteRecord2.setId(2L);
        assertThat(taskStepExecuteRecord1).isNotEqualTo(taskStepExecuteRecord2);
        taskStepExecuteRecord1.setId(null);
        assertThat(taskStepExecuteRecord1).isNotEqualTo(taskStepExecuteRecord2);
    }
}
