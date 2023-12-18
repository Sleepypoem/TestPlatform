package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.utils.Paginator;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.StudentTestDto;
import com.sleepypoem.testplatform.domain.entity.StudentTest;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student-tests")
public class StudentTestController extends AbstractQueryableController<StudentTest, Long>{
    public StudentTestController(AbstractQueryableService<Long, StudentTest> service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<StudentTestDto>> all(@PageableDefault Pageable pageable) {
        Page<StudentTest> results = getAllInternal(pageable);
        Paginator<StudentTestDto> paginator = new Paginator<>("student-tests");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(results));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentTestDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(getByIdInternal(id).toDto());
    }

    @GetMapping(params = {"query"})
    public ResponseEntity<PaginatedDto<StudentTestDto>> search(@RequestParam String query, @PageableDefault Pageable pageable) {
        Page<StudentTest> results = queryInternal(query, pageable);
        Paginator<StudentTestDto> paginator = new Paginator<>("student-tests");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(results));
    }

    @PostMapping
    public ResponseEntity<StudentTestDto> create(@RequestBody StudentTest studentTest) {
        return ResponseEntity.ok(createInternal(studentTest).toDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentTestDto> update(@PathVariable Long id, @RequestBody StudentTest studentTest) {
        return ResponseEntity.ok(updateInternal(id, studentTest).toDto());
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
