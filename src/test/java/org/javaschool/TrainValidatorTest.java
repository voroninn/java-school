package org.javaschool;

import org.javaschool.dto.TrackDto;
import org.javaschool.dto.TrainDto;
import org.javaschool.services.interfaces.TrainService;
import org.javaschool.validation.TrainValidator;
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
public class TrainValidatorTest {

    @InjectMocks
    private TrainValidator trainValidator;
    @Mock
    private TrainService trainService;

    private static TrainDto trainDto;
    private static Errors errors;

    private static final String NAME_VALID = "Train XI";
    private static final String NAME_INVALID = "Train11";
    private static final String NAME_LONG = "abcdefghijklnopqrstuvwxyz XXXXX";
    private static final String EMPTY_STRING = "";
    private static final String NUMBER_INVALID = "1d3";

    @BeforeEach
    public void setUp() {
        trainDto = TrainDto.builder()
                .track(TrackDto.builder().id(1).build())
                .name(NAME_VALID)
                .capacity(10)
                .build();
        errors = new BeanPropertyBindingResult(trainDto, "trainDto");
        when(trainService.getTrainByName(anyString())).thenReturn(null);
    }

    @Test
    public void validateTrainValid() {
        trainValidator.validate(trainDto, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void validateTrainTrackIdEmpty() {
        trainDto.getTrack().setId(0);
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("track.id"));
    }

    @Test
    public void validateTrainTrackIdLong() {
        trainDto.getTrack().setId(13);
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("track.id"));
    }

    @Test
    public void validateTrainTrackIdInvalid() {
        trainDto.getTrack().setId(6);
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("track.id"));
    }

    @Test
    public void validateTrainNameEmpty() {
        trainDto.setName(EMPTY_STRING);
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }

    @Test
    public void validateTrainNameInvalid() {
        trainDto.setName(NAME_INVALID);
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }

    @Test
    public void validateTrainNameLong() {
        trainDto.setName(NAME_LONG);
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }

    @Test
    public void validateTrainNameDuplicate() {
        trainDto.setId(1);
        when(trainService.getTrainByName(anyString())).thenReturn(new TrainDto());
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("name"));
    }

    @Test
    public void validateTrainCapacityEmpty() {
        trainDto.setCapacity(0);
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("capacity"));
    }

    @Test
    public void validateStationCapacityInvalid() {
        trainDto.setCapacity(100);
        trainValidator.validate(trainDto, errors);
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("capacity"));
    }
}