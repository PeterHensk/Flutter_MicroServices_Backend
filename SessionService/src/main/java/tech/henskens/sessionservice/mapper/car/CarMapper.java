package tech.henskens.sessionservice.mapper.car;

import org.springframework.stereotype.Component;
import tech.henskens.sessionservice.dto.car.CarDto;
import tech.henskens.sessionservice.model.Car;

@Component
public class CarMapper {
    public CarMapper() {
    }

    public CarDto carToCarDto(Car car) {
        CarDto dto = new CarDto();
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setLicensePlate(car.getLicensePlate());
        return dto;
    }

    public Car carDtoToCar(CarDto dto) {
        Car car = new Car();
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setLicensePlate(dto.getLicensePlate());
        return car;
    }
}

