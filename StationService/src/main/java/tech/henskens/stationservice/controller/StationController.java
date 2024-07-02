package tech.henskens.stationservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.henskens.stationservice.dto.ChargingPortDto;
import tech.henskens.stationservice.dto.ChargingPortStatusDto;
import tech.henskens.stationservice.dto.ImageUploadDto;
import tech.henskens.stationservice.dto.StationDto;
import tech.henskens.stationservice.manager.IS3Manager;
import tech.henskens.stationservice.manager.IStationManager;
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

    public StationController(IStationManager stationManager, IS3Manager s3Manager) {
        this.stationManager = stationManager;
        this.s3Manager = s3Manager;
    }

    @PostMapping
    public ResponseEntity<Void> createStation(@RequestBody StationDto stationDto) {
        stationManager.createStation(stationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StationDto>> getAllStations() {
        List<StationDto> stations = stationManager.getAllStations();
        return new ResponseEntity<>(stations, HttpStatus.OK);
    }

    @GetMapping("/{stationIdentifier}")
    public ResponseEntity<StationDto> getStation(@PathVariable String stationIdentifier) {
        Optional<StationDto> station = stationManager.getStation(stationIdentifier);
        return station.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{stationIdentifier}/port/{portIdentifier}")
    public ResponseEntity<ChargingPortDto> getChargingPort(@PathVariable String stationIdentifier, @PathVariable String portIdentifier) {
        Optional<ChargingPortDto> chargingPort = stationManager.getChargingPort(stationIdentifier, portIdentifier);
        return chargingPort.map(port -> new ResponseEntity<>(port, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{stationIdentifier}/port/{portIdentifier}")
    public ResponseEntity<Void> updateChargingPortStatus(@PathVariable String stationIdentifier, @PathVariable String portIdentifier, @RequestBody ChargingPortStatusDto statusDto) {
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
