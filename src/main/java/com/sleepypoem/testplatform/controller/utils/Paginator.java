package com.sleepypoem.testplatform.controller.utils;

import com.sleepypoem.testplatform.domain.dto.BaseDto;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.entity.base.EntityWithDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

public class Paginator<D extends BaseDto<?>> {

    public static final String PAGE = "page=";
    public static final String SIZE = "size=";
    private final String resourceName;
    @SuppressWarnings("all")
    private String API_PATH = "/api/";

    public Paginator(String resourceName) {
        this.resourceName = resourceName;
    }

    public <E extends EntityWithDto<D>> PaginatedDto<D> getPaginatedDtoFromPage(Page<E> pagedEntity) {
        PaginatedDto<D> paginatedDto = new PaginatedDto<>();
        paginatedDto.setCurrent(pagedEntity.getNumber());
        paginatedDto.setTotal(pagedEntity.getTotalElements());
        paginatedDto.setContent(pagedEntity.getContent().stream().map(EntityWithDto::toDto).toList());
        if (pagedEntity.hasPrevious()) {
            paginatedDto.setPrev(createPreviousPageUrl(pagedEntity));
        }
        if (pagedEntity.hasNext()) {
            paginatedDto.setNext(createNextPageUrl(pagedEntity));
        }
        return paginatedDto;
    }

    public <E> String createNextPageUrl(Page<E> page) {
        Pageable nextPageable = page.nextPageable();
        int pageNumber = nextPageable.getPageNumber();
        int pageSize = nextPageable.getPageSize();
        String result = API_PATH +
                resourceName +
                PAGE +
                pageNumber +
                "&" +
                SIZE +
                pageSize;
        if (page.getSort() != null) {
            return appendSortParam(result, page);
        }

        return result;
    }

    private String sortPropertyExtractor(Page<?> page) {
        List<String> sortProperties = Arrays.stream(page.getSort().toString().split(",")).toList();
        StringBuilder result = new StringBuilder();
        for (String property : sortProperties) {
            result.append("&sort=").append(property.replace(": ", ","));
        }
        return result.toString();
    }

    private <E> String appendSortParam(String url, Page<E> page) {
        return url + sortPropertyExtractor(page);
    }

    public String createPreviousPageUrl(Page<?> page) {
        Pageable previousPageable = page.previousPageable();
        int pageNumber = previousPageable.getPageNumber();
        int pageSize = previousPageable.getPageSize();

        String result = API_PATH +
                resourceName +
                PAGE +
                pageNumber +
                "&" +
                SIZE +
                pageSize;
        if (page.getSort() != null) {
            return appendSortParam(result, page);
        }

        return result;
    }


    public PaginatedDto<D> getPaginatedDtoFromList(Integer currentPage, Integer size, Long totalElements, List<D> content) {
        PaginatedDto<D> paginatedDto = new PaginatedDto<>();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        String nextPage = currentPage == (totalPages - 1) ? null : API_PATH + resourceName + PAGE + (currentPage + 1) + "&" + SIZE + size;
        String previousPage = currentPage == 0 ? null : API_PATH + resourceName + PAGE + (currentPage - 1) + "&" + SIZE + size;
        paginatedDto.setContent(content);
        paginatedDto.setTotal(totalElements);
        paginatedDto.setCurrent(currentPage);
        paginatedDto.setNext(nextPage);
        paginatedDto.setPrev(previousPage);
        return paginatedDto;
    }
}
