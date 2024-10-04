package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private long currentId = 0L;

    @GetMapping
    public Collection<User> getUsers() {
        log.info("[Request] GET /users");
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("[Request] POST /users, body: {}", user);
        user.setId(++currentId);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("[Response] POST /users, body: {} - user created", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("[Request] PUT /users, body: {}", user);
        if (!users.containsKey(user.getId())) {
            throw new IllegalArgumentException("Пользователь с id " + user.getId() + " не найден");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("[Response] PUT /users, body: {} - user updated", user);
        return user;
    }
}
