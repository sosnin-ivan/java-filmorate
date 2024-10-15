package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zalando.logbook.Logbook;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    Logbook logbook = Logbook.create();
    private final FilmService filmService;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmController(FilmService filmService, FilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("[Request] GET /films");
        Collection<Film> allFilms = filmStorage.getAll();
        log.info("[Response] GET /films: {}", allFilms);
        return allFilms;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmStorage.getFilm(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("[Request] POST /films, body: {}", film);
        Film createdFilm = filmStorage.create(film);
        log.info("[Response] POST /films, body: {} - film created", createdFilm);
        return createdFilm;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("[Request] PUT /films, body: {}", film);
        Film updatedFilm = filmStorage.update(film);
        log.info("[Response] PUT /films, body: {} - film updated", updatedFilm);
        return updatedFilm;
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("[Request] PUT /films/{}/likes/{}", filmId, userId);
        filmService.addLike(filmId, userId);
        log.info("[Response] PUT /films/{}/likes/{} - film liked", filmId, userId);
        return filmStorage.getFilm(filmId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("[Request] DELETE /films/{}/likes/{}", filmId, userId);
        filmService.removeLike(filmId, userId);
        log.info("[Response] DELETE /films/{}/likes/{} - film unliked", filmId, userId);
        return filmStorage.getFilm(filmId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("[Request] GET /films/popular?count={}", count);
        Collection<Film> popularFilms = filmService.getTopFilmsByLikes(count);
        log.info("[Response] GET /films/popular?count={}, body: {} - popular films", count, popularFilms);
        return popularFilms;
    }
}