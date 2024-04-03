package it.ziopagnotta.api;

import it.ziopagnotta.api.game.Game;
import it.ziopagnotta.api.game.GameManager;
import lombok.Getter;

@Getter
public final class GameApi implements IService {

    private final GameManager gameManager;

    public GameApi() {
        gameManager = new GameManager();
    }

    @Override
    public void start() {
        System.out.println("===================");
        System.out.println("    API ENABLED");
        System.out.println("===================");
    }

    @Override
    public void stop() {
        System.out.println("Disabling API, stopping all games...");

        gameManager.getGames().values().forEach(Game::stop);

        System.out.println("===================");
        System.out.println("   API DISABLED");
        System.out.println("===================");
    }
}
