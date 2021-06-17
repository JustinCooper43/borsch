package com.nayax.borsch.model.dto;

import java.util.List;

public class PageDto<T> {
    private List<T> responseList;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalElements;

    public PageDto() {

    }

    public PageDto(List<T> responseList) {
        this.responseList = responseList;
    }

    public List<T> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<T> responseList) {
        this.responseList = responseList;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }
}
