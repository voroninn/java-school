package org.javaschool.validation;

import org.apache.commons.lang3.time.DateUtils;
import org.javaschool.dto.TicketDto;
import org.javaschool.services.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class TicketValidator implements Validator {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public boolean supports(Class<?> aClass) {
        return TicketDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        TicketDto ticket = (TicketDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "departureStation", "NotEmpty.ticketForm.departureStation");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "arrivalStation", "NotEmpty.ticketForm.arrivalStation");

        if (!ticket.getDepartureStation().equals("") &&
                ticket.getDepartureStation().equals(ticket.getArrivalStation())) {
            errors.rejectValue("departureStation", "Diff.ticketForm.stations");
            errors.rejectValue("arrivalStation", "Diff.ticketForm.stations");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "NotEmpty.ticketForm.date");

        if (!ticket.getDate().equals("")) {
            if (!ticket.getDate().matches("^(?:(?:31(\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\.)" +
                    "(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\.)0?2\\3" +
                    "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])" +
                    "00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
                errors.rejectValue("date", "Format.ticketForm.date");
            } else if (!DateUtils.isSameDay(scheduleService.convertStringtoDate(ticket.getDate()), new Date()) &&
                    scheduleService.convertStringtoDate(ticket.getDate()).before(new Date())) {
                errors.rejectValue("date", "Past.ticketForm.date");
            }
        }
    }
}