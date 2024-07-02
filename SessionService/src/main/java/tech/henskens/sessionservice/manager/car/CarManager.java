package tech.henskens.sessionservice.manager.car;

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

    public CarDto createCar(CarDto carDto) {
        Car car = this.carMapper.carDtoToCar(carDto);
        car = (Car)this.carRepository.save(car);
        return this.carMapper.carToCarDto(car);
    }
}
