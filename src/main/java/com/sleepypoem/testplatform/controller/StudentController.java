package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.utils.Paginator;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.StudentDto;
import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController extends AbstractQueryableController<Student, Long> {
    public StudentController(AbstractQueryableService<Long, Student> service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<StudentDto>> all(@PageableDefault Pageable pageable) {
        Page<Student> students = getAllInternal(pageable);
        Paginator<StudentDto> paginator = new Paginator<>("students");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(students));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(getByIdInternal(id).toDto());
    }

    @GetMapping(params = {"query"})
    public ResponseEntity<PaginatedDto<StudentDto>> search(@RequestParam String query, @PageableDefault Pageable pageable) {
        Page<Student> students = queryInternal(query, pageable);
        Paginator<StudentDto> paginator = new Paginator<>("students");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(students));
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody Student student) {
        return ResponseEntity.ok(createInternal(student).toDto());
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(updateInternal(id, student).toDto());
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
