package it.ziopagnotta.api;

import it.ziopagnotta.api.game.Game;
import it.ziopagnotta.api.game.GameManager;
import it.ziopagnotta.api.game.GameObserver;
import lombok.Getter;

@Getter
public final class GameApi implements IService {
    @Getter private static GameApi instance;
    private final GameManager gameManager;
    private final GameObserver gameObserver;

    public GameApi() {
        gameManager = new GameManager();
        gameObserver = new GameObserver();
    }

    @Override
    public void start() {
        instance = this;
        gameObserver.start();

        System.out.println("===================");
        System.out.println("    API ENABLED");
        System.out.println("===================");
    }

    @Override
    public void stop() {
        System.out.println("Disabling API, stopping all games...");

        gameManager.getGames().values().forEach(Game::stop);
        gameObserver.stop();

        instance = null;

        System.out.println("===================");
        System.out.println("   API DISABLED");
        System.out.println("===================");
    }
}
