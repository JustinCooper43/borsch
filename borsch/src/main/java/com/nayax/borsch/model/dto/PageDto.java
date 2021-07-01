package com.nayax.borsch.model.dto;

import com.nayax.borsch.utility.PageDtoBuilder;

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

    //TODO mock method, to be removed when unmocking controllers
    public static <T> PageDto<T> getPagedList(Integer page, Integer pageSize, List<T> itemList) {
        return new PageDtoBuilder<T>()
                .page(itemList)
                .elementsPerPage(pageSize)
                .currentPageNum(page)
                .build();
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

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }
}
