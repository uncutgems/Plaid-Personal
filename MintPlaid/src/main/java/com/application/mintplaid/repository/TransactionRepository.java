package com.application.mintplaid.repository;

import com.application.mintplaid.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> findAllByPlaidAccountInAndDateBetween(List<String> plaidAccountIds, Date startDate, Date endDate,
                                                            Pageable pageable);

    List<Transaction> findAllByPlaidAccountIn(List<String> plaidAccountIds, Pageable pageable);

}
