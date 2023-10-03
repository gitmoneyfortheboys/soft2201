package invaders.entities;

public class YellowState implements BunkerState {
    @Override
    public void takeDamage(Bunker bunker) {
        bunker.setState(new RedState());
    }

    @Override
    public String getColor() {
        return "Yellow";
    }
}