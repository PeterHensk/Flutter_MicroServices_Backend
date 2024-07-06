package tech.henskens.maintenanceservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceDto;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceWithStationAndSessionsDto;
import tech.henskens.maintenanceservice.manager.maintenance.IMaintenanceManager;

@RestController
@RequestMapping({"/maintenance"})
public class MaintenanceController {
    private final IMaintenanceManager maintenanceManager;

    public MaintenanceController(IMaintenanceManager maintenanceManager) {
        this.maintenanceManager = maintenanceManager;
    }

    @PostMapping
    public ResponseEntity<MaintenanceDto> createMaintenance(@RequestBody MaintenanceDto maintenanceDto) {
        MaintenanceDto createdMaintenance = this.maintenanceManager.createMaintenance(maintenanceDto);
        return new ResponseEntity<>(createdMaintenance, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<MaintenanceDto> updateMaintenance(@PathVariable Long id, @RequestBody MaintenanceDto maintenanceDto) {
        maintenanceDto.setId(id);
        MaintenanceDto updatedMaintenance = this.maintenanceManager.updateMaintenance(maintenanceDto);
        return new ResponseEntity<>(updatedMaintenance, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<MaintenanceDto>> getAllMaintenances(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MaintenanceDto> maintenances = this.maintenanceManager.getAllMaintenances(pageable);
        return new ResponseEntity<>(maintenances, HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        this.maintenanceManager.deleteMaintenance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({"/report"})
    public ResponseEntity<Page<MaintenanceWithStationAndSessionsDto>> getAllMaintenancesWithStationAndSessions(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MaintenanceWithStationAndSessionsDto> maintenances = this.maintenanceManager.getAllMaintenancesWithStationAndSessions(pageable);
        return new ResponseEntity<>(maintenances, HttpStatus.OK);
    }
}

