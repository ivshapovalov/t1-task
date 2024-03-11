package org.example.customer.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingInfo {
    private int totalPages;
    private int currentPage;
    private long totalElements;
    private int itemPerPage;
}
