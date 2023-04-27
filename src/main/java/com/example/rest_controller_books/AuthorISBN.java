package com.example.rest_controller_books;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AuthorISBN {

    public Integer getAuthorisbnId() {
        return authorisbnId;
    }

    public void setAuthorisbnId(Integer authorisbnId) {
        this.authorisbnId = authorisbnId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer authorisbnId;

    private Integer authorId;

    private int isbn;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }



}
