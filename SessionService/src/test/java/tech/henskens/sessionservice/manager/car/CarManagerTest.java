package tech.henskens.sessionservice.manager.car;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.henskens.sessionservice.dto.car.CarDto;
import tech.henskens.sessionservice.mapper.car.CarMapper;
import tech.henskens.sessionservice.model.Car;
import tech.henskens.sessionservice.repository.ICarRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarManagerTest {

    @Mock
    private CarMapper carMapper;

    @Mock
    private ICarRepository carRepository;

    @InjectMocks
    private CarManager carManagerImpl;

    @Test
    public void testCreateCar() {
        CarDto carDto = new CarDto();
        carDto.setLicensePlate("ABC-123");

        Car car = new Car();
        car.setLicensePlate("ABC-123");

        when(carMapper.carDtoToCar(carDto)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(carMapper.carToCarDto(car)).thenReturn(carDto);

        ResponseEntity<CarDto> response = carManagerImpl.createCar(carDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(carDto, response.getBody());
    }
}