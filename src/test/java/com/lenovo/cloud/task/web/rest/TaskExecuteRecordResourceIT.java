package com.lenovo.cloud.task.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lenovo.cloud.task.IntegrationTest;
import com.lenovo.cloud.task.domain.TaskExecuteRecord;
import com.lenovo.cloud.task.domain.TaskStepExecuteRecord;
import com.lenovo.cloud.task.repository.TaskExecuteRecordRepository;
import com.lenovo.cloud.task.service.TaskExecuteRecordQueryService;
import com.lenovo.cloud.task.service.dto.TaskExecuteRecordCriteria;
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
 * Integration tests for the {@link TaskExecuteRecordResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskExecuteRecordResourceIT {

    private static final Long DEFAULT_TASK_ID = 1L;
    private static final Long UPDATED_TASK_ID = 2L;
    private static final Long SMALLER_TASK_ID = 1L - 1L;

    private static final Integer DEFAULT_TASK_TYPE = 1;
    private static final Integer UPDATED_TASK_TYPE = 2;
    private static final Integer SMALLER_TASK_TYPE = 1 - 1;

    private static final String DEFAULT_TASK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TASK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STEP_RECORD_NUM = 1;
    private static final Integer UPDATED_STEP_RECORD_NUM = 2;
    private static final Integer SMALLER_STEP_RECORD_NUM = 1 - 1;

    private static final Instant DEFAULT_EXECUTE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FINISH_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISH_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaskExecuteRecordRepository taskExecuteRecordRepository;

    @Autowired
    private TaskExecuteRecordQueryService taskExecuteRecordQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskExecuteRecordMockMvc;

    private TaskExecuteRecord taskExecuteRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskExecuteRecord createEntity(EntityManager em) {
        TaskExecuteRecord taskExecuteRecord = new TaskExecuteRecord()
            .taskId(DEFAULT_TASK_ID)
            .taskType(DEFAULT_TASK_TYPE)
            .taskName(DEFAULT_TASK_NAME)
            .stepRecordNum(DEFAULT_STEP_RECORD_NUM)
            .executeTime(DEFAULT_EXECUTE_TIME)
            .finishTime(DEFAULT_FINISH_TIME)
            .status(DEFAULT_STATUS)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return taskExecuteRecord;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskExecuteRecord createUpdatedEntity(EntityManager em) {
        TaskExecuteRecord taskExecuteRecord = new TaskExecuteRecord()
            .taskId(UPDATED_TASK_ID)
            .taskType(UPDATED_TASK_TYPE)
            .taskName(UPDATED_TASK_NAME)
            .stepRecordNum(UPDATED_STEP_RECORD_NUM)
            .executeTime(UPDATED_EXECUTE_TIME)
            .finishTime(UPDATED_FINISH_TIME)
            .status(UPDATED_STATUS)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        return taskExecuteRecord;
    }

    @BeforeEach
    public void initTest() {
        taskExecuteRecord = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskExecuteRecord() throws Exception {
        int databaseSizeBeforeCreate = taskExecuteRecordRepository.findAll().size();
        // Create the TaskExecuteRecord
        restTaskExecuteRecordMockMvc
            .perform(
                post("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isCreated());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeCreate + 1);
        TaskExecuteRecord testTaskExecuteRecord = taskExecuteRecordList.get(taskExecuteRecordList.size() - 1);
        assertThat(testTaskExecuteRecord.getTaskId()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testTaskExecuteRecord.getTaskType()).isEqualTo(DEFAULT_TASK_TYPE);
        assertThat(testTaskExecuteRecord.getTaskName()).isEqualTo(DEFAULT_TASK_NAME);
        assertThat(testTaskExecuteRecord.getStepRecordNum()).isEqualTo(DEFAULT_STEP_RECORD_NUM);
        assertThat(testTaskExecuteRecord.getExecuteTime()).isEqualTo(DEFAULT_EXECUTE_TIME);
        assertThat(testTaskExecuteRecord.getFinishTime()).isEqualTo(DEFAULT_FINISH_TIME);
        assertThat(testTaskExecuteRecord.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaskExecuteRecord.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testTaskExecuteRecord.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void createTaskExecuteRecordWithExistingId() throws Exception {
        // Create the TaskExecuteRecord with an existing ID
        taskExecuteRecord.setId(1L);

        int databaseSizeBeforeCreate = taskExecuteRecordRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskExecuteRecordMockMvc
            .perform(
                post("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTaskIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecuteRecordRepository.findAll().size();
        // set the field null
        taskExecuteRecord.setTaskId(null);

        // Create the TaskExecuteRecord, which fails.

        restTaskExecuteRecordMockMvc
            .perform(
                post("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaskTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecuteRecordRepository.findAll().size();
        // set the field null
        taskExecuteRecord.setTaskType(null);

        // Create the TaskExecuteRecord, which fails.

        restTaskExecuteRecordMockMvc
            .perform(
                post("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStepRecordNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecuteRecordRepository.findAll().size();
        // set the field null
        taskExecuteRecord.setStepRecordNum(null);

        // Create the TaskExecuteRecord, which fails.

        restTaskExecuteRecordMockMvc
            .perform(
                post("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecuteRecordRepository.findAll().size();
        // set the field null
        taskExecuteRecord.setStatus(null);

        // Create the TaskExecuteRecord, which fails.

        restTaskExecuteRecordMockMvc
            .perform(
                post("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecuteRecordRepository.findAll().size();
        // set the field null
        taskExecuteRecord.setCreateTime(null);

        // Create the TaskExecuteRecord, which fails.

        restTaskExecuteRecordMockMvc
            .perform(
                post("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecuteRecordRepository.findAll().size();
        // set the field null
        taskExecuteRecord.setUpdateTime(null);

        // Create the TaskExecuteRecord, which fails.

        restTaskExecuteRecordMockMvc
            .perform(
                post("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecords() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList
        restTaskExecuteRecordMockMvc
            .perform(get("/api/task-execute-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskExecuteRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskType").value(hasItem(DEFAULT_TASK_TYPE)))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME)))
            .andExpect(jsonPath("$.[*].stepRecordNum").value(hasItem(DEFAULT_STEP_RECORD_NUM)))
            .andExpect(jsonPath("$.[*].executeTime").value(hasItem(DEFAULT_EXECUTE_TIME.toString())))
            .andExpect(jsonPath("$.[*].finishTime").value(hasItem(DEFAULT_FINISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getTaskExecuteRecord() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get the taskExecuteRecord
        restTaskExecuteRecordMockMvc
            .perform(get("/api/task-execute-records/{id}", taskExecuteRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskExecuteRecord.getId().intValue()))
            .andExpect(jsonPath("$.taskId").value(DEFAULT_TASK_ID.intValue()))
            .andExpect(jsonPath("$.taskType").value(DEFAULT_TASK_TYPE))
            .andExpect(jsonPath("$.taskName").value(DEFAULT_TASK_NAME))
            .andExpect(jsonPath("$.stepRecordNum").value(DEFAULT_STEP_RECORD_NUM))
            .andExpect(jsonPath("$.executeTime").value(DEFAULT_EXECUTE_TIME.toString()))
            .andExpect(jsonPath("$.finishTime").value(DEFAULT_FINISH_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getTaskExecuteRecordsByIdFiltering() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        Long id = taskExecuteRecord.getId();

        defaultTaskExecuteRecordShouldBeFound("id.equals=" + id);
        defaultTaskExecuteRecordShouldNotBeFound("id.notEquals=" + id);

        defaultTaskExecuteRecordShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTaskExecuteRecordShouldNotBeFound("id.greaterThan=" + id);

        defaultTaskExecuteRecordShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTaskExecuteRecordShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskIdIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskId equals to DEFAULT_TASK_ID
        defaultTaskExecuteRecordShouldBeFound("taskId.equals=" + DEFAULT_TASK_ID);

        // Get all the taskExecuteRecordList where taskId equals to UPDATED_TASK_ID
        defaultTaskExecuteRecordShouldNotBeFound("taskId.equals=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskId not equals to DEFAULT_TASK_ID
        defaultTaskExecuteRecordShouldNotBeFound("taskId.notEquals=" + DEFAULT_TASK_ID);

        // Get all the taskExecuteRecordList where taskId not equals to UPDATED_TASK_ID
        defaultTaskExecuteRecordShouldBeFound("taskId.notEquals=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskIdIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskId in DEFAULT_TASK_ID or UPDATED_TASK_ID
        defaultTaskExecuteRecordShouldBeFound("taskId.in=" + DEFAULT_TASK_ID + "," + UPDATED_TASK_ID);

        // Get all the taskExecuteRecordList where taskId equals to UPDATED_TASK_ID
        defaultTaskExecuteRecordShouldNotBeFound("taskId.in=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskId is not null
        defaultTaskExecuteRecordShouldBeFound("taskId.specified=true");

        // Get all the taskExecuteRecordList where taskId is null
        defaultTaskExecuteRecordShouldNotBeFound("taskId.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskId is greater than or equal to DEFAULT_TASK_ID
        defaultTaskExecuteRecordShouldBeFound("taskId.greaterThanOrEqual=" + DEFAULT_TASK_ID);

        // Get all the taskExecuteRecordList where taskId is greater than or equal to UPDATED_TASK_ID
        defaultTaskExecuteRecordShouldNotBeFound("taskId.greaterThanOrEqual=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskId is less than or equal to DEFAULT_TASK_ID
        defaultTaskExecuteRecordShouldBeFound("taskId.lessThanOrEqual=" + DEFAULT_TASK_ID);

        // Get all the taskExecuteRecordList where taskId is less than or equal to SMALLER_TASK_ID
        defaultTaskExecuteRecordShouldNotBeFound("taskId.lessThanOrEqual=" + SMALLER_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskIdIsLessThanSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskId is less than DEFAULT_TASK_ID
        defaultTaskExecuteRecordShouldNotBeFound("taskId.lessThan=" + DEFAULT_TASK_ID);

        // Get all the taskExecuteRecordList where taskId is less than UPDATED_TASK_ID
        defaultTaskExecuteRecordShouldBeFound("taskId.lessThan=" + UPDATED_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskId is greater than DEFAULT_TASK_ID
        defaultTaskExecuteRecordShouldNotBeFound("taskId.greaterThan=" + DEFAULT_TASK_ID);

        // Get all the taskExecuteRecordList where taskId is greater than SMALLER_TASK_ID
        defaultTaskExecuteRecordShouldBeFound("taskId.greaterThan=" + SMALLER_TASK_ID);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskType equals to DEFAULT_TASK_TYPE
        defaultTaskExecuteRecordShouldBeFound("taskType.equals=" + DEFAULT_TASK_TYPE);

        // Get all the taskExecuteRecordList where taskType equals to UPDATED_TASK_TYPE
        defaultTaskExecuteRecordShouldNotBeFound("taskType.equals=" + UPDATED_TASK_TYPE);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskType not equals to DEFAULT_TASK_TYPE
        defaultTaskExecuteRecordShouldNotBeFound("taskType.notEquals=" + DEFAULT_TASK_TYPE);

        // Get all the taskExecuteRecordList where taskType not equals to UPDATED_TASK_TYPE
        defaultTaskExecuteRecordShouldBeFound("taskType.notEquals=" + UPDATED_TASK_TYPE);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskTypeIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskType in DEFAULT_TASK_TYPE or UPDATED_TASK_TYPE
        defaultTaskExecuteRecordShouldBeFound("taskType.in=" + DEFAULT_TASK_TYPE + "," + UPDATED_TASK_TYPE);

        // Get all the taskExecuteRecordList where taskType equals to UPDATED_TASK_TYPE
        defaultTaskExecuteRecordShouldNotBeFound("taskType.in=" + UPDATED_TASK_TYPE);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskType is not null
        defaultTaskExecuteRecordShouldBeFound("taskType.specified=true");

        // Get all the taskExecuteRecordList where taskType is null
        defaultTaskExecuteRecordShouldNotBeFound("taskType.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskType is greater than or equal to DEFAULT_TASK_TYPE
        defaultTaskExecuteRecordShouldBeFound("taskType.greaterThanOrEqual=" + DEFAULT_TASK_TYPE);

        // Get all the taskExecuteRecordList where taskType is greater than or equal to UPDATED_TASK_TYPE
        defaultTaskExecuteRecordShouldNotBeFound("taskType.greaterThanOrEqual=" + UPDATED_TASK_TYPE);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskType is less than or equal to DEFAULT_TASK_TYPE
        defaultTaskExecuteRecordShouldBeFound("taskType.lessThanOrEqual=" + DEFAULT_TASK_TYPE);

        // Get all the taskExecuteRecordList where taskType is less than or equal to SMALLER_TASK_TYPE
        defaultTaskExecuteRecordShouldNotBeFound("taskType.lessThanOrEqual=" + SMALLER_TASK_TYPE);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskType is less than DEFAULT_TASK_TYPE
        defaultTaskExecuteRecordShouldNotBeFound("taskType.lessThan=" + DEFAULT_TASK_TYPE);

        // Get all the taskExecuteRecordList where taskType is less than UPDATED_TASK_TYPE
        defaultTaskExecuteRecordShouldBeFound("taskType.lessThan=" + UPDATED_TASK_TYPE);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskType is greater than DEFAULT_TASK_TYPE
        defaultTaskExecuteRecordShouldNotBeFound("taskType.greaterThan=" + DEFAULT_TASK_TYPE);

        // Get all the taskExecuteRecordList where taskType is greater than SMALLER_TASK_TYPE
        defaultTaskExecuteRecordShouldBeFound("taskType.greaterThan=" + SMALLER_TASK_TYPE);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskNameIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskName equals to DEFAULT_TASK_NAME
        defaultTaskExecuteRecordShouldBeFound("taskName.equals=" + DEFAULT_TASK_NAME);

        // Get all the taskExecuteRecordList where taskName equals to UPDATED_TASK_NAME
        defaultTaskExecuteRecordShouldNotBeFound("taskName.equals=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskName not equals to DEFAULT_TASK_NAME
        defaultTaskExecuteRecordShouldNotBeFound("taskName.notEquals=" + DEFAULT_TASK_NAME);

        // Get all the taskExecuteRecordList where taskName not equals to UPDATED_TASK_NAME
        defaultTaskExecuteRecordShouldBeFound("taskName.notEquals=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskNameIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskName in DEFAULT_TASK_NAME or UPDATED_TASK_NAME
        defaultTaskExecuteRecordShouldBeFound("taskName.in=" + DEFAULT_TASK_NAME + "," + UPDATED_TASK_NAME);

        // Get all the taskExecuteRecordList where taskName equals to UPDATED_TASK_NAME
        defaultTaskExecuteRecordShouldNotBeFound("taskName.in=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskName is not null
        defaultTaskExecuteRecordShouldBeFound("taskName.specified=true");

        // Get all the taskExecuteRecordList where taskName is null
        defaultTaskExecuteRecordShouldNotBeFound("taskName.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskNameContainsSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskName contains DEFAULT_TASK_NAME
        defaultTaskExecuteRecordShouldBeFound("taskName.contains=" + DEFAULT_TASK_NAME);

        // Get all the taskExecuteRecordList where taskName contains UPDATED_TASK_NAME
        defaultTaskExecuteRecordShouldNotBeFound("taskName.contains=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskNameNotContainsSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where taskName does not contain DEFAULT_TASK_NAME
        defaultTaskExecuteRecordShouldNotBeFound("taskName.doesNotContain=" + DEFAULT_TASK_NAME);

        // Get all the taskExecuteRecordList where taskName does not contain UPDATED_TASK_NAME
        defaultTaskExecuteRecordShouldBeFound("taskName.doesNotContain=" + UPDATED_TASK_NAME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStepRecordNumIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where stepRecordNum equals to DEFAULT_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldBeFound("stepRecordNum.equals=" + DEFAULT_STEP_RECORD_NUM);

        // Get all the taskExecuteRecordList where stepRecordNum equals to UPDATED_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldNotBeFound("stepRecordNum.equals=" + UPDATED_STEP_RECORD_NUM);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStepRecordNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where stepRecordNum not equals to DEFAULT_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldNotBeFound("stepRecordNum.notEquals=" + DEFAULT_STEP_RECORD_NUM);

        // Get all the taskExecuteRecordList where stepRecordNum not equals to UPDATED_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldBeFound("stepRecordNum.notEquals=" + UPDATED_STEP_RECORD_NUM);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStepRecordNumIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where stepRecordNum in DEFAULT_STEP_RECORD_NUM or UPDATED_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldBeFound("stepRecordNum.in=" + DEFAULT_STEP_RECORD_NUM + "," + UPDATED_STEP_RECORD_NUM);

        // Get all the taskExecuteRecordList where stepRecordNum equals to UPDATED_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldNotBeFound("stepRecordNum.in=" + UPDATED_STEP_RECORD_NUM);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStepRecordNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where stepRecordNum is not null
        defaultTaskExecuteRecordShouldBeFound("stepRecordNum.specified=true");

        // Get all the taskExecuteRecordList where stepRecordNum is null
        defaultTaskExecuteRecordShouldNotBeFound("stepRecordNum.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStepRecordNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where stepRecordNum is greater than or equal to DEFAULT_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldBeFound("stepRecordNum.greaterThanOrEqual=" + DEFAULT_STEP_RECORD_NUM);

        // Get all the taskExecuteRecordList where stepRecordNum is greater than or equal to UPDATED_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldNotBeFound("stepRecordNum.greaterThanOrEqual=" + UPDATED_STEP_RECORD_NUM);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStepRecordNumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where stepRecordNum is less than or equal to DEFAULT_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldBeFound("stepRecordNum.lessThanOrEqual=" + DEFAULT_STEP_RECORD_NUM);

        // Get all the taskExecuteRecordList where stepRecordNum is less than or equal to SMALLER_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldNotBeFound("stepRecordNum.lessThanOrEqual=" + SMALLER_STEP_RECORD_NUM);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStepRecordNumIsLessThanSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where stepRecordNum is less than DEFAULT_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldNotBeFound("stepRecordNum.lessThan=" + DEFAULT_STEP_RECORD_NUM);

        // Get all the taskExecuteRecordList where stepRecordNum is less than UPDATED_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldBeFound("stepRecordNum.lessThan=" + UPDATED_STEP_RECORD_NUM);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStepRecordNumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where stepRecordNum is greater than DEFAULT_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldNotBeFound("stepRecordNum.greaterThan=" + DEFAULT_STEP_RECORD_NUM);

        // Get all the taskExecuteRecordList where stepRecordNum is greater than SMALLER_STEP_RECORD_NUM
        defaultTaskExecuteRecordShouldBeFound("stepRecordNum.greaterThan=" + SMALLER_STEP_RECORD_NUM);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByExecuteTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where executeTime equals to DEFAULT_EXECUTE_TIME
        defaultTaskExecuteRecordShouldBeFound("executeTime.equals=" + DEFAULT_EXECUTE_TIME);

        // Get all the taskExecuteRecordList where executeTime equals to UPDATED_EXECUTE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("executeTime.equals=" + UPDATED_EXECUTE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByExecuteTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where executeTime not equals to DEFAULT_EXECUTE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("executeTime.notEquals=" + DEFAULT_EXECUTE_TIME);

        // Get all the taskExecuteRecordList where executeTime not equals to UPDATED_EXECUTE_TIME
        defaultTaskExecuteRecordShouldBeFound("executeTime.notEquals=" + UPDATED_EXECUTE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByExecuteTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where executeTime in DEFAULT_EXECUTE_TIME or UPDATED_EXECUTE_TIME
        defaultTaskExecuteRecordShouldBeFound("executeTime.in=" + DEFAULT_EXECUTE_TIME + "," + UPDATED_EXECUTE_TIME);

        // Get all the taskExecuteRecordList where executeTime equals to UPDATED_EXECUTE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("executeTime.in=" + UPDATED_EXECUTE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByExecuteTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where executeTime is not null
        defaultTaskExecuteRecordShouldBeFound("executeTime.specified=true");

        // Get all the taskExecuteRecordList where executeTime is null
        defaultTaskExecuteRecordShouldNotBeFound("executeTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByFinishTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where finishTime equals to DEFAULT_FINISH_TIME
        defaultTaskExecuteRecordShouldBeFound("finishTime.equals=" + DEFAULT_FINISH_TIME);

        // Get all the taskExecuteRecordList where finishTime equals to UPDATED_FINISH_TIME
        defaultTaskExecuteRecordShouldNotBeFound("finishTime.equals=" + UPDATED_FINISH_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByFinishTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where finishTime not equals to DEFAULT_FINISH_TIME
        defaultTaskExecuteRecordShouldNotBeFound("finishTime.notEquals=" + DEFAULT_FINISH_TIME);

        // Get all the taskExecuteRecordList where finishTime not equals to UPDATED_FINISH_TIME
        defaultTaskExecuteRecordShouldBeFound("finishTime.notEquals=" + UPDATED_FINISH_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByFinishTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where finishTime in DEFAULT_FINISH_TIME or UPDATED_FINISH_TIME
        defaultTaskExecuteRecordShouldBeFound("finishTime.in=" + DEFAULT_FINISH_TIME + "," + UPDATED_FINISH_TIME);

        // Get all the taskExecuteRecordList where finishTime equals to UPDATED_FINISH_TIME
        defaultTaskExecuteRecordShouldNotBeFound("finishTime.in=" + UPDATED_FINISH_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByFinishTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where finishTime is not null
        defaultTaskExecuteRecordShouldBeFound("finishTime.specified=true");

        // Get all the taskExecuteRecordList where finishTime is null
        defaultTaskExecuteRecordShouldNotBeFound("finishTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where status equals to DEFAULT_STATUS
        defaultTaskExecuteRecordShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the taskExecuteRecordList where status equals to UPDATED_STATUS
        defaultTaskExecuteRecordShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where status not equals to DEFAULT_STATUS
        defaultTaskExecuteRecordShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the taskExecuteRecordList where status not equals to UPDATED_STATUS
        defaultTaskExecuteRecordShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTaskExecuteRecordShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the taskExecuteRecordList where status equals to UPDATED_STATUS
        defaultTaskExecuteRecordShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where status is not null
        defaultTaskExecuteRecordShouldBeFound("status.specified=true");

        // Get all the taskExecuteRecordList where status is null
        defaultTaskExecuteRecordShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where status is greater than or equal to DEFAULT_STATUS
        defaultTaskExecuteRecordShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the taskExecuteRecordList where status is greater than or equal to UPDATED_STATUS
        defaultTaskExecuteRecordShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where status is less than or equal to DEFAULT_STATUS
        defaultTaskExecuteRecordShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the taskExecuteRecordList where status is less than or equal to SMALLER_STATUS
        defaultTaskExecuteRecordShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where status is less than DEFAULT_STATUS
        defaultTaskExecuteRecordShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the taskExecuteRecordList where status is less than UPDATED_STATUS
        defaultTaskExecuteRecordShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where status is greater than DEFAULT_STATUS
        defaultTaskExecuteRecordShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the taskExecuteRecordList where status is greater than SMALLER_STATUS
        defaultTaskExecuteRecordShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where createTime equals to DEFAULT_CREATE_TIME
        defaultTaskExecuteRecordShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the taskExecuteRecordList where createTime equals to UPDATED_CREATE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where createTime not equals to DEFAULT_CREATE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the taskExecuteRecordList where createTime not equals to UPDATED_CREATE_TIME
        defaultTaskExecuteRecordShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultTaskExecuteRecordShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the taskExecuteRecordList where createTime equals to UPDATED_CREATE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where createTime is not null
        defaultTaskExecuteRecordShouldBeFound("createTime.specified=true");

        // Get all the taskExecuteRecordList where createTime is null
        defaultTaskExecuteRecordShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultTaskExecuteRecordShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the taskExecuteRecordList where updateTime equals to UPDATED_UPDATE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByUpdateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where updateTime not equals to DEFAULT_UPDATE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("updateTime.notEquals=" + DEFAULT_UPDATE_TIME);

        // Get all the taskExecuteRecordList where updateTime not equals to UPDATED_UPDATE_TIME
        defaultTaskExecuteRecordShouldBeFound("updateTime.notEquals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultTaskExecuteRecordShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the taskExecuteRecordList where updateTime equals to UPDATED_UPDATE_TIME
        defaultTaskExecuteRecordShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList where updateTime is not null
        defaultTaskExecuteRecordShouldBeFound("updateTime.specified=true");

        // Get all the taskExecuteRecordList where updateTime is null
        defaultTaskExecuteRecordShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskExecuteRecordsByTaskStepExecuteRecordIsEqualToSomething() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);
        TaskStepExecuteRecord taskStepExecuteRecord = TaskStepExecuteRecordResourceIT.createEntity(em);
        em.persist(taskStepExecuteRecord);
        em.flush();
        taskExecuteRecord.addTaskStepExecuteRecord(taskStepExecuteRecord);
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);
        Long taskStepExecuteRecordId = taskStepExecuteRecord.getId();

        // Get all the taskExecuteRecordList where taskStepExecuteRecord equals to taskStepExecuteRecordId
        defaultTaskExecuteRecordShouldBeFound("taskStepExecuteRecordId.equals=" + taskStepExecuteRecordId);

        // Get all the taskExecuteRecordList where taskStepExecuteRecord equals to taskStepExecuteRecordId + 1
        defaultTaskExecuteRecordShouldNotBeFound("taskStepExecuteRecordId.equals=" + (taskStepExecuteRecordId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaskExecuteRecordShouldBeFound(String filter) throws Exception {
        restTaskExecuteRecordMockMvc
            .perform(get("/api/task-execute-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskExecuteRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].taskType").value(hasItem(DEFAULT_TASK_TYPE)))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME)))
            .andExpect(jsonPath("$.[*].stepRecordNum").value(hasItem(DEFAULT_STEP_RECORD_NUM)))
            .andExpect(jsonPath("$.[*].executeTime").value(hasItem(DEFAULT_EXECUTE_TIME.toString())))
            .andExpect(jsonPath("$.[*].finishTime").value(hasItem(DEFAULT_FINISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));

        // Check, that the count call also returns 1
        restTaskExecuteRecordMockMvc
            .perform(get("/api/task-execute-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaskExecuteRecordShouldNotBeFound(String filter) throws Exception {
        restTaskExecuteRecordMockMvc
            .perform(get("/api/task-execute-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaskExecuteRecordMockMvc
            .perform(get("/api/task-execute-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTaskExecuteRecord() throws Exception {
        // Get the taskExecuteRecord
        restTaskExecuteRecordMockMvc.perform(get("/api/task-execute-records/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateTaskExecuteRecord() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        int databaseSizeBeforeUpdate = taskExecuteRecordRepository.findAll().size();

        // Update the taskExecuteRecord
        TaskExecuteRecord updatedTaskExecuteRecord = taskExecuteRecordRepository.findById(taskExecuteRecord.getId()).get();
        // Disconnect from session so that the updates on updatedTaskExecuteRecord are not directly saved in db
        em.detach(updatedTaskExecuteRecord);
        updatedTaskExecuteRecord
            .taskId(UPDATED_TASK_ID)
            .taskType(UPDATED_TASK_TYPE)
            .taskName(UPDATED_TASK_NAME)
            .stepRecordNum(UPDATED_STEP_RECORD_NUM)
            .executeTime(UPDATED_EXECUTE_TIME)
            .finishTime(UPDATED_FINISH_TIME)
            .status(UPDATED_STATUS)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restTaskExecuteRecordMockMvc
            .perform(
                put("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaskExecuteRecord))
            )
            .andExpect(status().isOk());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
        TaskExecuteRecord testTaskExecuteRecord = taskExecuteRecordList.get(taskExecuteRecordList.size() - 1);
        assertThat(testTaskExecuteRecord.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTaskExecuteRecord.getTaskType()).isEqualTo(UPDATED_TASK_TYPE);
        assertThat(testTaskExecuteRecord.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testTaskExecuteRecord.getStepRecordNum()).isEqualTo(UPDATED_STEP_RECORD_NUM);
        assertThat(testTaskExecuteRecord.getExecuteTime()).isEqualTo(UPDATED_EXECUTE_TIME);
        assertThat(testTaskExecuteRecord.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testTaskExecuteRecord.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskExecuteRecord.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskExecuteRecord.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void updateNonExistingTaskExecuteRecord() throws Exception {
        int databaseSizeBeforeUpdate = taskExecuteRecordRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskExecuteRecordMockMvc
            .perform(
                put("/api/task-execute-records")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecord))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskExecuteRecordWithPatch() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        int databaseSizeBeforeUpdate = taskExecuteRecordRepository.findAll().size();

        // Update the taskExecuteRecord using partial update
        TaskExecuteRecord partialUpdatedTaskExecuteRecord = new TaskExecuteRecord();
        partialUpdatedTaskExecuteRecord.setId(taskExecuteRecord.getId());

        partialUpdatedTaskExecuteRecord
            .taskId(UPDATED_TASK_ID)
            .taskName(UPDATED_TASK_NAME)
            .finishTime(UPDATED_FINISH_TIME)
            .status(UPDATED_STATUS)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restTaskExecuteRecordMockMvc
            .perform(
                patch("/api/task-execute-records")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskExecuteRecord))
            )
            .andExpect(status().isOk());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
        TaskExecuteRecord testTaskExecuteRecord = taskExecuteRecordList.get(taskExecuteRecordList.size() - 1);
        assertThat(testTaskExecuteRecord.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTaskExecuteRecord.getTaskType()).isEqualTo(DEFAULT_TASK_TYPE);
        assertThat(testTaskExecuteRecord.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testTaskExecuteRecord.getStepRecordNum()).isEqualTo(DEFAULT_STEP_RECORD_NUM);
        assertThat(testTaskExecuteRecord.getExecuteTime()).isEqualTo(DEFAULT_EXECUTE_TIME);
        assertThat(testTaskExecuteRecord.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testTaskExecuteRecord.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskExecuteRecord.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskExecuteRecord.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateTaskExecuteRecordWithPatch() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        int databaseSizeBeforeUpdate = taskExecuteRecordRepository.findAll().size();

        // Update the taskExecuteRecord using partial update
        TaskExecuteRecord partialUpdatedTaskExecuteRecord = new TaskExecuteRecord();
        partialUpdatedTaskExecuteRecord.setId(taskExecuteRecord.getId());

        partialUpdatedTaskExecuteRecord
            .taskId(UPDATED_TASK_ID)
            .taskType(UPDATED_TASK_TYPE)
            .taskName(UPDATED_TASK_NAME)
            .stepRecordNum(UPDATED_STEP_RECORD_NUM)
            .executeTime(UPDATED_EXECUTE_TIME)
            .finishTime(UPDATED_FINISH_TIME)
            .status(UPDATED_STATUS)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restTaskExecuteRecordMockMvc
            .perform(
                patch("/api/task-execute-records")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskExecuteRecord))
            )
            .andExpect(status().isOk());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
        TaskExecuteRecord testTaskExecuteRecord = taskExecuteRecordList.get(taskExecuteRecordList.size() - 1);
        assertThat(testTaskExecuteRecord.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTaskExecuteRecord.getTaskType()).isEqualTo(UPDATED_TASK_TYPE);
        assertThat(testTaskExecuteRecord.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testTaskExecuteRecord.getStepRecordNum()).isEqualTo(UPDATED_STEP_RECORD_NUM);
        assertThat(testTaskExecuteRecord.getExecuteTime()).isEqualTo(UPDATED_EXECUTE_TIME);
        assertThat(testTaskExecuteRecord.getFinishTime()).isEqualTo(UPDATED_FINISH_TIME);
        assertThat(testTaskExecuteRecord.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskExecuteRecord.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskExecuteRecord.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void partialUpdateTaskExecuteRecordShouldThrown() throws Exception {
        // Update the taskExecuteRecord without id should throw
        TaskExecuteRecord partialUpdatedTaskExecuteRecord = new TaskExecuteRecord();

        restTaskExecuteRecordMockMvc
            .perform(
                patch("/api/task-execute-records")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskExecuteRecord))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteTaskExecuteRecord() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        int databaseSizeBeforeDelete = taskExecuteRecordRepository.findAll().size();

        // Delete the taskExecuteRecord
        restTaskExecuteRecordMockMvc
            .perform(delete("/api/task-execute-records/{id}", taskExecuteRecord.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
