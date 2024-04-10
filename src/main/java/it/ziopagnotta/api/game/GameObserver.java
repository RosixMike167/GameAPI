package it.ziopagnotta.api.game;

import it.ziopagnotta.api.GameApi;
import it.ziopagnotta.api.game.Game;
import it.ziopagnotta.api.game.phase.GamePhase;
import it.ziopagnotta.api.game.phase.GamePhaseManager;
import it.ziopagnotta.api.game.phase.PhaseType;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

@Getter
public class GameObserver  {
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    private boolean cancel;

    public GameObserver() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        cancel = false;
    }

    public void start() {
        future = scheduler.scheduleAtFixedRate(this::task, 1L, 20L, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if(future != null) {
            future.cancel(true);
            cancel = true;
        }

        scheduler.shutdown();
        future = null;
        cancel = false;
    }

    private void task() {
        for(Game game : GameApi.getInstance().getGameManager().getGames().values()) {
            if(cancel || !game.isEnabled()) {
                return;
            }

            GamePhaseManager phaseManager = game.getGamePhaseManager();

            long elapsedGlobal = abs(Duration.between(game.getStartInstant(), Instant.now()).toMillis());

            if(elapsedGlobal >= game.getDuration().toMillis()) {
                phaseManager.changePhase(PhaseType.LAST);
                return;
            }

            GamePhase currentPhase = phaseManager.getCurrentPhase();

            long elapsedPhase = abs(Duration.between(currentPhase.getStartInstant(), Instant.now()).toMillis());

            if(elapsedPhase >= currentPhase.getDuration().toMillis()) {
                phaseManager.changePhase(PhaseType.NEXT);
            }
        }
    }
}
