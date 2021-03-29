package com.lenovo.cloud.task.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lenovo.cloud.task.IntegrationTest;
import com.lenovo.cloud.task.domain.TaskExecuteRecord;
import com.lenovo.cloud.task.domain.TaskStepExecuteRecord;
import com.lenovo.cloud.task.repository.TaskStepExecuteRecordRepository;
import com.lenovo.cloud.task.service.TaskStepExecuteRecordQueryService;
import com.lenovo.cloud.task.service.dto.TaskStepExecuteRecordCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaskStepExecuteRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskStepExecuteRecordResourceIT {

    private static final Long DEFAULT_TASK_ID = 1L;
    private static final Long UPDATED_TASK_ID = 2L;
    private static final Long SMALLER_TASK_ID = 1L - 1L;

    private static final Long DEFAULT_TASK_STEP_ID = 1L;
    private static final Long UPDATED_TASK_STEP_ID = 2L;
    private static final Long SMALLER_TASK_STEP_ID = 1L - 1L;

    private static final Integer DEFAULT_TASK_STEP_EXECUTE_ORDER = 1;
    private static final Integer UPDATED_TASK_STEP_EXECUTE_ORDER = 2;
    private static final Integer SMALLER_TASK_STEP_EXECUTE_ORDER = 1 - 1;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final Instant DEFAULT_EXECUTE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FINISH_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISH_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaskStepExecuteRecordRepository taskStepExecuteRecordRepository;

    @Autowired
    private TaskStepExecuteRecordQueryService taskStepExecuteRecordQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskStepExecuteRecordMockMvc;

    private TaskStepExecuteRecord taskStepExecuteRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskStepExecuteRecord createEntity(EntityManager em) {
        TaskStepExecuteRecord taskStepExecuteRecord = new TaskStepExecuteRecord()
            .taskId(DEFAULT_TASK_ID)
            .taskStepId(DEFAULT_TASK_STEP_ID)
            .taskStepExecuteOrder(DEFAULT_TASK_STEP_EXECUTE_ORDER)
            .status(DEFAULT_STATUS)
            .executeTime(DEFAULT_EXECUTE_TIME)
            .finishTime(DEFAULT_FINISH_TIME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return taskStepExecuteRecord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskStepExecuteRecord createUpdatedEntity(EntityManager em) {
        TaskStepExecuteRecord taskStepExecuteRecord = new TaskStepExecuteRecord()
            .taskId(UPDATED_TASK_ID)
            .taskStepId(UPDATED_TASK_STEP_ID)
            .taskStepExecuteOrder(UPDATED_TASK_STEP_EXECUTE_ORDER)
            .status(UPDATED_STATUS)
            .executeTime(UPDATED_EXECUTE_TIME)
            .finishTime(UPDATED_FINISH_TIME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        return taskStepExecuteRecord;
    }

    @BeforeEach
    public void initTest() {
        taskStepExecuteRecord = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskStepExecuteRecord() throws Exception {
        int databaseSizeBeforeCreate = taskStepExecuteRecordRepository.findAll().size();
        // Create the TaskStepExecuteRecord
        restTaskStepExecuteRecordMockMvc
            .perform(
                post("/api/task-step-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStepExecuteRecord))
            )
            .andExpect(status().isCreated());

        // Validate the TaskStepExecuteRecord in the database
        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeCreate + 1);
        TaskStepExecuteRecord testTaskStepExecuteRecord = taskStepExecuteRecordList.get(taskStepExecuteRecordList.size() - 1);
        assertThat(testTaskStepExecuteRecord.getTaskId()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testTaskStepExecuteRecord.getTaskStepId()).isEqualTo(DEFAULT_TASK_STEP_ID);
        assertThat(testTaskStepExecuteRecord.getTaskStepExecuteOrder()).isEqualTo(DEFAULT_TASK_STEP_EXECUTE_ORDER);
        assertThat(testTaskStepExecuteRecord.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaskStepExecuteRecord.getExecuteTime()).isEqualTo(DEFAULT_EXECUTE_TIME);
        assertThat(testTaskStepExecuteRecord.getFinishTime()).isEqualTo(DEFAULT_FINISH_TIME);
        assertThat(testTaskStepExecuteRecord.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testTaskStepExecuteRecord.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void createTaskStepExecuteRecordWithExistingId() throws Exception {
        // Create the TaskStepExecuteRecord with an existing ID
        taskStepExecuteRecord.setId(1L);

        int databaseSizeBeforeCreate = taskStepExecuteRecordRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskStepExecuteRecordMockMvc
            .perform(
                post("/api/task-step-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStepExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskStepExecuteRecord in the database
        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStepExecuteRecordRepository.findAll().size();
        // set the field null
        taskStepExecuteRecord.setStatus(null);

        // Create the TaskStepExecuteRecord, which fails.

        restTaskStepExecuteRecordMockMvc
            .perform(
                post("/api/task-step-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStepExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStepExecuteRecordRepository.findAll().size();
        // set the field null
        taskStepExecuteRecord.setCreateTime(null);

        // Create the TaskStepExecuteRecord, which fails.

        restTaskStepExecuteRecordMockMvc
            .perform(
                post("/api/task-step-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStepExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStepExecuteRecordRepository.findAll().size();
        // set the field null
        taskStepExecuteRecord.setUpdateTime(null);

        // Create the TaskStepExecuteRecord, which fails.

        restTaskStepExecuteRecordMockMvc
            .perform(
                post("/api/task-step-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStepExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecords() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList
        restTaskStepExecuteRecordMockMvc
            .perform(get("/api/task-step-execute-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStepExecuteRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskStepId").value(hasItem(DEFAULT_TASK_STEP_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskStepExecuteOrder").value(hasItem(DEFAULT_TASK_STEP_EXECUTE_ORDER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].executeTime").value(hasItem(DEFAULT_EXECUTE_TIME.toString())))
            .andExpect(jsonPath("$.[*].finishTime").value(hasItem(DEFAULT_FINISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getTaskStepExecuteRecord() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get the taskStepExecuteRecord
        restTaskStepExecuteRecordMockMvc
            .perform(get("/api/task-step-execute-records/{id}", taskStepExecuteRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskStepExecuteRecord.getId().intValue()))
            .andExpect(jsonPath("$.taskId").value(DEFAULT_TASK_ID.intValue()))
            .andExpect(jsonPath("$.taskStepId").value(DEFAULT_TASK_STEP_ID.intValue()))
            .andExpect(jsonPath("$.taskStepExecuteOrder").value(DEFAULT_TASK_STEP_EXECUTE_ORDER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.executeTime").value(DEFAULT_EXECUTE_TIME.toString()))
            .andExpect(jsonPath("$.finishTime").value(DEFAULT_FINISH_TIME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getTaskStepExecuteRecordsByIdFiltering() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        Long id = taskStepExecuteRecord.getId();

        defaultTaskStepExecuteRecordShouldBeFound("id.equals=" + id);
        defaultTaskStepExecuteRecordShouldNotBeFound("id.notEquals=" + id);

        defaultTaskStepExecuteRecordShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTaskStepExecuteRecordShouldNotBeFound("id.greaterThan=" + id);

        defaultTaskStepExecuteRecordShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTaskStepExecuteRecordShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskIdIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskId equals to DEFAULT_TASK_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskId.equals=" + DEFAULT_TASK_ID);

        // Get all the taskStepExecuteRecordList where taskId equals to UPDATED_TASK_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskId.equals=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskId not equals to DEFAULT_TASK_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskId.notEquals=" + DEFAULT_TASK_ID);

        // Get all the taskStepExecuteRecordList where taskId not equals to UPDATED_TASK_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskId.notEquals=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskIdIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskId in DEFAULT_TASK_ID or UPDATED_TASK_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskId.in=" + DEFAULT_TASK_ID + "," + UPDATED_TASK_ID);

        // Get all the taskStepExecuteRecordList where taskId equals to UPDATED_TASK_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskId.in=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskId is not null
        defaultTaskStepExecuteRecordShouldBeFound("taskId.specified=true");

        // Get all the taskStepExecuteRecordList where taskId is null
        defaultTaskStepExecuteRecordShouldNotBeFound("taskId.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskId is greater than or equal to DEFAULT_TASK_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskId.greaterThanOrEqual=" + DEFAULT_TASK_ID);

        // Get all the taskStepExecuteRecordList where taskId is greater than or equal to UPDATED_TASK_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskId.greaterThanOrEqual=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskId is less than or equal to DEFAULT_TASK_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskId.lessThanOrEqual=" + DEFAULT_TASK_ID);

        // Get all the taskStepExecuteRecordList where taskId is less than or equal to SMALLER_TASK_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskId.lessThanOrEqual=" + SMALLER_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskIdIsLessThanSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskId is less than DEFAULT_TASK_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskId.lessThan=" + DEFAULT_TASK_ID);

        // Get all the taskStepExecuteRecordList where taskId is less than UPDATED_TASK_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskId.lessThan=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskId is greater than DEFAULT_TASK_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskId.greaterThan=" + DEFAULT_TASK_ID);

        // Get all the taskStepExecuteRecordList where taskId is greater than SMALLER_TASK_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskId.greaterThan=" + SMALLER_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepIdIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepId equals to DEFAULT_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskStepId.equals=" + DEFAULT_TASK_STEP_ID);

        // Get all the taskStepExecuteRecordList where taskStepId equals to UPDATED_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepId.equals=" + UPDATED_TASK_STEP_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepId not equals to DEFAULT_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepId.notEquals=" + DEFAULT_TASK_STEP_ID);

        // Get all the taskStepExecuteRecordList where taskStepId not equals to UPDATED_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskStepId.notEquals=" + UPDATED_TASK_STEP_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepIdIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepId in DEFAULT_TASK_STEP_ID or UPDATED_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskStepId.in=" + DEFAULT_TASK_STEP_ID + "," + UPDATED_TASK_STEP_ID);

        // Get all the taskStepExecuteRecordList where taskStepId equals to UPDATED_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepId.in=" + UPDATED_TASK_STEP_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepId is not null
        defaultTaskStepExecuteRecordShouldBeFound("taskStepId.specified=true");

        // Get all the taskStepExecuteRecordList where taskStepId is null
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepId.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepId is greater than or equal to DEFAULT_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskStepId.greaterThanOrEqual=" + DEFAULT_TASK_STEP_ID);

        // Get all the taskStepExecuteRecordList where taskStepId is greater than or equal to UPDATED_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepId.greaterThanOrEqual=" + UPDATED_TASK_STEP_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepId is less than or equal to DEFAULT_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskStepId.lessThanOrEqual=" + DEFAULT_TASK_STEP_ID);

        // Get all the taskStepExecuteRecordList where taskStepId is less than or equal to SMALLER_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepId.lessThanOrEqual=" + SMALLER_TASK_STEP_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepIdIsLessThanSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepId is less than DEFAULT_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepId.lessThan=" + DEFAULT_TASK_STEP_ID);

        // Get all the taskStepExecuteRecordList where taskStepId is less than UPDATED_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskStepId.lessThan=" + UPDATED_TASK_STEP_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepId is greater than DEFAULT_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepId.greaterThan=" + DEFAULT_TASK_STEP_ID);

        // Get all the taskStepExecuteRecordList where taskStepId is greater than SMALLER_TASK_STEP_ID
        defaultTaskStepExecuteRecordShouldBeFound("taskStepId.greaterThan=" + SMALLER_TASK_STEP_ID);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepExecuteOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder equals to DEFAULT_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldBeFound("taskStepExecuteOrder.equals=" + DEFAULT_TASK_STEP_EXECUTE_ORDER);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder equals to UPDATED_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepExecuteOrder.equals=" + UPDATED_TASK_STEP_EXECUTE_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepExecuteOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder not equals to DEFAULT_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepExecuteOrder.notEquals=" + DEFAULT_TASK_STEP_EXECUTE_ORDER);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder not equals to UPDATED_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldBeFound("taskStepExecuteOrder.notEquals=" + UPDATED_TASK_STEP_EXECUTE_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepExecuteOrderIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder in DEFAULT_TASK_STEP_EXECUTE_ORDER or UPDATED_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldBeFound(
            "taskStepExecuteOrder.in=" + DEFAULT_TASK_STEP_EXECUTE_ORDER + "," + UPDATED_TASK_STEP_EXECUTE_ORDER
        );

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder equals to UPDATED_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepExecuteOrder.in=" + UPDATED_TASK_STEP_EXECUTE_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepExecuteOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is not null
        defaultTaskStepExecuteRecordShouldBeFound("taskStepExecuteOrder.specified=true");

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is null
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepExecuteOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepExecuteOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is greater than or equal to DEFAULT_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldBeFound("taskStepExecuteOrder.greaterThanOrEqual=" + DEFAULT_TASK_STEP_EXECUTE_ORDER);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is greater than or equal to UPDATED_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepExecuteOrder.greaterThanOrEqual=" + UPDATED_TASK_STEP_EXECUTE_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepExecuteOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is less than or equal to DEFAULT_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldBeFound("taskStepExecuteOrder.lessThanOrEqual=" + DEFAULT_TASK_STEP_EXECUTE_ORDER);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is less than or equal to SMALLER_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepExecuteOrder.lessThanOrEqual=" + SMALLER_TASK_STEP_EXECUTE_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepExecuteOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is less than DEFAULT_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepExecuteOrder.lessThan=" + DEFAULT_TASK_STEP_EXECUTE_ORDER);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is less than UPDATED_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldBeFound("taskStepExecuteOrder.lessThan=" + UPDATED_TASK_STEP_EXECUTE_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskStepExecuteOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is greater than DEFAULT_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldNotBeFound("taskStepExecuteOrder.greaterThan=" + DEFAULT_TASK_STEP_EXECUTE_ORDER);

        // Get all the taskStepExecuteRecordList where taskStepExecuteOrder is greater than SMALLER_TASK_STEP_EXECUTE_ORDER
        defaultTaskStepExecuteRecordShouldBeFound("taskStepExecuteOrder.greaterThan=" + SMALLER_TASK_STEP_EXECUTE_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where status equals to DEFAULT_STATUS
        defaultTaskStepExecuteRecordShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the taskStepExecuteRecordList where status equals to UPDATED_STATUS
        defaultTaskStepExecuteRecordShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where status not equals to DEFAULT_STATUS
        defaultTaskStepExecuteRecordShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the taskStepExecuteRecordList where status not equals to UPDATED_STATUS
        defaultTaskStepExecuteRecordShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTaskStepExecuteRecordShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the taskStepExecuteRecordList where status equals to UPDATED_STATUS
        defaultTaskStepExecuteRecordShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where status is not null
        defaultTaskStepExecuteRecordShouldBeFound("status.specified=true");

        // Get all the taskStepExecuteRecordList where status is null
        defaultTaskStepExecuteRecordShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where status is greater than or equal to DEFAULT_STATUS
        defaultTaskStepExecuteRecordShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the taskStepExecuteRecordList where status is greater than or equal to UPDATED_STATUS
        defaultTaskStepExecuteRecordShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where status is less than or equal to DEFAULT_STATUS
        defaultTaskStepExecuteRecordShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the taskStepExecuteRecordList where status is less than or equal to SMALLER_STATUS
        defaultTaskStepExecuteRecordShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where status is less than DEFAULT_STATUS
        defaultTaskStepExecuteRecordShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the taskStepExecuteRecordList where status is less than UPDATED_STATUS
        defaultTaskStepExecuteRecordShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where status is greater than DEFAULT_STATUS
        defaultTaskStepExecuteRecordShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the taskStepExecuteRecordList where status is greater than SMALLER_STATUS
        defaultTaskStepExecuteRecordShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByExecuteTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where executeTime equals to DEFAULT_EXECUTE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("executeTime.equals=" + DEFAULT_EXECUTE_TIME);

        // Get all the taskStepExecuteRecordList where executeTime equals to UPDATED_EXECUTE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("executeTime.equals=" + UPDATED_EXECUTE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByExecuteTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where executeTime not equals to DEFAULT_EXECUTE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("executeTime.notEquals=" + DEFAULT_EXECUTE_TIME);

        // Get all the taskStepExecuteRecordList where executeTime not equals to UPDATED_EXECUTE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("executeTime.notEquals=" + UPDATED_EXECUTE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByExecuteTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where executeTime in DEFAULT_EXECUTE_TIME or UPDATED_EXECUTE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("executeTime.in=" + DEFAULT_EXECUTE_TIME + "," + UPDATED_EXECUTE_TIME);

        // Get all the taskStepExecuteRecordList where executeTime equals to UPDATED_EXECUTE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("executeTime.in=" + UPDATED_EXECUTE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByExecuteTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where executeTime is not null
        defaultTaskStepExecuteRecordShouldBeFound("executeTime.specified=true");

        // Get all the taskStepExecuteRecordList where executeTime is null
        defaultTaskStepExecuteRecordShouldNotBeFound("executeTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByFinishTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where finishTime equals to DEFAULT_FINISH_TIME
        defaultTaskStepExecuteRecordShouldBeFound("finishTime.equals=" + DEFAULT_FINISH_TIME);

        // Get all the taskStepExecuteRecordList where finishTime equals to UPDATED_FINISH_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("finishTime.equals=" + UPDATED_FINISH_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByFinishTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where finishTime not equals to DEFAULT_FINISH_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("finishTime.notEquals=" + DEFAULT_FINISH_TIME);

        // Get all the taskStepExecuteRecordList where finishTime not equals to UPDATED_FINISH_TIME
        defaultTaskStepExecuteRecordShouldBeFound("finishTime.notEquals=" + UPDATED_FINISH_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByFinishTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where finishTime in DEFAULT_FINISH_TIME or UPDATED_FINISH_TIME
        defaultTaskStepExecuteRecordShouldBeFound("finishTime.in=" + DEFAULT_FINISH_TIME + "," + UPDATED_FINISH_TIME);

        // Get all the taskStepExecuteRecordList where finishTime equals to UPDATED_FINISH_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("finishTime.in=" + UPDATED_FINISH_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByFinishTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where finishTime is not null
        defaultTaskStepExecuteRecordShouldBeFound("finishTime.specified=true");

        // Get all the taskStepExecuteRecordList where finishTime is null
        defaultTaskStepExecuteRecordShouldNotBeFound("finishTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where createTime equals to DEFAULT_CREATE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the taskStepExecuteRecordList where createTime equals to UPDATED_CREATE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where createTime not equals to DEFAULT_CREATE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the taskStepExecuteRecordList where createTime not equals to UPDATED_CREATE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the taskStepExecuteRecordList where createTime equals to UPDATED_CREATE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where createTime is not null
        defaultTaskStepExecuteRecordShouldBeFound("createTime.specified=true");

        // Get all the taskStepExecuteRecordList where createTime is null
        defaultTaskStepExecuteRecordShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the taskStepExecuteRecordList where updateTime equals to UPDATED_UPDATE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByUpdateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where updateTime not equals to DEFAULT_UPDATE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("updateTime.notEquals=" + DEFAULT_UPDATE_TIME);

        // Get all the taskStepExecuteRecordList where updateTime not equals to UPDATED_UPDATE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("updateTime.notEquals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultTaskStepExecuteRecordShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the taskStepExecuteRecordList where updateTime equals to UPDATED_UPDATE_TIME
        defaultTaskStepExecuteRecordShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        // Get all the taskStepExecuteRecordList where updateTime is not null
        defaultTaskStepExecuteRecordShouldBeFound("updateTime.specified=true");

        // Get all the taskStepExecuteRecordList where updateTime is null
        defaultTaskStepExecuteRecordShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepExecuteRecordsByTaskExecuteRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);
        TaskExecuteRecord taskExecuteRecord = TaskExecuteRecordResourceIT.createEntity(em);
        em.persist(taskExecuteRecord);
        em.flush();
        taskStepExecuteRecord.setTaskExecuteRecord(taskExecuteRecord);
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);
        Long taskExecuteRecordId = taskExecuteRecord.getId();

        // Get all the taskStepExecuteRecordList where taskExecuteRecord equals to taskExecuteRecordId
        defaultTaskStepExecuteRecordShouldBeFound("taskExecuteRecordId.equals=" + taskExecuteRecordId);

        // Get all the taskStepExecuteRecordList where taskExecuteRecord equals to taskExecuteRecordId + 1
        defaultTaskStepExecuteRecordShouldNotBeFound("taskExecuteRecordId.equals=" + (taskExecuteRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaskStepExecuteRecordShouldBeFound(String filter) throws Exception {
        restTaskStepExecuteRecordMockMvc
            .perform(get("/api/task-step-execute-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStepExecuteRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskStepId").value(hasItem(DEFAULT_TASK_STEP_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskStepExecuteOrder").value(hasItem(DEFAULT_TASK_STEP_EXECUTE_ORDER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].executeTime").value(hasItem(DEFAULT_EXECUTE_TIME.toString())))
            .andExpect(jsonPath("$.[*].finishTime").value(hasItem(DEFAULT_FINISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));

        // Check, that the count call also returns 1
        restTaskStepExecuteRecordMockMvc
            .perform(get("/api/task-step-execute-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaskStepExecuteRecordShouldNotBeFound(String filter) throws Exception {
        restTaskStepExecuteRecordMockMvc
            .perform(get("/api/task-step-execute-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaskStepExecuteRecordMockMvc
            .perform(get("/api/task-step-execute-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTaskStepExecuteRecord() throws Exception {
        // Get the taskStepExecuteRecord
        restTaskStepExecuteRecordMockMvc
            .perform(get("/api/task-step-execute-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateTaskStepExecuteRecord() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        int databaseSizeBeforeUpdate = taskStepExecuteRecordRepository.findAll().size();

        // Update the taskStepExecuteRecord
        TaskStepExecuteRecord updatedTaskStepExecuteRecord = taskStepExecuteRecordRepository.findById(taskStepExecuteRecord.getId()).get();
        // Disconnect from session so that the updates on updatedTaskStepExecuteRecord are not directly saved in db
        em.detach(updatedTaskStepExecuteRecord);
        updatedTaskStepExecuteRecord
            .taskId(UPDATED_TASK_ID)
            .taskStepId(UPDATED_TASK_STEP_ID)
            .taskStepExecuteOrder(UPDATED_TASK_STEP_EXECUTE_ORDER)
            .status(UPDATED_STATUS)
            .executeTime(UPDATED_EXECUTE_TIME)
            .finishTime(UPDATED_FINISH_TIME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restTaskStepExecuteRecordMockMvc
            .perform(
                put("/api/task-step-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaskStepExecuteRecord))
            )
            .andExpect(status().isOk());

        // Validate the TaskStepExecuteRecord in the database
        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
        TaskStepExecuteRecord testTaskStepExecuteRecord = taskStepExecuteRecordList.get(taskStepExecuteRecordList.size() - 1);
        assertThat(testTaskStepExecuteRecord.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTaskStepExecuteRecord.getTaskStepId()).isEqualTo(UPDATED_TASK_STEP_ID);
        assertThat(testTaskStepExecuteRecord.getTaskStepExecuteOrder()).isEqualTo(UPDATED_TASK_STEP_EXECUTE_ORDER);
        assertThat(testTaskStepExecuteRecord.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskStepExecuteRecord.getExecuteTime()).isEqualTo(UPDATED_EXECUTE_TIME);
        assertThat(testTaskStepExecuteRecord.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testTaskStepExecuteRecord.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskStepExecuteRecord.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void updateNonExistingTaskStepExecuteRecord() throws Exception {
        int databaseSizeBeforeUpdate = taskStepExecuteRecordRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskStepExecuteRecordMockMvc
            .perform(
                put("/api/task-step-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskStepExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskStepExecuteRecord in the database
        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskStepExecuteRecordWithPatch() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        int databaseSizeBeforeUpdate = taskStepExecuteRecordRepository.findAll().size();

        // Update the taskStepExecuteRecord using partial update
        TaskStepExecuteRecord partialUpdatedTaskStepExecuteRecord = new TaskStepExecuteRecord();
        partialUpdatedTaskStepExecuteRecord.setId(taskStepExecuteRecord.getId());

        partialUpdatedTaskStepExecuteRecord.taskId(UPDATED_TASK_ID).finishTime(UPDATED_FINISH_TIME).createTime(UPDATED_CREATE_TIME);

        restTaskStepExecuteRecordMockMvc
            .perform(
                patch("/api/task-step-execute-records")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskStepExecuteRecord))
            )
            .andExpect(status().isOk());

        // Validate the TaskStepExecuteRecord in the database
        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
        TaskStepExecuteRecord testTaskStepExecuteRecord = taskStepExecuteRecordList.get(taskStepExecuteRecordList.size() - 1);
        assertThat(testTaskStepExecuteRecord.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTaskStepExecuteRecord.getTaskStepId()).isEqualTo(DEFAULT_TASK_STEP_ID);
        assertThat(testTaskStepExecuteRecord.getTaskStepExecuteOrder()).isEqualTo(DEFAULT_TASK_STEP_EXECUTE_ORDER);
        assertThat(testTaskStepExecuteRecord.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaskStepExecuteRecord.getExecuteTime()).isEqualTo(DEFAULT_EXECUTE_TIME);
        assertThat(testTaskStepExecuteRecord.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testTaskStepExecuteRecord.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskStepExecuteRecord.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateTaskStepExecuteRecordWithPatch() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        int databaseSizeBeforeUpdate = taskStepExecuteRecordRepository.findAll().size();

        // Update the taskStepExecuteRecord using partial update
        TaskStepExecuteRecord partialUpdatedTaskStepExecuteRecord = new TaskStepExecuteRecord();
        partialUpdatedTaskStepExecuteRecord.setId(taskStepExecuteRecord.getId());

        partialUpdatedTaskStepExecuteRecord
            .taskId(UPDATED_TASK_ID)
            .taskStepId(UPDATED_TASK_STEP_ID)
            .taskStepExecuteOrder(UPDATED_TASK_STEP_EXECUTE_ORDER)
            .status(UPDATED_STATUS)
            .executeTime(UPDATED_EXECUTE_TIME)
            .finishTime(UPDATED_FINISH_TIME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restTaskStepExecuteRecordMockMvc
            .perform(
                patch("/api/task-step-execute-records")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskStepExecuteRecord))
            )
            .andExpect(status().isOk());

        // Validate the TaskStepExecuteRecord in the database
        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
        TaskStepExecuteRecord testTaskStepExecuteRecord = taskStepExecuteRecordList.get(taskStepExecuteRecordList.size() - 1);
        assertThat(testTaskStepExecuteRecord.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTaskStepExecuteRecord.getTaskStepId()).isEqualTo(UPDATED_TASK_STEP_ID);
        assertThat(testTaskStepExecuteRecord.getTaskStepExecuteOrder()).isEqualTo(UPDATED_TASK_STEP_EXECUTE_ORDER);
        assertThat(testTaskStepExecuteRecord.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskStepExecuteRecord.getExecuteTime()).isEqualTo(UPDATED_EXECUTE_TIME);
        assertThat(testTaskStepExecuteRecord.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testTaskStepExecuteRecord.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskStepExecuteRecord.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void partialUpdateTaskStepExecuteRecordShouldThrown() throws Exception {
        // Update the taskStepExecuteRecord without id should throw
        TaskStepExecuteRecord partialUpdatedTaskStepExecuteRecord = new TaskStepExecuteRecord();

        restTaskStepExecuteRecordMockMvc
            .perform(
                patch("/api/task-step-execute-records")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskStepExecuteRecord))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteTaskStepExecuteRecord() throws Exception {
        // Initialize the database
        taskStepExecuteRecordRepository.saveAndFlush(taskStepExecuteRecord);

        int databaseSizeBeforeDelete = taskStepExecuteRecordRepository.findAll().size();

        // Delete the taskStepExecuteRecord
        restTaskStepExecuteRecordMockMvc
            .perform(delete("/api/task-step-execute-records/{id}", taskStepExecuteRecord.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskStepExecuteRecord> taskStepExecuteRecordList = taskStepExecuteRecordRepository.findAll();
        assertThat(taskStepExecuteRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
