package tech.henskens.sessionservice.manager.user;

import tech.henskens.sessionservice.model.Permission;
import tech.henskens.sessionservice.model.User;

public interface IActionStateManager {
    boolean hasPermission(User user, Permission permission);
}