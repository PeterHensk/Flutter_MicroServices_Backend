package tech.henskens.sessionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.henskens.sessionservice.model.Car;

import java.util.Optional;

public interface ICarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByLicensePlate(String licensePlate);
}