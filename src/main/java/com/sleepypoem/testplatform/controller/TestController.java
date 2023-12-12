package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.utils.Paginator;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.TestDto;
import com.sleepypoem.testplatform.domain.entity.Test;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
public class TestController extends AbstractQueryableController<Test, Long> {
    public TestController(AbstractQueryableService<Long, Test> service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<TestDto>> all(@PageableDefault Pageable pageable) {
        Page<Test> tests = getAllInternal(pageable);
        Paginator<TestDto> paginator = new Paginator<>("tests");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(tests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getByIdInternal(id).toDto());
    }

    @GetMapping(params = {"query"})
    public ResponseEntity<PaginatedDto<TestDto>> search(@RequestParam String query, @PageableDefault Pageable pageable) {
        Page<Test> tests = queryInternal(query, pageable);
        Paginator<TestDto> paginator = new Paginator<>("tests");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(tests));
    }


    @PostMapping
    public ResponseEntity<TestDto> create(@RequestBody Test test) {
        return ResponseEntity.ok(createInternal(test).toDto());
    }


    @PutMapping("/{id}")
    public ResponseEntity<TestDto> update(@PathVariable Long id, @RequestBody Test test) {
        return ResponseEntity.ok(updateInternal(id, test).toDto());
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
