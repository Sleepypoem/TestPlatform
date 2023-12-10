package com.sleepypoem.testplatform.controller;
import com.sleepypoem.testplatform.controller.utils.Paginator;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.RoleDto;
import com.sleepypoem.testplatform.domain.entity.Role;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/roles")
public class RoleController extends AbstractQueryableController<Role,  Long>{

    public RoleController(AbstractQueryableService<Long, Role> service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<RoleDto>> all(@PageableDefault Pageable pageable) {
        Page<Role> roles = getAllInternal(pageable);
        Paginator<RoleDto> paginator = new Paginator<>("roles");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(roles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(getByIdInternal(id).toDto());
    }

    @GetMapping(params = {"query"})
    public ResponseEntity<PaginatedDto<RoleDto>> search(@RequestParam String query, @PageableDefault Pageable pageable) {
        Page<Role> roles = queryInternal(query, pageable);
        Paginator<RoleDto> paginator = new Paginator<>("roles");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(roles));
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody Role role) {
        return ResponseEntity.ok(createInternal(role).toDto());
    }


    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@PathVariable Long id, @RequestBody Role role) {
        return ResponseEntity.ok(updateInternal(id, role).toDto());
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
