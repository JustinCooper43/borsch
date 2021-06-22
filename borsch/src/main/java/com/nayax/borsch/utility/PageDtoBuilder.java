package com.nayax.borsch.utility;

import com.nayax.borsch.model.dto.PageDto;

import java.util.List;

public class PageDtoBuilder<T> {


    private final PageDto<T> instancePage = new PageDto<>();

    public PageDtoBuilder<T> page(List<T> list) {
        instancePage.setResponseList(list);
        return this;
    }

    public PageDtoBuilder<T> currentPageNum(Integer number) {
        instancePage.setPage(number);
        return this;
    }

    public PageDtoBuilder<T> elementsPerPage(Integer number) {
        instancePage.setPageSize(number);
        return this;
    }

    public PageDtoBuilder<T> totalPages(Integer number) {
        instancePage.setTotalPages(number);
        return this;
    }

    public PageDtoBuilder<T> totalElements(Integer number) {
        instancePage.setTotalElements(number);
        return this;
    }

    public PageDto<T> build() {
        return instancePage;
    }

}
