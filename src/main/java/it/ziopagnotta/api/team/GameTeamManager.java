package it.ziopagnotta.api.team;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class GameTeamManager {
    private final List<GameTeam> gameTeams;

    public GameTeamManager() {
        gameTeams = new ArrayList<>();
    }

    public boolean exists(String name) {
        return gameTeams.stream().anyMatch(gameTeam -> gameTeam.getIdentifier().equals(name));
    }

    public void add(@NonNull GameTeam gameTeam) {
        if(exists(gameTeam.getIdentifier()))
            throw new UnsupportedOperationException("Cannot add a new game team, identifier already exists: " + gameTeam.getIdentifier());

        this.gameTeams.add(gameTeam);
    }

    public void remove(String name) {
        gameTeams.removeIf(gameTeam -> gameTeam.getIdentifier().equals(name));
    }

    @Nullable
    public GameTeam getByName(String name) {
        return gameTeams.stream()
                .filter(gameTeam -> gameTeam.getIdentifier().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public GameTeam getByUUID(UUID uuid) {
        return gameTeams.stream()
                .filter(gameTeam -> gameTeam.existsPlayer(uuid))
                .findFirst()
                .orElse(null);
    }

    public List<GameTeam> getGameTeams() {
        return Collections.unmodifiableList(gameTeams);
    }
}
