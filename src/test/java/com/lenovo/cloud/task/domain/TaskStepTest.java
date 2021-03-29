package com.lenovo.cloud.task.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.lenovo.cloud.task.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskStepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskStep.class);
        TaskStep taskStep1 = new TaskStep();
        taskStep1.setId(1L);
        TaskStep taskStep2 = new TaskStep();
        taskStep2.setId(taskStep1.getId());
        assertThat(taskStep1).isEqualTo(taskStep2);
        taskStep2.setId(2L);
        assertThat(taskStep1).isNotEqualTo(taskStep2);
        taskStep1.setId(null);
        assertThat(taskStep1).isNotEqualTo(taskStep2);
    }
}
