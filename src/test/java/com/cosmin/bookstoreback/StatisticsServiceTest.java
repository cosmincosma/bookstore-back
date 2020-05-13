package com.cosmin.bookstoreback;

import com.cosmin.bookstoreback.models.Availability;
import com.cosmin.bookstoreback.models.Book;
import com.cosmin.bookstoreback.models.Language;
import com.cosmin.bookstoreback.models.Statistics;
import com.cosmin.bookstoreback.repositories.BookRepository;
import com.cosmin.bookstoreback.services.StatisticsService;
import com.cosmin.bookstoreback.utils.NumberGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreBackApplication.class)
public class StatisticsServiceTest {

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    NumberGenerator numberGenerator;

    @Test
    public void shouldRetrieveStatistics() throws Exception {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Alchimistul");
        book1.setPublicationYear(2011);
        book1.setDescription("Cartea Alchimistul de Paolo Coelho");
        book1.setCost(35.00);
        book1.setNumberOfPages(220);
        book1.setLanguage(Language.ROMANIAN);
        book1.setImageURL("https://www.google.ro/alchimistul.jpg");
        book1.setIsbn(numberGenerator.generateNumber());
        book1.setAvailability(Availability.NOT_AVAILABLE);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Test1");
        book2.setPublicationYear(2011);
        book2.setDescription("Test1");
        book2.setCost(45.00);
        book2.setNumberOfPages(120);
        book2.setLanguage(Language.ENGLISH);
        book2.setImageURL("https://www.google.ro/alchimistul.jpg");
        book2.setIsbn(numberGenerator.generateNumber());
        book2.setAvailability(Availability.AVAILABLE);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setId(3L);
        book3.setTitle("Test2");
        book3.setPublicationYear(2021);
        book3.setDescription("Test2");
        book3.setCost(45.00);
        book3.setNumberOfPages(220);
        book3.setLanguage(Language.ENGLISH);
        book3.setImageURL("https://www.google.ro/alchimistul.jpg");
        book3.setIsbn(numberGenerator.generateNumber());
        book3.setAvailability(Availability.AVAILABLE);
        bookRepository.save(book3);

        Statistics statistics = statisticsService.createStatistics();

        assertEquals(3, statistics.getNumberOfBooks().intValue());
        assertEquals(2, statistics.getNumberOfAvailableBooks().intValue());
    }
}
