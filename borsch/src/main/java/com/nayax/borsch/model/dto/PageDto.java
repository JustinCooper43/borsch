package com.nayax.borsch.model.dto;

import java.util.List;

public class PageDto<T> {
    private List<T> page;
    private Integer currentPageNumber;
    private Integer elementsPerPage;
    private Integer totalPages;
    private Integer totalElements;

    public PageDto() {

    }

    public PageDto(List<T> page) {
        this.page = page;
    }

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }

    public Integer getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public Integer getElementsPerPage() {
        return elementsPerPage;
    }

    public void setElementsPerPage(Integer elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
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
