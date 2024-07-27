package tech.henskens.stationservice.manager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.henskens.stationservice.dto.ChargingPortDto;
import tech.henskens.stationservice.dto.ChargingPortStatusDto;
import tech.henskens.stationservice.dto.StationDto;
import tech.henskens.stationservice.mapper.StationMapper;
import tech.henskens.stationservice.model.ChargingPort;
import tech.henskens.stationservice.model.Station;
import tech.henskens.stationservice.repository.IStationRepository;

@Service
public class StationManager implements IStationManager {
    private final IStationRepository stationRepository;
    private final IS3Manager s3Manager;
    private final StationMapper stationMapper;

    public StationManager(IStationRepository stationRepository, IS3Manager s3Manager, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.s3Manager = s3Manager;
        this.stationMapper = stationMapper;
    }

    public Optional<ChargingPort> findChargingPort(String stationIdentifier, String portIdentifier) {
        Optional<Station> station = this.stationRepository.findByStationIdentifier(stationIdentifier);
        return station.flatMap(s -> s.getChargingPorts().stream()
                .filter(port -> port.getPortIdentifier().equals(portIdentifier))
                .findFirst());
    }


    public void createStation(StationDto stationDto) {
        UUID locationId = UUID.randomUUID();
        String imageId = this.s3Manager.uploadImage(stationDto.getImage(), stationDto.getContentType());
        stationDto.getLocation().setLocationId(String.valueOf(locationId));
        stationDto.setImageId(String.valueOf(imageId));
        Station station = this.stationMapper.dtoToStation(stationDto);
        this.stationRepository.save(station);
    }

    public Page<StationDto> getAllStations(Pageable pageable) {
        Page<Station> stations = this.stationRepository.findAll(pageable);
        return stations.map(this.stationMapper::stationToDto);
    }
    
    public Optional<ChargingPortDto> getChargingPort(String stationIdentifier, String portIdentifier) {
        Optional<ChargingPort> chargingPort = this.findChargingPort(stationIdentifier, portIdentifier);
        StationMapper mapper = this.stationMapper;
        Objects.requireNonNull(mapper);
        return chargingPort.map(mapper::toChargingPortDto);
    }


    public void updateChargingPortStatus(String stationIdentifier, String portIdentifier, ChargingPortStatusDto statusDto) {
        this.stationRepository.updateChargingPortStatus(stationIdentifier, portIdentifier, statusDto.getStatus());
    }

    public Optional<StationDto> getStation(String stationIdentifier) {
        Optional<Station> station = this.stationRepository.findByStationIdentifier(stationIdentifier);
        StationMapper mapper = this.stationMapper;
        Objects.requireNonNull(mapper);
        return station.map(mapper::stationToDto);
    }
}

