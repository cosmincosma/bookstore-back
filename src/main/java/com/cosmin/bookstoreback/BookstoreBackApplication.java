package com.cosmin.bookstoreback;

import com.cosmin.bookstoreback.models.Language;
import com.cosmin.bookstoreback.utils.EnumUtils;
import com.cosmin.bookstoreback.utils.IsbnGenerator;
import com.cosmin.bookstoreback.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreBackApplication.class, args);
    }

}
