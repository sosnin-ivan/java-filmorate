package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FilmService {

    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        checkFilmId(filmId);
        checkUserId(userId);
        filmStorage.getFilm(filmId).getLikes().add(userId);
    }

    public void removeLike(Long filmId, Long userId) {
        checkFilmId(filmId);
        checkUserId(userId);
        filmStorage.getFilm(filmId).getLikes().remove(userId);
    }

    public Collection<Film> getTopFilmsByLikes(int count) {
        return filmStorage.getAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkFilmId(Long filmId) {
        if (filmStorage.getFilm(filmId) == null) {
            throw new NotFoundException(String.format("Фильм c id %d не найден", filmId));
        }
    }

    private void checkUserId(Long userId) {
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException(String.format("Пользователь c id %d не найден", userId));
        }
    }
}