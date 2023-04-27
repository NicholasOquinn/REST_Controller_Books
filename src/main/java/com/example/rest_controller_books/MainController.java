package com.example.rest_controller_books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller //This means that this class is a Controller
@RequestMapping(path=RESTNouns.VERSION_1) //This means URLs start with /v1 (after Application path)
public class MainController {
    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository bookRepository;

    @Autowired private AuthorIsbnRepository authorIsbnRepository;

    /**
     * Get Mapping for Author
     * @return all authors
     */
    @GetMapping(path=RESTNouns.AUTHOR)
    public @ResponseBody Iterable<Author> getAllUsers(){
        return authorRepository.findAll();
    }

    /**
     * Get Mapping for User based on ID
     * @param authorId author id
     * @return author object
     */
    @GetMapping(path = RESTNouns.AUTHOR + "/{author_id}")
    public @ResponseBody Optional<Author> getAuthorWithId(@PathVariable(name = "author_id") Integer authorId){
        return authorRepository.findById(authorId);
    }

    /**
     * Post Mapping for User
     * @param firstName first name of user
     * @param lastName last name of user
     * @return message stating success / failure
     */
    @PostMapping(path=RESTNouns.AUTHOR)
    public @ResponseBody String addNewAuthor(@RequestParam String firstName, @RequestParam String lastName){
        Author author = new Author();
        //AuthorISBN authorISBN = new AuthorISBN(); // Create an AuthorISBN
        //authorISBN.setAuthorId(authorId);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        authorRepository.save(author);
        return "Successfully created new Author"; //TODO Send a better message
    }

    /**
     * Put Mapping for User based on ID
     * @param authorId author id
     * @param firstName first name update
     * @param lastName last name update
     * @return message stating success / failure
     */
    @PutMapping (path=RESTNouns.AUTHOR + "/{author_id}")
    public @ResponseBody String updateAuthor(@PathVariable(name = "author_id")  Integer authorId,
                                           @RequestParam String firstName,
                                           @RequestParam String lastName){
        Optional<Author> author = authorRepository.findById(authorId);
        if(author.isPresent()){
            author.get().setFirstName(firstName);
            author.get().setLastName(lastName);
            authorRepository.save(author.get());
            return "Updated Author Information";
        }
        return "Author not found";
    }
    /**
     * Delete Mapping for Author based on ID
     * @param authorId author id
     * @return message stating success / failure
     */
    @DeleteMapping (path=RESTNouns.AUTHOR + "/{author_id}")
    public @ResponseBody String deleteAuthor(@PathVariable(name = "author_id")  Integer authorId){
        Optional<Author> author = authorRepository.findById(authorId);
        if(author.isPresent()){
            authorRepository.delete(author.get());
            return "Author has been Deleted";
        }
        return "Author not found";
    }

    //BOOK MAPPING
    /**
     * Post Mapping for Book
     //* @param authorID author ID
     * @param isbn ISBN
     * @param title Title
     * @param editionNumber edition number
     * @param copyright Copyright
     * @return message stating success / failure
     */
    @PostMapping(path=RESTNouns.AUTHOR + "/{author_id}" + RESTNouns.BOOK)
    public @ResponseBody String addNewBook(@PathVariable(name = "author_id") Integer authorId,
                                           @RequestParam int isbn,
                                           @RequestParam String title,
                                           @RequestParam int editionNumber,
                                           @RequestParam LocalDate copyright) {

            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setEditionNumber(editionNumber);
            book.setCopyright(copyright);

            //Scope the author
            Optional<Author> author = authorRepository.findById(authorId);
            if (author.isPresent()) {
                book.setAuthor(author.get()); //Link it to the author
                bookRepository.save(book);
                AuthorISBN authorISBN = new AuthorISBN(); // Create an AuthorISBN
                authorISBN.setAuthorId(authorId);
                authorISBN.setIsbn(isbn);
                authorIsbnRepository.save(authorISBN);
                return "Book added successfully";
            } else {
                return "Author with ID " + authorId + " not found";
            }
        }



    @GetMapping(path=RESTNouns.BOOK)
    public @ResponseBody Iterable<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    /**
     * Get Mapping for Books by Author Id
     * @param authorId author id
     * @return list of books for the specified author id
     */
    @GetMapping(path = RESTNouns.AUTHOR + "/{author_id}" + RESTNouns.BOOK)
    public @ResponseBody Iterable<Book> getBooksByAuthorId(@PathVariable(name = "author_id") Integer authorId){
        return bookRepository.findByAuthorId(authorId);
    }
    /**
     * Delete Mapping for Book based on title
     * @param title title of book
     * @return message stating success / failure
     */
    @DeleteMapping(path = RESTNouns.BOOK)
    public @ResponseBody String deleteBook(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "isbn", required = false) Integer isbn) {
        if (title != null) {
            Optional<Book> book = bookRepository.findByTitle(title);
            if (book.isPresent()) {
                bookRepository.delete(book.get());

                return "Book with title " + title + " has been deleted";
            } else {
                return "Book with title " + title + " not found";
            }
        } else if (isbn != null) {
            Optional<Book> book = bookRepository.findByIsbn(isbn);
            if (book.isPresent()) {
                bookRepository.delete(book.get());
                //authorIsbnRepository.delete(authorIsbnRepository.find;
                return "Book with ISBN " + isbn + " has been deleted";
            } else {
                return "Book with ISBN " + isbn + " not found";
            }
        } else {
            return "Either title or isbn parameter must be provided";
        }
    }
    @PutMapping(path = RESTNouns.BOOK)
    public @ResponseBody String updateBook(@RequestParam(name = "title", required = false) String title,
                                           @RequestParam(name = "isbn", required = false) Integer isbn,
                                           @RequestBody Book updatedBook) {
        Optional<Book> bookToUpdate = Optional.empty();
        if (title != null) {
            bookToUpdate = bookRepository.findByTitle(title);
        } else if (isbn != null) {
            bookToUpdate = bookRepository.findByIsbn(isbn);
        }
        if (bookToUpdate.isPresent()) {
            Book existingBook = bookToUpdate.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setIsbn(updatedBook.getIsbn());
            bookRepository.save(existingBook);
            return "Book updated: " + existingBook.toString();
        }
        return "Book not found";
    }

    @GetMapping(path=RESTNouns.AUTHOR_ISBN)
    public @ResponseBody Iterable<AuthorISBN> getAllAuthorISBN(){
        return authorIsbnRepository.findAll();
    }





}
