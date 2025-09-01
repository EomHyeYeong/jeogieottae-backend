package com.example.mini.global.validation;

import com.example.mini.domain.accommodation.model.request.AccommodationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckInOutValidator implements ConstraintValidator<CheckInOutValidation, AccommodationRequestDto> {
    @Override
    public boolean isValid(AccommodationRequestDto dto, ConstraintValidatorContext context) {

        if (dto.checkIn() == null || dto.checkOut() == null) {
            return true; // @NotNull에서 잡도록 처리
        }

        if (dto.checkOut().isBefore(dto.checkIn())) {
            context.disableDefaultConstraintViolation();
            // 어노테이션에 정의된 message() 가져오기
            String message = context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("checkOut")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
