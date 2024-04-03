package it.ziopagnotta.api.userdata;

import it.ziopagnotta.api.property.PropertyHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Getter @RequiredArgsConstructor
public class UserData extends PropertyHolder {
    private final UUID uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(this.uuid, userData.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid);
    }
}
