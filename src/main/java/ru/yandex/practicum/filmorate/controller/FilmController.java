package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("[Request] GET /films");
        Collection<Film> allFilms = filmService.getAllFilms();
        log.info("[Response] GET /films: {}", allFilms);
        return allFilms;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        log.info("[Request] GET /films/{} ", id);
        Film film = filmService.getFilm(id);
        log.info("[Response] GET /films/{}, body: {}", id, film);
        return film;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("[Request] POST /films, body: {}", film);
        Film createdFilm = filmService.createFilm(film);
        log.info("[Response] POST /films, body: {} - film created", createdFilm);
        return createdFilm;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("[Request] PUT /films, body: {}", film);
        Film updatedFilm = filmService.updateFilm(film);
        log.info("[Response] PUT /films, body: {} - film updated", updatedFilm);
        return updatedFilm;
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("[Request] PUT /films/{}/likes/{}", filmId, userId);
        filmService.addLike(filmId, userId);
        log.info("[Response] PUT /films/{}/likes/{} - film liked", filmId, userId);
        return filmService.getFilm(filmId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        log.info("[Request] DELETE /films/{}/likes/{}", filmId, userId);
        filmService.removeLike(filmId, userId);
        log.info("[Response] DELETE /films/{}/likes/{} - film unliked", filmId, userId);
        return filmService.getFilm(filmId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("[Request] GET /films/popular?count={}", count);
        Collection<Film> popularFilms = filmService.getTopFilmsByLikes(count);
        log.info("[Response] GET /films/popular?count={}, body: {} - popular films", count, popularFilms);
        return popularFilms;
    }
}