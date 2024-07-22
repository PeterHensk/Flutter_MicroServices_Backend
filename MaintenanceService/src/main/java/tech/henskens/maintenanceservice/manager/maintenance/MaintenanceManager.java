package tech.henskens.maintenanceservice.manager.maintenance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceDto;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceWithStationAndSessionsDto;
import tech.henskens.maintenanceservice.dto.session.SessionCountDto;
import tech.henskens.maintenanceservice.dto.station.StationDto;
import tech.henskens.maintenanceservice.manager.session.ISessionManager;
import tech.henskens.maintenanceservice.manager.station.IStationManager;
import tech.henskens.maintenanceservice.mapper.MaintenanceMapper;
import tech.henskens.maintenanceservice.model.Maintenance;
import tech.henskens.maintenanceservice.repository.IMaintenanceRepository;
import tech.henskens.maintenanceservice.dto.station.ChargingPortStatusDto;

@Service
public class MaintenanceManager implements IMaintenanceManager {
    private final IMaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final IStationManager stationManager;
    private final ISessionManager sessionManager;

    public MaintenanceManager(IMaintenanceRepository maintenanceRepository, MaintenanceMapper maintenanceMapper, IStationManager stationManager, ISessionManager sessionManager) {
        this.maintenanceRepository = maintenanceRepository;
        this.maintenanceMapper = maintenanceMapper;
        this.stationManager = stationManager;
        this.sessionManager = sessionManager;
    }

    public MaintenanceDto createMaintenance(String token, MaintenanceDto maintenanceDto) {
        Maintenance maintenance = this.maintenanceMapper.toMaintenance(maintenanceDto);
        Maintenance savedMaintenance = this.maintenanceRepository.save(maintenance);
        ChargingPortStatusDto chargingPortStatusDto = MaintenanceMapper.toChargingPortStatusDtoFromSession(maintenance, "DAMAGED");

        for (int i = 1; i <= 4; i++) {
            chargingPortStatusDto.setPortIdentifier(Integer.toString(i));
            this.stationManager.updateChargingPortStatus(token, chargingPortStatusDto);
        }
        return this.maintenanceMapper.toMaintenanceDto(savedMaintenance);
    }

    public MaintenanceDto updateMaintenance(MaintenanceDto maintenanceDto) {
        Maintenance existingMaintenance = this.maintenanceRepository.findById(maintenanceDto.getId()).orElseThrow(() -> new NoSuchElementException("Maintenance with id " + maintenanceDto.getId() + " not found"));
        this.maintenanceMapper.updateMaintenance(existingMaintenance, maintenanceDto);
        Maintenance updatedMaintenance = this.maintenanceRepository.save(existingMaintenance);
        return this.maintenanceMapper.toMaintenanceDto(updatedMaintenance);
    }

    public Page<MaintenanceDto> getAllMaintenances(Pageable pageable) {
        Page<Maintenance> page = this.maintenanceRepository.findAll(pageable);
        MaintenanceMapper mapper = this.maintenanceMapper;
        Objects.requireNonNull(mapper);
        return page.map(mapper::toMaintenanceDto);
    }

    public void deleteMaintenance(Long id) {
        this.maintenanceRepository.deleteById(id);
    }

    public Page<MaintenanceWithStationAndSessionsDto> getAllMaintenancesWithStationAndSessions(String token, Pageable pageable) {
        Page<Maintenance> maintenances = this.maintenanceRepository.findAll(pageable);
        List<MaintenanceWithStationAndSessionsDto> dtos = maintenances.stream().map((maintenance) -> {
            ResponseEntity<StationDto> station = this.stationManager.getStation(token, maintenance.getStationIdentifier());
            LocalDateTime startDate = maintenance.getCreationDate();
            Maintenance nextMaintenance = this.maintenanceRepository.findFirstByStationIdentifierAndCreationDateAfterOrderByCreationDate(maintenance.getStationIdentifier(), maintenance.getMaintenanceDate());
            LocalDateTime endDate = nextMaintenance != null ? nextMaintenance.getCreationDate() : LocalDateTime.now();
            Optional<SessionCountDto> totalCompletedSessionsDto = this.sessionManager.getSessionCount(token, maintenance.getStationIdentifier(), startDate, endDate);
            Integer totalCompletedSessions = totalCompletedSessionsDto.isPresent() ? totalCompletedSessionsDto.get().getCount() : 0;
            return this.maintenanceMapper.toMaintenanceWithStationAndSessionsDto(maintenance, station.getBody(), totalCompletedSessions);
        }).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, maintenances.getTotalElements());
    }
}
