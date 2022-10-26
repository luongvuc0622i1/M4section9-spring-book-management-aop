package com.book.service.borrowcode;

import com.book.model.BorrowCode;
import com.book.repository.IBorrowCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowCodeService implements IBorrowCodeService{
    @Autowired
    private IBorrowCodeRepository borrowCodeRepository;


    @Override
    public Iterable<BorrowCode> findAll() {
        return borrowCodeRepository.findAll();
    }

    @Override
    public Optional<BorrowCode> findById(Long id) {
        return borrowCodeRepository.findById(id);
    }

    @Override
    public void save(BorrowCode borrowCode) {
        borrowCodeRepository.save(borrowCode);
    }

    @Override
    public void deleteById(Long id) {
        borrowCodeRepository.deleteById(id);
    }

    @Override
    public Optional<BorrowCode> findBorrowCodeByCode(int code) {
        return borrowCodeRepository.findBorrowCodeByCode(code);
    }
}
