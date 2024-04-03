package it.ziopagnotta.api.game;

import it.ziopagnotta.api.team.GameTeam;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {
    private final HashMap<String, Game> games;

    public GameManager() {
        games = new HashMap<>();
    }

    public Map<String, Game> getGames() {
        return Collections.unmodifiableMap(games);
    }

    public boolean exists(String identifier) {
        return games.containsKey(identifier);
    }

    public void register(@NonNull Game game) {
        if(exists(game.getIdentifier()))
            throw new UnsupportedOperationException("Cannot register a new Game, identifier already exists: " + game.getIdentifier());

        games.put(game.getIdentifier(), game);
    }

    public void remove(String identifier) {
        games.remove(identifier);
    }

    @Nullable
    public Game getById(@NonNull String identifier) {
        return games.getOrDefault(identifier, null);
    }

    public Game getByUUID(UUID uuid) {
        return games.values().stream()
                .filter(game -> {
                    GameTeam gameTeam = game.getGameTeamManager().getByUUID(uuid);
                    return gameTeam != null && gameTeam.existsPlayer(uuid);
                })
                .findFirst()
                .orElse(null);
    }
}
