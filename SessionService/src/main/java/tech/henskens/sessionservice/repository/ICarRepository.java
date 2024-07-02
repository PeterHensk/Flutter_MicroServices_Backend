package tech.henskens.sessionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.henskens.sessionservice.model.Car;

public interface ICarRepository extends JpaRepository<Car, Long> {
}