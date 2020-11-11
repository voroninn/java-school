package org.javaschool;

import org.javaschool.dto.StationDto;
import org.javaschool.exception.IllegalOperationException;
import org.javaschool.services.impl.StationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StationServiceTest {

    @InjectMocks
    private StationServiceImpl stationService;

    @Test
    public void testDeleteStationException() {
        StationDto stationDto = new StationDto();
        stationDto.setId(18);

        assertThrows(IllegalOperationException.class, () -> {
            stationService.deleteStation(stationDto);
        });
    }
}