package com.timeyang.account;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 为{@link Account}领域类提供包括分页和排序等基本的管理能力
 *
 * @author chaokunyang
 */
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    List<Account> findAccountsByUserId(@Param("userId") String userId);
}
