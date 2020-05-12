package com.cosmin.bookstoreback.controllers;

import com.cosmin.bookstoreback.dtos.AvailabilityDto;
import com.cosmin.bookstoreback.dtos.BookDetailsDto;
import com.cosmin.bookstoreback.dtos.BookDto;
import com.cosmin.bookstoreback.dtos.IsbnDto;
import com.cosmin.bookstoreback.models.Book;
import com.cosmin.bookstoreback.services.BookService;
import com.cosmin.bookstoreback.services.SecurityService;
import com.cosmin.bookstoreback.utils.Consts;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/api/books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final SecurityService securityService;
    private final Validator validator;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper, SecurityService securityService) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.securityService = securityService;

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> saveBook(@RequestBody @Valid BookDto bookDto, BindingResult bindingResult) {
        Map<String, String> validations = checkValidations(bookDto);
        checkTitleUniqueness(bookDto, validations);

        if (!validations.isEmpty()) {
            return new ResponseEntity<>(validations, HttpStatus.BAD_REQUEST);
        }

        Book book = bookService.saveBook(modelMapper.map(bookDto, Book.class));
        validations.put("book_id", book.getId().toString());
        validations.put("generated_isbn", securityService.decrypt(book.getIsbn(), Consts.secretKey));

        return new ResponseEntity<>(validations, HttpStatus.CREATED);
    }

    @GetMapping(value = "/book/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            log.error("NOT FOUND: " + Consts.BOOK_NOT_FOUND);
            return setResponseMessage(HttpStatus.NOT_FOUND, Consts.BOOK_NOT_FOUND);
        } else {
            log.info("OK: Book with id " + id + " found.");
            return new ResponseEntity<>(modelMapper.map(book, BookDetailsDto.class), HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List> getAllBooks() {
        List<Book> bookList = bookService.getAllBooksOrderedByTitleAsc();
        if (bookList.isEmpty()) {
            log.info("OK: There are no books saved.");
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        } else {
            log.info("OK: Books return with succes.");
            return new ResponseEntity<>(Arrays.asList(modelMapper.map(bookList, BookDetailsDto[].class)), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Map<String, Integer>> getCount() {
        Integer numberOfBook = bookService.countBooks();
        Map<String, Integer> countMessage = new HashMap<String, Integer>() {{
            put("count", numberOfBook);
        }};
        return new ResponseEntity<>(countMessage, HttpStatus.OK);
    }

    @DeleteMapping(value = "/book/{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable Long id, @RequestBody @Valid IsbnDto isbnDto, BindingResult bindingResult) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            log.error("NOT FOUND: " + Consts.BOOK_NOT_FOUND);
            return setResponseMessage(HttpStatus.NOT_FOUND, Consts.BOOK_NOT_FOUND);
        } else {
            Map<String, String> validations = checkValidations(isbnDto);

            if (!validations.isEmpty()) {
                return new ResponseEntity<>(validations, HttpStatus.BAD_REQUEST);
            }

            if (book.getIsbn().equals(securityService.encrypt(isbnDto.getIsbn(), Consts.secretKey))) {
                bookService.deleteBookById(id);
                log.error("OK: " + Consts.DELETED);
                return setResponseMessage(HttpStatus.OK, Consts.DELETED);
            } else {
                log.error("BAD REQUEST: " + Consts.WRONG_ISBN);
                return setResponseMessage(HttpStatus.BAD_REQUEST, Consts.WRONG_ISBN);
            }
        }
    }

    @PatchMapping(value = "/book/{id}/availability")
    public ResponseEntity<Object> updateAvailability(@PathVariable Long id, @RequestBody @Valid AvailabilityDto availabilityDto,
                                                     BindingResult bindingResult) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            log.error("NOT FOUND: " + Consts.BOOK_NOT_FOUND);
            return setResponseMessage(HttpStatus.NOT_FOUND, Consts.BOOK_NOT_FOUND);
        } else {
            Map<String, String> validations = checkValidations(availabilityDto);

            if (!validations.isEmpty()) {
                return new ResponseEntity<>(validations, HttpStatus.BAD_REQUEST);
            }

            bookService.updateBookAvailability(book, availabilityDto.getAvailability());
            log.error("OK: " + Consts.UPDATED);
            return setResponseMessage(HttpStatus.OK, Consts.UPDATED);
        }
    }

    public ResponseEntity<Object> setResponseMessage(HttpStatus httpStatus, String message) {
        Map<String, String> bookResponseMessage = new HashMap<>();
        bookResponseMessage.put("message", message);
        return new ResponseEntity<>(bookResponseMessage, httpStatus);
    }

    public Map<String, String> checkValidations(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        Map<String, String> validations = new HashMap<>();

        for (ConstraintViolation<Object> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            validations.put(propertyPath, message);
            log.error("BAD REQUEST: " + message);
        }

        return validations;
    }

    public void checkTitleUniqueness(BookDto bookDto, Map<String, String> validations) {
        if (bookService.getBookByTitle(bookDto.getTitle()) != null) {
            validations.put("title", "This title is already used.");
            log.error("BAD REQUEST: This title is already used.");
        }
    }

}
