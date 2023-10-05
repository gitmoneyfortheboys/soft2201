package invaders.entities;

import invaders.physics.Vector2D;

public class EnemyProjectileFactory extends ProjectileFactory {
    @Override
    public Projectile createProjectile(Vector2D position) {
        return new Projectile(position);
    }
}
