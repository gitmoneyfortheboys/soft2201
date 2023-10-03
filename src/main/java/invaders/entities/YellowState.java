package invaders.entities;

public class YellowState implements BunkerState {
    @Override
    public void takeDamage(Bunker bunker) {
        System.out.println("yellow takedamagemethod");
        bunker.setState(new RedState());
    }

    @Override
    public String getColor() {
        System.out.println("Yellow");
        return "Yellow";
    }
}