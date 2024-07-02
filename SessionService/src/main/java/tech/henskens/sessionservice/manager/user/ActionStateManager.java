package tech.henskens.sessionservice.manager.user;

import tech.henskens.sessionservice.model.Permission;
import tech.henskens.sessionservice.model.User;

public class ActionStateManager implements IActionStateManager {
    public ActionStateManager() {
    }

    public boolean hasPermission(User user, Permission permission) {
        return user.getRole().getPermissions().contains(permission);
    }
}
