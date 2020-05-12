package com.cosmin.bookstoreback;

import com.cosmin.bookstoreback.dtos.BookDto;
import com.cosmin.bookstoreback.models.Language;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Year;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreBackApplication.class)
public class BookValidationTest {

    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void shouldHaveNoViolations() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Alchimistul");
        bookDto.setDescription("Descriere");
        bookDto.setCost(100.00);
        bookDto.setNumberOfPages(200);
        bookDto.setPublicationYear(Year.now().getValue());
        bookDto.setImageURL("https://www.google.ro/image.jpg");
        bookDto.setLanguage(Language.ROMANIAN);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldDetectInvalidTitle() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("A");
        bookDto.setDescription("Descriere");
        bookDto.setCost(100.00);
        bookDto.setNumberOfPages(220);
        bookDto.setPublicationYear(Year.now().getValue());
        bookDto.setImageURL("https://www.google.ro/image.jpg");
        bookDto.setLanguage(Language.ROMANIAN);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDto);
        ConstraintViolation<BookDto> violation = violations.iterator().next();

        assertFalse(violations.isEmpty());
        assertEquals("Title does not respect the field validation rules (2-50 characters).",
                violation.getMessage());
        assertEquals("title", violation.getPropertyPath().toString());
    }

    @Test
    public void shouldDetectMultipleViolations() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("a");
        bookDto.setDescription("d");
        bookDto.setCost(225.00);
        bookDto.setPublicationYear(Year.now().getValue());
        bookDto.setImageURL("https://www.google.ro/image.jpg");
        bookDto.setLanguage(Language.ROMANIAN);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDto);

        assertTrue(violations.size() > 1);
    }

}
