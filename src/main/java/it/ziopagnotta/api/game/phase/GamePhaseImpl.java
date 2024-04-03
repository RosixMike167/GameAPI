package it.ziopagnotta.api.game.phase;

import it.ziopagnotta.api.property.PropertyHolder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter @AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class GamePhaseImpl extends PropertyHolder implements GamePhase {
    private final String identifier;
    private final Runnable action;
    private final Runnable leaveAction;

    public void onAction() {
        action.run();
    }

    public void onLeaveAction() {
        leaveAction.run();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePhaseImpl gameTeam = (GamePhaseImpl) o;
        return Objects.equals(this.identifier, gameTeam.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.identifier);
    }
}
