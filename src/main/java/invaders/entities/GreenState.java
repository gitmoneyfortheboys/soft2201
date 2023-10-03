package invaders.entities;

public class GreenState implements BunkerState {
    @Override
    public void takeDamage(Bunker bunker) {
        System.out.println("Green takedamagemethod");
        bunker.setState(new YellowState());
        bunker.adjustColor();        
    }

    @Override
    public String getColor() {
        System.out.println("State has turned Green");
        return "Green";
    }

    @Override
    public void adjustColor(Bunker bunker) {
        bunker.getColorAdjust().setHue(0);  // No adjustment for green
    }

} 
    
