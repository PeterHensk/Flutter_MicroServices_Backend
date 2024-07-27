package tech.henskens.maintenanceservice.manager.maintenance;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceDto;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceWithStationAndSessionsDto;
import tech.henskens.maintenanceservice.dto.session.SessionCountDto;
import tech.henskens.maintenanceservice.dto.station.ChargingPortStatusDto;
import tech.henskens.maintenanceservice.dto.station.StationDto;
import tech.henskens.maintenanceservice.manager.session.ISessionManager;
import tech.henskens.maintenanceservice.manager.station.IStationManager;
import tech.henskens.maintenanceservice.mapper.MaintenanceMapper;
import tech.henskens.maintenanceservice.model.Maintenance;
import tech.henskens.maintenanceservice.model.Status;
import tech.henskens.maintenanceservice.repository.IMaintenanceRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MaintenanceManagerTest {

    @Mock
    private IMaintenanceRepository maintenanceRepository;

    @Mock
    private MaintenanceMapper maintenanceMapper;

    @Mock
    private IStationManager stationManager;
    
    @Mock
    private ISessionManager sessionManager;

    @InjectMocks
    private MaintenanceManager maintenanceManager;

    @Test
    public void testCreateMaintenance() {
        // Arrange
        String token = "testToken";
        MaintenanceDto maintenanceDto = new MaintenanceDto();
        Maintenance maintenance = new Maintenance();
        Maintenance savedMaintenance = new Maintenance();

        when(maintenanceMapper.toMaintenance(maintenanceDto)).thenReturn(maintenance);
        when(maintenanceRepository.save(maintenance)).thenReturn(savedMaintenance);
        when(maintenanceMapper.toMaintenanceDto(savedMaintenance)).thenReturn(maintenanceDto);

        // Act
        maintenanceManager.createMaintenance(token, maintenanceDto);

        // Assert
        verify(maintenanceRepository, times(1)).save(maintenance);
        verify(maintenanceMapper, times(1)).toMaintenance(maintenanceDto);
        verify(maintenanceMapper, times(1)).toMaintenanceDto(savedMaintenance);
        verify(stationManager, times(4)).updateChargingPortStatus(eq(token), any(ChargingPortStatusDto.class));
    }

    @Test
    public void testUpdateMaintenance() {
        // Arrange
        String token = "testToken";
        MaintenanceDto maintenanceDto = new MaintenanceDto();
        maintenanceDto.setId(1L);
        maintenanceDto.setStatus(Status.COMPLETED);
        Maintenance existingMaintenance = new Maintenance();
        Maintenance updatedMaintenance = new Maintenance();

        when(maintenanceRepository.findById(maintenanceDto.getId())).thenReturn(Optional.of(existingMaintenance));
        when(maintenanceRepository.save(existingMaintenance)).thenReturn(updatedMaintenance);
        when(maintenanceMapper.toMaintenanceDto(updatedMaintenance)).thenReturn(maintenanceDto);

        // Act
        maintenanceManager.updateMaintenance(token, maintenanceDto);

        // Assert
        verify(maintenanceRepository, times(1)).findById(maintenanceDto.getId());
        verify(maintenanceMapper, times(1)).updateMaintenance(existingMaintenance, maintenanceDto);
        verify(maintenanceRepository, times(1)).save(existingMaintenance);
        verify(maintenanceMapper, times(1)).toMaintenanceDto(updatedMaintenance);
        verify(stationManager, times(4)).updateChargingPortStatus(eq(token), any(ChargingPortStatusDto.class));
    }

    @Test
    public void testGetAllMaintenances() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5);
        Maintenance maintenance = new Maintenance();
        MaintenanceDto maintenanceDto = new MaintenanceDto();
        Page<Maintenance> page = new PageImpl<>(Collections.singletonList(maintenance), pageable, 1);

        when(maintenanceRepository.findAll(pageable)).thenReturn(page);
        when(maintenanceMapper.toMaintenanceDto(maintenance)).thenReturn(maintenanceDto);

        // Act
        Page<MaintenanceDto> result = maintenanceManager.getAllMaintenances(pageable);

        // Assert
        verify(maintenanceRepository, times(1)).findAll(pageable);
        verify(maintenanceMapper, times(1)).toMaintenanceDto(maintenance);
        assertEquals(1, result.getTotalElements());
        assertEquals(maintenanceDto, result.getContent().getFirst());
    }

    @Test
    public void testDeleteMaintenance() {
        // Arrange
        Long id = 1L;
        // Act
        maintenanceManager.deleteMaintenance(id);
        // Assert
        verify(maintenanceRepository, times(1)).deleteById(id);
    }
}
