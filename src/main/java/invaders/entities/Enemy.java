package invaders.entities;

import invaders.GameObject;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;
import invaders.physics.BoxCollider;

public class Enemy implements Renderable, GameObject {
    private Vector2D position;
    private Image image;
    private BoxCollider collider;  // Add this line
    private ProjectileFactory projectileFactory;

    private Enemy(ProjectileFactory projectileFactory) {
        this.projectileFactory = projectileFactory;
    }
    

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public double getWidth() {
        // Return the width of the enemy image
        return image.getWidth();
    }

    @Override
    public double getHeight() {
        // Return the height of the enemy image
        return image.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Renderable.Layer getLayer() {
        return Renderable.Layer.FOREGROUND;
    }

    @Override
    public void start() {
        // Initialization logic for the enemy
    }

    @Override
    public void update() {
        // Update logic for the enemy
    }

    public Projectile shoot() {
        // Adjust the starting position of the projectile to appear just below the enemy
        Vector2D projectilePosition = new Vector2D(position.getX() + getWidth() / 2, position.getY() + getHeight() + 1);
        return projectileFactory.createProjectile(projectilePosition);
    }    

    public BoxCollider getCollider() {
        if (collider == null) {
            collider = new BoxCollider(getWidth(), getHeight(), getPosition());
        }
        return collider;
    }

    public static class EnemyBuilder {
        private ProjectileFactory projectileFactory;
        private Vector2D position;
        private Image image;

        public EnemyBuilder setPosition(Vector2D position) {
            this.position = position;
            return this;
        }

        public EnemyBuilder setImage(String imagePath, double width, double height) {
            this.image = new Image(getClass().getResource(imagePath).toString(), width, height, true, true);
            return this;
        }
        
        public Enemy build() {
            Enemy enemy = new Enemy(this.projectileFactory);
            enemy.position = this.position;
            enemy.image = this.image;
            return enemy;
        }
        

        public EnemyBuilder setProjectileFactory(ProjectileFactory projectileFactory) {
            this.projectileFactory = projectileFactory;
            return this;
        }
    }
}
