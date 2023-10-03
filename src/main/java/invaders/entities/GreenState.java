package invaders.entities;

public class GreenState implements BunkerState {
    @Override
    public void takeDamage(Bunker bunker) {
        bunker.setState(new YellowState());
    }

    @Override
    public String getColor() {
        return "Green";
    }
} 
    
