package tech.henskens.stationservice.manager.Session;

import tech.henskens.stationservice.model.User;

public interface IUserManager {
    void authenticatedUser(String bearerToken);
}
