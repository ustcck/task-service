package com.lenovo.cloud.task.web.rest;

import com.lenovo.cloud.task.domain.TaskStepExecuteRecord;
import com.lenovo.cloud.task.service.TaskStepExecuteRecordQueryService;
import com.lenovo.cloud.task.service.TaskStepExecuteRecordService;
import com.lenovo.cloud.task.service.dto.TaskStepExecuteRecordCriteria;
import com.lenovo.cloud.task.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lenovo.cloud.task.domain.TaskStepExecuteRecord}.
 */
@RestController
@RequestMapping("/api")
public class TaskStepExecuteRecordResource {

    private final Logger log = LoggerFactory.getLogger(TaskStepExecuteRecordResource.class);

    private static final String ENTITY_NAME = "taskStepExecuteRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskStepExecuteRecordService taskStepExecuteRecordService;

    private final TaskStepExecuteRecordQueryService taskStepExecuteRecordQueryService;

    public TaskStepExecuteRecordResource(
        TaskStepExecuteRecordService taskStepExecuteRecordService,
        TaskStepExecuteRecordQueryService taskStepExecuteRecordQueryService
    ) {
        this.taskStepExecuteRecordService = taskStepExecuteRecordService;
        this.taskStepExecuteRecordQueryService = taskStepExecuteRecordQueryService;
    }

    /**
     * {@code POST  /task-step-execute-records} : Create a new taskStepExecuteRecord.
     *
     * @param taskStepExecuteRecord the taskStepExecuteRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskStepExecuteRecord, or with status {@code 400 (Bad Request)} if the taskStepExecuteRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-step-execute-records")
    public ResponseEntity<TaskStepExecuteRecord> createTaskStepExecuteRecord(
        @Valid @RequestBody TaskStepExecuteRecord taskStepExecuteRecord
    ) throws URISyntaxException {
        log.debug("REST request to save TaskStepExecuteRecord : {}", taskStepExecuteRecord);
        if (taskStepExecuteRecord.getId() != null) {
            throw new BadRequestAlertException("A new taskStepExecuteRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskStepExecuteRecord result = taskStepExecuteRecordService.save(taskStepExecuteRecord);
        return ResponseEntity
            .created(new URI("/api/task-step-execute-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-step-execute-records} : Updates an existing taskStepExecuteRecord.
     *
     * @param taskStepExecuteRecord the taskStepExecuteRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStepExecuteRecord,
     * or with status {@code 400 (Bad Request)} if the taskStepExecuteRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskStepExecuteRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-step-execute-records")
    public ResponseEntity<TaskStepExecuteRecord> updateTaskStepExecuteRecord(
        @Valid @RequestBody TaskStepExecuteRecord taskStepExecuteRecord
    ) throws URISyntaxException {
        log.debug("REST request to update TaskStepExecuteRecord : {}", taskStepExecuteRecord);
        if (taskStepExecuteRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskStepExecuteRecord result = taskStepExecuteRecordService.save(taskStepExecuteRecord);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskStepExecuteRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-step-execute-records} : Updates given fields of an existing taskStepExecuteRecord.
     *
     * @param taskStepExecuteRecord the taskStepExecuteRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStepExecuteRecord,
     * or with status {@code 400 (Bad Request)} if the taskStepExecuteRecord is not valid,
     * or with status {@code 404 (Not Found)} if the taskStepExecuteRecord is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskStepExecuteRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-step-execute-records", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskStepExecuteRecord> partialUpdateTaskStepExecuteRecord(
        @NotNull @RequestBody TaskStepExecuteRecord taskStepExecuteRecord
    ) throws URISyntaxException {
        log.debug("REST request to update TaskStepExecuteRecord partially : {}", taskStepExecuteRecord);
        if (taskStepExecuteRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<TaskStepExecuteRecord> result = taskStepExecuteRecordService.partialUpdate(taskStepExecuteRecord);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskStepExecuteRecord.getId().toString())
        );
    }

    /**
     * {@code GET  /task-step-execute-records} : get all the taskStepExecuteRecords.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskStepExecuteRecords in body.
     */
    @GetMapping("/task-step-execute-records")
    public ResponseEntity<List<TaskStepExecuteRecord>> getAllTaskStepExecuteRecords(
        TaskStepExecuteRecordCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get TaskStepExecuteRecords by criteria: {}", criteria);
        Page<TaskStepExecuteRecord> page = taskStepExecuteRecordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-step-execute-records/count} : count all the taskStepExecuteRecords.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-step-execute-records/count")
    public ResponseEntity<Long> countTaskStepExecuteRecords(TaskStepExecuteRecordCriteria criteria) {
        log.debug("REST request to count TaskStepExecuteRecords by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskStepExecuteRecordQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-step-execute-records/:id} : get the "id" taskStepExecuteRecord.
     *
     * @param id the id of the taskStepExecuteRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskStepExecuteRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-step-execute-records/{id}")
    public ResponseEntity<TaskStepExecuteRecord> getTaskStepExecuteRecord(@PathVariable Long id) {
        log.debug("REST request to get TaskStepExecuteRecord : {}", id);
        Optional<TaskStepExecuteRecord> taskStepExecuteRecord = taskStepExecuteRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskStepExecuteRecord);
    }

    /**
     * {@code DELETE  /task-step-execute-records/:id} : delete the "id" taskStepExecuteRecord.
     *
     * @param id the id of the taskStepExecuteRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-step-execute-records/{id}")
    public ResponseEntity<Void> deleteTaskStepExecuteRecord(@PathVariable Long id) {
        log.debug("REST request to delete TaskStepExecuteRecord : {}", id);
        taskStepExecuteRecordService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
