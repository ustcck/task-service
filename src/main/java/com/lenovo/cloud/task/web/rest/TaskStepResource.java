package com.lenovo.cloud.task.web.rest;

import com.lenovo.cloud.task.domain.TaskStep;
import com.lenovo.cloud.task.service.TaskStepQueryService;
import com.lenovo.cloud.task.service.TaskStepService;
import com.lenovo.cloud.task.service.dto.TaskStepCriteria;
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
 * REST controller for managing {@link com.lenovo.cloud.task.domain.TaskStep}.
 */
@RestController
@RequestMapping("/api")
public class TaskStepResource {

    private final Logger log = LoggerFactory.getLogger(TaskStepResource.class);

    private static final String ENTITY_NAME = "taskStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskStepService taskStepService;

    private final TaskStepQueryService taskStepQueryService;

    public TaskStepResource(TaskStepService taskStepService, TaskStepQueryService taskStepQueryService) {
        this.taskStepService = taskStepService;
        this.taskStepQueryService = taskStepQueryService;
    }

    /**
     * {@code POST  /task-steps} : Create a new taskStep.
     *
     * @param taskStep the taskStep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskStep, or with status {@code 400 (Bad Request)} if the taskStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-steps")
    public ResponseEntity<TaskStep> createTaskStep(@Valid @RequestBody TaskStep taskStep) throws URISyntaxException {
        log.debug("REST request to save TaskStep : {}", taskStep);
        if (taskStep.getId() != null) {
            throw new BadRequestAlertException("A new taskStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskStep result = taskStepService.save(taskStep);
        return ResponseEntity
            .created(new URI("/api/task-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-steps} : Updates an existing taskStep.
     *
     * @param taskStep the taskStep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStep,
     * or with status {@code 400 (Bad Request)} if the taskStep is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskStep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-steps")
    public ResponseEntity<TaskStep> updateTaskStep(@Valid @RequestBody TaskStep taskStep) throws URISyntaxException {
        log.debug("REST request to update TaskStep : {}", taskStep);
        if (taskStep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskStep result = taskStepService.save(taskStep);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskStep.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-steps} : Updates given fields of an existing taskStep.
     *
     * @param taskStep the taskStep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskStep,
     * or with status {@code 400 (Bad Request)} if the taskStep is not valid,
     * or with status {@code 404 (Not Found)} if the taskStep is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskStep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-steps", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskStep> partialUpdateTaskStep(@NotNull @RequestBody TaskStep taskStep) throws URISyntaxException {
        log.debug("REST request to update TaskStep partially : {}", taskStep);
        if (taskStep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<TaskStep> result = taskStepService.partialUpdate(taskStep);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskStep.getId().toString())
        );
    }

    /**
     * {@code GET  /task-steps} : get all the taskSteps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskSteps in body.
     */
    @GetMapping("/task-steps")
    public ResponseEntity<List<TaskStep>> getAllTaskSteps(TaskStepCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TaskSteps by criteria: {}", criteria);
        Page<TaskStep> page = taskStepQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-steps/count} : count all the taskSteps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-steps/count")
    public ResponseEntity<Long> countTaskSteps(TaskStepCriteria criteria) {
        log.debug("REST request to count TaskSteps by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskStepQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-steps/:id} : get the "id" taskStep.
     *
     * @param id the id of the taskStep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskStep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-steps/{id}")
    public ResponseEntity<TaskStep> getTaskStep(@PathVariable Long id) {
        log.debug("REST request to get TaskStep : {}", id);
        Optional<TaskStep> taskStep = taskStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskStep);
    }

    /**
     * {@code DELETE  /task-steps/:id} : delete the "id" taskStep.
     *
     * @param id the id of the taskStep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-steps/{id}")
    public ResponseEntity<Void> deleteTaskStep(@PathVariable Long id) {
        log.debug("REST request to delete TaskStep : {}", id);
        taskStepService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
