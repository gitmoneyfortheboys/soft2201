package invaders.entities;

public interface BunkerState {
    void takeDamage(Bunker bunker);
    String getColor();
    void adjustColor(Bunker bunker);
}