package tech.henskens.maintenanceservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceDto;
import tech.henskens.maintenanceservice.dto.maintenance.MaintenanceWithStationAndSessionsDto;
import tech.henskens.maintenanceservice.manager.maintenance.IMaintenanceManager;
import tech.henskens.maintenanceservice.manager.session.IUserManager;

@RestController
@RequestMapping({"/maintenance"})
public class MaintenanceController {
    private final IMaintenanceManager maintenanceManager;
    private final IUserManager userManager;

    public MaintenanceController(IMaintenanceManager maintenanceManager, IUserManager userManager) {
        this.maintenanceManager = maintenanceManager;
        this.userManager = userManager;
    }

    @PostMapping
    public ResponseEntity<MaintenanceDto> createMaintenance(@RequestHeader("Authorization") String bearerToken, @RequestBody MaintenanceDto maintenanceDto) {
        this.userManager.authenticatedUser(bearerToken);
        MaintenanceDto createdMaintenance = this.maintenanceManager.createMaintenance(bearerToken, maintenanceDto);
        return new ResponseEntity<>(createdMaintenance, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<MaintenanceDto> updateMaintenance(@RequestHeader("Authorization") String bearerToken, @PathVariable Long id, @RequestBody MaintenanceDto maintenanceDto) {
        this.userManager.authenticatedUser(bearerToken);
        maintenanceDto.setId(id);
        MaintenanceDto updatedMaintenance = this.maintenanceManager.updateMaintenance(bearerToken, maintenanceDto);
        return new ResponseEntity<>(updatedMaintenance, HttpStatus.OK);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteMaintenance(@RequestHeader("Authorization") String bearerToken, @PathVariable Long id) {
        this.userManager.authenticatedUser(bearerToken);
        this.maintenanceManager.deleteMaintenance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({"/report"})
    public ResponseEntity<Page<MaintenanceWithStationAndSessionsDto>> getAllMaintenancesWithStationAndSessions(@RequestHeader("Authorization") String bearerToken, @RequestParam int page, @RequestParam int size) {
        this.userManager.authenticatedUser(bearerToken);
        Pageable pageable = PageRequest.of(page, size);
        Page<MaintenanceWithStationAndSessionsDto> maintenances = this.maintenanceManager.getAllMaintenancesWithStationAndSessions(bearerToken, pageable);
        return new ResponseEntity<>(maintenances, HttpStatus.OK);
    }
}

