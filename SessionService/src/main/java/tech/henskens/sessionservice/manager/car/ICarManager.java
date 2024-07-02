package tech.henskens.sessionservice.manager.car;

import tech.henskens.sessionservice.dto.car.CarDto;

public interface ICarManager {
    CarDto createCar(CarDto carDto);
}
