package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private Map<Integer, Film> filmList = new HashMap<>();
    private int idCounter = 1;

    @Override
    public Film add(Film film) {
        if (filmList.containsValue(film)) {
            log.warn("Ошибка добавления фильма.Такой фильм уже существует");
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Такой фильм уже существует");
        } else if (Validator.isValidFilm(film)) {
            film.setId(idCounter++);
            filmList.put(film.getId(), film);
            log.info("Успешно добален фильм с ID" + film.getId());
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!filmList.containsKey(film.getId())) {
            log.warn("Ошибка обновления фильма. Такого фильма не существует");
            throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Такого фильма не существует");
        } else if (Validator.isValidFilm(film)) {
            filmList.put(film.getId(), film);
            log.info("Успешно обновлен фильм с ID" + film.getId());
        }
        return film;
    }

    @Override
    public List<Film> getAll() {
        return List.copyOf(filmList.values());
    }

    @Override
    public void delete(Film film) {
        filmList.remove(film.getId());
    }
}