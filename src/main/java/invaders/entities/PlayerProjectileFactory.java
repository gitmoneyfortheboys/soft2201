package invaders.entities;

import invaders.entities.Projectile.ProjectileType;
import invaders.physics.Vector2D;

public class PlayerProjectileFactory extends ProjectileFactory {
    @Override
    public Projectile createProjectile(Vector2D position) {
        return new Projectile(position, ProjectileType.PLAYER);
    }
}