package com.cosmin.bookstoreback;

import com.cosmin.bookstoreback.models.Availability;
import com.cosmin.bookstoreback.models.Book;
import com.cosmin.bookstoreback.models.Language;
import com.cosmin.bookstoreback.repositories.BookRepository;
import com.cosmin.bookstoreback.utils.NumberGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Year;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreBackApplication.class)
class BookRepositoryTest {

    private final BookRepository bookRepository;
    private final NumberGenerator numberGenerator;

    @Autowired
    public BookRepositoryTest(BookRepository bookRepository, NumberGenerator numberGenerator) {
        this.bookRepository = bookRepository;
        this.numberGenerator = numberGenerator;
    }

    @Test
    public void repositoryTest() {
        assertEquals(Integer.valueOf(0), bookRepository.countBooks());
        assertEquals(0, bookRepository.findAllByOrderByTitleAsc().size());

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Alchimistul");
        book.setPublicationYear((short) Year.now().getValue());
        book.setDescription("Cartea Alchimistul de Paolo Coelho");
        book.setCost(35.00);
        book.setNumberOfPages(220);
        book.setLanguage(Language.ROMANIAN);
        book.setImageURL("https://www.google.ro/alchimistul.jpg");
        book.setIsbn(numberGenerator.generateNumber());
        book.setAvailability(Availability.UNKNOWN);
        bookRepository.save(book);

        Book bookFound = bookRepository.findBookById(book.getId());
        Book nullBook = bookRepository.findBookById(5L);

        assertNotNull(bookFound);
        assertNull(nullBook);

        assertEquals(Integer.valueOf(1), bookRepository.countBooks());
        assertEquals("Alchimistul", bookFound.getTitle());

        assertNotEquals(2, bookRepository.findAllByOrderByTitleAsc().size());
        assertNotEquals(Integer.valueOf(2), bookRepository.countBooks());
    }

}
