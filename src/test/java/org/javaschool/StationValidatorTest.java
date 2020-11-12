package org.javaschool;

import org.javaschool.dto.StationDto;
import org.javaschool.services.interfaces.StationService;
import org.javaschool.validation.StationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StationValidatorTest {

    @InjectMocks
    private StationValidator stationValidator;
    @Mock
    private StationService stationService;

    private static StationDto stationDto;
    private static Errors errors;

    private static final String NAME_VALID = "Station";
    private static final String NAME_INVALID = "s!tat1on-";
    private static final String EMPTY_STRING = "";
    private static final String STRING_LONG = "abcdefghijklnopqrstuvwxyz1234567890";

    @BeforeEach
    public void setUp() {
        stationDto = StationDto.builder().name(NAME_VALID).build();
        errors = new BeanPropertyBindingResult(stationDto, "stationDto");
        when(stationService.getStationByName(anyString())).thenReturn(null);
    }

    @Test
    public void validateStationValid() {
        stationValidator.validate(stationDto, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateStationNameEmpty() {
        stationDto.setName(EMPTY_STRING);
        stationValidator.validate(stationDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }

    @Test
    public void validateStationNameInvalid() {
        stationDto.setName(NAME_INVALID);
        stationValidator.validate(stationDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }

    @Test
    public void validateStationNameLong() {
        stationDto.setName(STRING_LONG);
        stationValidator.validate(stationDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }

    @Test
    public void validateStationNameDuplicate() {
        stationDto.setId(1);
        when(stationService.getStationByName(anyString())).thenReturn(new StationDto());
        stationValidator.validate(stationDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }
}