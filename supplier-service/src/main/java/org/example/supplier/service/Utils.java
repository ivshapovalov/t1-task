package org.example.supplier.service;

import org.example.supplier.model.dto.response.PagingInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class Utils {

    private static Integer PAGE_DEFAULT_NUMBER;

    private static Integer PAGE_DEFAULT_SIZE;

    private static String[] PAGE_DEFAULT_SORT;

    @Value("${supplier.page.default.number}")
    public void setPageDefaultNumberStatic(Integer pageDefaultNumber){
        Utils.PAGE_DEFAULT_NUMBER = pageDefaultNumber;
    }

    @Value("${supplier.page.default.size}")
    public void setPageDefaultSizeStatic(Integer pageDefaultSize){
        Utils.PAGE_DEFAULT_SIZE = pageDefaultSize;
    }

    @Value("${supplier.page.default.sort}")
    public void setPageDefaultSizeStatic(String[] pageDefaultSort){
        Utils.PAGE_DEFAULT_SORT= pageDefaultSort;
    }


    public static Pageable getPageable(Integer page, Integer size, String[] sort) {
        page = Objects.isNull(page) ? PAGE_DEFAULT_NUMBER : page;
        size = Objects.isNull(size) ? PAGE_DEFAULT_SIZE : size;
        sort = Objects.isNull(sort) ? PAGE_DEFAULT_SORT : sort;

        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] sorting = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(sorting[1]), sorting[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        int currentPage = page - 1;
        return PageRequest.of(currentPage, size, Sort.by(orders));
    }

    private static Sort.Direction getSortDirection(String dir) {
        return dir.toLowerCase().contains("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    public static PagingInfo getPagingInfoFromPage(Page page) {
        PagingInfo pagingInfo = new PagingInfo();
        int currentPage = page.getNumber() + 1;
        pagingInfo.setCurrentPage(currentPage);
        pagingInfo.setTotalPages(page.getTotalPages());
        pagingInfo.setTotalElements(page.getTotalElements());
        pagingInfo.setItemPerPage(page.getSize());
        return pagingInfo;
    }
}
