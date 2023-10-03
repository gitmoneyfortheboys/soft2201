package invaders.entities;

public class RedState implements BunkerState {
    @Override
    public void takeDamage(Bunker bunker) {
        System.out.println("Red takedamagemethod");
        bunker.markForDelete(); // Bunker is destroyed after turning red and taking another hit
    }

    @Override
    public String getColor() {
        System.out.println("Red");
        return "Red";
    }
}