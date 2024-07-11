package tech.henskens.sessionservice.mapper.session;

import java.time.LocalDateTime;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import tech.henskens.sessionservice.dto.car.CarDto;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.model.Car;
import tech.henskens.sessionservice.model.Session;

@Component
public class SessionMapper {
    public SessionMapper() {
    }

    public SessionDto toSessionDto(Session session) {
        SessionDto dto = new SessionDto();
        dto.setCar(toCarDto(session.getCar()));
        dto.setUserId(session.getUser().getId());
        dto.setKwh(session.getKwh());
        dto.setStationIdentifier(session.getStationIdentifier());
        dto.setPortIdentifier(session.getPortIdentifier());
        dto.setStarted(session.getStarted());
        dto.setEnded(session.getEnded());
        return dto;
    }

    private CarDto toCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setLicensePlate(car.getLicensePlate());
        return carDto;
    }

    public Session toSession(SessionDto dto) {
        Session session = new Session();
        session.setKwh(dto.getKwh());
        session.setStationIdentifier(dto.getStationIdentifier());
        session.setPortIdentifier(dto.getPortIdentifier());
        session.setStarted(dto.getStarted());
        session.setEnded(dto.getEnded());
        return session;
    }

    public void updateSessionFromDto(SessionDto dto, Session session) {
        if (dto.getKwh() != null) {
            session.setKwh(dto.getKwh());
        }
        if (dto.getStationIdentifier() != null) {
            session.setStationIdentifier(dto.getStationIdentifier());
        }
        if (dto.getPortIdentifier() != null) {
            session.setPortIdentifier(dto.getPortIdentifier());
        }
        if (dto.getStarted() != null) {
            session.setStarted(dto.getStarted());
        }
        if (dto.getEnded() != null) {
            session.setEnded(dto.getEnded());
        }
    }
}

