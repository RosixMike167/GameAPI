package it.ziopagnotta.api.game.phase;

import java.time.Duration;
import java.time.Instant;

public sealed interface GamePhase permits GamePhaseImpl {
    String getIdentifier();
    Runnable getAction();
    Runnable getLeaveAction();

    Duration getDuration();
    Instant getStartInstant();

    void onAction();
    void onLeaveAction();
    void setStartInstant(Instant startInstant);
}
