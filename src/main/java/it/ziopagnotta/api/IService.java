package it.ziopagnotta.api;

public sealed interface IService permits GameApi {
    void start();
    void stop();
}
