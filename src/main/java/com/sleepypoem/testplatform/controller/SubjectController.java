package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.utils.Paginator;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.SubjectDto;
import com.sleepypoem.testplatform.domain.entity.Subject;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subjects")
public class SubjectController extends AbstractQueryableController<Subject, Long>{
    public SubjectController(AbstractQueryableService<Long, Subject> service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<SubjectDto>> all(@PageableDefault Pageable pageable) {
        Page<Subject> subjects = getAllInternal(pageable);
        Paginator<SubjectDto> paginator = new Paginator<>("subjects");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(subjects));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(getByIdInternal(id).toDto());
    }

    @GetMapping(params = {"query"})
    public ResponseEntity<PaginatedDto<SubjectDto>> search(@RequestParam String query, @PageableDefault Pageable pageable) {
        Page<Subject> subjects = queryInternal(query, pageable);
        Paginator<SubjectDto> paginator = new Paginator<>("subjects");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(subjects));
    }

    @PostMapping
    public ResponseEntity<SubjectDto> create(@RequestBody Subject subject) {
        return ResponseEntity.ok(createInternal(subject).toDto());
    }


    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> update(@PathVariable Long id, @RequestBody Subject subject) {
        return ResponseEntity.ok(updateInternal(id, subject).toDto());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            deleteInternal(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
