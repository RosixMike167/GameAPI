package it.ziopagnotta.api.kit;

public interface GameKit {
    String getIdentifier();
    byte[] getContents();
    boolean isEnabled();

    default <T> void apply(T who) {
        //empty by default
    }
}
