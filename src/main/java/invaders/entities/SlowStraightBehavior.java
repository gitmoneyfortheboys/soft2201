package invaders.entities;

public class SlowStraightBehavior implements ProjectileBehavior {
    @Override
    public void move(Projectile projectile) {
        projectile.getPosition().setY(projectile.getPosition().getY() + 1);
    }
}
