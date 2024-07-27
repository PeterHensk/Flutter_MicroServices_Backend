package tech.henskens.stationservice.manager.station;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tech.henskens.stationservice.dto.ChargingPortDto;
import tech.henskens.stationservice.dto.ChargingPortStatusDto;
import tech.henskens.stationservice.dto.StationDto;
import tech.henskens.stationservice.manager.StationManager;
import tech.henskens.stationservice.mapper.StationMapper;
import tech.henskens.stationservice.model.ChargingPort;
import tech.henskens.stationservice.model.ChargingPortStatus;
import tech.henskens.stationservice.model.Station;
import tech.henskens.stationservice.repository.IStationRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StationManagerTest {

    @Mock
    private IStationRepository stationRepository;

    @Mock
    private StationMapper stationMapper;

    @InjectMocks
    private StationManager stationManager;

    @Test
    void findChargingPortTest() {
        String stationIdentifier = "station-1";
        String portIdentifier = "port-1";

        ChargingPort chargingPort = new ChargingPort();
        chargingPort.setPortIdentifier(portIdentifier);

        Station station = new Station();
        station.setStationIdentifier(stationIdentifier);
        station.setChargingPorts(List.of(chargingPort));

        when(stationRepository.findByStationIdentifier(stationIdentifier)).thenReturn(Optional.of(station));

        Optional<ChargingPort> result = stationManager.findChargingPort(stationIdentifier, portIdentifier);

        assertEquals(Optional.of(chargingPort), result);
    }

    @Test
    void createStationTest_NullPointerException() {
        StationDto stationDto = new StationDto();
        stationDto.setImage("image");
        stationDto.setContentType("image/jpeg");
        stationDto.setImageId("imageId");

        StationManager stationManager = new StationManager(stationRepository, null, new StationMapper());

        assertThrows(NullPointerException.class, () -> stationManager.createStation(stationDto));
    }

    @Test
    void getAllStationsTest() {
        Pageable pageable = PageRequest.of(0, 5);

        Station station = new Station();
        station.setStationIdentifier("station-1");
        station.setImageId("imageId");

        List<Station> stationList = List.of(station);
        Page<Station> stationPage = new PageImpl<>(stationList);

        when(stationRepository.findAll(pageable)).thenReturn(stationPage);

        StationDto stationDto = new StationDto();
        stationDto.setStationIdentifier(station.getStationIdentifier());
        stationDto.setImageId(station.getImageId());

        when(stationMapper.stationToDto(station)).thenReturn(stationDto);

        Page<StationDto> result = stationManager.getAllStations(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(station.getStationIdentifier(), result.getContent().getFirst().getStationIdentifier());
        assertEquals(station.getImageId(), result.getContent().getFirst().getImageId());
    }

    @Test
    void updateChargingPortStatusTest() {
        String stationIdentifier = "station-1";
        String portIdentifier = "port-1";
        ChargingPortStatusDto statusDto = new ChargingPortStatusDto();
        statusDto.setStatus(ChargingPortStatus.AVAILABLE);

        doNothing().when(stationRepository).updateChargingPortStatus(stationIdentifier, portIdentifier, statusDto.getStatus());

        stationManager.updateChargingPortStatus(stationIdentifier, portIdentifier, statusDto);

        verify(stationRepository, times(1)).updateChargingPortStatus(stationIdentifier, portIdentifier, statusDto.getStatus());
    }

    @Test
    void getStationTest() {
        String stationIdentifier = "station-1";

        Station station = new Station();
        station.setStationIdentifier(stationIdentifier);

        when(stationRepository.findByStationIdentifier(stationIdentifier)).thenReturn(Optional.of(station));

        StationDto stationDto = new StationDto();
        stationDto.setStationIdentifier(stationIdentifier);

        when(stationMapper.stationToDto(station)).thenReturn(stationDto);

        Optional<StationDto> result = stationManager.getStation(stationIdentifier);

        assertTrue(result.isPresent());
        assertEquals(stationIdentifier, result.get().getStationIdentifier());
    }
}
