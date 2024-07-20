package tech.henskens.sessionservice.manager.car;

import org.springframework.http.ResponseEntity;
import tech.henskens.sessionservice.dto.car.CarDto;

public interface ICarManager {
    ResponseEntity<CarDto> createCar(CarDto carDto);
}
