package tech.henskens.sessionservice.model;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {
    ADMIN(Set.of(Permission.READ, Permission.WRITE, Permission.DELETE)),
    VIEWER(Set.of(Permission.READ)),
    INTERACTOR(Set.of(Permission.READ, Permission.WRITE));

    private final Set permissions;

    private Role(Set permissions) {
        this.permissions = permissions;
    }

}
