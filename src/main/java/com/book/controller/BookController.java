package com.book.controller;

import com.book.exception.NotFoundException;
import com.book.model.Book;
import com.book.model.BorrowCode;
import com.book.service.book.IBookService;
import com.book.service.borrowcode.IBorrowCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private IBookService bookService;
    @Autowired
    private IBorrowCodeService borrowCodeService;

    @GetMapping("/books")
    public ModelAndView getBookList() {
        ModelAndView modelAndView = new ModelAndView("/book/menu");
        List<Book> books = (List<Book>) bookService.findAll();
        modelAndView.addObject("books", books);
        modelAndView.addObject("borrowCode", new BorrowCode());
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView showBookDetail(@PathVariable("id") Long id) throws NotFoundException {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            throw new NotFoundException();
        }
        return new ModelAndView("/book/view","book",bookOptional.get());
    }

    @GetMapping("/borrow/{id}")
    public ModelAndView generateCode(@PathVariable("id") Long id) throws NotFoundException {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            throw new NotFoundException();
        } else {
            ModelAndView modelAndView = new ModelAndView("/book/view");
            Book book = bookOptional.get();
            int currentAmount = book.getAmount();
            if (currentAmount == 0) {
                modelAndView.addObject("message", "This book is not available now");
                modelAndView.addObject("book", book);
            } else {
                book.setAmount(currentAmount - 1);
                bookService.save(book);
                modelAndView.addObject("book", book);
                int code = (int) ((Math.random()*(99999-10000)) + 1000);
                BorrowCode borrowCode = new BorrowCode(code, bookOptional.get());
                borrowCodeService.save(borrowCode);
                modelAndView.addObject("code", code);
            } return modelAndView;
        }
    }

    @PostMapping("/return")
    public ModelAndView returnBook(@ModelAttribute("borrowCode") BorrowCode borrowCode) throws NotFoundException {
        Optional<BorrowCode> borrowCodeOptional = borrowCodeService.findBorrowCodeByCode(borrowCode.getCode());
        if (!borrowCodeOptional.isPresent()) {
            throw  new NotFoundException();
        } else {
            BorrowCode borrowCode1 = borrowCodeOptional.get();
            Book book = borrowCode1.getBook();
            book.setAmount(book.getAmount() + 1);
            bookService.save(book);
            borrowCodeService.deleteById(borrowCode1.getId());
            return new ModelAndView("redirect:/books");
        }
    }

}
