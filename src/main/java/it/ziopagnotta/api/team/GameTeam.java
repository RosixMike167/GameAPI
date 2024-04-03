package it.ziopagnotta.api.team;

import it.ziopagnotta.api.property.PropertyHolder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.function.BiConsumer;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GameTeam extends PropertyHolder {
    @Getter private final String identifier;
    private final List<UUID> players;
    private final BiConsumer<List<UUID>, UUID> onJoinAction;

    public boolean existsPlayer(UUID uuid) {
        return players.contains(uuid);
    }

    public boolean addPlayer(UUID uuid, boolean performAction) {
        if(existsPlayer(uuid)) {
            return false;
        }

        if(performAction)
            onPlayerJoinAction(uuid);

        return this.players.add(uuid);
    }

    public void removePlayer(UUID uuid) {
        this.players.remove(uuid);
    }

    public List<UUID> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public void onPlayerJoinAction(UUID uuid) {
        onJoinAction.accept(players, uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameTeam gameTeam = (GameTeam) o;
        return Objects.equals(this.identifier, gameTeam.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.identifier);
    }

    public static class Builder {
        private final String identifier;
        private List<UUID> players;
        private BiConsumer<List<UUID>, UUID> onJoinAction;

        public Builder(String identifier) {
            this.identifier = identifier;
        }

        @Nullable
        public Builder players(List<UUID> players) {
            this.players = players;
            return this;
        }

        public Builder players(UUID... uuids) {
            if(players == null)
                players = new ArrayList<>();

            players.addAll(List.of(uuids));
            return this;
        }

        public Builder onJoinAction(BiConsumer<List<UUID>, UUID> onJoinAction) {
            if(onJoinAction == null) {
                throw new NullPointerException("GameTeam.Builder -> onJoinAction cannot be null");
            }

            this.onJoinAction = onJoinAction;
            return this;
        }

        public GameTeam build() {
            if(players == null) players = new ArrayList<>();

            return new GameTeam(identifier, players, onJoinAction);
        }
    }
}
