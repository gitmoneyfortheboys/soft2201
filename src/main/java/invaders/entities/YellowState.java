package invaders.entities;

public class YellowState implements BunkerState {
    @Override
    public void takeDamage(Bunker bunker) {
        bunker.setState(new RedState());
    }

    @Override
    public String getColor() {
        System.out.println("State has turned Yellow");
        return "Yellow";
    }

    @Override
    public void adjustColor(Bunker bunker) {
        bunker.getColorAdjust().setHue(0.15);  // Adjust to yellow
    }

}