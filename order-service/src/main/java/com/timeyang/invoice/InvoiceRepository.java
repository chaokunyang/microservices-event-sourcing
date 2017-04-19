package com.timeyang.invoice;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author yangck
 */
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, String> {
}
