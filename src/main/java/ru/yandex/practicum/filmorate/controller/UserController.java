package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zalando.logbook.Logbook;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    Logbook logbook = Logbook.create();
    private final UserService userService;
    private final UserStorage userStorage;

    @Autowired
    public UserController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("[Request] GET /users");
        Collection<User> allUsers = userStorage.getAll();
        log.info("[Response] GET /users, body: {}", allUsers);
        return allUsers;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("[Request] GET /user/{}", id);
        User user = userStorage.getUser(id);
        log.info("[Response] GET /user/{}, body: {} - user found", id, user);
        return user;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("[Request] POST /users, body: {}", user);
        User createdUser = userStorage.create(user);
        log.info("[Response] POST /users, body: {} - user created", createdUser);
        return createdUser;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("[Request] PUT /users, body: {}", user);
        User updatedUser = userStorage.update(user);
        log.info("[Response] PUT /users, body: {} - user updated", updatedUser);
        return updatedUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("[Request] PUT /users/{}/friends/{}", id, friendId);
        User user = userService.addFriend(id, friendId);
        log.info("[Response] PUT /users/{}/friends/{}, body: {} - friend added", id, friendId, user);
        return userStorage.getUser(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.removeFriend(id, friendId);
        return userStorage.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getAllFriends(@PathVariable Long id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}