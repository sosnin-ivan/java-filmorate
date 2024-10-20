package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.IsAfterDateConstraint;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {
    private Long id;

    @NotBlank(message = "name - обязательное поле для заполнения")
    private String name;

    @Size(max = 200, message = "description - размер до 200 символов")
    private String description;

    @IsAfterDateConstraint(message = "releaseDate - должно быть после 28 декабря 1890 года")
    private LocalDate releaseDate;

    @Positive(message = "duration - должно быть положительным")
    private Integer duration;

    @JsonIgnore
    private Set<Long> likes = new HashSet<>(List.of());
}