package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.IsAfterDateConstraint;

import java.time.LocalDate;

@Data
public class Film {
    Long id;

    @NotBlank(message = "name - обязательное поле для заполнения")
    String name;

    @Size(max = 200, message = "description - размер до 200 символов")
    String description;

    @IsAfterDateConstraint(message = "releaseDate - должно быть после 28 декабря 1890 года")
    LocalDate releaseDate;

    @Positive(message = "duration - должно быть положительным")
    Integer duration;
}
