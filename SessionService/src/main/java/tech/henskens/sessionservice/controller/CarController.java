package tech.henskens.sessionservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.henskens.sessionservice.dto.car.CarDto;
import tech.henskens.sessionservice.manager.car.ICarManager;
import tech.henskens.sessionservice.manager.user.IUserManager;

@RestController
@RequestMapping({"/car"})
public class CarController {
    private final ICarManager carManager;
    private final IUserManager userManager;

    public CarController(ICarManager carManager, IUserManager userManager) {
        this.carManager = carManager;
        this.userManager = userManager;
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto, @RequestHeader("Authorization") String bearerToken) {
        this.userManager.authenticatedUser(bearerToken);
        return this.carManager.createCar(carDto);
    }
}

