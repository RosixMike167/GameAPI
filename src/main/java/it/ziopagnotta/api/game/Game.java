package it.ziopagnotta.api.game;

import it.ziopagnotta.api.game.phase.GamePhaseManager;
import it.ziopagnotta.api.game.phase.PhaseType;
import it.ziopagnotta.api.kit.GameKitManager;
import it.ziopagnotta.api.property.PropertyHolder;
import it.ziopagnotta.api.team.GameTeam;
import it.ziopagnotta.api.team.GameTeamManager;
import it.ziopagnotta.api.userdata.UserDataHolder;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Game extends PropertyHolder {
    private final String identifier;
    @Setter private boolean enabled;

    private final UserDataHolder userDataHolder;
    private final GameTeamManager gameTeamManager;
    private final GamePhaseManager gamePhaseManager;
    private final GameKitManager gameKitManager;

    @Setter private Runnable startAction, stopAction;

    protected Game(String identifier,
                   boolean enabled,
                   UserDataHolder userDataHolder,
                   GameTeamManager gameTeamManager,
                   GamePhaseManager gamePhaseManager,
                   GameKitManager gameKitManager,
                   Runnable startAction,
                   Runnable stopAction) {
        this.identifier = identifier;
        this.enabled = enabled;

        this.userDataHolder = userDataHolder;
        this.gameTeamManager = gameTeamManager;
        this.gamePhaseManager = gamePhaseManager;
        this.gameKitManager = gameKitManager;

        this.startAction = startAction;
        this.stopAction = stopAction;
    }

    public void start() {
        if(startAction == null) {
            return;
        }

        startAction.run();
        gamePhaseManager.changePhase(PhaseType.FIRST);
    }

    public void stop() {
        if(stopAction == null) {
            return;
        }

        gamePhaseManager.getCurrentPhase().onLeaveAction();
        stopAction.run();
    }

    public List<UUID> getAllTeamsPlayers() {
        List<UUID> l = new ArrayList<>();

        for(GameTeam gameTeam : gameTeamManager.getGameTeams()) {
            l.addAll(gameTeam.getPlayers());
        }

        return l;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(this.identifier, game.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.identifier);
    }

    public static class Builder {
        private final String identifier;
        private boolean enabled;

        private Runnable startAction, stopAction;
        private UserDataHolder userDataHolder;
        private GameTeamManager gameTeamManager;
        private GamePhaseManager gamePhaseManager;
        private GameKitManager gameKitManager;

        public Builder(String identifier) {
            this.identifier = identifier;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder userData(@Nullable UserDataHolder userDataHolder) {
            this.userDataHolder = userDataHolder;
            return this;
        }

        public Builder teams(@Nullable GameTeamManager gameTeamManager) {
            this.gameTeamManager = gameTeamManager;
            return this;
        }

        public Builder phases(@Nullable GamePhaseManager gamePhaseManager) {
            this.gamePhaseManager = gamePhaseManager;
            return this;
        }

        public Builder kits(@Nullable GameKitManager gameKitManager) {
            this.gameKitManager = gameKitManager;
            return this;
        }

        public Builder startAction(@Nullable Runnable startAction) {
            this.startAction = startAction;
            return this;
        }

        public Builder stopAction(@Nullable Runnable stopAction) {
            this.stopAction = stopAction;
            return this;
        }

        public Game build() {
            if(userDataHolder == null) userDataHolder = new UserDataHolder();
            if(gameTeamManager == null) gameTeamManager = new GameTeamManager();
            if(gamePhaseManager == null) gamePhaseManager = new GamePhaseManager();
            if(gameKitManager == null) gameKitManager = new GameKitManager();

            return new Game(identifier,
                    enabled,
                    userDataHolder,
                    gameTeamManager,
                    gamePhaseManager,
                    gameKitManager,
                    startAction,
                    stopAction);
        }
    }
}
