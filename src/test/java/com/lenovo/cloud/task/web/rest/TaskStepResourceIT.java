package com.lenovo.cloud.task.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lenovo.cloud.task.IntegrationTest;
import com.lenovo.cloud.task.domain.Task;
import com.lenovo.cloud.task.domain.TaskStep;
import com.lenovo.cloud.task.repository.TaskStepRepository;
import com.lenovo.cloud.task.service.TaskStepQueryService;
import com.lenovo.cloud.task.service.dto.TaskStepCriteria;
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
 * Integration tests for the {@link TaskStepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskStepResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    private static final Integer SMALLER_ORDER = 1 - 1;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DEL_FLAG = 1;
    private static final Integer UPDATED_DEL_FLAG = 2;
    private static final Integer SMALLER_DEL_FLAG = 1 - 1;

    @Autowired
    private TaskStepRepository taskStepRepository;

    @Autowired
    private TaskStepQueryService taskStepQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskStepMockMvc;

    private TaskStep taskStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskStep createEntity(EntityManager em) {
        TaskStep taskStep = new TaskStep()
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .delFlag(DEFAULT_DEL_FLAG);
        return taskStep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskStep createUpdatedEntity(EntityManager em) {
        TaskStep taskStep = new TaskStep()
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .delFlag(UPDATED_DEL_FLAG);
        return taskStep;
    }

    @BeforeEach
    public void initTest() {
        taskStep = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskStep() throws Exception {
        int databaseSizeBeforeCreate = taskStepRepository.findAll().size();
        // Create the TaskStep
        restTaskStepMockMvc
            .perform(post("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStep)))
            .andExpect(status().isCreated());

        // Validate the TaskStep in the database
        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeCreate + 1);
        TaskStep testTaskStep = taskStepList.get(taskStepList.size() - 1);
        assertThat(testTaskStep.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskStep.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testTaskStep.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testTaskStep.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testTaskStep.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
    }

    @Test
    @Transactional
    void createTaskStepWithExistingId() throws Exception {
        // Create the TaskStep with an existing ID
        taskStep.setId(1L);

        int databaseSizeBeforeCreate = taskStepRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskStepMockMvc
            .perform(post("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStep)))
            .andExpect(status().isBadRequest());

        // Validate the TaskStep in the database
        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStepRepository.findAll().size();
        // set the field null
        taskStep.setName(null);

        // Create the TaskStep, which fails.

        restTaskStepMockMvc
            .perform(post("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStep)))
            .andExpect(status().isBadRequest());

        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStepRepository.findAll().size();
        // set the field null
        taskStep.setOrder(null);

        // Create the TaskStep, which fails.

        restTaskStepMockMvc
            .perform(post("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStep)))
            .andExpect(status().isBadRequest());

        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStepRepository.findAll().size();
        // set the field null
        taskStep.setCreateTime(null);

        // Create the TaskStep, which fails.

        restTaskStepMockMvc
            .perform(post("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStep)))
            .andExpect(status().isBadRequest());

        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStepRepository.findAll().size();
        // set the field null
        taskStep.setUpdateTime(null);

        // Create the TaskStep, which fails.

        restTaskStepMockMvc
            .perform(post("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStep)))
            .andExpect(status().isBadRequest());

        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDelFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskStepRepository.findAll().size();
        // set the field null
        taskStep.setDelFlag(null);

        // Create the TaskStep, which fails.

        restTaskStepMockMvc
            .perform(post("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStep)))
            .andExpect(status().isBadRequest());

        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaskSteps() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList
        restTaskStepMockMvc
            .perform(get("/api/task-steps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)));
    }

    @Test
    @Transactional
    void getTaskStep() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get the taskStep
        restTaskStepMockMvc
            .perform(get("/api/task-steps/{id}", taskStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskStep.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG));
    }

    @Test
    @Transactional
    void getTaskStepsByIdFiltering() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        Long id = taskStep.getId();

        defaultTaskStepShouldBeFound("id.equals=" + id);
        defaultTaskStepShouldNotBeFound("id.notEquals=" + id);

        defaultTaskStepShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTaskStepShouldNotBeFound("id.greaterThan=" + id);

        defaultTaskStepShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTaskStepShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTaskStepsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where name equals to DEFAULT_NAME
        defaultTaskStepShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the taskStepList where name equals to UPDATED_NAME
        defaultTaskStepShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where name not equals to DEFAULT_NAME
        defaultTaskStepShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the taskStepList where name not equals to UPDATED_NAME
        defaultTaskStepShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTaskStepShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the taskStepList where name equals to UPDATED_NAME
        defaultTaskStepShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where name is not null
        defaultTaskStepShouldBeFound("name.specified=true");

        // Get all the taskStepList where name is null
        defaultTaskStepShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepsByNameContainsSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where name contains DEFAULT_NAME
        defaultTaskStepShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the taskStepList where name contains UPDATED_NAME
        defaultTaskStepShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where name does not contain DEFAULT_NAME
        defaultTaskStepShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the taskStepList where name does not contain UPDATED_NAME
        defaultTaskStepShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where order equals to DEFAULT_ORDER
        defaultTaskStepShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the taskStepList where order equals to UPDATED_ORDER
        defaultTaskStepShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepsByOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where order not equals to DEFAULT_ORDER
        defaultTaskStepShouldNotBeFound("order.notEquals=" + DEFAULT_ORDER);

        // Get all the taskStepList where order not equals to UPDATED_ORDER
        defaultTaskStepShouldBeFound("order.notEquals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultTaskStepShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the taskStepList where order equals to UPDATED_ORDER
        defaultTaskStepShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where order is not null
        defaultTaskStepShouldBeFound("order.specified=true");

        // Get all the taskStepList where order is null
        defaultTaskStepShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where order is greater than or equal to DEFAULT_ORDER
        defaultTaskStepShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the taskStepList where order is greater than or equal to UPDATED_ORDER
        defaultTaskStepShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepsByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where order is less than or equal to DEFAULT_ORDER
        defaultTaskStepShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the taskStepList where order is less than or equal to SMALLER_ORDER
        defaultTaskStepShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where order is less than DEFAULT_ORDER
        defaultTaskStepShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the taskStepList where order is less than UPDATED_ORDER
        defaultTaskStepShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepsByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where order is greater than DEFAULT_ORDER
        defaultTaskStepShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the taskStepList where order is greater than SMALLER_ORDER
        defaultTaskStepShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    void getAllTaskStepsByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where createTime equals to DEFAULT_CREATE_TIME
        defaultTaskStepShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the taskStepList where createTime equals to UPDATED_CREATE_TIME
        defaultTaskStepShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where createTime not equals to DEFAULT_CREATE_TIME
        defaultTaskStepShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the taskStepList where createTime not equals to UPDATED_CREATE_TIME
        defaultTaskStepShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultTaskStepShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the taskStepList where createTime equals to UPDATED_CREATE_TIME
        defaultTaskStepShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where createTime is not null
        defaultTaskStepShouldBeFound("createTime.specified=true");

        // Get all the taskStepList where createTime is null
        defaultTaskStepShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepsByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultTaskStepShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the taskStepList where updateTime equals to UPDATED_UPDATE_TIME
        defaultTaskStepShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByUpdateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where updateTime not equals to DEFAULT_UPDATE_TIME
        defaultTaskStepShouldNotBeFound("updateTime.notEquals=" + DEFAULT_UPDATE_TIME);

        // Get all the taskStepList where updateTime not equals to UPDATED_UPDATE_TIME
        defaultTaskStepShouldBeFound("updateTime.notEquals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultTaskStepShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the taskStepList where updateTime equals to UPDATED_UPDATE_TIME
        defaultTaskStepShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllTaskStepsByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where updateTime is not null
        defaultTaskStepShouldBeFound("updateTime.specified=true");

        // Get all the taskStepList where updateTime is null
        defaultTaskStepShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepsByDelFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where delFlag equals to DEFAULT_DEL_FLAG
        defaultTaskStepShouldBeFound("delFlag.equals=" + DEFAULT_DEL_FLAG);

        // Get all the taskStepList where delFlag equals to UPDATED_DEL_FLAG
        defaultTaskStepShouldNotBeFound("delFlag.equals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllTaskStepsByDelFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where delFlag not equals to DEFAULT_DEL_FLAG
        defaultTaskStepShouldNotBeFound("delFlag.notEquals=" + DEFAULT_DEL_FLAG);

        // Get all the taskStepList where delFlag not equals to UPDATED_DEL_FLAG
        defaultTaskStepShouldBeFound("delFlag.notEquals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllTaskStepsByDelFlagIsInShouldWork() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where delFlag in DEFAULT_DEL_FLAG or UPDATED_DEL_FLAG
        defaultTaskStepShouldBeFound("delFlag.in=" + DEFAULT_DEL_FLAG + "," + UPDATED_DEL_FLAG);

        // Get all the taskStepList where delFlag equals to UPDATED_DEL_FLAG
        defaultTaskStepShouldNotBeFound("delFlag.in=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllTaskStepsByDelFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where delFlag is not null
        defaultTaskStepShouldBeFound("delFlag.specified=true");

        // Get all the taskStepList where delFlag is null
        defaultTaskStepShouldNotBeFound("delFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllTaskStepsByDelFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where delFlag is greater than or equal to DEFAULT_DEL_FLAG
        defaultTaskStepShouldBeFound("delFlag.greaterThanOrEqual=" + DEFAULT_DEL_FLAG);

        // Get all the taskStepList where delFlag is greater than or equal to UPDATED_DEL_FLAG
        defaultTaskStepShouldNotBeFound("delFlag.greaterThanOrEqual=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllTaskStepsByDelFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where delFlag is less than or equal to DEFAULT_DEL_FLAG
        defaultTaskStepShouldBeFound("delFlag.lessThanOrEqual=" + DEFAULT_DEL_FLAG);

        // Get all the taskStepList where delFlag is less than or equal to SMALLER_DEL_FLAG
        defaultTaskStepShouldNotBeFound("delFlag.lessThanOrEqual=" + SMALLER_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllTaskStepsByDelFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where delFlag is less than DEFAULT_DEL_FLAG
        defaultTaskStepShouldNotBeFound("delFlag.lessThan=" + DEFAULT_DEL_FLAG);

        // Get all the taskStepList where delFlag is less than UPDATED_DEL_FLAG
        defaultTaskStepShouldBeFound("delFlag.lessThan=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllTaskStepsByDelFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        // Get all the taskStepList where delFlag is greater than DEFAULT_DEL_FLAG
        defaultTaskStepShouldNotBeFound("delFlag.greaterThan=" + DEFAULT_DEL_FLAG);

        // Get all the taskStepList where delFlag is greater than SMALLER_DEL_FLAG
        defaultTaskStepShouldBeFound("delFlag.greaterThan=" + SMALLER_DEL_FLAG);
    }

    @Test
    @Transactional
    void getAllTaskStepsByTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);
        Task task = TaskResourceIT.createEntity(em);
        em.persist(task);
        em.flush();
        taskStep.setTask(task);
        taskStepRepository.saveAndFlush(taskStep);
        Long taskId = task.getId();

        // Get all the taskStepList where task equals to taskId
        defaultTaskStepShouldBeFound("taskId.equals=" + taskId);

        // Get all the taskStepList where task equals to taskId + 1
        defaultTaskStepShouldNotBeFound("taskId.equals=" + (taskId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaskStepShouldBeFound(String filter) throws Exception {
        restTaskStepMockMvc
            .perform(get("/api/task-steps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)));

        // Check, that the count call also returns 1
        restTaskStepMockMvc
            .perform(get("/api/task-steps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaskStepShouldNotBeFound(String filter) throws Exception {
        restTaskStepMockMvc
            .perform(get("/api/task-steps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaskStepMockMvc
            .perform(get("/api/task-steps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTaskStep() throws Exception {
        // Get the taskStep
        restTaskStepMockMvc.perform(get("/api/task-steps/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateTaskStep() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        int databaseSizeBeforeUpdate = taskStepRepository.findAll().size();

        // Update the taskStep
        TaskStep updatedTaskStep = taskStepRepository.findById(taskStep.getId()).get();
        // Disconnect from session so that the updates on updatedTaskStep are not directly saved in db
        em.detach(updatedTaskStep);
        updatedTaskStep
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .delFlag(UPDATED_DEL_FLAG);

        restTaskStepMockMvc
            .perform(
                put("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedTaskStep))
            )
            .andExpect(status().isOk());

        // Validate the TaskStep in the database
        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeUpdate);
        TaskStep testTaskStep = taskStepList.get(taskStepList.size() - 1);
        assertThat(testTaskStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskStep.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testTaskStep.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskStep.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testTaskStep.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void updateNonExistingTaskStep() throws Exception {
        int databaseSizeBeforeUpdate = taskStepRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskStepMockMvc
            .perform(put("/api/task-steps").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskStep)))
            .andExpect(status().isBadRequest());

        // Validate the TaskStep in the database
        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskStepWithPatch() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        int databaseSizeBeforeUpdate = taskStepRepository.findAll().size();

        // Update the taskStep using partial update
        TaskStep partialUpdatedTaskStep = new TaskStep();
        partialUpdatedTaskStep.setId(taskStep.getId());

        partialUpdatedTaskStep.order(UPDATED_ORDER).createTime(UPDATED_CREATE_TIME).delFlag(UPDATED_DEL_FLAG);

        restTaskStepMockMvc
            .perform(
                patch("/api/task-steps")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskStep))
            )
            .andExpect(status().isOk());

        // Validate the TaskStep in the database
        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeUpdate);
        TaskStep testTaskStep = taskStepList.get(taskStepList.size() - 1);
        assertThat(testTaskStep.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskStep.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testTaskStep.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskStep.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testTaskStep.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void fullUpdateTaskStepWithPatch() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        int databaseSizeBeforeUpdate = taskStepRepository.findAll().size();

        // Update the taskStep using partial update
        TaskStep partialUpdatedTaskStep = new TaskStep();
        partialUpdatedTaskStep.setId(taskStep.getId());

        partialUpdatedTaskStep
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .delFlag(UPDATED_DEL_FLAG);

        restTaskStepMockMvc
            .perform(
                patch("/api/task-steps")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskStep))
            )
            .andExpect(status().isOk());

        // Validate the TaskStep in the database
        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeUpdate);
        TaskStep testTaskStep = taskStepList.get(taskStepList.size() - 1);
        assertThat(testTaskStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskStep.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testTaskStep.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTaskStep.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testTaskStep.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    void partialUpdateTaskStepShouldThrown() throws Exception {
        // Update the taskStep without id should throw
        TaskStep partialUpdatedTaskStep = new TaskStep();

        restTaskStepMockMvc
            .perform(
                patch("/api/task-steps")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskStep))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteTaskStep() throws Exception {
        // Initialize the database
        taskStepRepository.saveAndFlush(taskStep);

        int databaseSizeBeforeDelete = taskStepRepository.findAll().size();

        // Delete the taskStep
        restTaskStepMockMvc
            .perform(delete("/api/task-steps/{id}", taskStep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskStep> taskStepList = taskStepRepository.findAll();
        assertThat(taskStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
