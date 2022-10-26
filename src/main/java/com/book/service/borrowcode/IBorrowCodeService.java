package com.book.service.borrowcode;

import com.book.model.BorrowCode;
import com.book.service.IGeneralService;
import java.util.Optional;

public interface IBorrowCodeService extends IGeneralService<BorrowCode> {
    Optional<BorrowCode> findBorrowCodeByCode(int code);
}
