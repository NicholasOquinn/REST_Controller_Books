package com.example.rest_controller_books;

/**
 * This will be AUTO IMPLEMENTED
 * by Spring into a Bean called authorRepository
 */

import com.example.rest_controller_books.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {


}