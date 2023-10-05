package invaders.entities;

import invaders.entities.Projectile.ProjectileType;
import invaders.physics.Vector2D;

public class EnemyProjectileFactory extends ProjectileFactory {
    public EnemyProjectileFactory(ProjectileBehavior behavior) {
        super(behavior);
    }

    @Override
    public Projectile createProjectile(Vector2D position) {
        return new Projectile(position, ProjectileType.ENEMY, behavior);
    }
}