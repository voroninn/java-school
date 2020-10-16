package org.javaschool.validation;

import org.javaschool.entities.TicketEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TicketValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TicketEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        TicketEntity ticket = (TicketEntity) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "departureStation", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arrivalStation", "NotEmpty");

        if (ticket.getDepartureStation().equals(ticket.getArrivalStation())) {
            errors.rejectValue("departureStation", "Diff.ticketForm.stations");
            errors.rejectValue("arrivalStation", "Diff.ticketForm.stations");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "NotEmpty");
    }
}
