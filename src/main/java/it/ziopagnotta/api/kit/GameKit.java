package it.ziopagnotta.api.kit;

import it.ziopagnotta.api.property.PropertyHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter @AllArgsConstructor
public class GameKit extends PropertyHolder {
    private final String identifier;
    private final byte[] contents;
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameKit kit = (GameKit) o;
        return Objects.equals(this.identifier, kit.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.identifier);
    }
}
