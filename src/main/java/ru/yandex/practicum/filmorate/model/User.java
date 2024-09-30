package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    Long id;

    @NotBlank(message = "email - обязательное поле для заполнения")
    @Email(message = "email - не корректный email")
    String email;

    @NotBlank(message = "login - обязательное поле для заполнения")
    @Pattern(regexp = "^\\S+$", message = "login - поле не должно содержать пробелы")
    String login;

    String name;

    @Past(message = "birthday - не может быть в будущем")
    LocalDate birthday;
}
