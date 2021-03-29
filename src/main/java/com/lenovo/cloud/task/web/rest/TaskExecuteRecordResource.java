package com.lenovo.cloud.task.web.rest;

import com.lenovo.cloud.task.domain.TaskExecuteRecord;
import com.lenovo.cloud.task.service.TaskExecuteRecordQueryService;
import com.lenovo.cloud.task.service.TaskExecuteRecordService;
import com.lenovo.cloud.task.service.dto.TaskExecuteRecordCriteria;
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
 * REST controller for managing {@link com.lenovo.cloud.task.domain.TaskExecuteRecord}.
 */
@RestController
@RequestMapping("/api")
public class TaskExecuteRecordResource {

    private final Logger log = LoggerFactory.getLogger(TaskExecuteRecordResource.class);

    private static final String ENTITY_NAME = "taskExecuteRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskExecuteRecordService taskExecuteRecordService;

    private final TaskExecuteRecordQueryService taskExecuteRecordQueryService;

    public TaskExecuteRecordResource(
        TaskExecuteRecordService taskExecuteRecordService,
        TaskExecuteRecordQueryService taskExecuteRecordQueryService
    ) {
        this.taskExecuteRecordService = taskExecuteRecordService;
        this.taskExecuteRecordQueryService = taskExecuteRecordQueryService;
    }

    /**
     * {@code POST  /task-execute-records} : Create a new taskExecuteRecord.
     *
     * @param taskExecuteRecord the taskExecuteRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskExecuteRecord, or with status {@code 400 (Bad Request)} if the taskExecuteRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-execute-records")
    public ResponseEntity<TaskExecuteRecord> createTaskExecuteRecord(@Valid @RequestBody TaskExecuteRecord taskExecuteRecord)
        throws URISyntaxException {
        log.debug("REST request to save TaskExecuteRecord : {}", taskExecuteRecord);
        if (taskExecuteRecord.getId() != null) {
            throw new BadRequestAlertException("A new taskExecuteRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskExecuteRecord result = taskExecuteRecordService.save(taskExecuteRecord);
        return ResponseEntity
            .created(new URI("/api/task-execute-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-execute-records} : Updates an existing taskExecuteRecord.
     *
     * @param taskExecuteRecord the taskExecuteRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskExecuteRecord,
     * or with status {@code 400 (Bad Request)} if the taskExecuteRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskExecuteRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-execute-records")
    public ResponseEntity<TaskExecuteRecord> updateTaskExecuteRecord(@Valid @RequestBody TaskExecuteRecord taskExecuteRecord)
        throws URISyntaxException {
        log.debug("REST request to update TaskExecuteRecord : {}", taskExecuteRecord);
        if (taskExecuteRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskExecuteRecord result = taskExecuteRecordService.save(taskExecuteRecord);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskExecuteRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-execute-records} : Updates given fields of an existing taskExecuteRecord.
     *
     * @param taskExecuteRecord the taskExecuteRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskExecuteRecord,
     * or with status {@code 400 (Bad Request)} if the taskExecuteRecord is not valid,
     * or with status {@code 404 (Not Found)} if the taskExecuteRecord is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskExecuteRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-execute-records", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskExecuteRecord> partialUpdateTaskExecuteRecord(@NotNull @RequestBody TaskExecuteRecord taskExecuteRecord)
        throws URISyntaxException {
        log.debug("REST request to update TaskExecuteRecord partially : {}", taskExecuteRecord);
        if (taskExecuteRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<TaskExecuteRecord> result = taskExecuteRecordService.partialUpdate(taskExecuteRecord);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskExecuteRecord.getId().toString())
        );
    }

    /**
     * {@code GET  /task-execute-records} : get all the taskExecuteRecords.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskExecuteRecords in body.
     */
    @GetMapping("/task-execute-records")
    public ResponseEntity<List<TaskExecuteRecord>> getAllTaskExecuteRecords(TaskExecuteRecordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskExecuteRecords by criteria: {}", criteria);
        Page<TaskExecuteRecord> page = taskExecuteRecordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-execute-records/count} : count all the taskExecuteRecords.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-execute-records/count")
    public ResponseEntity<Long> countTaskExecuteRecords(TaskExecuteRecordCriteria criteria) {
        log.debug("REST request to count TaskExecuteRecords by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskExecuteRecordQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-execute-records/:id} : get the "id" taskExecuteRecord.
     *
     * @param id the id of the taskExecuteRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskExecuteRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-execute-records/{id}")
    public ResponseEntity<TaskExecuteRecord> getTaskExecuteRecord(@PathVariable Long id) {
        log.debug("REST request to get TaskExecuteRecord : {}", id);
        Optional<TaskExecuteRecord> taskExecuteRecord = taskExecuteRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskExecuteRecord);
    }

    /**
     * {@code DELETE  /task-execute-records/:id} : delete the "id" taskExecuteRecord.
     *
     * @param id the id of the taskExecuteRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-execute-records/{id}")
    public ResponseEntity<Void> deleteTaskExecuteRecord(@PathVariable Long id) {
        log.debug("REST request to delete TaskExecuteRecord : {}", id);
        taskExecuteRecordService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
