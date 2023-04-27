package com.example.rest_controller_books;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "book")
public class Book {



    @Id
    private int isbn;

    private String title;

    private int editionNumber;

    @JsonFormat(pattern="yyyy-MM-dd") LocalDate copyright;





    //@Enumerated(EnumType.ORDINAL) private HeatingType heatingType;
    //@Enumerated(EnumType.ORDINAL) private Location location;



    //TODO Update these parameters
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    public LocalDate getCopyright() {
        return copyright;
    }

    public void setCopyright(LocalDate copyright) {
        this.copyright = copyright;
    }



}
