package ru.crm.taskboard.data.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
public class CustomPage {

    private List<?> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;

    public CustomPage(Page<?> page) {
        Pageable pageable = page.getPageable();

        this.content = page.getContent();
        this.pageNumber = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

    public CustomPage(Page<?> page, List<?> customContent) {
        Pageable pageable = page.getPageable();

        this.content = customContent;
        this.pageNumber = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
