package tech.henskens.sessionservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.henskens.sessionservice.dto.car.CarDto;
import tech.henskens.sessionservice.manager.car.ICarManager;

@RestController
@RequestMapping({"/car"})
public class CarController {
    private final ICarManager carManager;

    public CarController(ICarManager carManager) {
        this.carManager = carManager;
    }

    @PostMapping
    public CarDto createCar(@RequestBody CarDto carDto) {
        return this.carManager.createCar(carDto);
    }
}

