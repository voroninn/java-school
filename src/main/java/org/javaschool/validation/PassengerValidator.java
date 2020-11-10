package org.javaschool.validation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.javaschool.dto.PassengerDto;
import org.javaschool.services.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PassengerValidator implements Validator {

    private final ScheduleService scheduleService;

    @Override
    public boolean supports(Class<?> aClass) {
        return PassengerDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        PassengerDto passenger = (PassengerDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");

        if (!passenger.getFirstName().trim().equals("")) {
            if (!passenger.getFirstName().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
                errors.rejectValue("firstName", "Format.passengerForm.firstName");
            }
            if (passenger.getFirstName().length() > 20) {
                errors.rejectValue("firstName", "Size.passengerForm.firstName");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");

        if (!passenger.getFirstName().trim().equals("")) {
            if (!passenger.getLastName().matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
                errors.rejectValue("lastName", "Format.passengerForm.lastName");
            }
            if (passenger.getLastName().length() > 20) {
                errors.rejectValue("lastName", "Size.passengerForm.lastName");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "NotEmpty");

        if (!passenger.getBirthDate().trim().equals("")) {
            if (!passenger.getBirthDate().matches("^(?:(?:31(\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\.)" +
                    "(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\.)0?2\\3" +
                    "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])" +
                    "00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
                errors.rejectValue("birthDate", "Format.passengerForm.birthDate");
            } else if (DateUtils.addYears(scheduleService.convertStringtoDate(passenger.getBirthDate()),
                    16).after(new Date())) {
                errors.rejectValue("birthDate", "Young.passengerForm.birthDate");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passportNumber", "NotEmpty");

        if (!String.valueOf(passenger.getPassportNumber()).trim().equals("")) {
            if (!String.valueOf(passenger.getPassportNumber()).matches("^\\d+$")) {
                errors.rejectValue("passportNumber", "Format.passengerForm.passportNumber");
            }
            if (String.valueOf(passenger.getPassportNumber()).length() != 8) {
                errors.rejectValue("passportNumber", "Length.passengerForm.passportNumber");
            }
        }
    }
}