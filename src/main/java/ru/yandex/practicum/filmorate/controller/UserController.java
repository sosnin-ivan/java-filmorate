package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("[Request] GET /users");
        Collection<User> allUsers = userService.getAllUsers();
        log.info("[Response] GET /users, body: {}", allUsers);
        return allUsers;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("[Request] GET /user/{}", id);
        User user = userService.getUser(id);
        log.info("[Response] GET /user/{}, body: {} - user found", id, user);
        return user;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("[Request] POST /users, body: {}", user);
        User createdUser = userService.createUser(user);
        log.info("[Response] POST /users, body: {} - user created", createdUser);
        return createdUser;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("[Request] PUT /users, body: {}", user);
        User updatedUser = userService.updateUser(user);
        log.info("[Response] PUT /users, body: {} - user updated", updatedUser);
        return updatedUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("[Request] PUT /users/{}/friends/{}", id, friendId);
        User user = userService.addFriend(id, friendId);
        log.info("[Response] PUT /users/{}/friends/{}, body: {} - friend added", id, friendId, user);
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("[Request] DELETE /users/{}/friends/{}", id, friendId);
        userService.removeFriend(id, friendId);
        log.info("[Response] DELETE /users/{}/friends/{}, body: {} - friend removed", id, friendId, id);
        return userService.getUser(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getAllFriends(@PathVariable Long id) {
        log.info("[Request] GET /users/{}/friends", id);
        Collection<User> allFriends = userService.getAllFriends(id);
        log.info("[Response] GET /users/{}/friends, body: {}", id, allFriends);
        return allFriends;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("[Request] GET /users/{}/friends/common/{}", id, otherId);
        Collection<User> commonFriends = userService.getCommonFriends(id, otherId);
        log.info("[Response] GET /users/{}/friends/common/{}, body: {}", id, otherId, commonFriends);
        return commonFriends;
    }
}