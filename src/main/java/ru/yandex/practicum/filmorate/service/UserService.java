package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(Long userId, Long friendId) {
        checkUserId(userId);
        checkUserId(friendId);
        userStorage.getUser(userId).getFriends().add(friendId);
        userStorage.getUser(friendId).getFriends().add(userId);
        return userStorage.getUser(userId);
    }

    public void removeFriend(Long userId, Long friendId) {
        checkUserId(userId);
        checkUserId(friendId);
        userStorage.getUser(userId).getFriends().remove(friendId);
        userStorage.getUser(friendId).getFriends().remove(userId);
    }

    public Collection<User> getAllFriends(Long userId) {
        checkUserId(userId);
        return userStorage.getUser(userId).getFriends().stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(Long userId, Long otherId) {
        checkUserId(userId);
        checkUserId(otherId);
        User user1 = userStorage.getUser(userId);
        User user2 = userStorage.getUser(otherId);

        Collection<User> commonFriends = new ArrayList<>();
        for (Long id : user1.getFriends()) {
            if (user2.getFriends().contains(id)) {
                commonFriends.add(userStorage.getUser(id));
            }
        }
        return commonFriends;
    }

    private void checkUserId(Long userId) {
        if (userStorage.getUser(userId) == null) {
            throw new NotFoundException(String.format("Пользователь c id %d не найден", userId));
        }
    }
}