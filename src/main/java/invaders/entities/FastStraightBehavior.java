package invaders.entities;

public class FastStraightBehavior implements ProjectileBehavior {
    @Override
    public void move(Projectile projectile) {
        projectile.getPosition().setY(projectile.getPosition().getY() + 2);
    }
}
