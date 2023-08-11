package ru.yandex.yandexlavka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class YandexLavkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YandexLavkaApplication.class, args);
    }
}
