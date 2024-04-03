package it.ziopagnotta.api.kit;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameKitManager {
    private final HashMap<String, GameKit> gameKits;

    public GameKitManager() {
        gameKits = new HashMap<>();
    }

    public Map<String, GameKit> getGameKits() {
        return Collections.unmodifiableMap(gameKits);
    }

    public boolean exists(String identifier) {
        return gameKits.containsKey(identifier);
    }

    public void add(@NonNull GameKit gameKit) {
        if(exists(gameKit.getIdentifier()))
            throw new UnsupportedOperationException("Cannot add a new kit, identifier already exists: " + gameKit.getIdentifier());

        gameKits.put(gameKit.getIdentifier(), gameKit);
    }

    public void remove(String identifier) {
        gameKits.remove(identifier);
    }

    @Nullable
    public GameKit get(String identifier) {
        return gameKits.getOrDefault(identifier, null);
    }
}
