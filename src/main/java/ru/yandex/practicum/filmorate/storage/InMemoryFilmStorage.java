package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
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

    public Collection<Film> getAllFilms() {
        return films.values();
    }

    public Film getFilm(Long filmId) {
        return films.get(filmId);
    }

    public Film createFilm(Film film) {
        film.setId(++currentId);
        films.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }
}