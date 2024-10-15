package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films;
    private long currentId;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
        currentId = 0L;
    }

    public Collection<Film> getAll() {
        return films.values();
    }

    public Film getFilm(Long filmId) {
        checkFilmId(filmId);
        return films.get(filmId);
    }

    public Film create(Film film) {
        film.setId(++currentId);
        films.put(film.getId(), film);
        return film;
    }

    public Film update(Film film) {
        checkFilmId(film.getId());
        films.put(film.getId(), film);
        return film;
    }

    private void checkFilmId(Long filmId) {
        if (films.get(filmId) == null) {
            throw new NotFoundException(String.format("Фильм c id %d не найден", filmId));
        }
    }
}