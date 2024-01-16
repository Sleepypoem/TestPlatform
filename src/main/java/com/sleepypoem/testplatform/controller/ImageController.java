package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.utils.Paginator;
import com.sleepypoem.testplatform.domain.dto.ImageDto;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController extends AbstractController<Image, Long> {
    public ImageController(AbstractService<Long, Image> service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<PaginatedDto<ImageDto>> all(@PageableDefault Pageable pageable) {
        Page<Image> images = getAllInternal(pageable);
        Paginator<ImageDto> paginator = new Paginator<>("images");
        return ResponseEntity.ok(paginator.getPaginatedDtoFromPage(images));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(getByIdInternal(id).toDto());
    }

    @PostMapping
    public ResponseEntity<ImageDto> create(@RequestBody Image image) {
        return ResponseEntity.ok(createInternal(image).toDto());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ImageDto> update(@PathVariable Long id, @RequestBody Image image) {
        return ResponseEntity.ok(updateInternal(id, image).toDto());
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
