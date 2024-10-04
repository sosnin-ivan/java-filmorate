package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotation.IsAfterDateConstraint;

import java.time.LocalDate;

public class IsAfterDateValidator implements ConstraintValidator<IsAfterDateConstraint, LocalDate> {
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1890, 12, 28);

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext constraintValidatorContext) {
        return releaseDate.isAfter(MIN_RELEASE_DATE);
    }
}
