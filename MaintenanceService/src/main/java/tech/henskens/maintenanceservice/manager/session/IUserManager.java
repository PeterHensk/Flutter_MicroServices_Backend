package tech.henskens.maintenanceservice.manager.session;

public interface IUserManager {
    void authenticatedUser(String bearerToken);
}
