package it.ziopagnotta.api.game.phase;

import it.ziopagnotta.api.property.PropertyHolder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Getter
public final class GamePhaseImpl extends PropertyHolder implements GamePhase {
    private final String identifier;
    private final Runnable action;
    private final Runnable leaveAction;
    private final Duration duration;
    @Setter private Instant startInstant;

    public GamePhaseImpl(String identifier, Duration duration, Runnable action, Runnable leaveAction) {
        this.identifier = identifier;
        this.duration = duration;
        this.action = action;
        this.leaveAction = leaveAction;
    }

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
