package invaders.entities;

import invaders.physics.Vector2D;

public abstract class ProjectileFactory {
    protected ProjectileBehavior behavior;

    public ProjectileFactory(ProjectileBehavior behavior) {
        this.behavior = behavior;
    }

    public abstract Projectile createProjectile(Vector2D position);
}
