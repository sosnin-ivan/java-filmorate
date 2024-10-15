package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users;
    private long currentId;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        currentId = 0L;
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public User getUser(Long userId) {
        checkUserId(userId);
        return users.get(userId);
    }

    public User create(User user) {
        user.setId(++currentId);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    public User update(User user) {
        checkUserId(user.getId());
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    private void checkUserId(Long userId) {
        if (users.get(userId) == null) {
            throw new NotFoundException(String.format("Пользователь c id %d не найден", userId));
        }
    }
}