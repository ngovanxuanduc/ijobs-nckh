package com.immortal.internship.payload.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class PageResponse {

    public PageResponse(Page page, List content){
        this.content = content;
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
        this.sortType = page.getSort();
    }

    private List content;
    private long totalElements;
    private long totalPages;
    private long size;
    private long number;
    private long numberOfElements;
    private boolean first;
    private boolean last;
    private boolean empty;
    private Sort sortType;

}
