package it.ziopagnotta.api.game.phase;

public sealed interface GamePhase permits GamePhaseImpl {
    String getIdentifier();
    Runnable getAction();
    Runnable getLeaveAction();

    void onAction();
    void onLeaveAction();
}
