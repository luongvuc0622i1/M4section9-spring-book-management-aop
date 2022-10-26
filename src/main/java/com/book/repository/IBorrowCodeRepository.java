package com.book.repository;

import com.book.model.BorrowCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBorrowCodeRepository extends JpaRepository<BorrowCode, Long> {
    Optional<BorrowCode> findBorrowCodeByCode(int code);
}
