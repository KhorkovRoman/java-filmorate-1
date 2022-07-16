package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private final Film film = new Film("Film name",
            "Description",
            1,
            LocalDate.now(),
            200,
            List.of(new Genre(1, "Комедия")),
            new MpaRating(1, "G"));

    @Test
    @Order(1)
    public void addFilm() {
        filmDbStorage.add(film);
        assertEquals(film, filmDbStorage.getFilmById(1));
    }

    @Test
    @Order(2)
    public void getFilmById() {
        assertEquals(film, filmDbStorage.getFilmById(1));
    }

    @Test
    @Order(3)
    public void getAllFilms() {
        assertEquals(List.of(film), filmDbStorage.getAll());
    }

    @Test
    @Order(4)
    public void updateFilm() {
        film.setName("New name");
        filmDbStorage.update(film);
        assertEquals(film, filmDbStorage.getFilmById(1));
    }

    @Test
    @Order(5)
    public void deleteFilm() {
        filmDbStorage.delete(film);
        assertTrue(filmDbStorage.getAll().isEmpty());
    }
}