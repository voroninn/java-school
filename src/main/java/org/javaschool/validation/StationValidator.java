package org.javaschool.validation;

import lombok.RequiredArgsConstructor;
import org.javaschool.dto.StationDto;
import org.javaschool.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StationValidator implements Validator {

    private final StationService stationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return StationDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        StationDto station = (StationDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");

        if (!station.getName().matches("^[a-zA-Z]+")) {
            errors.rejectValue("name", "Format.stationForm.name");
        }
        if (station.getName().length() > 20) {
            errors.rejectValue("name", "Size.stationForm.name");
        }

        if (stationService.getStationByName(station.getName()) != null &&
                stationService.getStationByName(station.getName()).getId() != station.getId()) {
            errors.rejectValue("name", "Duplicate.stationForm.name");
        }
    }
}