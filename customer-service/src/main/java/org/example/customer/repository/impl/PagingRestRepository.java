package org.example.customer.repository.impl;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PagingRestRepository {

    protected String convertPagingParamsToString(Integer page, Integer size, String[] sort){
        List<String> params=new ArrayList<>();
        if (!ObjectUtils.isEmpty(page)) {
            params.add("page="+page);
        }
        if (!ObjectUtils.isEmpty(size)) {
            params.add("size="+size);
        }
        if (!ObjectUtils.isEmpty(sort)) {
            params.add("sort="+ Arrays.stream(sort).collect(Collectors.joining(",")));
        }
        return ObjectUtils.isEmpty(params)?"":"?"+params.stream().collect(Collectors.joining("&"));
    }
}
