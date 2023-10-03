package invaders.entities;

public class RedState implements BunkerState {
    @Override
    public void takeDamage(Bunker bunker) {
        bunker.markForDelete(); // Bunker is destroyed after turning red and taking another hit
    }

    @Override
    public String getColor() {
        System.out.println("State has turned red");
        return "Red";
    }

    @Override
    public void adjustColor(Bunker bunker) {
        bunker.getColorAdjust().setHue(0.5);  // Adjust to red
    }

}