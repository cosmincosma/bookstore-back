package com.cosmin.bookstoreback.repositories;

import com.cosmin.bookstoreback.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);

    Book findBookByTitle(String title);

    List<Book> findAllByOrderByTitleAsc();

    @Query("SELECT COUNT(b) FROM Book b")
    Integer countBooks();

}
