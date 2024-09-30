package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();
    private long currentId = 0L;

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("[Request] GET /films");
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("[Request] POST /films, body: {}", film);
        film.setId(++currentId);
        films.put(film.getId(), film);
        log.info("[Response] POST /films, body: {} - film created", film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("[Request] PUT /films, body: {}", film);
        if (!films.containsKey(film.getId())) {
            throw new IllegalArgumentException("Фильм с id " + film.getId() + " не найден");
        }
        films.put(film.getId(), film);
        log.info("[Response] PUT /films, body: {} - film updated", film);
        return film;
    }
}
