package com.book.model;

import javax.persistence.*;

@Entity
public class BorrowCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int code;
    @ManyToOne
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BorrowCode(Long id, int code, Book book) {
        this.id = id;
        this.code = code;
        this.book = book;
    }

    public BorrowCode(int code, Book book) {
        this.code = code;
        this.book = book;
    }

    public BorrowCode() {
    }
}
