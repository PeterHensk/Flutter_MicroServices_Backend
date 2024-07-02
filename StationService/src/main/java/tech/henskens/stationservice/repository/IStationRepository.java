package tech.henskens.stationservice.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tech.henskens.stationservice.model.Station;

@Repository
public interface IStationRepository extends MongoRepository<Station, String>, IStationRepositoryCustom {
    Optional<Station> findByStationIdentifier(String stationIdentifier);
}
