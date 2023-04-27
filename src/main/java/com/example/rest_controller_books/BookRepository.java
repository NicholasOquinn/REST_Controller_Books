package com.example.rest_controller_books;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository <Book, Integer> {

    /**
     * Get all books for an author
     *
     * @param
     * @return author
     */
    Iterable<Book> getAllByAuthorId(Integer authorId);

    Iterable<Book> findByAuthorId(Integer authorId);

    Optional<Book> findByTitle(String title);

    Optional<Book> findByIsbn(int isbn);
}