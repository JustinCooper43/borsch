package com.nayax.borsch.utility;

import com.nayax.borsch.model.dto.PageDto;

import java.util.List;

public class PageDtoBuilder<T> {


    private final PageDto<T> instancePage = new PageDto<>();

    public static int getTotalPages(int pageSize, int totalElements) {
        return totalElements % pageSize == 0 ?
                totalElements / pageSize :
                totalElements / pageSize + 1;
    }

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

    public PageDto<T> build() {
        paginationList();
        instancePage.setTotalPages(getTotalPages(instancePage.getPageSize(),instancePage.getResponseList().size()));
        instancePage.setTotalElements(instancePage.getResponseList().size());
        return instancePage;
    }

    private void paginationList(){
        if (instancePage.getPageSize() < instancePage.getResponseList().size()) {
            int pageFrom = (instancePage.getPage() - 1) * instancePage.getPageSize();
            int pageTo = Math.min(pageFrom + instancePage.getPageSize(), instancePage.getResponseList().size());
            instancePage.setResponseList(instancePage.getResponseList().subList(pageFrom, pageTo));
        }
    }
}
