package it.ziopagnotta.api.game.phase;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GamePhaseManager {
    private final LinkedHashMap<String, GamePhase> gamePhases;
    @Getter
    private GamePhase currentPhase;

    public GamePhaseManager(@Nullable LinkedHashMap<String, GamePhase> gamePhases) {
        this.gamePhases = gamePhases == null ? new LinkedHashMap<>() : gamePhases;
        this.currentPhase = (gamePhases == null || gamePhases.isEmpty()) ? null : gamePhases.values().iterator().next();
    }

    public GamePhaseManager() {
        this(null);
    }

    public boolean exists(String identifier) {
        return gamePhases.containsKey(identifier);
    }

    public void add(String identifier, Duration duration, Runnable action, Runnable leaveAction) {
        if(exists(identifier))
            throw new UnsupportedOperationException("Cannot add a new game phase, identifier already exists: " + identifier);


        gamePhases.put(identifier, new GamePhaseImpl(identifier, duration, action, leaveAction));
    }

    public void remove(String identifier) {
        gamePhases.remove(identifier);
    }

    @Nullable
    public GamePhase get(@NonNull String identifier) {
        return gamePhases.getOrDefault(identifier, null);
    }

    public void setCurrentPhase(@NonNull GamePhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Map<String, GamePhase> getPhases() {
        return Collections.unmodifiableMap(gamePhases);
    }

    @Nullable
    public GamePhase next() {
        if(currentPhase == null) return null;

        return gamePhases.values().stream()
                .dropWhile(gamePhase -> gamePhase.getIdentifier().equals(currentPhase.getIdentifier()))
                .skip(1)
                .findFirst()
                .orElse(getPhase(PhaseType.LAST));
    }

    @Nullable
    public GamePhase previous() {
        if(currentPhase == null) return null;

        return gamePhases.values().stream()
                .takeWhile(gamePhase -> gamePhase.getIdentifier().equals(currentPhase.getIdentifier()))
                .reduce((first, second) -> second)
                .orElse(getPhase(PhaseType.FIRST));
    }

    @Nullable
    public GamePhase getPhase(@NonNull PhaseType type) {
        return switch (type) {
            case FIRST -> gamePhases.values().stream()
                        .findFirst().orElse(null);
            case NEXT -> next();
            case CURRENT -> currentPhase;
            case PREVIOUS -> previous();
            case LAST -> gamePhases.values().stream()
                    .reduce((first, second) -> second).orElse(null);
        };
    }

    @Nullable
    public GamePhase changePhase(@NonNull PhaseType type) {
        GamePhase phase = getPhase(type);

        if(phase == null) {
            return null;
        }

        if(currentPhase != null) {
            currentPhase.onLeaveAction();
        }

        setCurrentPhase(phase);
        phase.onAction();

        return phase;
    }
}
