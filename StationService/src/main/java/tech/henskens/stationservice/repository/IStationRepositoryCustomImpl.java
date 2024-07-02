package tech.henskens.stationservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import tech.henskens.stationservice.model.ChargingPortStatus;
import tech.henskens.stationservice.model.Station;

@Repository
public class IStationRepositoryCustomImpl implements IStationRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

    public IStationRepositoryCustomImpl() {
    }

    public void updateChargingPortStatus(String stationIdentifier, String portIdentifier, ChargingPortStatus status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("stationIdentifier").is(stationIdentifier).and("chargingPorts.portIdentifier").is(portIdentifier));
        Update update = new Update();
        update.set("chargingPorts.$.status", status);
        this.mongoTemplate.updateFirst(query, update, Station.class);
    }
}
