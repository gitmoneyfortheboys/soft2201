package invaders.entities;

import invaders.physics.Vector2D;

abstract class ProjectileFactory {
    public abstract Projectile createProjectile(Vector2D position);
}
