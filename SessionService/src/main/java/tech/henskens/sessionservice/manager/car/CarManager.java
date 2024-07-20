package tech.henskens.sessionservice.manager.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.henskens.sessionservice.dto.car.CarDto;
import tech.henskens.sessionservice.mapper.car.CarMapper;
import tech.henskens.sessionservice.model.Car;
import tech.henskens.sessionservice.repository.ICarRepository;

@Service
public class CarManager implements ICarManager {
    private final ICarRepository carRepository;
    private final CarMapper carMapper;

    public CarManager(ICarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public ResponseEntity<CarDto> createCar(CarDto carDto) {
        Car car = carMapper.carDtoToCar(carDto);
        car = carRepository.save(car);
        CarDto createdCarDto = carMapper.carToCarDto(car);
        return new ResponseEntity<>(createdCarDto, HttpStatus.CREATED);
    }
}
