package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.utils.Paginator;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.TeacherDto;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
public class TeacherController extends AbstractQueryableController<Teacher, Long> {

    public TeacherController(AbstractQueryableService<Long, Teacher> service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<TeacherDto>> all(@PageableDefault Pageable pageable) {
        Page<Teacher> teachers = getAllInternal(pageable);
        Paginator<TeacherDto> paginator = new Paginator<>("teachers");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(teachers));
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(createInternal(teacher).toDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @RequestBody Teacher teacher) {
        return ResponseEntity.ok(updateInternal(id, teacher).toDto());
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


    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(getByIdInternal(id).toDto());
    }

    @GetMapping(params = {"query"})
    public ResponseEntity<PaginatedDto<TeacherDto>> search(@RequestParam String query, @PageableDefault Pageable pageable) {
        Page<Teacher> queryResult = queryInternal(query, pageable);
        Paginator<TeacherDto> paginator = new Paginator<>("teachers" + "?query=" + query);
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(queryResult));
    }
}
