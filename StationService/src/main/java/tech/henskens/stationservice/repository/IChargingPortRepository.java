package tech.henskens.stationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tech.henskens.stationservice.model.ChargingPort;

@Repository
public interface IChargingPortRepository extends MongoRepository<ChargingPort, String> {
}