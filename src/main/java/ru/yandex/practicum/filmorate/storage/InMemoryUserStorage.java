package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
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

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUser(Long userId) {
        return users.get(userId);
    }

    public User createUser(User user) {
        user.setId(++currentId);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }
}