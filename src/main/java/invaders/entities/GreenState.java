package invaders.entities;

public class GreenState implements BunkerState {
    @Override
    public void takeDamage(Bunker bunker) {
        System.out.println("Green takedamagemethod");
        bunker.setState(new YellowState());
    }

    @Override
    public String getColor() {
        System.out.println("Green");
        return "Green";
    }
} 
    
