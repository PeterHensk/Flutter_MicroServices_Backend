package tech.henskens.stationservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.henskens.stationservice.dto.ChargingPortDto;
import tech.henskens.stationservice.dto.ChargingPortStatusDto;
import tech.henskens.stationservice.dto.ImageUploadDto;
import tech.henskens.stationservice.dto.StationDto;
import tech.henskens.stationservice.manager.IS3Manager;
import tech.henskens.stationservice.manager.IStationManager;
import tech.henskens.stationservice.manager.Session.IUserManager;
import tech.henskens.stationservice.model.ChargingPortStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/station")
public class StationController {
    private final IStationManager stationManager;
    private final IS3Manager s3Manager;
    private final IUserManager userManager;

    public StationController(IStationManager stationManager, IS3Manager s3Manager, IUserManager userManager) {
        this.stationManager = stationManager;
        this.s3Manager = s3Manager;
        this.userManager = userManager;
    }

    @PostMapping
    public ResponseEntity<Void> createStation(@RequestBody StationDto stationDto) {
        stationManager.createStation(stationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<StationDto>> getAllStations(@RequestHeader("Authorization") String bearerToken, Pageable pageable) {
        this.userManager.authenticatedUser(bearerToken);
        Page<StationDto> stations = stationManager.getAllStations(pageable);
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @GetMapping("/{stationIdentifier}")
    public ResponseEntity<StationDto> getStation(@RequestHeader("Authorization") String bearerToken, @PathVariable String stationIdentifier) {
        this.userManager.authenticatedUser(bearerToken);
        Optional<StationDto> station = stationManager.getStation(stationIdentifier);
        return station.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{stationIdentifier}/port/{portIdentifier}")
    public ResponseEntity<ChargingPortDto> getChargingPort(@RequestHeader("Authorization") String bearerToken, @PathVariable String stationIdentifier, @PathVariable String portIdentifier) {
        this.userManager.authenticatedUser(bearerToken);
        Optional<ChargingPortDto> chargingPort = stationManager.getChargingPort(stationIdentifier, portIdentifier);
        return chargingPort.map(port -> new ResponseEntity<>(port, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{stationIdentifier}/port/{portIdentifier}")
    public ResponseEntity<Void> updateChargingPortStatus(@RequestHeader("Authorization") String bearerToken,@PathVariable String stationIdentifier, @PathVariable String portIdentifier, @RequestBody ChargingPortStatusDto statusDto) {
        this.userManager.authenticatedUser(bearerToken);
        stationManager.updateChargingPortStatus(stationIdentifier, portIdentifier, statusDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllStatuses() {
        List<String> statuses = Arrays.stream(ChargingPortStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    @PostMapping("/upload-image")
    public String uploadImage(@RequestBody ImageUploadDto imageUploadDto) {
        return s3Manager.uploadImage(imageUploadDto.getImage(), imageUploadDto.getContentType());
    }
}
